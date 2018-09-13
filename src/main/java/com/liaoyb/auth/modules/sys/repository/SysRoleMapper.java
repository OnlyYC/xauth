package com.liaoyb.auth.modules.sys.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liaoyb.auth.modules.sys.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> getUserRole(String userId);
}
