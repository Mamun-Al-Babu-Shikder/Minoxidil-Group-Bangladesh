package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by A.A.MAMUN on 10/7/2020.
 */
@Controller
//@RequestMapping("/account")
public class AccountController {

    @Autowired
    private CommonData commonData;

    @RequestMapping("/login")
    public  String login(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        return "/client/pages/account/login";
    }

    @RequestMapping("/registration")
    public  String registration(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        return "/client/pages/account/register";
    }

    @RequestMapping("/account/profile")
    public String profile(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        return "/client/pages/account/profile";
    }


}
