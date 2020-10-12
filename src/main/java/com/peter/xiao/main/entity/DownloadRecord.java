package com.peter.xiao.main.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
public class DownloadRecord implements Serializable {

    //private static final long serialVersionUID = 1L;

    /**
     * 标识id
     */
    private Integer id;

    /**
     * 文件id
     */
    private Integer fileId;

    /**
     * 下载用户
     */
    private Integer downloadUser;

    /**
     * 下载时间
     */
    private LocalDateTime downloadTime;


}
