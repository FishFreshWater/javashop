package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.plusesb.validator.group.AddGroup;
import com.plusesb.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;


/**
 * 字典表
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 15:03:55
 */
@SuppressWarnings("serial")
@TableName("sys_dic_item")
public class SysDicItemEntity extends BaseEntity {
                /**
         * 名称
         */
        @NotBlank(message="名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
        private String name;
                /**
         * 值
         */
        @NotBlank(message="值不能为空", groups = {AddGroup.class, UpdateGroup.class})
        private String value;

        /**
         * 类型ID
         */
        private String code;
        /**
         * 排序
         */
        private Integer orderNum;
        /**
         * 备注
         */
        private String remark;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
         * 设置：值
         */
        public void setValue(String value) {
            this.value = value;
        }
        /**
         * 获取：值
         */
        public String getValue() {
            return value;
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
    
            /**
         * 设置：备注
         */
        public void setRemark(String remark) {
            this.remark = remark;
        }
        /**
         * 获取：备注
         */
        public String getRemark() {
            return remark;
        }
    
    
    
    
}
