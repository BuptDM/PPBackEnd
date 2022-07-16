package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.controller.util.R;
import com.pp.dao.IHelpDao;
import com.pp.domain.HelpArticle;
import com.pp.service.IHelpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HelpServiceImpl extends ServiceImpl<IHelpDao, HelpArticle> implements IHelpService {
    @Override
    public R getThreeHelpArticle() {
        return null;
    }

    @Override
    public R getAllArticles() {
        return null;
    }

    @Override
    public R getArticleByID() {
        return null;
    }

    @Override
    public R addArticle() {
        return null;
    }

    @Override
    public R modifyArticle() {
        return null;
    }

    @Override
    public R deleteArticle() {
        return null;
    }

    @Override
    public R getRecommendArticles() {
        return null;
    }
}
