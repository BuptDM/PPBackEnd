package com.pp.controller;


import com.pp.config.AppProperties;
import com.pp.controller.util.R;
import com.pp.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/file-test")
public class FileTestController {
    @Autowired
    AppProperties appProperties;
    @Autowired
    WebUtils webUtils;

    @PostMapping
    public R upload( @RequestParam("files")MultipartFile file){
        try{
            String fileName = file.getOriginalFilename();
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