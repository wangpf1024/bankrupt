package cn.bankrupt.workflow.scheduled;


import cn.bankrupt.workflow.scheduled.domain.dto.TaskDto;

public interface ScheduledTask {

    String getJobName();

    void execute(TaskDto param);

}
