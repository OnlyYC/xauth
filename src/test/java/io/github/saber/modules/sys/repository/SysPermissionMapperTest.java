package io.github.saber.modules.sys.repository;

import io.github.saber.SaberApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class SysPermissionMapperTest extends SaberApplicationTests {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void test() {
        //插入
        System.out.println(passwordEncoder.encode("123456"));

    }
}
