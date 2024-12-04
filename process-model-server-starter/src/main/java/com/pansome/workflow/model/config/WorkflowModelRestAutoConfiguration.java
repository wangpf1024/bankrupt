package com.pansome.workflow.model.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkflowModelRestAutoConfiguration {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {

        // ASCII艺术字的文本
        String[] asciiArt = {
                "   _    _    _    _    _    _ ",
                "  / \\  / \\  / \\  / \\  / \\",
                "  \\_/  \\_/  \\_/  \\_/  \\_/  process-model-server-starter 1.0.0"
        };
        // 输出ASCII艺术字
        for (String line : asciiArt) {
            System.out.println(line);
        }
        System.out.println();
    }
}


