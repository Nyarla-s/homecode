package com.example.usercontrol;

import com.example.usercontrol.enums.RoleEnum;
import com.example.usercontrol.model.MyUser;
import com.example.usercontrol.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Test
    public void getBase64(){
        MyUser operationUser = new MyUser();
        operationUser.setUserId(1000);
        operationUser.setAccountName("admin1");
        operationUser.setRole(RoleEnum.ADMIN.value());

        String base64Str = CommonUtils.getBase64Str(operationUser);
        System.out.println(base64Str);
    }
}
