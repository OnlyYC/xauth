package com.liaoyb.auth.modules.sys.repository;

import com.liaoyb.auth.XAuthApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class SysPermissionMapperTest extends XAuthApplicationTests {
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
