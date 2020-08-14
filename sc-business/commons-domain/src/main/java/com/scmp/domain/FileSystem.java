package com.scmp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Coconut Tree
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("file_system")
public class FileSystem {
    /**
     * 文件 id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long fileId;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件 url
     */
    private String fileUrl;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件上传者姓名
     */
    private String uploaderName;
    /**
     * 文件描述信息
     */
    private String fileInfo;
}
