package com.kingdee.hrp.sms.system.menu.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.FormActionMapper;
import com.kingdee.hrp.sms.common.dao.generate.MenuMapper;
import com.kingdee.hrp.sms.common.enums.Constant;
import com.kingdee.hrp.sms.common.model.FormAction;
import com.kingdee.hrp.sms.common.model.FormActionExample;
import com.kingdee.hrp.sms.common.model.Menu;
import com.kingdee.hrp.sms.common.model.MenuExample;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.menu.service.MenuService;
import com.kingdee.hrp.sms.system.user.service.UserService;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @return List<Menu>
     */
    @Override
    public List<Menu> getMenusByParentId(Integer parentId) {

        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);

        MenuExample example = new MenuExample();

        if (parentId > 0) {
            MenuExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
        }

        List<Menu> menus = menuMapper.selectByExample(example);

        // 用户角色类别
        Constant.UserRoleType userRoleType = SessionUtil.getUserRoleType();

        // 查询t_form_action 用于检验用户类别是否拥有menu的查看权（没有查看权限菜单不显示）
        FormActionMapper formActionMapper = sqlSession.getMapper(FormActionMapper.class);

        FormActionExample formActionExample = new FormActionExample();
        // 只查询查看权限，有查看权限才可能需要显示菜单
        formActionExample.createCriteria().andAccessMaskEqualTo(Constant.AccessMask.VIEW.getNumber());

        List<FormAction> formActions = formActionMapper.selectByExample(formActionExample);

        // 获取用户所有角色的授权结果
        UserService userService = Environ.getBean(UserService.class);
        Map<Integer, Integer> roleAccessControl = userService.getAccessControl();


        // 迭代过滤菜单项
        menus = menus.stream().filter(menu -> {

            Integer formActionId = menu.getFormActionId();


            // for test
            if (userRoleType == Constant.UserRoleType.SYSTEM) {
                return true;
            }

            // 一级菜单先不过滤
            if (menu.getParentId() == 0) {
                return true;
            }

            // for test
            if (null == formActionId || formActionId == 0) {
                // 没有配置formActionId的菜单认为是不需要控制，如一级菜单 for test
                return true;
            }

            // 是否这个用户类别可见的菜单，每个菜单配置一个查看权限
            Optional<FormAction> formActionOptional = formActions.stream().filter(formAction -> formActionId == formAction.getClassId().intValue() &&
                    ((userRoleType.getNumber() & formAction.getOwnerType()) == userRoleType.getNumber())).findFirst();

            if (!formActionOptional.isPresent()) {
                // 该菜单未配置查看权限或菜单非该用户类别可见
                return false;
            }

            Integer accessMask = roleAccessControl.get(formActionId);

            // formAction中查看权限AccessMask必须配置为1即 AccessMask.VIEW.getNumber()
            return accessMask != null && (accessMask & Constant.AccessMask.VIEW.getNumber()) == Constant.AccessMask.VIEW.getNumber();

        }).collect(Collectors.toList());

        List<Menu> ret = menus;

        // 二次过滤，将没有二级菜单的一级菜单过滤掉
        return ret.stream().filter(menu -> menu.getParentId() != 0 || ret.stream().anyMatch(item -> item.getParentId().equals(menu.getId())))
                .collect(Collectors.toList());

    }

}
