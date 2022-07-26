package com.pp.service;

import com.pp.controller.util.R;
import com.pp.domain.Algorithm;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAlgorithmService{
    /**
     * k均值算法
     * @return 返回是否调用成功以及聚类结果
     */
    R kmeans(MultipartFile file, HttpServletRequest request) ;

    /**
     * 所有的算法
     */
    List<Algorithm> allAlgorithm();
}