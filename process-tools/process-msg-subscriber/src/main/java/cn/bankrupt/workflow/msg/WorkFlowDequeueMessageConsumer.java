package cn.bankrupt.workflow.msg;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import cn.bankrupt.workflow.cache.ProcessRedisCache;
import cn.bankrupt.workflow.dto.TaskMsgDataDto;
import cn.bankrupt.workflow.enums.ProcessWorkFlowBaseEventEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class WorkFlowDequeueMessageConsumer implements Runnable {


    @Autowired
    ProcessRedisCache processRedisCache;

    private Thread consumerThread;

    private volatile boolean running = true;

    private final List<WorkFlowMessageHandler> handlers;

    @Autowired
    public WorkFlowDequeueMessageConsumer(List<WorkFlowMessageHandler> handlers) {
        this.handlers = handlers;
    }

    // Initialize and start the thread when the bean is created
    @PostConstruct
    public void init() {
        consumerThread = new Thread(this);
        consumerThread.start();
    }

    @Override
    public void run() {
        while (running) {
            String objStr = processRedisCache.rightPopWithSchema(ProcessWorkFlowBaseEventEnum.process_event_step_by_step.getCode());
            if(StrUtil.isNotEmpty(objStr)){
                //类型转换
                TaskMsgDataDto dto = JSON.parseObject(objStr,TaskMsgDataDto.class);
                if(dto != null
                        && StrUtil.isNotEmpty(dto.getEventCode())){

                    for (WorkFlowMessageHandler handler : handlers) {
                        try {
                            //按照事件编码匹配
                            if(handler.getEventCode() != null
                                    && dto.getEventCode().equals(handler.getEventCode())){
                                handler.execute(dto);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
            try {
                Thread.sleep(1000l);
            }catch (Exception e){
                Thread.currentThread().interrupt();
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        // Signal the thread to stop and wait for it to finish
        running = false;
        if (consumerThread != null && consumerThread.isAlive()) {
            consumerThread.interrupt();
            try {
                consumerThread.join(); // Wait for the thread to terminate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
