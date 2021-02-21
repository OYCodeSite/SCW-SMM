package com.oy.scwfunding.controller;

import com.oy.scwfunding.bean.TAdmin;
import com.oy.scwfunding.bean.TMember;
import com.oy.scwfunding.bean.TMenu;
import com.oy.scwfunding.service.TAdminService;
import com.oy.scwfunding.service.TMenuService;
import com.oy.scwfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/1/25
 */
@Controller
public class DispatcherController {

    Logger log = LoggerFactory.getLogger(DispatcherController.class);

    @Autowired
    TAdminService adminService;

    @Autowired
    TMenuService menuService;

    @RequestMapping("/index")
    public String index(){
        //log.debug("跳转到系统主页面");
        return "index";
    }

    @RequestMapping("/toLogin")
    public String login(){
        log.debug("跳转到登录主页面");
        return "login";
    }

    /**
     * 后台管理,动态加载菜单
     * @param session
     * @return
     */
    @RequestMapping("/main")
    public String main(HttpSession session){
        log.debug("跳转到登录菜单");

        if(session == null){
            return "redirect:/toLogin";
        }

        // 存放父菜单
        List<TMenu> menuList = (List<TMenu>) session.getAttribute("menuList");


        if(menuList == null){
            menuList = menuService.listMenuAll();
            session.setAttribute("menuList", menuList);
        }
        return "main";
    }


//    @RequestMapping("/logout")
//    public String logout(HttpSession session){
//        log.debug("注销系统....");
//
//        if(session!= null){
//            session.removeAttribute(Const.LOGIN_ADMIN);
//            session.invalidate();
//        }
//        return "redirect:/index";
//    }
//
//
//    @RequestMapping("/doLogin")
//    public String doLogin(String loginacct, String userpswd, HttpSession session, Model model){
//        log.debug("开始登录");
//
//        log.debug("loginacct={}",loginacct);
//        log.debug("userpswd={}",userpswd);
//
//        Map<String, Object> paramMap = new HashMap<String,Object>();
//        paramMap.put("loginacct", loginacct);
//        paramMap.put("userpswd",userpswd);
//
//        try {
//            TAdmin admin = adminService.getTAdminByLogin(paramMap);
//            session.setAttribute(Const.LOGIN_ADMIN,admin);
//            log.debug("登录成功....");
//            // 避免表单重复提交，采用重定向
////            return "main";
//            return "redirect:/main";
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.debug("登录失败={}",e.getMessage());
//            model.addAttribute("message",e.getMessage());
//            return "login";
//        }
//
//    }

}
