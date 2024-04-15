package com.example.usercontrol.interceptor;

import com.example.usercontrol.model.CommonResult;
import com.example.usercontrol.model.MyUser;
import com.example.usercontrol.dao.CheckAuthDao;

import com.example.usercontrol.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class ResourceInterceptor implements HandlerInterceptor {

    private CheckAuthDao checkAuthService;

    @Autowired
    public void setCheckAuthService(CheckAuthDao checkAuthService) {
        this.checkAuthService = checkAuthService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userInfo = request.getHeader("userInfo");
        if (userInfo == null) {
            CommonUtils.writeErrorResponse(response, "not Authorized", HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        String pathInfo = request.getRequestURI();
        MyUser userInfoFromBase64 = null;
        try {
            userInfoFromBase64 = CommonUtils.getUserInfoFromBase64(userInfo);
        } catch (Exception e) {
            CommonUtils.writeErrorResponse(response, "wrong header ", HttpStatus.BAD_REQUEST.value());
            return false;
        }

        CommonResult commonResult = checkAuthService.checkResourceAuth(userInfoFromBase64.getUserId(), pathInfo.replace("/user/", ""));
        if (!commonResult.isSuccess()) {
            CommonUtils.writeErrorResponse(response, commonResult);
            return false;
        }
        return true;
    }
}