package com.oy.scwfunding.servie.impl;

import com.github.pagehelper.PageInfo;
import com.oy.scwfunding.bean.TAdmin;
import com.oy.scwfunding.bean.TAdminExample.Criteria;
import com.oy.scwfunding.bean.TAdminExample;
import com.oy.scwfunding.exception.LoginException;
import com.oy.scwfunding.mapper.TAdminMapper;
import com.oy.scwfunding.service.TAdminService;
import com.oy.scwfunding.util.AppDateUtils;
import com.oy.scwfunding.util.Const;
import com.oy.scwfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/1/25
 */
@Service
public class TAdminServiceImpl implements TAdminService {

    @Autowired
    TAdminMapper adminMapper;


    @Override
    public TAdmin getTAdminByLogin(Map<String, Object> paramMap) {
        // 1. 密码加密

        // 2.查询用户是否存在
        String loginacct = (String) paramMap.get("loginacct");
        String userpwsd = (String) paramMap.get("userpswd");

        // 3.判断用户是否为null
        TAdminExample example = new TAdminExample();
        example.createCriteria().andLoginacctEqualTo(loginacct);

        List<TAdmin> list = adminMapper.selectByExample(example);

        if(list==null || list.size() ==0){
            throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
        }

        TAdmin admin = list.get(0);

        // 判断密码是否一致
        if(!admin.getUserpswd().equals(MD5Util.digest(userpwsd))){
            throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
        }

        return admin;
    }

    @Override
    public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {

        String condition = (String)paramMap.get("condition");

        TAdminExample example = new TAdminExample();

        if(!"".equals(condition)){
            example.createCriteria().andLoginacctLike("%"+condition+"%");

            Criteria criteria2 = example.createCriteria();
            criteria2.andUsernameLike("%"+condition+"%");

            Criteria criteria3 = example.createCriteria();
            criteria3.andEmailLike("%"+condition+"%");

            example.or(criteria2);
            example.or(criteria3);
        }


        //examle.setOrderByClause("createtime desc");

        List<TAdmin> list = adminMapper.selectByExample(example);

        PageInfo<TAdmin> page = new PageInfo<>(list, 5);

        return page;
    }

    @Override
    public void saveTAdmin(TAdmin admin) {
        admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));
        admin.setCreatetime(AppDateUtils.getFormatTime());

        //insert into t_admin(loginacct,username,email) values(?,?,?);
        //insert into t_admin(loginacct,username,email,userpswd,createtime) values(?,?,?,?,?);

        // 动态sql，有选择性保存
        adminMapper.insertSelective(admin);
    }

    @Override
    public TAdmin getTAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateTAdmin(TAdmin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void deleteTAdmin(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(List<Integer> idList) {
        adminMapper.deleteBatch(idList);
    }
}
