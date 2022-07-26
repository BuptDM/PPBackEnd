package com.pp.service.impl;

import com.pp.algorithm.KMeans;
import com.pp.config.AppProperties;
import com.pp.controller.util.R;
import com.pp.dao.IAlgorithmCallDao;
import com.pp.dao.IAlgorithmDao;
import com.pp.domain.Algorithm;
import com.pp.domain.AlgorithmCall;
import com.pp.service.IAlgorithmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AlgorithmServiceImpl implements IAlgorithmService {
    @Autowired
    AppProperties appProperties;
    @Autowired
    IAlgorithmCallDao algorithmCallDao;
    @Autowired
    IAlgorithmDao algorithmDao;
    @Override
    public R kmeans(MultipartFile file, HttpServletRequest request) {
        try {
            // 检查文件格式
            if(file==null){
                return R.error().message("请上传数据");
            }else if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv"))
                return R.error().message("数据仅支持csv格式");
            // 判断数据存储文件夹是否存在
            File directory = new File(appProperties.getAlgorithmDataPath());
            if (!directory.exists())
                directory.mkdir();
            // 将csv文件存储在本地
            String fileName = RandomStringUtils.randomAlphanumeric(5) + System.currentTimeMillis();
            String csvFilePath = appProperties.getAlgorithmDataPath() + fileName + ".csv";
            File csvFile = new File(csvFilePath);
            file.transferTo(csvFile);// IO写
            // 加载csv文件，转换成arff格式的数据
            Instances allData = ConverterUtils.DataSource.read(csvFilePath); //IO 读
            ArffSaver saver = new ArffSaver();
            saver.setInstances(allData);
            // 开始聚类
            KMeans kMeans = new KMeans();
            kMeans.setParam(request);
            kMeans.setData(saver.getInstances());
            kMeans.run();
            // 生成数据库记录
            AlgorithmCall algorithmCall = new AlgorithmCall();
            algorithmCall.setStudentId((String)SecurityUtils.getSubject().getSession().getAttribute("userID"));
            algorithmCall.setAlgorithmId(KMeans.algorithmID);
            algorithmCall.setParam(kMeans.getParamJsonStr());
            algorithmCall.setResult(kMeans.getResult());
            algorithmCall.setPostTime(new Timestamp(System.currentTimeMillis()));
            algorithmCall.setScore(kMeans.getScore());
            // 将算法调用记录存储到数据库中
            algorithmCallDao.insert(algorithmCall);
            // 返回数据给前端
            return R.ok().message("success").data("result", kMeans.getResult());
        } catch (Exception e){
            log.error("后端捕获到异常，"+ Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            return R.error().message("后端服务器异常，请联系管理员");
        }
    }

    @Override
    public List<Algorithm> allAlgorithm() {
        return algorithmDao.selectList(null);
    }
}