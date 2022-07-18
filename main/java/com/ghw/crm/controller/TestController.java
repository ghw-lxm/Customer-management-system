package com.ghw.crm.controller;

import com.ghw.crm.pojo.CrmUser;
import com.ghw.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @Autowired
    UserService service;
    @RequestMapping("/test")
    public String test(Model model){
        CrmUser user = service.getUserByID(5);
        model.addAttribute("user",user);


        return "index";
    }
}
