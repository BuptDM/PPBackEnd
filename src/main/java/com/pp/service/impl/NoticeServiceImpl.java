package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.controller.util.R;
import com.pp.dao.INoticeDao;
import com.pp.domain.Notice;
import com.pp.service.INoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class NoticeServiceImpl extends ServiceImpl<INoticeDao, Notice> implements INoticeService {
    @Override
    public R addNotice() {
        return null;
    }

    @Override
    public R deleteNotice() {
        return null;
    }

    @Override
    public R getThreeNotices() {
        return null;
    }

    @Override
    public R getNoticeByID() {
        return null;
    }

    @Override
    public R getAllNotices() {
        return null;
    }

    @Override
    public R getRecommendNotice() {
        return null;
    }

    @Override
    public R modifyNotice() {
        return null;
    }
}