package com.peter.xiao.main.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Eweee
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UploadFile implements Serializable {

    //private static final long serialVersionUID = 1L;

    /**
     * 标识id
     */
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Double size;

    /**
     * 上传用户
     */
    private Integer uploadUser;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 备注
     */
    private String remark;


}
