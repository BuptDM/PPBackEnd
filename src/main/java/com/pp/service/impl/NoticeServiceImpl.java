package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.config.AppProperties;
import com.pp.controller.util.R;
import com.pp.dao.INoticeDao;
import com.pp.domain.Notice;
import com.pp.service.INoticeService;
import com.pp.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
public class NoticeServiceImpl extends ServiceImpl<INoticeDao, Notice> implements INoticeService {

    private final INoticeDao noticeDao;
    private final WebUtils webUtils;
    private final AppProperties appProperties;

    public NoticeServiceImpl(INoticeDao noticeDao, WebUtils webUtils, AppProperties appProperties) {
        this.noticeDao = noticeDao;
        this.webUtils = webUtils;
        this.appProperties = appProperties;
    }

    @Override
    public R addNotice(MultipartFile file, HttpServletRequest request) {
        System.out.println("11111111");
        // 判断你传入的文件是不是为空
        if(file==null)
            return R.error().message("文件为空，请检查");
        // 生成新的文件名
        HashMap<String,Object> hashMap = new HashMap<>();
        String newFileName;
        do {
            newFileName = RandomStringUtils.randomAlphanumeric(10)+".md";
            hashMap.put("file_name",newFileName);
        }while(noticeDao.selectByMap(hashMap).size()!=0);
        // 创建Notice对象
        Notice notice = new Notice();
        notice.setTitle(request.getParameter("title"));
        notice.setSummary(request.getParameter("summary"));
        notice.setFileName(newFileName);
        notice.setUrl(webUtils.getStaticResourceUri("notice", newFileName));
        notice.setPostTime(new Timestamp(System.currentTimeMillis()));
        // 执行文件存储和数据库插入操作
        try {
            // 存储静态文件
            File directory = new File(appProperties.getNoticeFilePath());
            if (!directory.exists())
                directory.mkdir();
            File dest = new File(appProperties.getNoticeFilePath() + newFileName);
            file.transferTo(dest);
            // 将相关记录存入数据库
            noticeDao.insert(notice);
            log.info(notice.toString());
            return R.ok().message("存储成功").data("notice",notice);
        }catch (Exception e){
            return R.error().message("服务器内部错误，文件上传失败");
        }
    }

    @Override
    public R deleteNotice(String id) {
        // 从数据库中获得待删除通知的实例对象
        Notice notice = noticeDao.selectById(id);
        if(notice==null)
            return R.error().message("通知不存在");
        // 从数据库中删除记录
        noticeDao.deleteById(id);
        // 删除文件
        String fileName = notice.getFileName();
        File file = new File(appProperties.getNoticeFilePath()+fileName);
        file.delete();
        return R.ok().message("删除成功");
    }

    @Override
    public R getThreeNotices() {
        // 拿到数据库中所有的文章信息
        List<Notice> list = noticeDao.selectList(null);
        if(list!=null){
            // 按照发表时间进行排序
            list.sort(Comparator.comparing(Notice::getPostTime));
            return R.ok().message("查找成功").data("list",list.size()<=3?list:list.subList(list.size()-3,list.size()));
        }else{
            return R.error().message("服务器错误，请联系管理员");
        }
    }

    @Override
    public R getNoticeByID(String id) {
        Notice notice = noticeDao.selectById(id);
        if(notice==null){
            return R.error().message("通知不存在");
        }else{
            return R.ok().message("查找成功").data("notice",notice);
        }
    }

    @Override
    public R getAllNotices() {
        List<Notice> noticeList = noticeDao.selectList(null);
        if(noticeList!= null){
            return R.ok().message("查找成功").data("list",noticeList);
        }else {
            return R.error().message("服务器错误，请联系管理员");
        }
    }

    @Override
    public R getRecommendNotice(String id) {
        List<Notice> noticeList = noticeDao.selectList(null);
        if(noticeList!=null){
            // 去除当前文章
            for(int i=0;i<noticeList.size();i++){
                if(noticeList.get(i).getId()==Integer.parseInt(id)){
                    noticeList.remove(i);
                    break;
                }
            }
            // 按照发表时间进行排序
            noticeList.sort(Comparator.comparing(Notice::getPostTime));
            // 返回值给前端
            return R.ok().message("查找成功").data("noticeList",noticeList.size()<=3?noticeList:noticeList.subList(noticeList.size()-3,noticeList.size()));
        }else{
            return R.error().message("服务器错误，请联系管理员");
        }
    }

    @Override
    public R modifyNotice(MultipartFile file,HttpServletRequest request) {
        // 获取前端传输的参数
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String summary = request.getParameter("summary");
        // 根据id从数据库中获取到通知实例对象
        Notice notice = noticeDao.selectById(id);
        if(notice==null)
            return R.error().message("id不正确");
        // 删除旧文件
        new File(appProperties.getNoticeFilePath()+notice.getFileName()).delete();
        // 生成新的文件名
        HashMap<String,Object> hashMap = new HashMap<>();
        String newFileName;
        do {
            newFileName = RandomStringUtils.randomAlphanumeric(10)+".md";
            hashMap.put("file_name",newFileName);
        }while(noticeDao.selectByMap(hashMap).size()!=0);
        // 存储静态文件
        File directory = new File(appProperties.getNoticeFilePath());
        if (!directory.exists())
            directory.mkdir();
        File dest = new File(appProperties.getNoticeFilePath() + newFileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 更新数据库
        notice.setPostTime(new Timestamp(System.currentTimeMillis()));
        notice.setUrl(webUtils.getStaticResourceUri("notice",newFileName));
        notice.setFileName(newFileName);
        notice.setSummary(summary);
        notice.setTitle(title);
        noticeDao.updateById(notice);
        // 返回数据库记录
        return R.ok().message("修改成功").data("newNotice",notice);
    }
}