package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.controller.util.R;
import com.pp.domain.HelpArticle;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IHelpService extends IService<HelpArticle> {
    R getThreeHelpArticle();

    R getAllArticles();

    /**
     * 根据ID查找文章
     * @param id 帮助文章的id
     * @return 查找是否成功，如果查找成功，返回帮助文章的实例对象
     */
    R getArticleByID(String id);

    /**
     * 添加文章
     * @param file md文件
     * @param request http请求
     * */
    R addArticle(MultipartFile file, HttpServletRequest request);

    R modifyArticle();

    /**
     * 删除帮助文章
     * @param id 需要删除的文章的id
     * @return 是否删除成功
     */
    R deleteArticle(String id);

    R getRecommendArticles();
}