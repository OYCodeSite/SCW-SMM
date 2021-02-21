package com.oy.scwfunding.servie.impl;

import com.oy.scwfunding.bean.TPermission;
import com.oy.scwfunding.mapper.TPermissionMapper;
import com.oy.scwfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/1/30
 */
@Service
public class TPermissionServiceImpI implements TPermissionService {

    @Autowired
    TPermissionMapper permissionMapper;

    @Override
    public List<TPermission> getAllPermissions() {
        return permissionMapper.selectByExample(null);
    }

    @Override
    public void savePermission(TPermission permission) {
        permissionMapper.insertSelective(permission);
    }

    @Override
    public TPermission getPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatePermission(TPermission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public void deleteById(Integer id) {
        permissionMapper.deleteByPrimaryKey(id);
    }


}
