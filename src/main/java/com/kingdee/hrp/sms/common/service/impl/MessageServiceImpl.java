package com.kingdee.hrp.sms.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.kingdee.hrp.sms.common.dao.generate.MessageMapper;
import com.kingdee.hrp.sms.common.enums.Constant;
import com.kingdee.hrp.sms.common.model.Message;
import com.kingdee.hrp.sms.common.model.MessageExample;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息服务
 *
 * @author yadda
 */
@Service
public class MessageServiceImpl extends BaseService implements MessageService {
    /**
     * 新增一条消息
     *
     * @param message 消息对象
     * @return 新增消息的id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(Message message) {

        if (null == message) {
            return -1L;
        }

        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
        mapper.insert(message);

        return message.getId();
    }

    /**
     * 获取一条消息
     *
     * @param id 消息id
     * @return 消息对象
     */
    @Override
    public Message get(Long id) {
        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);

        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 获取指定归属组织的消息，最多返回10条记录
     *
     * @param org   消息归属组织
     * @param count 消息数量，最多返回100条,默认10条
     * @return List<Message>
     */
    @Override
    public List<Message> getByOrg(Long org, Integer count) {
        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
        MessageExample example = new MessageExample();
        example.createCriteria().andReceiverOrgEqualTo(org);

        count = null == count ? 10 : count;
        count = count > 100 ? 100 : count;

        PageHelper.startPage(1, count, false);

        return mapper.selectByExample(example);
    }

    /**
     * 获取指定归属组织，指定状态的消息
     *
     * @param org    消息归属组织
     * @param status 消息状态
     * @param count  消息数量，最多返回100条,默认10条
     * @return List<Message>
     */
    @Override
    public List<Message> getByOrg(Long org, Constant.MessageStatus status, Integer count) {

        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
        MessageExample example = new MessageExample();

        example.createCriteria().andReceiverOrgEqualTo(org).andStatusEqualTo(status.getNumber());

        example.orderBy(Message.Column.date.desc());

        count = null == count ? 10 : count;
        count = count > 100 ? 100 : count;

        PageHelper.startPage(1, count, false);

        return mapper.selectByExample(example);
    }
}
