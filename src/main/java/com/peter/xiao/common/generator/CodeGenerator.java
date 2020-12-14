package com.peter.xiao.common.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.peter.xiao.main.entity.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {

  public void generator(Generator generator,String tableName) {
    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();
    // set freemarker engine 自定义模块引擎
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());

    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    // 获取当前项目根目录 无需更改
    String projectPath = System.getProperty("user.dir");
    // 所有的生成的包和类到此路径下
    gc.setOutputDir("C:/Users/Administrator/Desktop/test" + "/src/main/java");
    // 开发人员名字 可忽略
    gc.setAuthor(generator.getAutor());
    // 是否打开输出目录，就是代码生成后是否打开文件夹的意思
    gc.setOpen(true);
    // gc.setSwagger2(true); 实体属性 Swagger2 注解
    mpg.setGlobalConfig(gc);

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    // 连接数据库 URL
    dsc.setUrl(generator.getDatasourceUrl());
    // dsc.setSchemaName("public");
    // 数据库驱动
    dsc.setDriverName(generator.getDriverName());
    // 数据库用户名
    dsc.setUsername(generator.getUsername());
    // 数据库密码
    dsc.setPassword(generator.getPassword());
    mpg.setDataSource(dsc);

    // 包配置
    PackageConfig pc = new PackageConfig();
    // 这里 就是调用了main方法外面的 scanner方法。 来从控制台获取 模块名
    pc.setModuleName(generator.getModelName());
    // 这里可以看做 自己的包名
    pc.setParent(generator.getParent());
    // 创建实体类包名
    pc.setEntity("entity");
    // 创建业务层接口
    pc.setService("service");
    // 创建实现类
    pc.setServiceImpl("service.impl");
    // Mapper Dao层的意思
    pc.setMapper("mapper");
    // Controller 控制层
    pc.setController("controller");
    mpg.setPackageInfo(pc);

    // 如果模板引擎是 freemarker
    String templatePath = "/templates/mapper.xml.ftl";
    // 如果模板引擎是 velocity
    // String templatePath = "/templates/mapper.xml.vm";

    // 自定义输出配置
    List<FileOutConfig> focList = new ArrayList<>();
    // 自定义配置会被优先输出
    focList.add(
        new FileOutConfig(templatePath) {
          @Override
          public String outputFile(TableInfo tableInfo) {
            // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
            return projectPath
                + "/src/main/resources/mapper/"
                + pc.getModuleName()
                + "/"
                + tableInfo.getEntityName()
                + "Mapper"
                + StringPool.DOT_XML;
          }
        });

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    // strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!"); //因为 实体不需要继承所以注释掉
    strategy.setEntityLombokModel(true);
    strategy.setRestControllerStyle(true);

    // 公共父类
    // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!"); //因为 控制层不需要继承所以注释掉
    strategy.setInclude(tableName);
    strategy.setControllerMappingHyphenStyle(true);
    strategy.setTablePrefix(pc.getModuleName() + "_");
    mpg.setStrategy(strategy);
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    mpg.execute();
  }
}
