package com.pp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.dao.IAlgorithmCallDao;
import com.pp.domain.AlgorithmCall;
import com.pp.service.IAlgorithmCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class AlgorithmCallImp  extends ServiceImpl<IAlgorithmCallDao, AlgorithmCall> implements IAlgorithmCallService {
    @Autowired
    IAlgorithmCallDao algorithmCallDao;
    @Override
    public List<AlgorithmCall> selectAlgorithmCall(Integer algorithmId){
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
        //List<AlgorithmCall> list =this.list(Wrappers.<AlgorithmCall>lambdaQuery().eq(AlgorithmCall::getAlgorithmId,algorithmId).orderByDesc(AlgorithmCall::getScore));
        List<AlgorithmCall> list =this.list(Wrappers.<AlgorithmCall>lambdaQuery().eq(AlgorithmCall::getAlgorithmId,algorithmId));
        if (list.isEmpty())
            throw new NullPointerException("查询算法调用记录不存在");
        return list;
    }
}
