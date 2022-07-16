package com.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.domain.HelpArticle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHelpDao extends BaseMapper<HelpArticle> {
}
