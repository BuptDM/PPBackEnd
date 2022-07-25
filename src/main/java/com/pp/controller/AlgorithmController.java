package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IAlgorithmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/algorithm")
@Slf4j
public class AlgorithmController {
    final IAlgorithmService algorithmService;

    public AlgorithmController(IAlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @PostMapping("/kmeans")
    public R kmeans(MultipartFile file, HttpServletRequest request) {
        return algorithmService.kmeans(file,request);
    }
}
