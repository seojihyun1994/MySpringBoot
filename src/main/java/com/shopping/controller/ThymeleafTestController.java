package com.shopping.controller;

import com.shopping.dto.PersonDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/person")
public class ThymeleafTestController {
    @GetMapping(value = "/ex01")
    public String personExam01(Model model){
        model.addAttribute("data","안녕하세용") ;
        return "person/viewEx01" ;
    }
    @GetMapping(value = "/ex02")
    public String personExam02(Model model){
        PersonDto bean = new PersonDto() ;
        bean.setAge(29);
        bean.setName("아이유");
        bean.setBirth("1993/5월/6일");
        bean.setCountry("대한민국");
        bean.setJob("가수");

        model.addAttribute("bean",bean) ;

        return "person/viewEx02" ;
    }

    private List<PersonDto> getPersonDtoData(int gaesu){
        List<PersonDto> personDtoList = new ArrayList<PersonDto>() ;
        String[] name = {"아이유", "공유", "박해일", "김태리", "김혜수", "이효리", "비", "송강", "전지현", "한소희"} ;
        String[] job = {"가수","배우","배우","배우","배우","가수","가수","배우","배우","배우"} ;
        String country = "대한민국";
        int[] age = {29, 43, 45, 32, 52, 43, 40, 28, 40, 27} ;
        String[] birth = {"1993/5월/6일","1979년 7월 10일","1977년 1월 26일","1990년 4월 24일","1970년 9월 5일","1979년 5월 10일","1982년 6월 25일","1994년 4월 23일","1981년 10월 30일","1994년 11월 18일"} ;

        for (int i = 1; i <= gaesu ; i++) {
            PersonDto bean = new PersonDto() ;
            bean.setJob(job[i-1]);
            bean.setName(name[i-1]);
            bean.setBirth(birth[i-1]);
            bean.setCountry(country);
            bean.setAge(age[i-1]);

            personDtoList.add(bean) ;
        }
        return personDtoList ;
    }

    @GetMapping(value = "/ex03")
    public String personExam03(Model model){
        List<PersonDto> personDtoList = this.getPersonDtoData(10) ;
        model.addAttribute("personDtoList", personDtoList) ;
        return "person/viewEx03" ;
    }

    @GetMapping(value = "/ex04")
    public String personExam04(Model model){
        List<PersonDto> personDtoList = this.getPersonDtoData(10) ;
        model.addAttribute("personDtoList", personDtoList) ;
        return "person/viewEx04" ;
    }

    @GetMapping(value = "/ex05")
    public String personExam05(Model model){
        List<PersonDto> personDtoList = this.getPersonDtoData(10) ;
        model.addAttribute("personDtoList", personDtoList) ;
        return "person/viewEx05" ;
    }
    @GetMapping(value = "/ex06")
    public String personExam06(Model model){
        return "person/viewEx06" ;
    }
    @GetMapping(value = "/ex07")
    public String personExam07(Model model, String param1, String param2){
        if(param1==null){param1="하하하";}
        if(param2==null){param2="하하하";}

        model.addAttribute("param1",param1) ;
        model.addAttribute("param2",param2) ;

        return "person/viewEx07" ;
    }
    @GetMapping(value = "/ex08")
    public String personExam08(Model model){
        return "person/viewEx08" ;
    }

    @GetMapping(value = "/ex09")
    public String personExam09(Model model){
        return "person/viewEx09" ;
    }

}
