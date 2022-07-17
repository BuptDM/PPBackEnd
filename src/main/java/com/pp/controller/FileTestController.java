package com.pp.controller;

import com.pp.config.AppProperties;
import com.pp.controller.util.R;
import com.pp.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/file-test")
public class FileTestController {
    final AppProperties appProperties;
    final WebUtils webUtils;
    public FileTestController(AppProperties appProperties, WebUtils webUtils) {
        this.appProperties = appProperties;
        this.webUtils = webUtils;
    }

    @PostMapping
    public R upload(MultipartFile file, HttpServletRequest request){
        if(file==null){
            return R.error().message("没有上传文件");
        }
        log.info(request.getParameter("test"));
        try{
            String fileName = file.getOriginalFilename();
            File directory = new File(appProperties.getTestFilePath());
            if(!directory.exists()){
                directory.mkdir();
            }
            File dest = new File(appProperties.getTestFilePath()+fileName);
            file.transferTo(dest);
            assert fileName != null;
            return R.ok().message("文件上传成功").data("filePath",webUtils.getStaticResourceUri("test",fileName));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("服务器错误，文件上传失败");
            return R.error().message("文件上传失败");
        }
    }
}