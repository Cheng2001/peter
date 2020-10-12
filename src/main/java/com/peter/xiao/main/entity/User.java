package com.peter.xiao.main.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Eweee
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    //private static final long serialVersionUID = 1L;

    /**
     * 标识id
     */
    @TableId(value ="id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户标识令牌
     */
    private String token;


}
