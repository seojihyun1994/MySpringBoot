package com.shopping.controller;

import com.shopping.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonTestController {

    @GetMapping(value = "/person")
    public Person test(){
        Person bean = new Person();
        bean.setAddress("마포구 공덕동");
        bean.setId("kim9");
        bean.setSalary(10000);
        bean.setName("김구");
        return bean ;
    }
}
