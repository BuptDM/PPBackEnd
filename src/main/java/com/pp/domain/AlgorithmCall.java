package com.pp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AlgorithmCall {
    @TableId(type = IdType.AUTO)
    int id;
    String studentId;
    int algorithmId;
    String param;// 是一个Json字符串
    String result;
    Timestamp postTime;
    float score;
}