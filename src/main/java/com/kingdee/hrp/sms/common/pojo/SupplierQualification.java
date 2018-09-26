package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 供应商查看对医院提供的供应商资质后台返回模型
 *
 * @author yadda
 */
@Setter
@Getter
@Accessors(chain = true)
public class SupplierQualification {

    /**
     * 对医院提供的证件列表明细
     */
    private List<Qualification> detail;
    /**
     * 应该对医院提供的证件类别及当前是否已提供信息
     */
    private List<Type> types;

    @Setter
    @Getter
    @Accessors(chain = true)
    public class Qualification {
        /**
         * 证件代码
         */
        private String number;
        /**
         * 证件类型
         */
        private Long type;
        /**
         * 发证机关
         */
        private String issue;
        /**
         * 证件有效期开始
         */
        private Date validityPeriodBegin;
        /**
         * 证件有效期结束
         */
        private Date validityPeriodEnd;

        public Qualification() {
        }

        public Qualification(String number, Long type, String issue, Date validityPeriodBegin, Date validityPeriodEnd) {
            this.number = number;
            this.type = type;
            this.issue = issue;
            this.validityPeriodBegin = validityPeriodBegin;
            this.validityPeriodEnd = validityPeriodEnd;
        }
    }

    /**
     * 应该对医院提供的证件类别及当前是否已提供
     * 来自于医院供应商资质类别设置
     */
    @Setter
    @Getter
    @Accessors(chain = true)
    public class Type {

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

        public Type() {
        }

        public Type(Long type, String name, Boolean isExist) {
            this.type = type;
            this.name = name;
            this.isExist = isExist;
        }

        public Type(String name, Boolean isExist) {
            this.name = name;
            this.isExist = isExist;
        }

    }
/*    {
        "detail": [
        {
            "issue": "发证机关",
                "number": "证件代码",
                "type": "证件类型",
                "validity_period_begin": "2018-12-01",
                "validity_period_end": "2038-12-31"
        }
    ],
        "type": [
        {
            "isExist": true,
                "name": "营业执照"
        },
        {
            "isExist": false,
                "name": "药品经营许可证"
        }
    ]
    }*/
}
