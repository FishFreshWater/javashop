package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.plusesb.validator.group.AddGroup;
import com.plusesb.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;


/**
 * 部门表
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 14:45:44
 */
@SuppressWarnings("serial")
@TableName("sys_dept")
public class SysDeptEntity extends BaseEntity {
                /**
         * 名称
         */
        @NotBlank(message="名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
        private String name;
                /**
         * 父类ID,一级为0
         */
        private Long parentId;
                /**
         * 排序
         */
        private Integer orderNum;

        @TableField(exist = false)
        private String parentName;

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        /**
         * 设置：名称
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * 获取：名称
         */
        public String getName() {
            return name;
        }
    
            /**
         * 设置：父类ID,一级为0
         */
        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }
        /**
         * 获取：父类ID,一级为0
         */
        public Long getParentId() {
            return parentId;
        }
    
            /**
         * 设置：排序
         */
        public void setOrderNum(Integer orderNum) {
            this.orderNum = orderNum;
        }
        /**
         * 获取：排序
         */
        public Integer getOrderNum() {
            return orderNum;
        }
    
    
    
    
}
