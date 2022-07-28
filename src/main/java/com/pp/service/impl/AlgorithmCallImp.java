package com.pp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.controller.util.R;
import com.pp.dao.IAlgorithmCallDao;
import com.pp.dao.IAlgorithmDao;
import com.pp.dao.IUserDao;
import com.pp.domain.Algorithm;
import com.pp.domain.AlgorithmCall;
import com.pp.domain.User;
import com.pp.service.IAlgorithmCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class AlgorithmCallImp  extends ServiceImpl<IAlgorithmCallDao, AlgorithmCall> implements IAlgorithmCallService {
    @Autowired
    IAlgorithmCallDao algorithmCallDao;
    @Autowired
    IAlgorithmDao algorithmDao;
    @Autowired
    IUserDao userDao;
    @Override
    public R selectAlgorithmCall(Integer algorithmId){
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
        return R.ok().data("result",hashMap);
    }

    @Override
    public R selectAlgorithmCallOrderByScore(Integer algorithmId){
        LambdaQueryWrapper<AlgorithmCall> qw =new LambdaQueryWrapper<>();
        qw.eq(AlgorithmCall::getAlgorithmId,algorithmId);
        qw.orderByDesc(AlgorithmCall::getScore);
        List<AlgorithmCall> list = algorithmCallDao.selectList(qw);
        return R.ok().data("result",list);
    }

    @Override
    public R getRecordForStudent(String studentID, String algorithmID) {
        LambdaQueryWrapper<AlgorithmCall> qw = new LambdaQueryWrapper<>();
        qw.eq(AlgorithmCall::getAlgorithmId,algorithmID);
        qw.eq(AlgorithmCall::getStudentId,studentID);
        List<AlgorithmCall> list = algorithmCallDao.selectList(qw);
        return R.ok().data("result",list);
    }

    @Override
    public R getScoreForStudent(String studentID, String algorithmID) {
        LambdaQueryWrapper<AlgorithmCall> qw = new LambdaQueryWrapper<>();
        qw.eq(AlgorithmCall::getAlgorithmId,algorithmID);
        qw.eq(AlgorithmCall::getStudentId,studentID);
        List<AlgorithmCall> list = algorithmCallDao.selectList(qw);
        if(list.isEmpty()) return R.ok().data("result",null);
        float maxScore = Float.MIN_VALUE;
        for (AlgorithmCall algorithmCall : list) {
            if(algorithmCall.getScore()>maxScore) maxScore=algorithmCall.getScore();
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("score",maxScore);
        // 查找用户的名称
        String userName = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount,studentID)).getName();
        map.put("name",userName);
        // 返回值给前端
        return R.ok().data("result",map);
    }
}