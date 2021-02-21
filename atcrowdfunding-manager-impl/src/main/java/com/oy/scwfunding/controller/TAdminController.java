package com.oy.scwfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oy.scwfunding.bean.TAdmin;
import com.oy.scwfunding.bean.TRole;
import com.oy.scwfunding.service.TAdminService;
import com.oy.scwfunding.service.TRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/1/25
 */

@Controller
public class TAdminController {

    @Autowired
    TAdminService adminService;

    @Autowired
    TRoleService roleService;

    Logger log = LoggerFactory.getLogger(TAdminController.class);


    @ResponseBody
    @RequestMapping("/admin/doUnAssion")
    public String doUnAssion(Integer[] roleId, Integer adminId){
        log.debug("adminId={}",adminId);
        for (Integer rId : roleId) {
            log.debug("roleId={}",rId);
        }

        roleService.deleteAdminAndRoleRelationship(roleId,adminId);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/admin/doAssion")
    public String doAssion(Integer[] roleId, Integer adminId){

        log.debug("adminId={}",adminId);
        for(Integer rId : roleId){
            log.debug("roleId={}",rId);
        }

        roleService.saveAdminAndRoleRelationship(roleId,adminId);
        return "ok";
    }


    @RequestMapping("/admin/toAssign")
    public String toAssign(String id, Model model){

        // 1.查询所有角色
        List<TRole> allList = roleService.listAllRole();

        // 2.根据用户id查询已经拥有的角色id
        List<Integer> roleIdList = roleService.getRoleByIdByAdmin(id);


        // 3.将所有角色，进行划分
        List<TRole> assignList = new ArrayList<>();
        List<TRole> unAssignList = new ArrayList<TRole>();

        model.addAttribute("assignList",assignList);
        model.addAttribute("unAssignList",unAssignList);
        for (TRole role : allList) {

            if(roleIdList.contains(role.getId())){
                // 以分配角色
                assignList.add(role);
            }else{
                // 未分配角色
                unAssignList.add(role);
            }
        }

        return "admin/assignRole";
    }



    /**
     * 批量删除
     * @param ids
     * @param pageNum
     * @return
     */
    @RequestMapping("/admin/doDeleteBatch")
    public String doDelete(String ids, Integer pageNum){
        List<Integer> idList = new ArrayList<>();

        String[] split = ids.split(",");

        for(String idStr : split){
            int id = Integer.parseInt(idStr);
            idList.add(id);
        }

        adminService.deleteBatch(idList);

      return "redirect:/admin/index?pageNum=" + pageNum;
    }

    /**
     * 单项删除
     * @param id
     * @param pageNum
     * @return
     */
    @RequestMapping("admin/doDelete")
    public String doDelete(Integer id, Integer pageNum){
        adminService.deleteTAdmin(id);
        return "redirect:/admin/index?pageNum=" + pageNum;
    }

    /**
     * 处理修改操作
     * @param admin
     * @param pageNum
     * @return
     */
    @RequestMapping("admin/doUpdate")
    public String doUpdate(TAdmin admin, Integer pageNum){
        adminService.updateTAdmin(admin);
        return "redirect:/admin/index?pageNum=" + pageNum;
    }

    /**
     * 跳转修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("admin/toUpdate")
    public String toUpdate(Integer id, Model model){

        TAdmin admin = adminService.getTAdminById(id);
        model.addAttribute("admin",admin);

        return "admin/update";
    }

    /**
     *  处理添加操作
     */


    @RequestMapping("admin/doAdd")
    public String doAdd(TAdmin admin){

        adminService.saveTAdmin(admin);
        // 添加后跳转到最后分页
        return "redirect:/admin/index?pageNum="+Integer.MAX_VALUE;
    }

    /**
     * 跳转添加页面
     * @return
     */
    @PreAuthorize("hasRole('PM - 项目经理')")
    @RequestMapping("/admin/toAdd")
    public String toAdd(){
        return "admin/add";
    }

    /**
     *  用户维护
     */
    @RequestMapping("/admin/index")
    public String index(@RequestParam(value = "pageNum",required = false, defaultValue = "1" ) Integer pageNum,
                        @RequestParam(value = "PageSize", required = false,defaultValue = "2") Integer pageSize,
                        Model model,
                        @RequestParam(value = "condition", required = false, defaultValue = "") String condition){
        log.debug("PageNum={}", pageNum);
        log.debug("PageSize={}", pageSize);
        log.debug("condition={}", condition);
        // 线程绑定
        PageHelper.startPage(pageNum, pageSize );

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("condition",condition);

        PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);
        model.addAttribute("page", page);

        return "admin/index";
    }
}
