package com.shopping.controller;

import com.shopping.entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeTestController {
    @GetMapping(value = "/employee")
    public Employee test(){
        Employee bean = new Employee() ;
        bean.setName("김유신");
        bean.setId("yusin");
        bean.setAge(20);
        return bean;
    }
}
