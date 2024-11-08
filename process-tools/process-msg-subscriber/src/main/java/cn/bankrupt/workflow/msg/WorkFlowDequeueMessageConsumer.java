package cn.bankrupt.workflow.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class WorkFlowDequeueMessageConsumer implements Runnable {


    private final List<WorkFlowMessageHandler> handlers;

    @Autowired
    public WorkFlowDequeueMessageConsumer(List<WorkFlowMessageHandler> handlers) {
        this.handlers = handlers;
    }

    // Initialize and start the thread when the bean is created
    @PostConstruct
    public void init() {
        Thread consumerThread = new Thread(this);
        consumerThread.start();
    }

    @Override
    public void run() {
        while (true) {
            for (WorkFlowMessageHandler handler : handlers) {
                try {
                    handler.execute();
                    Thread.sleep(500l);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
