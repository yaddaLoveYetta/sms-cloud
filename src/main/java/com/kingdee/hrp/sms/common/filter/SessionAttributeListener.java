package com.kingdee.hrp.sms.common.filter;

import com.kingdee.hrp.sms.util.SessionUtil;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * HttpSession属性监听器
 *
 * @author yadda
 * @ClassName SessionAttributeListener
 * @date 2017-05-28 13:13:10 星期日
 */
public class SessionAttributeListener implements HttpSessionAttributeListener {
    /**
     * 添加Session
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        SessionUtil.set(event.getName(), event.getValue());
    }

    /**
     * 销毁Session
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        SessionUtil.remove(event.getName());
    }

    /**
     * 重置Session
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

        SessionUtil.set(event.getName(), event.getValue());
    }

}
