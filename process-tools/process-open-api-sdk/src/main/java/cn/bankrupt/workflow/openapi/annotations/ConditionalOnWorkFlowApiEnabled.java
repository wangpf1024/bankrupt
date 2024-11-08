package cn.bankrupt.workflow.openapi.annotations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(name = "pansome.workflow.host")
public @interface ConditionalOnWorkFlowApiEnabled {
}
