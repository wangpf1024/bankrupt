package com.pansome.workflow.scheduled;

import com.pansome.workflow.scheduled.domain.dto.TaskDto;


public interface ScheduledTask {

    String getJobName();

    void execute(TaskDto param);

}
