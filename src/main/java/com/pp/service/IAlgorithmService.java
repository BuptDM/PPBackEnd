package com.pp.service;

import com.pp.controller.util.R;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

public interface IAlgorithmService{
    /**
     * k均值算法
     * @return 返回是否调用成功以及聚类结果
     */
    R kmeans(MultipartFile file, HttpServletRequest request) ;
}