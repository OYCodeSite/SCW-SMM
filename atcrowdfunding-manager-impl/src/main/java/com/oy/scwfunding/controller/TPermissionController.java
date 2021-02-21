package com.oy.scwfunding.controller;

import com.oy.scwfunding.bean.TPermission;
import com.oy.scwfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author OY
 * @Date 2021/1/30
 */
@Controller
public class TPermissionController {

    @Autowired
    TPermissionService permissionService;

    @ResponseBody
    @RequestMapping(value = "/permission/delete",method = RequestMethod.DELETE)
    // @DeleteMapping("/permission/delete")
    public String delete(Integer id){
        permissionService.deleteById(id);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/permission/edit")
    public String editPermission(TPermission permission){
        permissionService.updatePermission(permission);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/permission/get")
    public TPermission getPermission(Integer id){
        return permissionService.getPermissionById(id);
    }

    @ResponseBody
    @RequestMapping("/permission/add")
    public String Add(TPermission permission){
        permissionService.savePermission(permission);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/permission/listAllPermissionTree")
    public List<TPermission> listAllPermissionTree(){
        return permissionService.getAllPermissions();
    }


    @RequestMapping("/permission/index")
    public String index(){
        return "permission/index";
    }

}
