package com.example.usercontrol.interceptor;

import com.example.usercontrol.dao.CheckAuthDao;
import com.example.usercontrol.utils.CommonUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {


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
        } else {
            try {
                if (!checkAuthService.checkAdmin(CommonUtils.getUserInfoFromBase64(userInfo))) {
                    CommonUtils.writeErrorResponse(response, "have no access to add user", HttpStatus.FORBIDDEN.value());
                    return false;
                }
            } catch (Exception e) {
                CommonUtils.writeErrorResponse(response, "wrong header ", HttpStatus.BAD_REQUEST.value());
                return false;
            }
            return true;
        }
    }


}
