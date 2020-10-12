package com.peter.xiao.main.service;

import com.peter.xiao.main.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Eweee
 * @since 2020-09-08
 */
public interface IUserService extends IService<User> {

  /**
   * 判断用户是否存在
   * @param user 用户实体
   * @return 存在true,不存在为false
   */
  boolean exits(User user);

  /**
   * 向用户设置token
   * @param user 用户实体
   * @return 设置结果
   */
  User setToken(User user);

  /**
   * 根据token获取用户id
   * @param Token 用户token
   * @return 用户对象
   */
  User getUser(String Token);
}
