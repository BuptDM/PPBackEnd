package com.pp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class EARelation {
    @TableId(type = IdType.AUTO)
    int id;
    int experimentId;
    int algorithmId;
}
