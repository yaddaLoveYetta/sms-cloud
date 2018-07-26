package com.kingdee.hrp.sms.common.pojo;

import com.kingdee.hrp.sms.common.model.FormClass;
import com.kingdee.hrp.sms.common.model.FormClassEntry;
import com.kingdee.hrp.sms.common.model.FormField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
     * <p>
     * key子表序号 1:第一个子表 2:第二个子表 n:第n个子表
     */
    private Map<Integer, FormClassEntry> formClassEntry = new HashMap<>();

    /**
     * 字段模板对象
     * <p>
     * key模板序号 0:主表 1:第一个子表 2:第二个子表 n:第n个子表
     */
    private Map<Integer, Map<String, FormField>> formFields = new HashMap<>();

}
