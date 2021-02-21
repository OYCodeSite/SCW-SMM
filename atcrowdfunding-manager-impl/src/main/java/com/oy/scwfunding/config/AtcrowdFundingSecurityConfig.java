package com.oy.scwfunding.config;

import com.oy.scwfunding.component.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author OY
 * @Date 2021/2/5
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AtcrowdFundingSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        http.authorizeRequests().antMatchers("/static/**", "/welcome.jsp", "/toLogin").permitAll()
                .anyRequest().authenticated();// 剩下都需要认证

        // /login.jsp == POST 用户请求发给Security
        http.formLogin().loginPage("/toLogin")
                .usernameParameter("loginacct")
                .passwordParameter("userpswd")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main", true);


        http.csrf().disable();


        // 注销
        http.logout().logoutSuccessUrl("/index");


        // 权限不够处理
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler(){

            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

                // X-Requested-With: XMLHttpRequest
                String type = httpServletRequest.getHeader("X-Requested-With");

                if("XMLHttpRequest".equals(type)){
                    // 权限不够，访问拒绝
                    httpServletResponse.getWriter().print("403");
                }else{
                    httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/error/error-403.jsp").forward(httpServletRequest,httpServletResponse);
                }
            }
        });

        // 记住我
        http.rememberMe();
    }
}
