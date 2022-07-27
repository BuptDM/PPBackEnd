package com.pp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.controller.util.R;
import com.pp.dao.IAlgorithmCallDao;
import com.pp.dao.IAlgorithmDao;
import com.pp.domain.Algorithm;
import com.pp.domain.AlgorithmCall;
import com.pp.service.IAlgorithmCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class AlgorithmCallImp  extends ServiceImpl<IAlgorithmCallDao, AlgorithmCall> implements IAlgorithmCallService {
    @Autowired
    IAlgorithmCallDao algorithmCallDao;
    @Autowired
    IAlgorithmDao algorithmDao;
    @Override
    public List<AlgorithmCall> selectAlgorithmCall(Integer algorithmId){
        // 判断算法ID是否有效
        Algorithm algorithm = algorithmDao.selectOne(new LambdaQueryWrapper<Algorithm>().eq(Algorithm::getId,algorithmId));
        // 查询所有记录
        List<AlgorithmCall> list = this.list(new LambdaQueryWrapper<AlgorithmCall>().eq(AlgorithmCall::getAlgorithmId,algorithmId));
        // 去除重复记录
        HashMap<String,AlgorithmCall> hashMap = new HashMap<>();
        list.forEach(e->{
            if(hashMap.containsKey(e.getStudentId())&&hashMap.get(e.getStudentId()).getScore()>=e.getScore())
                return;
            hashMap.put(e.getStudentId(),e);
        });
        return new ArrayList<>(hashMap.values());
    }

    @Override
    public List<AlgorithmCall> selectAlgorithmCallOrderByScore(Integer algorithmId){
        return this.list(Wrappers.<AlgorithmCall>lambdaQuery().eq(AlgorithmCall::getAlgorithmId,algorithmId).orderByDesc(AlgorithmCall::getScore));
    }
}
