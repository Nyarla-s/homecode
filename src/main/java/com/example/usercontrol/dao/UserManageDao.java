package com.example.usercontrol.dao;

import com.example.usercontrol.enums.RoleEnum;
import com.example.usercontrol.model.CommonResult;
import com.example.usercontrol.model.MyUser;
import com.example.usercontrol.service.MyDataService;
import com.example.usercontrol.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;

@Validated
@Component
public class UserManageDao {

    private MyDataService myDataService;

    @Autowired
    public void setMyDataService(MyDataService myDataService) {
        this.myDataService = myDataService;
    }

    public CommonResult addUser(MyUser target) {
        if (!RoleEnum.checkRole(target.getRole())) {
            return CommonResult.getFailedResult("not supported role");
        }
        if (myDataService.getUser(target.getUserId()) != null) {
            return CommonResult.getFailedResult("user already exist id repeated");
        }
        try {
            myDataService.addUser(target);
        } catch (IOException e) {
            return CommonResult.getFailedResult("add failed " + e.getMessage());
        }
        return CommonResult.getSuccessResult("success", CommonUtils.getBase64Str(target));
    }
}
