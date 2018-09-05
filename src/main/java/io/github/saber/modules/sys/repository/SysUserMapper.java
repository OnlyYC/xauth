package io.github.saber.modules.sys.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.github.saber.modules.sys.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
