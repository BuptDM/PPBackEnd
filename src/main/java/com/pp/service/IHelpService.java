package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.controller.util.R;
import com.pp.domain.HelpArticle;

public interface IHelpService extends IService<HelpArticle> {
    public R getThreeHelpArticle();

    public R getAllArticles();

    public R getArticleByID();

    public R addArticle();

    public R modifyArticle();

    public R deleteArticle();

    public R getRecommendArticles();
}