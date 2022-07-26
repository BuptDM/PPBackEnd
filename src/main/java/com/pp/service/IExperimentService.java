package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.controller.util.R;
import com.pp.domain.Experiment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IExperimentService {
    /**
     * 查询所有实验
     *
     * @return  实验表格所有记录
     */
    R getAllExperiment();
    /**
     * 添加实验
     * @param file md文件
     * @param request http请求
     * */
    R addExperiment(MultipartFile file, HttpServletRequest request);

}
