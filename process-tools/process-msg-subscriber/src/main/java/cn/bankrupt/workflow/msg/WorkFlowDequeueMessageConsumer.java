package cn.bankrupt.workflow.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class WorkFlowDequeueMessageConsumer implements Runnable {

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
            for (WorkFlowMessageHandler handler : handlers) {
                try {
                    handler.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(500l);
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
