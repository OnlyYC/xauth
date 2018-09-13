package com.liaoyb.auth.common.beans;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 分页表单对象
 *
 * @author liaoyanbo
 */
@Data
public class PageForm {
    private static final String ASC = "asc";
    private static final String DESC = "desc";
    @NotNull
    private Integer limit = 10;

    @NotNull
    private Integer page = 1;
    /**
     * 排序:如id asc(只能单个属性排序)
     */
    @Pattern(regexp = ".*\\s+(desc|asc)", message = "排序参数有误(eg:id desc)")
    private String order;

    public <T> Page<T> toPage() {
        Page<T> page = new Page<T>(this.page, this.limit);
        if (StringUtils.isNotBlank(order)) {
            String[] orders = StringUtils.splitByWholeSeparator(order, null);
            if (ASC.equals(orders[1])) {
                page.setAscs(Lists.newArrayList(orders[0]));
            } else {
                page.setDescs(Lists.newArrayList(orders[0]));
            }
        }

        return page;
    }
}
