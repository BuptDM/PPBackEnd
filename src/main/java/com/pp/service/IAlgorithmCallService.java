package com.pp.service;

import com.pp.controller.util.R;
import com.pp.domain.AlgorithmCall;

import java.util.List;

public interface IAlgorithmCallService {
    /**
     * 查询特定算法的调用情况
     * @param algorithmId 需要查询算法的ID
     * @return 返回调用情况（某同学多次查询，需要取最优）
     */
    R selectAlgorithmCall(Integer algorithmId);

    /**
     * 算法排行榜
     * @param algorithmId 需要查询算法的ID
     * @return 将所有调用记录按照分数进行排序
     */
    R selectAlgorithmCallOrderByScore(Integer algorithmId);

    /**
     * 查看特定同学特定算法的调用记录
     */
    R getRecordForStudent(String studentID,String algorithmID);

    /**
     * 查看特定同学特定算法的最高分数
     */
    R getScoreForStudent(String studentID,String algorithmID);
}
