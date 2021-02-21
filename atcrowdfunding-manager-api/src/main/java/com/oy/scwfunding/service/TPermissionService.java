package com.oy.scwfunding.service;

import com.oy.scwfunding.bean.TPermission;

import java.util.List;

public interface TPermissionService {
    List<TPermission> getAllPermissions();

    void savePermission(TPermission permission);

    TPermission getPermissionById(Integer id);

    void updatePermission(TPermission permission);

    void deleteById(Integer id);
}
