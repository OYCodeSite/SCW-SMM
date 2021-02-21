package com.oy.scwfunding.exception;

/**
 *
 * 业务层事务回滚，spring声明式事务默认只对RuntimeException类型异常进行事务回滚
 * @Author OY
 * @Date 2021/1/25
 */
public class LoginException extends RuntimeException{

    public LoginException(){

    }

    public LoginException(String message){
        super(message);
    }
}
