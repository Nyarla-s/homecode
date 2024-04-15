package com.example.usercontrol.model;


import jakarta.validation.constraints.*;
import lombok.Data;


import java.util.Set;


@Data
public class MyUser {
    @NotNull
    @Min(0)
    private Integer userId;
    private String accountName;
    private String role;
    @NotNull
    @NotEmpty
    private Set<String> endpoints;
}
