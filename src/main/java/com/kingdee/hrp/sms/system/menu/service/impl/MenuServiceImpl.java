package com.kingdee.hrp.sms.system.menu.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.FormActionMapper;
import com.kingdee.hrp.sms.common.dao.generate.MenuMapper;
import com.kingdee.hrp.sms.common.model.FormAction;
import com.kingdee.hrp.sms.common.model.FormActionExample;
import com.kingdee.hrp.sms.common.model.Menu;
import com.kingdee.hrp.sms.common.model.MenuExample;
import com.kingdee.hrp.sms.common.enums.AccessMask;
import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.menu.service.MenuService;
import com.kingdee.hrp.sms.system.user.service.UserService;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/31
 */
@Service
public class MenuServiceImpl extends BaseService implements MenuService {

    private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    /**
     * 根据用户角色类别及parentId获取所有子菜单
     * <p>
     * 必须是用户有权限的菜单
     *
     * @param parentId 父菜单id
     * @return
     */
    @Override
    public List<Menu> getMenusByParentId(Integer parentId) {

        List<Menu> ret = new ArrayList<>();

        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);

        MenuExample example = new MenuExample();

        if (parentId > 0) {
            MenuExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
        }

        List<Menu> menus = menuMapper.selectByExample(example);

        UserRoleType userRoleType = SessionUtil.getUserRoleType();
        // 用户所属组织
        Long userLinkOrg = SessionUtil.getUserLinkOrg();

        // 查询t_form_action 用于检验用户类别是否拥有menu的查看权（没有查看权限菜单不显示）
        FormActionMapper formActionMapper = sqlSession.getMapper(FormActionMapper.class);
        FormActionExample formActionExample = new FormActionExample();
        FormActionExample.Criteria criteria = formActionExample.createCriteria();
        // 只查询查看权限，有查看权限才可能需要显示菜单
        criteria.andAccessMaskEqualTo(AccessMask.VIEW.getNumber());

        List<FormAction> formActions = formActionMapper.selectByExample(formActionExample);

        // 获取用户所有角色的授权结果
        UserService userService = Environ.getBean(UserService.class);
        Map<Integer, Integer> roleAccessControl = userService.getAccessControl();

        // 迭代过滤菜单项
        for (Menu menu : menus) {

            Integer formActionId = menu.getFormActionId();

            if (null == formActionId || formActionId == 0) {
                // 没有配置formActionId的菜单认为是不需要控制，如一级菜单
                ret.add(menu);
                continue;
            }

            for (FormAction formAction : formActions) {

                Integer formActionClassId = formAction.getClassId();
                Integer ownerType = formAction.getOwnerType();

                if (formActionId == formActionClassId.intValue() &&
                        ((userRoleType.getNumber() & ownerType) == userRoleType.getNumber())) {
                    // 是这个用户类别可见的菜单
                    Integer accessMask = roleAccessControl.get(formActionId);
                    if (accessMask != null &&
                            (accessMask & AccessMask.VIEW.getNumber()) == AccessMask.VIEW.getNumber()) {
                        // 查看权限是1，判断是否有查看权限(此处为有权限)
                        ret.add(menu);
                        break;
                    }

                }

            }
        }

        // 二次过滤，将没有二级菜单的一级菜单过滤掉
        Iterator<Menu> iterator = ret.iterator();

        while (iterator.hasNext()) {

            Menu menu = iterator.next();
            // 表示是否存在二级菜单
            boolean existSubMenu = false;

            if (menu.getParentId() != 0) {
                continue;
            }

            for (Menu item : ret) {
                if (item.getParentId() == menu.getId().intValue()) {
                    existSubMenu = true;
                    break;
                }
            }

            if (!existSubMenu) {
                // 用户对该一级菜单的所有二级菜单都没有权限查看，则该一级菜单不显示
                iterator.remove();
            }

        }

        return ret;

    }

}
