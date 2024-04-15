package com.example.usercontrol.dao;

import com.example.usercontrol.model.CommonResult;
import com.example.usercontrol.model.MyUser;
import com.example.usercontrol.service.MyDataService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CheckAuthDao {

    @Resource
    MyDataService myDataService;

    public CommonResult checkResourceAuth(Integer key, String resource) {
        MyUser user = myDataService.getUser(key);
        if (user == null) {
            return CommonResult.getFailedResult("no such user").setResponseCode(HttpStatus.UNAUTHORIZED.value());
        }
        return user.getEndpoints().contains(resource)
                ? CommonResult.getSuccessResult("")
                : CommonResult.getFailedResult("No access permission").setResponseCode(HttpStatus.FORBIDDEN.value());

    }

    public boolean checkAdmin(MyUser user) {
        MyUser userInData = myDataService.getUser(user.getUserId());
        return userInData != null && Objects.equals("admin", user.getRole());
    }
}
