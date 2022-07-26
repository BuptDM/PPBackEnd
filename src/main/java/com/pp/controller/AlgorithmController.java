package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IAlgorithmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/algorithms")
@Slf4j
public class AlgorithmController {
    @Autowired
    IAlgorithmService algorithmService;
    @PostMapping("/kmeans")
    public R kmeans(MultipartFile file, HttpServletRequest request) {
        return algorithmService.kmeans(file,request);
    }

    /**
     * 查询所有算法
     * @return 算法表格所有记录
     */
    @GetMapping("/list")
    public R list(){
        return R.ok().data("result",algorithmService.allAlgorithm());
    }
}
