package com.peter.xiao.main.serviceImpl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.peter.xiao.main.entity.Generator;
import com.peter.xiao.main.mapper.GeneratorMapper;
import com.peter.xiao.main.service.IGeneratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Eweee
 * @since 2020-11-21
 */
@Service
public class GeneratorServiceImpl extends ServiceImpl<GeneratorMapper, Generator> implements IGeneratorService {

  @Resource GeneratorMapper generatorMapper;
  /**
   * 查询数据库中表信息
   *
   * @return 表信息
   */
  @Override
  @DS("sub")
  public List<Map<String,String>> selectTables() {
    return generatorMapper.listTable();
  }
}
