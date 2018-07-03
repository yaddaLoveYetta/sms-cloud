package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 列表查询排序条件对象
 * @author yadda
 */
@Getter
@Setter
@Accessors(chain = true)
public class Sort implements Serializable {

    private String fieldKey;
    private DirectionEnum direction;

    public Sort(String fieldKey, DirectionEnum direction) {
        this.fieldKey = fieldKey;
        this.direction = direction;
    }

    public enum DirectionEnum implements Serializable {

        ASC("asc", "升序"), DESC("desc", "降序"), NOT_SUPPORT("NONE", "不支持的排序方向");

        private String name;

        private String description;

        DirectionEnum(String name, String description) {
            this.name = name;
            this.description = description;
        }

        private static DirectionEnum getDirectionEnum(String name) {

            for (DirectionEnum directionEnum : DirectionEnum.values()) {
                if (directionEnum.name.equalsIgnoreCase(name)) {
                    return directionEnum;
                }
            }
            return DirectionEnum.NOT_SUPPORT;
        }

    }

}
