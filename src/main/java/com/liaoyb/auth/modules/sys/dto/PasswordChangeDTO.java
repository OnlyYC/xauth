package com.liaoyb.auth.modules.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 修改密码数据对象
 *
 * @author liaoyanbo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {
    private String currentPassword;
    @Length(min = 6, max = 20, message = "密码长度需6-20位")
    private String newPassword;
}
