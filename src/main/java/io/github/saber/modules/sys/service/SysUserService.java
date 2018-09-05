package io.github.saber.modules.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.github.saber.modules.sys.domain.SysUser;
import io.github.saber.modules.sys.dto.UserDTO;
import io.github.saber.modules.sys.dto.UserQueryVO;
import io.github.saber.modules.sys.dto.UserVO;
import io.github.saber.modules.sys.dto.UserWithAuthoritiesDTO;

import java.util.List;
import java.util.Optional;

public interface SysUserService extends IService<SysUser> {
    Optional<UserWithAuthoritiesDTO> getUserWithAuthoritiesByLogin(String login);

    Optional<SysUser> getUserByLogin(String login);

    Optional<SysUser> getUserByLogin();

    Page<UserQueryVO> getAllUsers(Page page, String name);

    Optional<SysUser> getUserById(String userId);

    UserVO convert2UserVO(SysUser sysUser);

    /**
     * 修改用户信息
     *
     * @param userDTO
     * @return
     */
    Optional<UserVO> updateUser(UserDTO userDTO);

    SysUser createUser(UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param userId
     */
    void deleteUser(String userId);

    /**
     * 批量删除用户
     *
     * @param userIds
     */
    void batchDeleteUser(List<String> userIds);

    /**
     * 修改密码
     *
     * @param currentClearTextPassword
     * @param newPassword
     */
    void changePassword(String currentClearTextPassword, String newPassword);
}
