package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IAlgorithmCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/algorithmCall")
public class AlgorithmCallController {
    @Autowired
    private IAlgorithmCallService algorithmCallService;
    /**
     * 查询特定算法的调用情况
     * param algorithmId
     * return 对应算法所有同学的调用记录列表
     */
    @PostMapping("/select")
    public R selectAlgorithmCall(Integer algorithmId){
        return R.ok().data("result", algorithmCallService.selectAlgorithmCall(algorithmId));
    }
    /**
     * 算法排行榜
     * param: algorithm-id
     * return: 该届学生、该算法的所有提交记录，按照score排序之后的列表
     */
    @PostMapping("/order")
    public R selectAlgorithmCallByScore(Integer algorithmId){
        return R.ok().data("result",algorithmCallService.selectAlgorithmCallOrderByScore(algorithmId) );
    }
}
