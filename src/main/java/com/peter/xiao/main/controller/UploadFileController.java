package com.peter.xiao.main.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.peter.xiao.annotation.Jwt;
import com.peter.xiao.main.entity.UploadFile;
import com.peter.xiao.main.service.IUploadFileService;
import com.peter.xiao.main.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Eweee
 * @since 2020-09-08
 */
@CrossOrigin
@RestController
@RequestMapping("/file")
public class UploadFileController {

  /**
   * 文件操作service层对象
   */
  @Resource
  IUploadFileService fileService;

  /**
   * 用户操作service层对象
   */
  @Resource
  IUserService userService;

  /**
   * 返回临时令牌信息
   *
   * @return 信息
   */
  @GetMapping("/TmpToken")
  public Map<String, Object> tmpToken() {
    Map<String, Object> returnMessage = new HashMap<>();
    returnMessage.put("code", 200);
    returnMessage.put("data", fileService.getTmpToken());
    return returnMessage;
  }

  /**
   * 获取指定key对象下载地址
   *
   * @param key 文件key
   * @return 文件下载地址
   */
  @GetMapping("/getUrl")
  public Map<String, Object> getUrl(@RequestParam String key) {
    Map<String, Object> returnMessage = new HashMap<>(16);
    returnMessage.put("code", 200);
    returnMessage.put("message", "请求成功");
    returnMessage.put("data", fileService.getDownUrl(key));
    return returnMessage;
  }

  /**
   * 上传文件
   *
   * @param file 文件实体
   * @return 保存结果
   */
  @PostMapping("/file")
  public Map<String, Object> file(UploadFile file, HttpServletRequest request) {
    Map<String, Object> returnMessage = new HashMap<>(16);
    // 填充上传时间为现在
    file.setUploadTime(LocalDateTime.now());
    // 从请求头中获取token
    String token = request.getHeader("token");
    // 获取用户id
    Integer userId = userService.getUser(token).getId();
    // 设置用户id
    file.setUploadUser(userId);
    //保存用户
    if (fileService.save(file)) {
      returnMessage.put("code", 200);
      returnMessage.put("message", "上传成功");
      return returnMessage;
    }
    returnMessage.put("code", 500);
    returnMessage.put("message", "上传失败");
    return returnMessage;
  }

  /**
   * 分页获取文件信息
   *
   * @return
   */
  @GetMapping("/file/{page}/{limit}")
  public Map<String, Object> file(@PathVariable Integer page, HttpServletRequest request, @PathVariable Integer limit) {
    Map<String, Object> returnMessage = new HashMap<>(16);
    String token = request.getHeader("token");
    Integer userId = userService.getUser(token).getId();
    List<UploadFile> files = fileService.getFiles(userId,page,limit);
    returnMessage.put("code", 200);
    returnMessage.put("data", files);
    if (fileService.getCountByUser(userId) <= ((page+1) * limit)) {
      returnMessage.put("finished", true);
    }
    return returnMessage;
  }
}
