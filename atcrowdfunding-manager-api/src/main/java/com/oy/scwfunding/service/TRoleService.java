package com.oy.scwfunding.service;

import com.github.pagehelper.PageInfo;
import com.oy.scwfunding.bean.TRole;

import java.util.List;
import java.util.Map;

public interface TRoleService {
    PageInfo<TRole> listRolePage(Map<String, Object> paramMap);

    void saveTRole(TRole tRole);

    TRole getRoleById(Integer id);

    void deleteTRole(Integer id);

    List<TRole> listAllRole();

    List<Integer> getRoleByIdByAdmin(String id);

    void saveAdminAndRoleRelationship(Integer[] roleId, Integer adminId);

    void deleteAdminAndRoleRelationship(Integer[] roleId, Integer adminId);

    void saveRoleAndPermissionRelationShip(Integer roleId, List<Integer> ids);

    List<Integer> listPermissionIdByRoleId(Integer roleId);
}
