package com.pp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "pp")
@Data
public class AppProperties {
    String fileDirectory;// 静态文件的根目录
    String noticeFilePath;// 通知文章静态路径
    String helpFilePath;// 帮助文章静态路径
    String experimentFilePath;//  实验指导书路径
    String experimentSubmissionPath;// 作业提交文件路径
    String algorithmDataPath; //算法调用数据文件路径
    String serverIp; // 服务器IP地址
    @Value("${server.port}")
    String PORT; // 程序运行的端口
}