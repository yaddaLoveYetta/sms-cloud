package com.kingdee.hrp.sms.common.pojo;

import com.kingdee.hrp.sms.common.model.FormClass;
import com.kingdee.hrp.sms.common.model.FormClassEntry;
import com.kingdee.hrp.sms.common.model.FormField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 业务单据模板对象
 *
 * @author le.xiao
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class FormTemplate {

    /**
     * 主表描述
     */
    private FormClass formClass;
    /**
     * 子表描述
     */
    private FormClassEntry formClassEntry;

    /**
     * 字段模板对象
     * <p>
     * key模板序号 0:主表 1:第一个子表 2:第二个子表 n:第n个子表
     */
    private FormFields formFields;

    @Setter
    @Getter
    @ToString
    @Accessors(chain = true)
    private class FormFields {

        private Integer page;

        private FormFieldWarp formFieldWarp;
    }

    @Setter
    @Getter
    @ToString
    @Accessors(chain = true)
    private class FormFieldWarp {
        /**
         * 字段模板key-把key抽出来方便操作
         */
        private String key;
        /**
         * 模板字段
         */
        private FormField formField;
    }
}
