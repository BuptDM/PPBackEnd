package com.pp.utils;

import com.pp.config.AppProperties;
import org.springframework.stereotype.Component;

@Component
public class WebUtils {
    final AppProperties appProperties;

    public WebUtils(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * 输入静态文件的分类【files下的子路径】和文件名，返回静态文件路径
     * @param directory 子路径
     * @param fileName 文件名
     * @return String 静态文件的访问路径
     * */
    public  String getStaticResourceUri(String directory, String fileName){
        String ret = "http://";
        ret+=appProperties.getServerIp()+":"+ appProperties.getPORT()+ "/files/" + directory + "/" + fileName;
        return ret;
    }
}