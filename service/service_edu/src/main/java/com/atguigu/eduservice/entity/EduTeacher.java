package com.atguigu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 讲师
 * @TableName edu_teacher
 */
@TableName(value ="edu_teacher")
@Data
public class EduTeacher implements Serializable {
    /**
     * 讲师ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 讲师姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 讲师简介
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 讲师资历,一句话说明讲师
     */
    @TableField(value = "career")
    private String career;

    /**
     * 头衔 1高级讲师 2首席讲师
     */
    @TableField(value = "level")
    private Object level;

    /**
     * 讲师头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Object sort;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableField(value = "is_deleted")
    @TableLogic
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