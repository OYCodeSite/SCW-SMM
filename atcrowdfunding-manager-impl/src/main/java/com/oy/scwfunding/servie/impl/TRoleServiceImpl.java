package com.oy.scwfunding.servie.impl;

import com.github.pagehelper.PageInfo;
import com.oy.scwfunding.bean.TRole;
import com.oy.scwfunding.bean.TRoleExample;
import com.oy.scwfunding.bean.TRolePermissionExample;
import com.oy.scwfunding.mapper.TAdminRoleMapper;
import com.oy.scwfunding.mapper.TRoleMapper;
import com.oy.scwfunding.mapper.TRolePermissionMapper;
import com.oy.scwfunding.service.TRoleService;
import com.oy.scwfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/1/27
 */
@Service
public class TRoleServiceImpl implements TRoleService {

    @Autowired
    TRoleMapper tRoleMapper;

    @Autowired
    TAdminRoleMapper adminRoleMapper;

    @Autowired
    TRolePermissionMapper rolePermissionMapper;


    @Override
    public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {

        String condition = (String) paramMap.get("condition");

        List<TRole> list = null;

        if(StringUtil.isEmpty(condition) ){
            list = tRoleMapper.selectByExample(null);
        }else{

            TRoleExample example = new TRoleExample();
            example.createCriteria().andNameLike("%"+condition+"%");

            list = tRoleMapper.selectByExample(example);
        }

        PageInfo<TRole> page = new PageInfo<>(list, 5);

        return page;
    }

    @Override
    public void saveTRole(TRole tRole) {
        tRoleMapper.insertSelective(tRole);
    }

    @Override
    public TRole getRoleById(Integer id) {
        return tRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteTRole(Integer id) {
        tRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TRole> listAllRole() {
        return tRoleMapper.selectByExample(null);
    }


    @Override
    public List<Integer> getRoleByIdByAdmin(String id) {
        return adminRoleMapper.getRoleIdByAdminId(id);
    }

    @Override
    public void saveAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {
        adminRoleMapper.saveAdminAndRoleRelationship(roleId,adminId);
    }

    @Override
    public void deleteAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {
        adminRoleMapper.deleteAdminAndRoleRelationship(roleId,adminId);
    }

    @Override
    public void saveRoleAndPermissionRelationShip(Integer roleId, List<Integer> ids) {
        // 先删除之前分配过的，然后在重新分配所有的打钩的
        TRolePermissionExample example = new TRolePermissionExample();
        example.createCriteria().andRoleidEqualTo(roleId);
        rolePermissionMapper.deleteByExample(example);

        rolePermissionMapper.saveRoleAndPermissionRelationShip(roleId,ids);
    }

    @Override
    public List<Integer> listPermissionIdByRoleId(Integer roleId) {
        return rolePermissionMapper.listPermissionIdByRole(roleId);
    }

}
