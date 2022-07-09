package com.pp.domain;

import lombok.Data;

@Data
public class User {
    // 静态变量，表示用户身份
    public static String TEACHER = "teacher";
    public static String STUDENT = "student";
    // 字段
    int id;
    String account;
    String name;
    String password;
    String identify;
}