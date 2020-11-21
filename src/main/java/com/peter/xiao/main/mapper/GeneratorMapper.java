package com.peter.xiao.main.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.peter.xiao.main.entity.Generator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Eweee
 * @since 2020-11-21
 */
public interface GeneratorMapper extends BaseMapper<Generator> {
  /**
   * 查询所有表信息
   */
  @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database())")
  List<Map<String,String>> listTable();
}
