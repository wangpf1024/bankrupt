package com.pansome.workflow.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

@Aspect
@Component
public class ControllerAspect {

    @Before("execution(* com.pansome.workflow.web.AbstractController.update(..)) && args(entity)")
    public void setUpdateTimeForSave(Object entity) {
        if (entity != null) {
            try {
                // Check if the entity has a 'updateTime' field
                Field updateTimeField = getFieldByName(entity.getClass(), "updateTime");
                // If the field exists and is of type Date
                if (updateTimeField != null && updateTimeField.getType().equals(Date.class)) {
                    updateTimeField.setAccessible(true);
                    // If updateTime is null, set it to current time
                    if (updateTimeField.get(entity) == null) {
                        updateTimeField.set(entity, new Date());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Before("execution(* com.pansome.workflow.web.AbstractController.insert(..)) && args(entity)")
    public void setCreateTimeForSave(Object entity) {
        if (entity != null) {
            try {
                // Check if the entity has a 'createTime' field
                Field createTimeField = getFieldByName(entity.getClass(), "createTime");
                // If the field exists and is of type LocalDateTime
                if (createTimeField != null && createTimeField.getType().equals(Date.class)) {
                    createTimeField.setAccessible(true);
                    // If createTime is null, set it to current time
                    if (createTimeField.get(entity) == null) {
                        createTimeField.set(entity, new Date());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to find the 'createTime' field, including fields in parent classes
    private Field getFieldByName(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null; // Return null if the field is not found
    }

}
