package com.peter.xiao.main.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.peter.xiao.main.entity.UploadFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Eweee
 * @since 2020-09-08
 */
public interface IUploadFileService extends IService<UploadFile> {

  /**
   * 获取临时token
   *
   * @return 返回信息
   */
  Map<?, ?> getTmpToken();

  /**
   * 获取文件下载地址
   *
   * @param key key
   * @return 下载地址
   */
  String getDownUrl(String key);

  /**
   * 分页查询
   *
   * @param userId 用户id
   * @param page   分页页数
   * @param limit  每页显示条数
   * @return 返回值
   */
  List<UploadFile> getFiles(Integer userId, int page, int limit);

  /**
   * 查看该用户上传的文件个数
   *
   * @param userId 用户id
   * @return 文件数量
   */
  int getCountByUser(int userId);
}
