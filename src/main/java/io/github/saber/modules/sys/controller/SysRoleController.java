package io.github.saber.modules.sys.controller;

import io.github.saber.modules.sys.domain.SysRole;
import io.github.saber.modules.sys.service.SysRoleService;
import io.github.saber.security.AuthoritiesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户角色管理
 *
 * @author liaoyanbo
 */
@RestController
@RequestMapping("/api/sys")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * @return 所有角色列表
     */
    @GetMapping("/roles")
    @Secured(AuthoritiesConstants.ADMIN)
    public List<SysRole> getRoles() {
        return sysRoleService.getRoles();
    }
}
