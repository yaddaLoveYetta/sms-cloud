package com.kingdee.hrp.sms.common.service;

import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.model.Message;

import java.util.List;

/**
 * 系统消息服务
 *
 * @author yadda
 */
public interface MessageService {

    /**
     * 新增一条消息
     *
     * @param message 消息对象
     * @return 新增消息的id
     */
    Long add(Message message);

    /**
     * 获取一条消息
     *
     * @param id 消息id
     * @return 消息对象
     */
    Message get(Long id);

    /**
     * 获取指定归属组织的消息，最多返回10条记录
     *
     * @param org   消息归属组织
     * @param count 消息数量，最多返回100条,默认10条
     * @return List<Message>
     */
    List<Message> getByOrg(Long org, Integer count);

    /**
     * 获取指定归属组织，指定状态的消息
     *
     * @param org    消息归属组织
     * @param status 消息状态
     * @param count  消息数量，最多返回100条,默认10条
     * @return List<Message>
     */
    List<Message> getByOrg(Long org, Constants.MessageStatus status, Integer count);

}
