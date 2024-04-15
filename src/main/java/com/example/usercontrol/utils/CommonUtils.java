package com.example.usercontrol.utils;

import com.example.usercontrol.model.CommonResult;
import com.example.usercontrol.model.MyUser;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CommonUtils {
    private static final Gson gson = new Gson();

    public static MyUser getUserInfoFromBase64(String encode) throws Exception {
        if (encode == null) {
            return null;
        }
        byte[] decode = Base64.getDecoder().decode(encode);
        String user = new String(decode, StandardCharsets.UTF_8);
        return gson.fromJson(user, MyUser.class);
    }

    public static void writeErrorResponse(HttpServletResponse response, String errorMsg ,int code) {
        response.reset();
        response.setStatus(code);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(errorMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeErrorResponse(HttpServletResponse response, CommonResult result) {
        response.reset();
        response.setStatus(result.getResponseCode());
        try (PrintWriter writer = response.getWriter()) {
            writer.write(result.getMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getBase64Str(MyUser user) {
        return new String(Base64.getEncoder().encode(gson.toJson(user).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }
}
