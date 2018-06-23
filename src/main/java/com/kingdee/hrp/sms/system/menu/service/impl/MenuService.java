package com.kingdee.hrp.sms.system.menu.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.FormActionMapper;
import com.kingdee.hrp.sms.common.dao.generate.MenuMapper;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.pojo.UserRoleTypeEnum;
import com.kingdee.hrp.sms.system.menu.service.IMenuService;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.user.service.IUserService;
import com.kingdee.hrp.sms.system.user.service.impl.UserService;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.SessionUtil;
import com.sun.tools.doclint.Env;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/31
 */
@Service
public class MenuService extends BaseService implements IMenuService {

    private static Logger logger = LoggerFactory.getLogger(MenuService.class);

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

        Long userLinkOrg = 0L;
        Integer userRoleType = SessionUtil.getUserRoleType();

        if (userRoleType == UserRoleTypeEnum.SYSTEM.getNumber().intValue()) {
            // 系统管理员
            logger.info("系统用户所有菜单可见!");
        } else if (userRoleType == UserRoleTypeEnum.HOSPITAL.getNumber().intValue()) {
            // 医院
            userLinkOrg = SessionUtil.getUserLinkHospital();
        } else if (userRoleType == UserRoleTypeEnum.SUPPLIER.getNumber().intValue()) {
            // 供应商
            userLinkOrg = SessionUtil.getUserLinkSupplier();
        }

        // 查询t_form_action 用于检验用户类别是否拥有menu的查看权（没有查看权限菜单不显示）

        FormActionMapper formActionMapper = sqlSession.getMapper(FormActionMapper.class);

        FormActionExample formActionExample = new FormActionExample();
        FormActionExample.Criteria criteria = formActionExample.createCriteria();
        // 只查询查看权限，有查看权限才可能需要显示菜单
        criteria.andAccessMaskEqualTo(1);

        List<FormAction> formActions = formActionMapper.selectByExample(formActionExample);

        // 获取用户所有权限
        IUserService userService = Environ.getBean(IUserService.class);
        Map<Integer, Integer> roleAccessControl = userService.getAccessControl();

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

                if (formActionId == formActionClassId.intValue() && ((userRoleType & ownerType) == userRoleType)) {
                    // 是这个用户类别可见的菜单
                    Integer accessMask = roleAccessControl.get(formActionId);
                    if (accessMask != null && (accessMask & 1) == 1) {
                        // 查看权限是1，判断是否有查看权限(此处为有权限)
                        ret.add(menu);
                        break;
                    }

                }

            }
        }

        return ret;

    }

}
