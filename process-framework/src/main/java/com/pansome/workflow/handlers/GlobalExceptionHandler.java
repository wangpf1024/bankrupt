package com.pansome.workflow.handlers;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> errorDetails = Map.of(
                "error", "Method Not Allowed",
                "message", String.format("HTTP method %s is not supported for this endpoint.", ex.getMethod()),
                "supportedMethods", ex.getSupportedHttpMethods()
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(JSONUtil.toJsonPrettyStr(errorDetails));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, Object> errorDetails = Map.of(
                "message", String.format("Required request body is missing", ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(JSONUtil.toJsonPrettyStr(errorDetails));
    }

}
