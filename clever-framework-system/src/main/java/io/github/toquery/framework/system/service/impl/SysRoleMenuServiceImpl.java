package io.github.toquery.framework.system.service.impl;

import io.github.toquery.framework.crud.service.impl.AppBaseServiceImpl;
import io.github.toquery.framework.system.entity.SysMenu;
import io.github.toquery.framework.system.entity.SysRoleMenu;
import io.github.toquery.framework.system.repository.SysRoleMenuRepository;
import io.github.toquery.framework.system.service.ISysMenuService;
import io.github.toquery.framework.system.service.ISysRoleMenuService;
import io.github.toquery.framework.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author toquery
 * @version 1
 */
public class SysRoleMenuServiceImpl extends AppBaseServiceImpl<Long, SysRoleMenu, SysRoleMenuRepository> implements ISysRoleMenuService {

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Override
    public Map<String, String> getQueryExpressions() {
        Map<String, String> map = new HashMap<>();
        map.put("roleId", "roleId:EQ");
        map.put("menuId", "menuId:EQ");
        map.put("menuIds", "menuId:IN");
        map.put("roleIds", "roleId:IN");
        return map;
    }

    @Override
    public boolean existsByRoleId(Long roleId) {
        List<SysRoleMenu> sysRoleMenus = this.findByRoleId(roleId);
        return sysRoleMenus != null && sysRoleMenus.size() > 0;
    }

    @Override
    public boolean existsByMenuId(Long menuId) {
        List<SysRoleMenu> sysRoleMenus = this.findByMenuId(menuId);
        return sysRoleMenus != null && sysRoleMenus.size() > 0;
    }

    @Override
    public List<SysRoleMenu> findByRoleId(Long roleId) {
        Map<String, Object> param = new HashMap<>();
        param.put("roleId", roleId);
        return super.find(param);
    }

    @Override
    public List<SysRoleMenu> findByRoleIds(Set<Long> sysRoleIds) {
        Map<String, Object> param = new HashMap<>();
        param.put("roleIds", sysRoleIds);
        return super.find(param);
    }

    @Override
    public List<SysRoleMenu> findByMenuId(Long menuId) {
        Map<String, Object> param = new HashMap<>();
        param.put("menuId", menuId);
        return super.find(param);
    }

    @Override
    public List<SysRoleMenu> findByMenuIds(Set<Long> sysMenuIds) {
        Map<String, Object> param = new HashMap<>();
        param.put("menuIds", sysMenuIds);
        return super.find(param);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long sysRoleId) {
        List<SysRoleMenu> sysRoleMenus = this.findByRoleId(sysRoleId);
        return sysMenuService.findByIds(sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
    }


    @Override
    public List<SysMenu> findSysMenuByRoleIds(Set<Long> sysRoleIds) {
        List<Long> sysRoleMenuIds = this.findByRoleIds(sysRoleIds).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        return sysMenuService.findByIds(sysRoleMenuIds);
    }

    @Override
    public List<SysRoleMenu> findWithSysMenuByRoleIds(Set<Long> sysRoleIds) {
        List<SysRoleMenu> sysRoleMenus = this.findByRoleIds(sysRoleIds);
        Map<Long,List<SysRoleMenu>> roleMenuMap = sysRoleMenus.stream().collect(Collectors.groupingBy(SysRoleMenu::getRoleId));
        List<SysMenu> sysMenus = sysMenuService.findByIds(sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
        return sysRoleMenus;
    }


    public List<SysRoleMenu> findWithSysRoleMenuByRoleIds(Set<Long> sysRoleIds) {
        return super.dao.findWithSysRoleMenuByRoleIds(sysRoleIds);
    }
}
