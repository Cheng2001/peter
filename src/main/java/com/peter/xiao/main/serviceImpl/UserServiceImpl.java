package com.peter.xiao.main.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.peter.xiao.common.utils.Encrypted;
import com.peter.xiao.main.constant.ConfigurationConstants;
import com.peter.xiao.main.entity.User;
import com.peter.xiao.main.mapper.UserMapper;
import com.peter.xiao.main.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Eweee
 * @since 2020-09-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

  /**
   * userDao层操作对象
   */
  @Resource
  UserMapper userMapper;

  @Override
  public boolean exits(User user) {
    // 定义条件构造器
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("username", user.getUsername());
    // 查询并返回结果
    return userMapper.selectCount(wrapper)!=0;
  }

  @Override
  public User setToken(User user) {
    // 给token初始化
    String token = "";
    try{
      // 获取加密后的token
      token = Encrypted.getInstance().encryption(user.getUsername(), ConfigurationConstants.SALT);
    } catch (NoSuchAlgorithmException e){
      e.printStackTrace();
    }
    // 给用户设置token
    user.setToken(token);
    // 返回用户数实体
    return user;
  }

  @Override
  public User getUser(String token) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("token", token);
    return userMapper.selectOne(wrapper);
  }
}
