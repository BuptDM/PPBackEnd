package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.controller.util.R;
import com.pp.domain.Experiment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IExperimentService {
    /**
     * 查询所有实验
     * @return  实验表格所有记录
     */
    R getAllExperiment();

    /**
     * 添加实验
     * @param file md文件
     * @param request http请求
     * */
    R addExperiment(MultipartFile file, HttpServletRequest request);

    /**
     * 修改实验
     * @param file 新说明文档
     */
    R modifyExperiment(MultipartFile file,HttpServletRequest request);

    /**
     * 删除实验
     * @param id 需要删除的实验的id
     */
    R deleteExperiment(String id);


    /**
     * 查找实验
     * @param id 需要查询实验的id
     */
    R selectByID(String id);

    /**
     * 首页实验概述接口
     * @return 按照发布时间最近的前三条记录
     */
    R getExperimentFP();
}
