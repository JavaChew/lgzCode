package com.archives.controller;

import com.archives.activiti.ActivitiTest;
import com.archives.model.User;
import com.archives.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/select")
    public ModelAndView selectUser() throws Exception {
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUser(1);
        mv.addObject("user", user);
        mv.setViewName("user");

        ActivitiTest.deploy();
        ActivitiTest.startInstanceByKey("helloProcess");
        ActivitiTest.findTaskByAssignee("lisi");

        return mv;
    }
}
