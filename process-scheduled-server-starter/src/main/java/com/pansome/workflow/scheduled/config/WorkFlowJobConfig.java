package com.pansome.workflow.scheduled.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 *  job api 配置
 * 
 * @author workflow
 */
@Configuration
@EnableScheduling
public class WorkFlowJobConfig {

    @PostConstruct
    public void init() {
        // ASCII艺术字的文本
        String[] asciiArt = {
                "   _    _    _    _    _    _ ",
                "  / \\  / \\  / \\  / \\  / \\",
                "  \\_/  \\_/  \\_/  \\_/  \\_/  pansome-scheduled-job 1.0.0"
        };
        // 打印ASCII艺术字
        for (String line : asciiArt) {
            System.out.println(line);
        }
        System.out.println();
    }


    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // 设置线程池大小
        scheduler.setThreadNamePrefix("process-scheduled-job-");
        scheduler.initialize();
        return scheduler;
    }
}
