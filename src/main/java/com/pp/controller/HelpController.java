package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IHelpService;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 首页概览，获取最近发布的三条数据
     * @return 最近发布的三条数据
     */
    @PostMapping("/getT")
    public R getHelpArticleT(){
        return helpService.getThreeHelpArticle();
    }

    /**
     * 获取当前所有的通知文章，无参数
     * @return 返回所有的通知文章
     */
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

    /**
     * 修改文章
     * @param  file 修改后的文件
     * @return 返回是否修改成功，以及修改后的数据库记录;request中包含filename,id,title,summary
     */
    @PostMapping("/modify")
    public R modifyArticle(MultipartFile file, HttpServletRequest request) {
        return helpService.modifyArticle(file,request);
    }

    /**
     * 删除通知文件
     * @param id  需要删除的帮助文章的 id
     * */
    @PostMapping("/delete")
    public R deleteArticle(String id){
        return helpService.deleteArticle(id);
    }


    /**
     * 获取推荐文章列表
     * @return 暂时返回最近发布的三篇文章
     */
    @PostMapping("/recommend")
    public R recommendArticles(String id){
        return helpService.getRecommendArticles(id);
    }
}