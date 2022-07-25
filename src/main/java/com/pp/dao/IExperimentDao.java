package com.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.domain.Experiment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IExperimentDao extends BaseMapper<Experiment> {
}
