package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IHelpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/help")
public class HelpController {
    final IHelpService helpService;

    public HelpController(IHelpService helpService) {
        this.helpService = helpService;
    }

    @PostMapping("/getT")
    public R getHelpArticleT(){
        return helpService.getThreeHelpArticle();
    }

    @PostMapping("/getAll")
    public R getAllArticles(){
        return helpService.getAllArticles();
    }

    @GetMapping("/getByID")
    public R getArticleByID(){
        return helpService.getArticleByID();
    }

    @PostMapping("/add")
    public R addArticle(){
        return helpService.addArticle();
    }

    @PostMapping("/modify")
    public R modifyArticle() {
        return helpService.modifyArticle();
    }

    @PostMapping("/delete")
    public R deleteArticle(){
        return helpService.deleteArticle();
    }

    @PostMapping("/recommend")
    public R recommendArticles(){
        return helpService.getRecommendArticles();
    }
}
