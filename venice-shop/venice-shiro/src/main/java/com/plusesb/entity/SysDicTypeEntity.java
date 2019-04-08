package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.plusesb.validator.group.AddGroup;
import com.plusesb.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;


/**
 * 字典类型表
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 15:03:54
 */
@SuppressWarnings("serial")
@TableName("sys_dic_type")
public class SysDicTypeEntity extends BaseEntity {
        /**
         * 编码
         */
        @NotBlank(message="编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
        private String code;
                /**
         * 字典类型名称
         */
        @NotBlank(message="名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
        private String name;
                /**
         * 排序
         */
        private Integer orderNum;
                
    
            /**
         * 设置：编码
         */
        public void setCode(String code) {
            this.code = code;
        }
        /**
         * 获取：编码
         */
        public String getCode() {
            return code;
        }
    
            /**
         * 设置：字典类型名称
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * 获取：字典类型名称
         */
        public String getName() {
            return name;
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
