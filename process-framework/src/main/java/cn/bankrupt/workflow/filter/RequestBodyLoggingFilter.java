package cn.bankrupt.workflow.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RequestBodyLoggingFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(RequestBodyLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        RequestWrapper wrappedRequest = new RequestWrapper(httpServletRequest);
        filterChain.doFilter(wrappedRequest, servletResponse);
    }

    /**
     * 包装类
     */
    private static class RequestWrapper  extends HttpServletRequestWrapper {

        private final byte[] body;

        public String getBody() {
            return new String(body, StandardCharsets.UTF_8);
        }

        public RequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            try (InputStream inputStream = request.getInputStream();
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                body = byteArrayOutputStream.toByteArray();
                System.out.println("Request body: " + new String(body, StandardCharsets.UTF_8));
                System.out.println("\n");
            }
        }

        @Override
        public ServletInputStream getInputStream() {
            return new ServletInputStream() {
                private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {}

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }
    }

}


