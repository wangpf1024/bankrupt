package cn.bankrupt.workflow.openapi.config;

import cn.bankrupt.workflow.openapi.annotations.ConditionalOnWorkFlowApiEnabled;
import cn.bankrupt.workflow.openapi.WorkFlowOpenApiClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;

/**
 *  open api 配置
 * 
 * @author workflow
 */
@Configuration
public class WorkFlowOpenApiConfig{

    @PostConstruct
    public void init() {
        // ASCII艺术字的文本
        String[] asciiArt = {
                "   _    _    _    _    _    _ ",
                "  / \\  / \\  / \\  / \\  / \\",
                "  \\_/  \\_/  \\_/  \\_/  \\_/  pansome-work-flow-open-api 1.0.0"
        };
        // 打印ASCII艺术字
        for (String line : asciiArt) {
            System.out.println(line);
        }
        System.out.println();
    }

    @Bean
    @ConditionalOnClass
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnWorkFlowApiEnabled
    public WorkFlowOpenApiClient client(){
        return new WorkFlowOpenApiClient();
    }

}
