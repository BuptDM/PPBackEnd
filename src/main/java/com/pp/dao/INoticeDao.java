package com.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.domain.Notice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface INoticeDao extends BaseMapper<Notice> {
}
