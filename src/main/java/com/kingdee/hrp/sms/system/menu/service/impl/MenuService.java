package com.kingdee.hrp.sms.system.menu.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.MenuMapper;
import com.kingdee.hrp.sms.system.menu.service.IMenuService;
import com.kingdee.hrp.sms.common.model.Menu;
import com.kingdee.hrp.sms.common.model.MenuExample;
import com.kingdee.hrp.sms.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/31
 */
@Service
public class MenuService extends BaseService implements IMenuService {


    /**
     * 根据parentId获取所有子菜单
     *
     * @param parentId 父菜单id
     * @return
     */
    @Override
    public List<Menu> getMenusByParentId(Integer parentId) {

        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);

        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();

        criteria.andParentIdEqualTo(parentId);

        return  menuMapper.selectByExample(example);

    }
}
