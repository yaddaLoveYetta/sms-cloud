package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 供应商查看对医院提供的供应商资质后台返回模型
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/09/26 11:11
 */
@Setter
@Getter
@Accessors(chain = true)
public class SupplierQualificationModel {

    /**
     * detail记录数
     */
    private Long total;
    /**
     * 对医院提供的证件列表明细
     */
    private List<Qualification> detail;
    /**
     * 应该对医院提供的证件类别及当前是否已提供信息
     */
    private List<QualificationType> types;

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
