package io.github.toquery.framework.system.service.impl;

import io.github.toquery.framework.crud.service.impl.AppBaseServiceImpl;
import io.github.toquery.framework.system.entity.SysMenu;
import io.github.toquery.framework.system.entity.SysRoleMenu;
import io.github.toquery.framework.system.mapper.SysRoleMenuMapper;
import io.github.toquery.framework.system.repository.SysRoleMenuRepository;
import io.github.toquery.framework.system.service.ISysMenuService;
import io.github.toquery.framework.system.service.ISysRoleMenuService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author toquery
 * @version 1
 */
public class SysRoleMenuServiceImpl extends AppBaseServiceImpl<SysRoleMenu, SysRoleMenuRepository> implements ISysRoleMenuService {

    @Resource
    private ISysMenuService sysMenuService;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

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
        return super.list(param);
    }

    @Override
    public List<SysRoleMenu> findByRoleIds(Set<Long> sysRoleIds) {
        Map<String, Object> param = new HashMap<>();
        param.put("roleIds", sysRoleIds);
        return super.list(param);
    }

    @Override
    public List<SysRoleMenu> findByMenuId(Long menuId) {
        Map<String, Object> param = new HashMap<>();
        param.put("menuId", menuId);
        return super.list(param);
    }

    @Override
    public List<SysRoleMenu> findByMenuIds(Set<Long> sysMenuIds) {
        Map<String, Object> param = new HashMap<>();
        param.put("menuIds", sysMenuIds);
        return super.list(param);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long sysRoleId) {
        List<SysRoleMenu> sysRoleMenus = this.findByRoleId(sysRoleId);
        return sysMenuService.listByIds(sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
    }


    @Override
    public List<SysMenu> findSysMenuByRoleIds(Set<Long> sysRoleIds) {
        List<Long> sysRoleMenuIds = this.findByRoleIds(sysRoleIds).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        return sysMenuService.listByIds(sysRoleMenuIds);
    }

    @Override
    public List<SysRoleMenu> findWithSysMenuByRoleIds(Set<Long> sysRoleIds) {
        List<SysRoleMenu> sysRoleMenus = this.findByRoleIds(sysRoleIds);
        Map<Long,List<SysRoleMenu>> roleMenuMap = sysRoleMenus.stream().collect(Collectors.groupingBy(SysRoleMenu::getRoleId));
        List<SysMenu> sysMenus = sysMenuService.listByIds(sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
        return sysRoleMenus;
    }

    @Override
    public List<SysRoleMenu> reSaveMenu(Long roleId, Collection<Long> menuIds) {
        List<SysRoleMenu> sysRoleMenus = this.findByRoleId(roleId);

        Map<Long,SysRoleMenu> sysRoleMenuMap = sysRoleMenus.stream().collect(Collectors.toMap(SysRoleMenu::getMenuId, item->item, (o,n)->n));;

        List<Long> dbMenuIds = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());


        // 差集 dbMenuIds - newMenuIds
        List<SysRoleMenu> deleteList = dbMenuIds.stream()
                .filter(menuId -> !menuIds.contains(menuId))
                .map(sysRoleMenuMap::get)
                .collect(Collectors.toList());

        // 差集 newMenuIds - dbMenuIds
        List<SysRoleMenu> addList = menuIds.stream()
                .filter(menuId -> !dbMenuIds.contains(menuId))
                .map(menuId -> new SysRoleMenu(roleId, menuId))
                .collect(Collectors.toList());

        if (dbMenuIds.size() > 0) {
            super.delete(deleteList);
        }

        if (addList.size() > 0) {
            super.save(addList);
        }

        return null;
    }


    public List<SysRoleMenu> findWithSysRoleMenuByRoleIds(Set<Long> sysRoleIds) {
        return sysRoleMenuMapper.findWithSysRoleMenuByRoleIds(sysRoleIds);
    }
}
