package io.github.saber.common.beans;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量删除表单数据
 *
 * @author liaoyanbo
 */
@Data
public class BatchDeleteForm {
    @NotEmpty
    private List<String> ids;
}
