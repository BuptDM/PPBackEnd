package com.pp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EaRelation {
    @TableId(type = IdType.AUTO)
    int id;
    int experimentId;
    int algorithmId;
    public EaRelation(int experimentId,int algorithmId){
        this.experimentId=experimentId;
        this.algorithmId = algorithmId;
    }
}
