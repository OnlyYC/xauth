package com.liaoyb.auth.modules.sys.enums;

/**
 * 用户状态  0：正常   1：禁用
 *
 * @author liaoyanbo
 */
public enum UserStatusType {
    NORMAL(0),
    DISABLE(1);
    private int value;

    UserStatusType(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static UserStatusType from(Integer value) {
        if (value != null) {
            for (UserStatusType type : values()) {
                if (Integer.compare(value, type.getValue()) == 0) {
                    return type;
                }
            }
        }
        return null;
    }
}
