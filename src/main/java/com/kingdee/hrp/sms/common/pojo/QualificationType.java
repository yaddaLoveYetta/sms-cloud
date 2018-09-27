package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 应该对医院提供的证件类别及当前是否已提供
 * 来自于医院供应商资质类别设置
 * @author le.xiao
 */
@Setter
@Getter
@Accessors(chain = true)
public class QualificationType {

    /**
     * 证件类型
     */
    private Long type;

    /**
     * 证件名称
     */
    private String name;

    /**
     * 是否已提供当前类别证件
     */
    private Boolean isExist;

    /**
     * 是否医院要求必须提供的类别
     */
    private Boolean isMust;

    public QualificationType() {
    }

    public QualificationType(Long type, String name, Boolean isExist) {
        this.type = type;
        this.name = name;
        this.isExist = isExist;
    }

    public QualificationType(String name, Boolean isExist) {
        this.name = name;
        this.isExist = isExist;
    }

}
