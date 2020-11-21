package com.peter.xiao.main.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Eweee
 * @since 2020-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Generator implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value="id",type = IdType.AUTO)
  private Integer id;
  /** 数据库连接地址 */
  private String datasourceUrl;

  /** 用户名 */
  private String username;

  /** 密码 */
  private String password;

  /** 作者名称 */
  private String autor;

  /** 数据库驱动 */
  private String driverName;

  /** 包名 */
  private String parent;

  /** 模块名 */
  private String modelName;
}
