package com.dasha.action;

import com.dasha.model.Employee;
import com.dasha.model.JobType;
import com.dasha.service.employee.params.UpdateEmployeeParams;
import com.dasha.service.post.PostService;

public class UpdateAction {
    private final PostService service = new PostService();

    public void update(Employee employee, UpdateEmployeeParams params){

        if(notNull(params.getFirstName())) employee.setFirstName(params.getFirstName());
        if(notNull(params.getLastName())) employee.setLastName(params.getLastName());
        if(notNull(params.getDescription())) employee.setDescription(params.getDescription());
        if(notNull(params.getCharacteristics())) employee.setCharacteristics(params.getCharacteristics());
        if(notNull(params.getContacts())) employee.setContacts(params.getContacts());
        if(notNull(params.getPostId())) employee.setPost(service.getById(params.getPostId()));
        if(notNull(params.getJobType())) employee.setJobType(JobType.valueOf(params.getJobType()));

        //return employee;
    }

    private static boolean notNull(Object param){
        return param != null;
    }
}
