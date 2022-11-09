package com.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/hello")
public class HelloController {
    @GetMapping(value = "/test")
    public String hellControllerEx01(Model model){ // 컨트롤러 메소드
        model.addAttribute("name", "김철수") ;
        model.addAttribute("age", 10) ;
        return "world/View" ;
    }
}
