package com.oy.scwfunding.component;

import com.oy.scwfunding.bean.TAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

/**
 * @Author OY
 * @Date 2021/2/6
 */
public class TSecurityAdmin extends User {

    TAdmin admin;

     public TSecurityAdmin(TAdmin admin, Set<GrantedAuthority> authorities){
        super(admin.getUsername(), admin.getUserpswd(),true,true,true,true,authorities);
        this.admin = admin;
    }
}
