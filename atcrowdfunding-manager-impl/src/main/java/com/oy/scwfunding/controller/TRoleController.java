package com.oy.scwfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oy.scwfunding.bean.TRole;
import com.oy.scwfunding.service.TRoleService;
import com.oy.scwfunding.util.Datas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/1/27
 */
@Controller
public class TRoleController {

    @Autowired
    TRoleService tRoleService;

    Logger log = LoggerFactory.getLogger(TRoleController.class);

    @ResponseBody
    @RequestMapping("/role/listPermissionIdByRoleId")
    public List<Integer> listPermissionIdByRoleId(Integer roleId){

        log.debug("roleId={}",roleId);

        List<Integer> list = tRoleService.listPermissionIdByRoleId(roleId);

        return list;
    }

    @ResponseBody
    @RequestMapping("/role/doAssignPermissionToRole")
    public String doAssignPermissionToRole(Integer roleId, Datas ds){
        log.debug("roleId={}",roleId);
        log.debug("permissionIds={}",ds.getIds());

        tRoleService.saveRoleAndPermissionRelationShip(roleId, ds.getIds());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/role/doDelete")
    public String doDelete(Integer id){
        tRoleService.deleteTRole(id);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/role/getRoleById")
    public TRole getRoleById(Integer id){
        return tRoleService.getRoleById(id);
    }

    @PreAuthorize("hasRole('PM - 项目经理')")
    @ResponseBody
    @RequestMapping("/role/doAdd")
    public String doAdd(TRole tRole){
        tRoleService.saveTRole(tRole);
        return "ok";
    }


    @RequestMapping("/role/index")
    public String index(){

        return "role/index";
    }


    /**
     *  启用消息返回转换器： HttpMessageConverter
     *
     *  如果返回结果为对象（Entity Class, List, Map..）类型： 启用这个转换器 -> MappingJackson2HttpMessageConverter
     *  将对象序列化为json串，使用Jackson组件转换
     *
     *
     *  如果返回结果为String类型： 启用这个转换器 -> StringHttpMessageConverter 将字符串原样输出。
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/loadData")
    public PageInfo<TRole> loadData(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize,
                                    @RequestParam(value = "condition", required = false, defaultValue = "") String condition){
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("condition",condition);

        PageInfo<TRole> page = tRoleService.listRolePage(paramMap);


        log.debug("page={}", page);

        // 转为json 串返回
        return page;
    }

}
