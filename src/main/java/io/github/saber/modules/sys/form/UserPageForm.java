package io.github.saber.modules.sys.form;

import io.github.saber.common.beans.PageForm;
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
