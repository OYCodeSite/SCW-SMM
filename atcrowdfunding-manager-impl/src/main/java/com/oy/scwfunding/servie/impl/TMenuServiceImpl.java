package com.oy.scwfunding.servie.impl;

import com.oy.scwfunding.bean.TMenu;
import com.oy.scwfunding.mapper.TMenuMapper;
import com.oy.scwfunding.service.TMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author OY
 * @Date 2021/1/26
 */
@Service
public class TMenuServiceImpl implements TMenuService {

    Logger log = LoggerFactory.getLogger(TMenuServiceImpl.class);

    @Autowired
    TMenuMapper tMenuMapper;

    @Override
    public List<TMenu> listMenuAll() {
        // 只存放父菜单,但是将children属性赋值
        List<TMenu> menusList = new ArrayList<>();
        HashMap<Integer, TMenu> cache = new HashMap<>();

        List<TMenu> allList = tMenuMapper.selectByExample(null);

        for (TMenu tMenu : allList) {
            if (tMenu.getPid() == 0) {
                menusList.add(tMenu);
                cache.put(tMenu.getId(), tMenu);
            }
        }
        for (TMenu tMenu : allList) {
            if (tMenu.getPid() != 0) {
                Integer pid = tMenu.getPid();
                TMenu parent = cache.get(pid);
                // 根据子菜单pid 查找父菜单id, 然后根据父菜单childer属性进行父子关系组合
                parent.getChildren().add(tMenu);
            }
        }

        log.debug("菜单={}", menusList);

        return menusList;
    }

    @Override
    public List<TMenu> listMenuAllTree() {
        return tMenuMapper.selectByExample(null);
    }

    @Override
    public void saveTMenu(TMenu tMenu) {
        tMenuMapper.insertSelective(tMenu);
    }

    @Override
    public TMenu getMenuById(Integer id) {
        return tMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateTMenu(TMenu menu) {
        tMenuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void deleteTMenu(Integer id) {
        tMenuMapper.deleteByPrimaryKey(id);
    }


}
