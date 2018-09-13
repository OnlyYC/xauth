package com.liaoyb.auth.modules.sys.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liaoyb.auth.modules.sys.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
