package com.atguigu.educms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 首页banner表
 *
 * @TableName crm_banner
 */
@TableName(value = "crm_banner")
@Data
public class CrmBanner implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 图片地址
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 链接地址
     */
    @TableField(value = "link_url")
    private String linkUrl;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Object sort;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}