package com.pp.utils;

import com.pp.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class WebUtils {
    final AppProperties appProperties;

    public WebUtils(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String getResourceUri(String directory,String fileName){
        String ret = "http://";
        ret+=appProperties.getServerIp()+":"+ appProperties.getPort()+ "/files/" + directory + "/" + fileName;
        return ret;
    }
}
