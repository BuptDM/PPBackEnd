package com.pp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Algorithm {
    @TableId(type = IdType.AUTO)
    int id;
    String title;
    String summary;
}
