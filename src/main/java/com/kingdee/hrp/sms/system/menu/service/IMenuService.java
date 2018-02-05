package com.kingdee.hrp.sms.system.menu.service;

import com.kingdee.hrp.sms.common.model.Menu;

import java.util.List;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/31
 */
public interface IMenuService {

    /**
     * 根据parentId获取所有子菜单
     *
     * @param parentId 父菜单id
     * @return
     */
    List<Menu> getMenusByParentId(Integer parentId);
}
