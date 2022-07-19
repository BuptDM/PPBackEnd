package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.INoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/notices")
@Slf4j
public class NoticeController {
    final INoticeService noticeService;
    public NoticeController(INoticeService noticeService) {
        this.noticeService = noticeService;
    }
    /**
     * 通知概览，获取最近发布的三条数据
     * @return 最近发布的三条数据
     */
    @PostMapping("/getT")
    public R getNoticesT(){
        return noticeService.getThreeNotices();
    }
    /**
     * 获取当前所有的通知文章，无参数
     * @return 返回所有的通知文章
     */
    @PostMapping("/getAll")
    public R getNotices(){
        return noticeService.getAllNotices();
    }

    /**
     * 根据ID查找通知
     * @param id 通知 的id
     * */
    @PostMapping("/getByID")
    public R getNotice(String id){
        return noticeService.getNoticeByID(id);
    }
    /**
     * 添加通知，传入文件、标题、摘要
     * @param file 传输的文件
     * @param request 以form-data形式传参，除了file之外， 还需要传递summary,title
     * */
    @PostMapping("/add")
    public R addNotice(MultipartFile file, HttpServletRequest request){
        return noticeService.addNotice(file,request);
    }

    /**
     * 修改通知
     * @param  file 修改后的文件
     * @return 返回是否修改成功，以及修改后的数据库记录;request中包含filename,id,title,summary
     */
    @PostMapping("/modify")
    public R modifyNotice(MultipartFile file,HttpServletRequest request) {
        return noticeService.modifyNotice(file,request);
    }

    /**
     * 删除通知文件
     * @param id  需要删除的通知的 id
     * */
    @PostMapping("/delete")
    public R deleteNotice(String id){
        return noticeService.deleteNotice(id);
    }

    /**
     * 获取推荐通知列表
     * @return 暂时返回最近发布的三篇文章
     */
    @PostMapping("/recommend")
    public R recommendNotice(String id){
        return noticeService.getRecommendNotice(id);
    }
}