package cn.bankrupt.workflow.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonExecutionListener implements ExecutionListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void notify(DelegateExecution execution) throws Exception {

    }
}
