package com.xinhuanet.controller;

import com.xinhuanet.model.Login;
import com.xinhuanet.service.LoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "getLogin")
    @ResponseBody
    public Login getLogin(Long id) {
        Login login = loginService.getLogin(id);
        log.info(login.getUsername());
        return login;
    }

}
