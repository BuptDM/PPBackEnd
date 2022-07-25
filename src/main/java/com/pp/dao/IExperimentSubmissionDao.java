package com.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.domain.ExperimentSubmission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IExperimentSubmissionDao extends BaseMapper<ExperimentSubmission> {
}
