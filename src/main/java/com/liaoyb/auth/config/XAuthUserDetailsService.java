package com.liaoyb.auth.config;

import com.liaoyb.auth.modules.sys.domain.SysRole;
import com.liaoyb.auth.modules.sys.domain.SysUser;
import com.liaoyb.auth.modules.sys.enums.UserStatusType;
import com.liaoyb.auth.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class XAuthUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户及权限
        return sysUserService.getUserWithAuthoritiesByLogin(username)
                .map(userWithAuthoritiesDTO -> {
                    List<GrantedAuthority> auths = new ArrayList<>();
                    //角色
                    for (SysRole sysRole : userWithAuthoritiesDTO.getSysRoles()) {
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + sysRole.getName());
                        auths.add(authority);
                    }
                    //权限
                    for (String permission : userWithAuthoritiesDTO.getPermissions()) {
                        auths.add(new SimpleGrantedAuthority(permission));
                    }

                    SysUser sysUser = userWithAuthoritiesDTO.getUser();
                    //是否禁用
                    UserStatusType statusType = UserStatusType.from(sysUser.getStatus());
                    return new XAuthUserDetails(sysUser, sysUser.getUsername(), sysUser.getPassword(), UserStatusType.NORMAL.equals(statusType), true, true, true, auths);
                })
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在:" + username));
    }
}
