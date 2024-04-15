package com.example.usercontrol.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonResult {
    boolean success;
    String msg;
    int responseCode;
    Object data;


    public static CommonResult getSuccessResult(String msg) {
        CommonResult commonResult = new CommonResult(msg);
        commonResult.setSuccess(true);
        commonResult.setResponseCode(HttpStatus.OK.value());
        return commonResult;
    }
    public static CommonResult getSuccessResult(String msg,Object data) {
        CommonResult commonResult = new CommonResult(msg);
        commonResult.setSuccess(true);
        commonResult.setResponseCode(HttpStatus.OK.value());
        commonResult.setData(data);
        return commonResult;
    }

    public static CommonResult getFailedResult(String msg) {
        CommonResult commonResult = new CommonResult(msg);
        commonResult.setSuccess(false);
        return commonResult;
    }

    public CommonResult setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public CommonResult() {
    }

    public CommonResult(String msg) {
        this.msg = msg;
    }
}
