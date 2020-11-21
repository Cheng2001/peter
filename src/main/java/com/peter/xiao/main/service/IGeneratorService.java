package com.peter.xiao.main.service;

import com.peter.xiao.main.entity.Generator;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Eweee
 * @since 2020-11-21
 */
public interface IGeneratorService extends IService<Generator> {

  /**
   * 查询数据库中表信息
   * @return 表信息
   */
  List<Map<String,String>> selectTables();

}
