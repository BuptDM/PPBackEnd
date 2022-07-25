package com.pp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FreeProgramming {
    @TableId(type = IdType.AUTO)
    int id;
    String studentId;
    int experimentId;
    String codeUrl;
    int score;
    Timestamp time;
}
