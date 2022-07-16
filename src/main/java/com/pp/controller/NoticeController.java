package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.INoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notices")
@Slf4j
public class NoticeController {
    final INoticeService noticeService;

    public NoticeController(INoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/getT")
    public R getNoticesT(){
        return noticeService.getThreeNotices();
    }

    @PostMapping("/getAll")
    public R getNotices(){
        return noticeService.getAllNotices();
    }

    @GetMapping("/getByID")
    public R getNotice(){
        return noticeService.getNoticeByID();
    }

    @PostMapping("/add")
    public R addNotice(){
        return noticeService.addNotice();
    }

    @PostMapping("/modify")
    public R modifyNotice() {
        return noticeService.modifyNotice();
    }

    @PostMapping("/delete")
    public R deleteNotice(){
        return noticeService.deleteNotice();
    }

    @PostMapping("/recommend")
    public R recommendNotice(){
        return noticeService.getRecommendNotice();
    }
}