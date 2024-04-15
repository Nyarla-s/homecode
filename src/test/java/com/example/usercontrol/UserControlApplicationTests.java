package com.example.usercontrol;

import com.example.usercontrol.enums.RoleEnum;
import com.example.usercontrol.model.MyUser;
import com.example.usercontrol.service.MyDataService;
import com.example.usercontrol.utils.CommonUtils;
import com.google.gson.Gson;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControlApplicationTests {


    @Autowired
    MyDataService myDataService;

    @Resource
    MockMvc mockMvc;


    @Test
    @Order(1)
    void addUserSuccessTest() throws Exception {
        MyUser operationUser = new MyUser();
        operationUser.setUserId(1000);
        operationUser.setAccountName("admin1");
        operationUser.setRole(RoleEnum.ADMIN.value());

        MyUser user = new MyUser();
        user.setUserId(1);
        user.setRole(RoleEnum.USER.value());
        user.setAccountName("user1");
        HashSet<String> strings = new HashSet<>();
        strings.add("user1edpoints");
        user.setEndpoints(strings);
        Gson gson = new Gson();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("userInfo", CommonUtils.getBase64Str(operationUser));
        mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/admin/addUser")
                        .headers(httpHeaders)
                        .contentType("application/json")
                        .content(gson.toJson(user))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

    }

    @Test
    @Order(2)
    void addUserFailedTest() throws Exception {
        MyUser operationUser = new MyUser();
        operationUser.setUserId(1);
        operationUser.setAccountName("user1");
        operationUser.setRole("user");

        MyUser user = new MyUser();
        user.setUserId(2);
        user.setRole(RoleEnum.USER.value());
        user.setAccountName("user2");
        HashSet<String> strings = new HashSet<>();
        strings.add("user2edpoints");
        user.setEndpoints(strings);
        Gson gson = new Gson();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("userInfo", CommonUtils.getBase64Str(operationUser));
        mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/admin/addUser")
                        .headers(httpHeaders)
                        .contentType("application/json")
                        .content(gson.toJson(user))
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(print());
    }


    @Test
    @Order(3)
    void getResourceSuccessTest() throws Exception {
        MyUser user = new MyUser();
        user.setUserId(1);
        user.setRole(RoleEnum.USER.value());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("userInfo", CommonUtils.getBase64Str(user));
        mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/user/user1edpoints")
                        .headers(httpHeaders)
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Order(4)
    void getResourceFailedTest() throws Exception {
        MyUser myUser = new MyUser();
        myUser.setUserId(1);
        myUser.setAccountName("user1");
        myUser.setRole("user");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("userInfo", CommonUtils.getBase64Str(myUser));
        mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/user/user2edpoints")
                        .headers(httpHeaders)
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(print());

    }
}
