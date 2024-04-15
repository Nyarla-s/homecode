package com.example.usercontrol.controller;

import com.example.usercontrol.model.CommonResult;
import com.example.usercontrol.model.MyUser;
import com.example.usercontrol.dao.UserManageDao;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    private UserManageDao myDataService;

    @Autowired
    public void setMyDataService(UserManageDao myDataService) {
        this.myDataService = myDataService;
    }

    @PostMapping(value = "/admin/addUser")
    public CommonResult addUser(@RequestBody @Valid MyUser target) {
        return myDataService.addUser(target);
    }

    @GetMapping(value = "/user/{resource}")
    public String getResource(@PathVariable(value = "resource") String resource) {
        return "get " + resource;
    }
}
