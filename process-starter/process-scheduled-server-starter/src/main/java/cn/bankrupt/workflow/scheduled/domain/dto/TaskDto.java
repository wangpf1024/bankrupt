package cn.bankrupt.workflow.scheduled.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDto {

    public TaskDto(String jobName){
        this.jobName = jobName;
    }
    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 最新执行时间
     */
    private Date latestTime;
}
