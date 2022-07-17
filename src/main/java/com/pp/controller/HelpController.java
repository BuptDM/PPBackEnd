package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IHelpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 根据ID查找帮助文章
     * @param id 文章 的id
     * */
    @PostMapping("/getByID")
    public R getArticleByID(String id){
        return helpService.getArticleByID(id);
    }

    /**
     * 添加帮助文章，传入文件、标题、摘要
     * @param file 传输的文件
     * @param request 以form-data形式传参，除了file之外， 还需要传递summary,title
     * */
    @PostMapping("/add")
    public R addArticle(MultipartFile file, HttpServletRequest request){
        return helpService.addArticle(file,request);
    }

    @PostMapping("/modify")
    public R modifyArticle() {
        return helpService.modifyArticle();
    }

    /**
     * 删除通知文件
     * @param id  需要删除的帮助文章的 id
     * */
    @PostMapping("/delete")
    public R deleteArticle(String id){
        return helpService.deleteArticle(id);
    }

    @PostMapping("/recommend")
    public R recommendArticles(){
        return helpService.getRecommendArticles();
    }
}