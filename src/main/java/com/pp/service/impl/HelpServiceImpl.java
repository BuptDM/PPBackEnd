package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.config.AppProperties;
import com.pp.controller.util.R;
import com.pp.dao.IHelpDao;
import com.pp.domain.HelpArticle;
import com.pp.service.IHelpService;
import com.pp.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;

@Service
@Slf4j
public class HelpServiceImpl extends ServiceImpl<IHelpDao, HelpArticle> implements IHelpService {
    final WebUtils webUtils;
    final IHelpDao helpDao;
    final AppProperties appProperties;
    public HelpServiceImpl(WebUtils webUtils, IHelpDao helpDao, AppProperties appProperties) {
        this.webUtils = webUtils;
        this.helpDao = helpDao;
        this.appProperties = appProperties;
    }
    @Override
    public R getThreeHelpArticle() {
        return null;
    }
    @Override
    public R getAllArticles() {
        return null;
    }
    @Override
    public R getArticleByID(String id) {
        HelpArticle helpArticle = helpDao.selectById(id);
        if(helpArticle==null){
            return R.error().message("文章不存在");
        }else{
            return R.ok().message("查找成功").data("article",helpArticle);
        }
    }
    @Override
    public R addArticle(MultipartFile file, HttpServletRequest request) {
        // 判断你传入的文件是不是为空
        if(file==null)
            return R.error().message("文件为空，请检查");
        // 生成新的文件名
        HashMap<String,Object> hashMap = new HashMap<>();
        String newFileName;
        do {
            newFileName = RandomStringUtils.randomAlphanumeric(10)+".md";
            hashMap.put("file_name",newFileName);
        }while(helpDao.selectByMap(hashMap).size()!=0);
        // 创建HelpArticle对象
        HelpArticle helpArticle = new HelpArticle();
        helpArticle.setTitle(request.getParameter("title"));
        helpArticle.setSummary(request.getParameter("summary"));
        helpArticle.setFileName(newFileName);
        helpArticle.setUrl(webUtils.getStaticResourceUri("help", newFileName));
        helpArticle.setPostTime(new Timestamp(System.currentTimeMillis()));
        // 执行文件存储和数据库插入操作
        try {
            // 存储静态文件
            File directory = new File(appProperties.getHelpFilePath());
            if (!directory.exists())
                directory.mkdir();
            File dest = new File(appProperties.getHelpFilePath() + newFileName);
            file.transferTo(dest);
            // 将相关记录存入数据库
            helpDao.insert(helpArticle);
            log.info(helpArticle.toString());
            return R.ok().message("存储成功").data("article",helpArticle);
        }catch (Exception e){
            return R.error().message("服务器内部错误，文件上传失败");
        }
    }
    @Override
    public R modifyArticle() {
        return null;
    }
    @Override
    public R deleteArticle(String id) {
        // 从数据库中获得待删除文章的实例对象
        HelpArticle helpArticle = helpDao.selectById(id);
        if(helpArticle==null)
            return R.error().message("文章不存在");
        // 从数据库中删除记录
        helpDao.deleteById(id);
        // 删除文件
        String fileName = helpArticle.getFileName();
        File file = new File(appProperties.getHelpFilePath()+fileName);
        file.delete();
        return R.ok().message("删除成功");
    }
    @Override
    public R getRecommendArticles() {
        return null;
    }
}
