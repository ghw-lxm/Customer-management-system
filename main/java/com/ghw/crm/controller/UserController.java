package com.ghw.crm.controller;

import com.ghw.crm.dto.LoginResult;
import com.ghw.crm.pojo.CrmUser;
import com.ghw.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    //显示login.jsp
    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/dologin")
    public String userLogin(HttpSession session, String usercode, String password, Model model) {
        LoginResult result = userService.login(usercode, password);
        //如果登入成功，保存用户的身份信息到session
        if (result.getUser() != null) {
            session.setAttribute("user", result.getUser());
            //如果成功
            return "redirect:/customerList";
        } else {
            //将错误信息放入model
            model.addAttribute("msg", result.getMsg());
            return "login";
        }
    }

    //退出登录
    @RequestMapping("userLogout")
    public String LogOut(HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }
}
