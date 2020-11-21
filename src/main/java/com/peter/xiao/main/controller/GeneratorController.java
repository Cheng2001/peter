package com.peter.xiao.main.controller;

import com.peter.xiao.common.ResultData;
import com.peter.xiao.common.generator.CodeGenerator;
import com.peter.xiao.main.entity.Generator;
import com.peter.xiao.main.service.IGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 前端控制器
 *
 * @author Eweee
 * @since 2020-11-21
 */
@RestController
@RequestMapping("/generator")
@Slf4j
@CrossOrigin
public class GeneratorController {
  /** 代码生成服务 */
  @Resource IGeneratorService generatorService;

  @GetMapping("tables")
  public ResultData tables() {
    return ResultData.success(generatorService.selectTables());
  }

  @GetMapping("/{tableName}")
  public ResultData generator(@PathVariable String tableName) {
    // 代码生成实体
    Generator generator = generatorService.getById(1);
    new CodeGenerator().generator(generator,tableName);
    return ResultData.success("成功");
  }
}
