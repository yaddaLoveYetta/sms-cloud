package com.kingdee.hrp.sms.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingdee.hrp.sms.common.model.HospitalSupplierQualificationAttachment;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 供应商资质对象
 *
 * @author yadda
 */
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
     * 证件类型名称
     */
    private String typeName;
    /**
     * 发证机关
     */
    private String issue;
    /**
     * 证件有效期开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validityPeriodBegin;
    /**
     * 证件有效期结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validityPeriodEnd;

    /**
     * 附件
     */
    private List<HospitalSupplierQualificationAttachment> attachments;

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
