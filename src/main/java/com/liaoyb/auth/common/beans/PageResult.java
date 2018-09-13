package com.liaoyb.auth.common.beans;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.data.util.Streamable;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果（转换Page对象）
 *
 * @param <T>
 * @author liaoyanbo
 */
public class PageResult<T> extends Page<T> implements Streamable<T> {
    public PageResult() {
        super();
    }

    public PageResult(long total, int size, int current, List<T> records) {
        this.setTotal(total);
        this.setSize(size);
        this.setCurrent(current);
        this.setRecords(records);
    }

    @Override
    public Iterator<T> iterator() {
        return this.getRecords().iterator();
    }

    public static <T> PageResult<T> of(Page<T> page) {
        return new PageResult<T>(page.getTotal(), page.getSize(), page.getCurrent(), page.getRecords());
    }

    @Override
    public <U> PageResult<U> map(Function<? super T, ? extends U> converter) {
        return new PageResult<U>(this.getTotal(), this.getSize(), this.getCurrent(), this.getConvertedContent(converter));
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null!");
        return this.stream().map(converter::apply).collect(Collectors.toList());
    }
}
