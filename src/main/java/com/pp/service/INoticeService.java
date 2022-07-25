package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.controller.util.R;
import com.pp.domain.Notice;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface INoticeService{
    /**
     * 添加文章
     * @param file md文件
     * @param request http请求
     * */
    R addNotice(MultipartFile file, HttpServletRequest request);


    /**
     * @param id 需要删除的文章的id
     * @return 是否删除成功
     */
    R deleteNotice(String id);

    /**
     * 按照时间顺序，获取最近三条通知
     * @return 最近发布的三条数据，返回列表
     * */
    R getThreeNotices();

    /**
     * 根据ID查找文章
     * @param id 帮助文章的id
     * @return 查找是否成功，如果查找成功，返回帮助文章的实例对象
     */
    R getNoticeByID(String id);

    /**
     * 查找所有的帮助文章
     * @return 文章列表
     */
    R getAllNotices();

    /**
     * 获取推荐文章，返回列表
     * @return 暂时返回最近发布的三篇文章
     */
    R getRecommendNotice(String id);

    /**
     * 修改文章
     * @param  file 修改后的文件
     * @return 返回是否修改成功，以及修改后的数据库记录
     */
    R modifyNotice(MultipartFile file,HttpServletRequest request);
}
