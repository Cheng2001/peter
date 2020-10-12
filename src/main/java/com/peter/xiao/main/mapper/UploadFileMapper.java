package com.peter.xiao.main.mapper;

import com.peter.xiao.main.entity.UploadFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Eweee
 * @since 2020-09-08
 */
public interface UploadFileMapper extends BaseMapper<UploadFile> {

  /**
   * 获取文件集合
   *
   * @param userId 用户id
   * @param page   页数
   * @param limit  每页显示条数
   * @return 集合
   */
  List<UploadFile> files(int userId, int page, int limit);
}
