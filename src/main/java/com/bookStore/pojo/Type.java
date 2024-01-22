package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @TableName type
 */
@TableName(value = "type")
@Data
@ApiModel(description = "图书类别信息")
public class Type implements Serializable {
    @ApiModelProperty(value = "类别id", example = "1",required = true)
    private Long id;
    @ApiModelProperty(value = "图书类别", example = "文学")
    private String type;
    @ApiModelProperty(value = "父类别id", example = "0")
    private Long parentId;

    private static final long serialVersionUID = 1L;
}