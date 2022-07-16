package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.controller.util.R;
import com.pp.domain.Notice;

public interface INoticeService extends IService<Notice> {
    /**
     * 添加通知
     * */
    public R addNotice();


    /**
     *删除通知
     */
    public R deleteNotice();

    /**
     * 按照时间顺序，获取最近三条通知
     * */
    public R getThreeNotices();

    /**
     * 根据传前端传来的ID获取通知
     * */
    public R getNoticeByID();

    /**
     * 获取所有通知
     * */
    public R getAllNotices();

    /**
     * 直接返回按照时间循序最近的5条通知
     * */
    public R getRecommendNotice();

    /**
     * 修改通知
     * */
    public R modifyNotice();
}
