package io.github.saber.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.github.saber.common.beans.PageResult;
import io.github.saber.common.utils.DateUtils;
import io.github.saber.modules.sys.domain.SysPermission;
import io.github.saber.modules.sys.domain.SysRole;
import io.github.saber.modules.sys.domain.SysUser;
import io.github.saber.modules.sys.domain.SysUserRole;
import io.github.saber.modules.sys.dto.UserDTO;
import io.github.saber.modules.sys.dto.UserQueryVO;
import io.github.saber.modules.sys.dto.UserVO;
import io.github.saber.modules.sys.dto.UserWithAuthoritiesDTO;
import io.github.saber.modules.sys.enums.PermissionType;
import io.github.saber.modules.sys.enums.UserStatusType;
import io.github.saber.modules.sys.repository.SysUserMapper;
import io.github.saber.modules.sys.service.SysPermissionService;
import io.github.saber.modules.sys.service.SysRoleService;
import io.github.saber.modules.sys.service.SysUserRoleService;
import io.github.saber.modules.sys.service.SysUserService;
import io.github.saber.security.AuthoritiesConstants;
import io.github.saber.security.SecurityUtils;
import io.github.saber.web.errors.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 系统用户
 *
 * @author liaoyanbo
 */
@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public Optional<SysUser> getUserByLogin(String login) {
        SysUser query = new SysUser();
        query.setUsername(login);
        //用户
        return Optional.ofNullable(this.baseMapper.selectOne(query));
    }

    @Override
    public Optional<SysUser> getUserByLogin() {
        return SecurityUtils.getCurrentUserLogin().flatMap(this::getUserByLogin);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<UserQueryVO> getAllUsers(Page page, String name) {
        Page<SysUser> sysUserPage = this.selectPage(page,
                Optional.ofNullable(name)
                        .map(username -> new EntityWrapper<SysUser>().like("username", username))
                        .orElse(new EntityWrapper<SysUser>()));

        return PageResult.of(sysUserPage).map(UserQueryVO::new);
    }

    @Override
    public Optional<SysUser> getUserById(String userId) {
        return Optional.ofNullable(this.selectById(userId));
    }

    @Override
    public UserVO convert2UserVO(SysUser sysUser) {
        //转换为UserWithAuthoritiesDTO
        UserWithAuthoritiesDTO userWithAuthoritiesDTO = getUserWithAuthorities(sysUser);
        //转换为UserDTO
        UserVO userVO = new UserVO();

        userVO.setId(userWithAuthoritiesDTO.getUser().getId());
        userVO.setUsername(userWithAuthoritiesDTO.getUser().getUsername());
        userVO.setEmail(userWithAuthoritiesDTO.getUser().getEmail());
        userVO.setName(userWithAuthoritiesDTO.getUser().getName());
        userVO.setMobile(userWithAuthoritiesDTO.getUser().getMobile());
        userVO.setStatus(userWithAuthoritiesDTO.getUser().getStatus());
        userVO.setMenuList(userWithAuthoritiesDTO.getMenuList());
        userVO.setPermissions(userWithAuthoritiesDTO.getPermissions());
        userVO.setRoles(userWithAuthoritiesDTO.getSysRoles());
        return userVO;
    }

    @Override
    public Optional<UserVO> updateUser(UserDTO userDTO) {
        return Optional.of(this.selectById(userDTO.getId()))
                .map(user -> {
                    //基本信息
                    this.updateById(userDTO.toUser());
                    //删除之前的角色
                    sysUserRoleService.delete(new EntityWrapper<SysUserRole>().eq("user_id", user.getId()));
                    //角色
                    List<SysUserRole> userRoles = userDTO.toUserRole(userDTO.getId());
                    if (!CollectionUtils.isEmpty(userRoles)) {
                        sysUserRoleService.insertBatch(userRoles);
                    }

                    return user;
                })
                .map(this::convert2UserVO);
    }

    @Override
    public SysUser createUser(UserDTO userDTO) {
        SysUser sysUser = userDTO.toUser();
        sysUser.setStatus(UserStatusType.NORMAL.getValue());
        String encryptedPassword = passwordEncoder.encode(AuthoritiesConstants.DEFAULT_USER_PASSWORD);
        sysUser.setPassword(encryptedPassword);
        sysUser.setCreateTime(DateUtils.now());
        this.insert(sysUser);
        //角色
        List<SysUserRole> userRoles = userDTO.toUserRole(sysUser.getId());
        if (!CollectionUtils.isEmpty(userRoles)) {
            sysUserRoleService.insertBatch(userRoles);
        }

        log.debug("Created Information for User: {}", sysUser);
        return sysUser;
    }

    @Override
    public void deleteUser(String userId) {
        this.deleteById(userId);
        //删除用户角色
        sysUserRoleService.delete(new EntityWrapper<SysUserRole>().eq("user_id", userId));
        log.debug("Deleted User: {}", userId);
    }

    @Override
    public void batchDeleteUser(List<String> userIds) {
        Assert.notEmpty(userIds, "userIds must not empty");
        for (String userId : userIds) {
            deleteUser(userId);
        }
    }

    @Override
    public void changePassword(String currentClearTextPassword, String newPassword) {
        this.getUserByLogin().ifPresent(user -> {
            String currentEncryptedPassword = user.getPassword();
            //当前密码是否正确
            if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                throw new InvalidPasswordException();
            }
            //保存新密码
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            this.updateById(user);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserWithAuthoritiesDTO> getUserWithAuthoritiesByLogin(String login) {
        SysUser query = new SysUser();
        query.setUsername(login);
        //用户
        SysUser sysUser = this.baseMapper.selectOne(query);
        return Optional.ofNullable(sysUser).map(this::getUserWithAuthorities);
    }

    private UserWithAuthoritiesDTO getUserWithAuthorities(SysUser user) {
        //权限
        List<SysPermission> userPermissions = sysPermissionService.getUserPermission(user.getId());

        //角色
        List<SysRole> sysRoles = sysRoleService.getUserRole(user.getId());

        UserWithAuthoritiesDTO userWithAuthoritiesDTO = new UserWithAuthoritiesDTO();
        userWithAuthoritiesDTO.setUser(user);
        userWithAuthoritiesDTO.setSysRoles(sysRoles);
        userWithAuthoritiesDTO.setPermissionList(userPermissions);

        List<SysPermission> menus = Lists.newArrayList();
        Set<String> permissions = Sets.newHashSet();

        //权限、菜单
        userPermissions.forEach(sysPermission -> {
            PermissionType permissionType = PermissionType.from(sysPermission.getType());
            if (PermissionType.MENU.equals(permissionType) || PermissionType.CATALOG.equals(permissionType)) {
                menus.add(sysPermission);
            } else {
                permissions.addAll(Arrays.asList(StringUtils.splitByWholeSeparator(sysPermission.getPerms(), ",")));
            }
        });
        userWithAuthoritiesDTO.setMenuList(menus);
        userWithAuthoritiesDTO.setPermissions(permissions);

        return userWithAuthoritiesDTO;
    }
}
