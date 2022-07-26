package com.pp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class EaRelation {
    @TableId(type = IdType.AUTO)
    int id;
    int experimentId;
    int algorithmId;
}
