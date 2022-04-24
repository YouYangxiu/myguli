package com.atguigu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程视频
 *
 * @TableName edu_video
 */
@TableName(value = "edu_video")
@Data
public class EduVideo implements Serializable {
    /**
     * 视频ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 课程ID
     */
    @TableField(value = "course_id")
    private String courseId;

    /**
     * 章节ID
     */
    @TableField(value = "chapter_id")
    private String chapterId;

    /**
     * 节点名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 云端视频资源
     */
    @TableField(value = "video_source_id")
    private String videoSourceId;

    /**
     * 原始文件名称
     */
    @TableField(value = "video_original_name")
    private String videoOriginalName;

    /**
     * 排序字段
     */
    @TableField(value = "sort")
    private Object sort;

    /**
     * 播放次数
     */
    @TableField(value = "play_count")
    private Long playCount;

    /**
     * 是否可以试听：0收费 1免费
     */
    @TableField(value = "is_free")
    private Integer isFree;

    /**
     * 视频时长（秒）
     */
    @TableField(value = "duration")
    private Double duration;

    /**
     * Empty未上传 Transcoding转码中  Normal正常
     */
    @TableField(value = "status")
    private String status;

    /**
     * 视频源文件大小（字节）
     */
    @TableField(value = "size")
    private Long size;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    private Long version;

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