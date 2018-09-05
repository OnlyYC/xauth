package io.github.saber.modules.sys.enums;

/**
 * 权限类型 0：目录   1：菜单   2：按钮
 *
 * @author liaoyanbo
 */
public enum PermissionType {
    CATALOG(0),
    MENU(1),
    BUTTON(2);
    private int value;

    PermissionType(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static PermissionType from(Integer value) {
        if (value != null) {
            for (PermissionType type : values()) {
                if (Integer.compare(value, type.getValue()) == 0) {
                    return type;
                }
            }
        }
        return null;
    }
}
