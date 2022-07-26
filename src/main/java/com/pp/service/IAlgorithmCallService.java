package com.pp.service;

import com.pp.domain.AlgorithmCall;

import java.util.List;

public interface IAlgorithmCallService {
    /**
     * 查询特定算法的调用情况
     * @param algorithmId 需要查询算法的ID
     * @return 返回调用情况（某同学多次查询，需要取最优）
     */
    List<AlgorithmCall> selectAlgorithmCall(Integer algorithmId);

    /**
     * 算法排行榜
     * @param algorithmId 需要查询算法的ID
     * @return 将所有调用记录按照分数进行排序
     */
    List<AlgorithmCall> selectAlgorithmCallOrderByScore(Integer algorithmId);
}
