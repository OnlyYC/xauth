package com.liaoyb.auth.modules.sys.form;

import com.liaoyb.auth.common.beans.PageForm;
import lombok.Data;

/**
 * 用户表单查询
 *
 * @author liaoyanbo
 */
@Data
public class UserPageForm extends PageForm {
    private String username;
}
