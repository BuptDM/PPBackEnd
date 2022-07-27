package com.pp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.config.AppProperties;
import com.pp.controller.util.R;
import com.pp.dao.IEARelationDao;
import com.pp.dao.IExperimentDao;
import com.pp.domain.EaRelation;
import com.pp.domain.Experiment;
import com.pp.service.IExperimentService;
import com.pp.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ExperimentServiceImpl extends ServiceImpl<IExperimentDao, Experiment> implements IExperimentService {
    @Autowired
    WebUtils webUtils;
    @Autowired
    IExperimentDao experimentDao;
    @Autowired
    AppProperties appProperties;
    @Autowired
    IEARelationDao ieaRelationDao;
    @Override
    public R getAllExperiment() {
        List<Experiment> list = experimentDao.selectList(null);
        if(list!=null){
            return R.ok().message("查找成功").data("list",list);
        }else{
            return R.error().message("服务器错误，请联系管理员");
        }
    }
    @Override
    public R addExperiment(MultipartFile file, HttpServletRequest request) {
        // 判断你传入的文件是不是为空
        if(file==null)
            return R.error().message("文件为空，请检查");
        // 如果不是markdown文件，返回提示信息
        if(!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".md")){
            return R.error().message("必须上传markdown文件");
        }
        // 生成新的文件名
        HashMap<String,Object> hashMap = new HashMap<>();
        String newFileName;
        do {
            newFileName = RandomStringUtils.randomAlphanumeric(10)+".md";
            hashMap.put("file_name",newFileName);
        }while(experimentDao.selectByMap(hashMap).size()!=0);
        // 创建Experiment对象
        Experiment experiment = new Experiment();
        experiment.setTitle(request.getParameter("title"));
        experiment.setSummary(request.getParameter("summary"));
        experiment.setFileName(newFileName);
        experiment.setFileUrl(webUtils.getStaticResourceUri("experiment", newFileName));
        experiment.setPostTime(new Timestamp(System.currentTimeMillis()));
        experimentDao.insert(experiment);
        // 添加实验-算法关联关系
        String str = request.getParameter("algorithmId");
        String[] algorithms = str.split(";");
        for (String algorithm : algorithms) {
            EaRelation eaRelation = new EaRelation();
            eaRelation.setExperimentId(experiment.getId());
            eaRelation.setAlgorithmId(Integer.parseInt(algorithm));
            ieaRelationDao.insert(eaRelation);
        }
        // 执行文件存储和数据库插入操作
        try {
            // 存储静态文件
            File directory = new File(appProperties.getExperimentFilePath());
            if (!directory.exists())
                directory.mkdir();
            File dest = new File(appProperties.getExperimentFilePath() + newFileName);
            file.transferTo(dest);
            log.info("成功添加实验，操作用户:"+ SecurityUtils.getSubject().getSession().getAttribute("userID"));
            return R.ok().message("存储成功").data("experiment",experiment);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("服务器内部错误，文件上传失败");
        }
    }

    @Override
    public R modifyExperiment(MultipartFile file, HttpServletRequest request) {
        try {
            // 获取前端传来的数据
            String id = request.getParameter("id");
            String title = request.getParameter("title");
            String summary = request.getParameter("summary");
            String algorithmIDs = request.getParameter("algorithmIDs");
            // 删除旧文件
            Experiment e = getById(id);
            new File(appProperties.getExperimentFilePath() + e.getFileName()).delete();
            // 保存新文件
            File newFile = new File(appProperties.getExperimentFilePath() + e.getFileName());
            file.transferTo(newFile);
            // 更新数据库记录
            e.setSummary(summary);
            e.setTitle(title);
            e.setPostTime(new Timestamp(System.currentTimeMillis()));
            this.updateById(e);
            // 更新EaRelation
            String[] algorithms = algorithmIDs.split(";");
            ieaRelationDao.delete(new LambdaQueryWrapper<EaRelation>().eq(EaRelation::getExperimentId,id));
            for (String algorithm : algorithms) {
                ieaRelationDao.insert(new EaRelation(Integer.parseInt(id),Integer.parseInt(algorithm)));
            }
            return R.ok().data("result",e);
        }catch (Exception e){
            log.info(Arrays.toString(e.getStackTrace()));
            return R.error().message("服务器内部异常，请检查");
        }
    }

    @Override
    public R deleteExperiment(String id) {
        try {
            /*返回信息给前端*/
            Experiment e = experimentDao.selectById(id);
            if (e == null)
                return R.error().message("实验id错误，请检查");
            /*返回信息给前端*/
            String fileName = e.getFileName();
            File file = new File(appProperties.getExperimentFilePath() + fileName);
            file.delete();
            /*删除数据库记录*/
            experimentDao.deleteById(id);
            /*删除实验和算法的相关性记录*/
            ieaRelationDao.delete(new LambdaQueryWrapper<EaRelation>().eq(EaRelation::getExperimentId, id));
            /*返回信息给前端*/
            return R.ok().message("删除成功");
        }catch (Exception e){
            log.info(Arrays.toString(e.getStackTrace()));
            return R.error().message("服务器内部错误");
        }
    }

    @Override
    public R selectByID(String id) {
        /*查找实验*/
        Experiment e = this.getById(id);
        /*判断实验是否为空*/
        if(e==null)
            return R.error().message("id错误，请检查!");
        /*返回值给前端*/
        return R.ok().data("result",e);
    }
}