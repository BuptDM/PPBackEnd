package com.pp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pp")
@Data
public class AppProperties {
    String testFilePath; // 测试文件的路径
    String fileDirectory;// 静态文件的根目录
    String serverIp; // 服务器IP地址
    @Value("${server.port}")
    String port; // 程序运行的端口
}