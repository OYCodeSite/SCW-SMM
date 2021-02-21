package com.oy.scwfunding.controller;

import com.oy.scwfunding.bean.TMenu;
import com.oy.scwfunding.service.TMenuService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/1/30
 */
@Controller
public class TMenuController {

    @Autowired
    TMenuService tMenuService;


    @ResponseBody
    @RequestMapping("/menu/doDelete")
    public String doDelete(Integer id){
        tMenuService.deleteTMenu(id);
        return "ok";
    }

    @RequestMapping("/menu/doUpdate")
    @ResponseBody
    public String doUpdate(TMenu menu){
        tMenuService.updateTMenu(menu);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/menu/getMenuById")
    public TMenu getMenuById(Integer id){
        TMenu menu = tMenuService.getMenuById(id);
        return menu;
    }

    @ResponseBody
    @RequestMapping("/menu/doAdd")
    public String doAdd(TMenu tMenu){
        tMenuService.saveTMenu(tMenu);
        return "ok";
    }

    @RequestMapping("/menu/index")
    public String index(){
        return "menu/index";
    }

    @ResponseBody
    @RequestMapping("/menu/loadTree")
    public List<TMenu> loadTree(){
        return tMenuService.listMenuAllTree();
    }
}
