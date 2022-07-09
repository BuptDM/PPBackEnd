package com.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pp.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDao extends BaseMapper<User> {
}