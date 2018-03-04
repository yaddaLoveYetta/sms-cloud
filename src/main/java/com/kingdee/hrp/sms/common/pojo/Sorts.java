package com.kingdee.hrp.sms.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 列表查询排序条件对象
 */
public class Sorts implements Serializable {

    private List<Sort> sorts;

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    private class Sort implements Serializable {
        private String fieldKey;
        private DirectionEnum direction;

        public Sort(String fieldKey, DirectionEnum direction) {
            this.fieldKey = fieldKey;
            this.direction = direction;
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public DirectionEnum getDirection() {
            return direction;
        }

        public void setDirection(DirectionEnum direction) {
            this.direction = direction;
        }
    }

    private enum DirectionEnum implements Serializable {

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
