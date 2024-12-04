package com.pansome.workflow.scheduled;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pansome.workflow.scheduled.domain.dto.TaskDto;
import com.pansome.workflow.scheduled.entity.WorkFlowJobLog;
import com.pansome.workflow.scheduled.service.WorkFlowJobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTaskManager {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<ScheduledTask> tasks;

    private final ThreadPoolTaskScheduler taskScheduler;

    private final WorkFlowJobLogService workFlowJobLogService;

    public ScheduledTaskManager(List<ScheduledTask> tasks,
                                ThreadPoolTaskScheduler taskScheduler,
                                WorkFlowJobLogService workFlowJobLogService) {
        this.tasks = tasks;
        this.taskScheduler = taskScheduler;
        this.workFlowJobLogService = workFlowJobLogService;
    }

    @Scheduled(cron = "0 0/3 * * * ?")
    public void processJobs() {
        long start = System.currentTimeMillis();
        logger.info("process-scheduled-job execute started");
        for (ScheduledTask task : tasks) {
            WorkFlowJobLog workFlowJobLog = workFlowJobLogService.getOne(Wrappers.<WorkFlowJobLog>lambdaQuery()
                    .eq(WorkFlowJobLog::getJobName, task.getJobName())
                    .orderByDesc(WorkFlowJobLog::getId).last("limit 1"),false);
            TaskDto dto = new TaskDto(task.getJobName());
            if(workFlowJobLog != null){
                dto.setLatestTime(workFlowJobLog.getCreateTime());
            }
           taskScheduler.execute(() -> task.execute(dto));
            WorkFlowJobLog log = new WorkFlowJobLog();
            log.setJobName(task.getJobName());
            log.setCreateTime(new Date());
            workFlowJobLogService.save(log);
        }
        logger.info("process-scheduled-job execute ended cost {} ms",(System.currentTimeMillis() - start));
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void cleaner() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,-7);
        workFlowJobLogService.remove(
                Wrappers.<WorkFlowJobLog>lambdaQuery().lt(WorkFlowJobLog::getCreateTime,calendar.getTime()));
    }


}
