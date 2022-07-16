package com.pp.domain;

import lombok.Data;
import java.sql.Timestamp;

/**
 * 通知类
 * @author lq
 * */
@Data
public class Notice {
    private int id;
    private String title;//　标题
    private String summary;// 文章摘要
    private Timestamp postTime;// 通知发表的时间
    private String fileName;// 文件名
    private String url;// 静态文件的资源路径
}