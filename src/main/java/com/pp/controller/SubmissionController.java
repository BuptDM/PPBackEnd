package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.ISubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/experimentSubmission")
public class SubmissionController {
    @Autowired
    ISubmissionService experimentSubmissionService;
    /**
     * 获取某实验的所有同学完成情况
     * @param experimentId 特定实验的id
     * @return 返回该特定实验的所有提交记录
     */
    @PostMapping("/getByExperimentID")
    public R getExperimentSubmission(String experimentId){
        return experimentSubmissionService.getExperimentSubmission(experimentId);
    }
    /**
     * 对某个同学某次实验进行评分
     * @return success
     */
    @PostMapping("/grade")
    public R gradeExperimentSubmission(Integer experimentId, Integer studentId, float score){
        return R.ok().data("result",experimentSubmissionService.gradeExperimentSubmission(experimentId,studentId,score));
    }
    /**
     * 根据石否评分进行查询
     * @param ifScore (true of false，表示是否评分)
     * @return 返回作业提交表格中，已评分or未评分的记录列表
     */
    @PostMapping("/selectByIfScore")
    public R getExperimentSubmissionByScore(Boolean ifScore,String experimentID){
        return R.ok().data("result",experimentSubmissionService.getExperimentSubmissionByScore(ifScore,experimentID));
    }
    /**
     * 查询某同学某实验的完成情况
     */
    @PostMapping("/queryForStudent")
    public R queryForStudent(String studentID,String experimentID){
        return experimentSubmissionService.queryForStudent(studentID,experimentID);
    }
}