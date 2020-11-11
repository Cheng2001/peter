package com.peter.xiao.main.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.peter.xiao.annotation.Jwt;
import com.peter.xiao.main.entity.User;
import com.peter.xiao.main.exceptionlog.ResultData;
import com.peter.xiao.main.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 前端控制器
 *
 * @author Eweee
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

  /** 用户service层操作对象 */
  @Resource IUserService userService;

  /**
   * 登录
   *
   * @param username 用户名
   * @param password 密码
   * @return 状态
   */
  @CrossOrigin
  @GetMapping("/login")
  public Map<String, Object> login(
      @RequestParam("username") String username, @RequestParam("password") String password) {
    // 定义返回的map
    Map<String, Object> returnMessage = new HashMap<>();
    // 定义条件构造器
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("username", username).eq("password", password);
    // service层查询该对象
    User userSelect = userService.getOne(wrapper);
    // 查询用户存在情况
    if (null != userSelect) {
      returnMessage.put("code", 200);
      returnMessage.put("data", userSelect);
      return returnMessage;
    }
    // 查询用户不存在情况
    returnMessage.put("code", 404);
    returnMessage.put("data", "该用户不存在或账号密码错误。。。");
    return returnMessage;
  }

  /**
   * 注册接口
   *
   * @param user 注册的用户
   * @return 返回信息
   */
  @Jwt
  @CrossOrigin
  @PostMapping("/register")
  public Map<String, Object> register(User user) {
    // 定义返回map
    Map<String, Object> returnMessage = new HashMap<>();
    // 查询注册的用户是否已经注册过
    if (userService.exits(user)) {
      returnMessage.put("code", 400);
      returnMessage.put("message", "该用户已经存在");
      return returnMessage;
    }
    // 给用户设置token
    user = userService.setToken(user);
    // 未注册过执行注册(保持同步)
    synchronized (this) {
      // 执行注册,注册失败。
      if (!userService.save(user)) {
        returnMessage.put("code", 400);
        returnMessage.put("message", "网络传输错误，请重新尝试");
        return returnMessage;
      }
    }
    System.out.println(user);
    // 注册成功后返回该信息
    returnMessage.put("code", 200);
    returnMessage.put("data", user);
    return returnMessage;
  }

  @GetMapping("test")
  @Jwt
  public Integer showUserId(@RequestAttribute Integer userId) {
    return userId;
  }
}
