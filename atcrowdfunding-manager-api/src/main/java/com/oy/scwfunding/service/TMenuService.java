package com.oy.scwfunding.service;

import com.oy.scwfunding.bean.TMenu;

import java.util.List;

public interface TMenuService {
    List<TMenu> listMenuAll();

    List<TMenu> listMenuAllTree();

    void saveTMenu(TMenu tMenu);

    TMenu getMenuById(Integer id);

    void updateTMenu(TMenu menu);

    void deleteTMenu(Integer id);
}
