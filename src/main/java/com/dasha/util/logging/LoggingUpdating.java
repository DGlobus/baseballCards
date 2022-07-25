package com.dasha.util.logging;

import com.dasha.model.Employee;
import com.dasha.service.employee.EmployeeService;
import com.dasha.service.employee.params.UpdateEmployeeParamsForService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Aspect
@Slf4j
@ConditionalOnProperty(prefix = "logging", name = "update.employee")
@Component
public class LoggingUpdating {
    @Autowired
    private EmployeeService service;

    @Pointcut("@annotation(UpdateLogging)")
    public void loggingUpdatingPointcut(){}

    @Before(value = "loggingUpdatingPointcut() && args(params)", argNames = "params")
    private void recordUpdating(UpdateEmployeeParamsForService params) {
        Employee employee = service.getById(params.getId());
        log.info("Employee update: "+ employee.getId());

        if(checkNotEquals(employee.getFirstName(), params.getLastName()))
            logging("First Name", employee.getFirstName(), params.getLastName());
        if(checkNotEquals(employee.getLastName(), params.getLastName()))
            logging("Last Name", employee.getLastName(), params.getLastName());
        if(checkNotEquals(employee.getDescription(), params.getDescription()))
            logging("Description", employee.getDescription(), params.getDescription());
        if(checkNotEquals(employee.getFirstName(), params.getLastName()))
            logging("Characteristics", employee.getCharacteristics().stream().map(String::valueOf).collect(Collectors.joining(", ")),
                    params.getCharacteristics().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        if(checkNotEquals(employee.getPost(), params.getPost()))
            logging("Post", employee.getPost().getName(), params.getPost().getName());
        if(checkNotEquals(employee.getContacts(), params.getContacts()))
            logging("Contacts", employee.getContacts().toString(), params.getContacts().toString());
        if(checkNotEquals(employee.getJobType(), params.getJobType()))
            logging("Job Type", employee.getJobType().toString(), params.getJobType().toString());
    }

    private boolean checkNotEquals(Object oldValue, Object newValue){
        return !oldValue.equals(newValue);
    }

    private void logging(String fieldName, String oldValue, String newValue){
        log.info(String.format("Field: %s,\tPrevious Value: %s,\t, New Value: %s", fieldName, oldValue, newValue));
    }
}
