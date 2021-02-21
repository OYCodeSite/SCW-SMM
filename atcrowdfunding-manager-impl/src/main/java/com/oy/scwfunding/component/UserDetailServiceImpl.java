package com.oy.scwfunding.component;

import com.oy.scwfunding.bean.TAdmin;
import com.oy.scwfunding.bean.TAdminExample;
import com.oy.scwfunding.bean.TPermission;
import com.oy.scwfunding.bean.TRole;
import com.oy.scwfunding.mapper.TAdminMapper;
import com.oy.scwfunding.mapper.TPermissionMapper;
import com.oy.scwfunding.mapper.TRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author OY
 * @Date 2021/2/5
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    TAdminMapper adminMapper;

    @Autowired
    TRoleMapper roleMapper;

    @Autowired
    TPermissionMapper permissionMapper;


    Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. 查询用户对象
        TAdminExample example = new TAdminExample();
        example.createCriteria().andLoginacctEqualTo(username);
        List<TAdmin> list = adminMapper.selectByExample(example);

        if(list != null && list.size() ==1){
            TAdmin admin = list.get(0);
            Integer adminId = admin.getId();

            log.debug("用户信息:{}",admin);

            // 2.查询角色集合
            List<TRole> roleList = roleMapper.listRoleByAdminId(adminId);


            log.debug("用户角色集合:{}", roleList);

            // 3. 查询权限集合
            List<TPermission> permissionList = permissionMapper.listPermissionByAdminId(adminId);

            log.debug("用户权限集合:{}",permissionList);

            // 4.构建用户所有权限集合 => {ROLE_角色+权限}
            Set<GrantedAuthority> authorities = new HashSet<>();

            for (TRole role : roleList) {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            }

            for (TPermission permission : permissionList) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }

            log.debug("用户总权限集合:{}",authorities);

            return new TSecurityAdmin(admin,authorities);

        }else {
            return null;
        }


    }
}
