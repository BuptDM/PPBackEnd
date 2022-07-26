package com.pp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.config.AppProperties;
import com.pp.controller.util.R;
import com.pp.dao.*;
import com.pp.domain.*;
import com.pp.service.ISubmissionService;
import com.pp.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class SubmissionServiceImpl extends ServiceImpl<IExperimentSubmissionDao, ExperimentSubmission>
        implements ISubmissionService {
    @Autowired
    WebUtils webUtils;
    @Autowired
    IExperimentSubmissionDao experimentSubmissionDao;
    @Autowired
    AppProperties appProperties;
    @Autowired
    IExperimentDao experimentDao;
    @Autowired
    IUserDao userDao;
    @Autowired
    IEARelationDao relationDao;
    @Autowired
    IAlgorithmCallDao algorithmCallDao;
    @Autowired
    IAlgorithmDao algorithmDao;
    @Autowired
    IFreeProgrammingDao freeProgrammingDao;
    @Override
    public R getExperimentSubmission(String experimentID) {
        // 所有相关实验
        List<Algorithm> algorithms  = getAllRelatedAlgorithms(experimentID);
        // 查询所有学生目录
        List<User> students = userDao.selectList(new LambdaQueryWrapper<User>().eq(User::getRole,"student"));
        HashMap<String,HashMap<String,Object>> ret = new HashMap<>();
        students.forEach(e->{
            String studentID = e.getAccount();
            HashMap<String,Object> hashMap = new HashMap<>();
            // 查询每个学生每个算法的完成情况
            queryForAllAlgorithmsScore(algorithms,studentID,hashMap);
            // 查询每个学生的自主编程完成情况
            hashMap.put("fp", getMaxFPScore(studentID,experimentID));
            // 查询总评分
            hashMap.put("finalScore",getFinalScore(studentID,experimentID));
            // 将学生姓名放到hashMap中
            hashMap.put("name",e.getName());
            ret.put(studentID,hashMap);
        });
        return R.ok().message("查找成功").data("result",ret).data("algorithms",algorithms);
    }
    @Override
    public R gradeExperimentSubmission(Integer experimentId, Integer studentId, float score) {
        LambdaQueryWrapper<ExperimentSubmission> qw = new LambdaQueryWrapper<>();
        qw.eq(ExperimentSubmission::getStudentId, studentId);
        qw.eq(ExperimentSubmission::getExperimentId, experimentId);
        ExperimentSubmission record = experimentSubmissionDao.selectOne(qw);
        if(record.getScore()!=null)
            return R.error().message("已经评过分");
        record.setScore(score);
        this.saveOrUpdate(record);
        return R.ok().message("评分成功");
    }
    @Override
    public R getExperimentSubmissionByScore(Boolean ifScore,String experimentID) {
        // 所有相关实验
        List<Algorithm> algorithms  = getAllRelatedAlgorithms(experimentID);
        // 查询所有学生目录
        List<User> students = userDao.selectList(new LambdaQueryWrapper<User>().eq(User::getRole,"student"));
        HashMap<String,HashMap<String,Object>> ret = new HashMap<>();
        students.forEach(e->{
            String studentID = e.getAccount();
            HashMap<String,Object> hashMap = new HashMap<>();
            // 查询总评分
            Float finalScore = getFinalScore(studentID,experimentID);
            if(finalScore!=null&&!ifScore)
                return;
            if(finalScore==null&&ifScore)
                return;
            hashMap.put("finalScore",finalScore);
            // 查询每个学生每个算法的完成情况
            queryForAllAlgorithmsScore(algorithms,studentID,hashMap);
            // 查询每个学生的自主编程完成情况
            hashMap.put("fp", getMaxFPScore(studentID,experimentID));
            // 将学生姓名放到hashMap中
            hashMap.put("name",e.getName());
            ret.put(studentID,hashMap);
        });
        return R.ok().message("查找成功").data("result",ret).data("algorithms",algorithms);
    }
    @Override
    public R queryForStudent(String studentID,String experimentID) {
        // 查询所有相关算法
        List<Algorithm> allRelatedAlgorithms = getAllRelatedAlgorithms(experimentID);
        // 声明返回的map对象
        HashMap<String,Object> hashMap = new HashMap<>();
        // 查询总评分
        hashMap.put("finalScore",getFinalScore(studentID,experimentID));
        // 查询学生每个算法的完成情况
        queryForAllAlgorithmsScore(allRelatedAlgorithms,studentID,hashMap);
        // 查询每个学生的自主编程完成情况
        hashMap.put("fp", getMaxFPScore(studentID,experimentID));
        // 将学生姓名放到hashMap中
        User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount,studentID));
        hashMap.put("studentName",user.getName());
        // 返回值给前端
        return R.ok().data("result",hashMap).data("algorithms",allRelatedAlgorithms);
    }
    /*获取学生某实验最终评分*/
    public Float getFinalScore(String studentID,String experimentID){
        LambdaQueryWrapper<ExperimentSubmission> qw1 = new LambdaQueryWrapper<>();
        qw1.eq(ExperimentSubmission::getExperimentId,experimentID);
        qw1.eq(ExperimentSubmission::getStudentId,studentID);
        ExperimentSubmission e = experimentSubmissionDao.selectOne(qw1);
        return e==null?null:e.getScore();
    }
    /*获取某实验的所有相关实验*/
    List<Algorithm> getAllRelatedAlgorithms(String experimentID){
        // 查询所有相关算法的ID
        List<EaRelation> relations = relationDao.selectList(new LambdaQueryWrapper<EaRelation>().eq(EaRelation::getExperimentId,experimentID));
        // 所有相关实验
        ArrayList<Algorithm> algorithms  = new ArrayList<>();
        relations.forEach(e->{
            algorithms.add(algorithmDao.selectById(e.getAlgorithmId()));
        });
        return algorithms;
    }
    /*查询某个学生，某实验自主编程的最高分数*/
    public Float getMaxFPScore(String studentID,String experimentID){
        LambdaQueryWrapper<FreeProgramming> qw = new LambdaQueryWrapper<>();
        qw.eq(FreeProgramming::getStudentId,studentID);
        qw.eq(FreeProgramming::getExperimentId,experimentID);
        List<FreeProgramming> fpList = freeProgrammingDao.selectList(qw);
        if(fpList.size()==0){
            return null;
        }else {
            float max = Float.MIN_VALUE;
            int index = -1;
            for (int i = 0; i < fpList.size(); i++) {
                if (fpList.get(i).getScore() > max) {
                    max = fpList.get(i).getScore();
                    index = i;
                }
            }
            return fpList.get(index).getScore();
        }
    }
    /*查询某学生的所有算法调用记录*/
    public void queryForAllAlgorithmsScore(List<Algorithm> algorithms,String studentID,HashMap<String,Object> hashMap){
        algorithms.forEach(algorithm->{
            // 查找该学生，该算法的所有算法调用记录
            LambdaQueryWrapper<AlgorithmCall> qw = new LambdaQueryWrapper<>();
            qw.eq(AlgorithmCall::getStudentId,studentID);
            qw.eq(AlgorithmCall::getAlgorithmId,algorithm.getId());
            List<AlgorithmCall> algorithmCalls = algorithmCallDao.selectList(qw);
            // 判断该算法是否被调用过
            if(algorithmCalls.size()==0) {
                hashMap.put(String.valueOf(algorithm.getId()), "null");
                return;
            }
            // 选择所有算法调用记录中，评分最高的记录
            float max = Float.MIN_VALUE;
            int index = -1;
            for (int i = 0; i < algorithmCalls.size(); i++) {
                if(algorithmCalls.get(i).getScore()>max){
                    max = algorithmCalls.get(i).getScore();
                    index = i;
                }
            }
            hashMap.put(String.valueOf(algorithm.getId()),algorithmCalls.get(index).getScore());
        });
    }
}