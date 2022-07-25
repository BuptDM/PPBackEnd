package com.pp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Experiment {
    @TableId(type= IdType.AUTO)
    int id;
    String title;
    String summary;
    String fileUrl;
    String fileName;
    Timestamp postTime;
}
