package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 登陆日志
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-25 00:13:15
 */
@SuppressWarnings("serial")
@TableName("sys_login_log")
public class SysLoginLogEntity extends BaseEntity {
                /**
         * ip地址
         */
        private String ip;
                /**
         * 用户名
         */
        private String username;
                /**
         * 登陆状态
         */
        private String type;
        /**
         * 登陆状态
         */
        private String status;
        /**
         * 信息
         */
        private String exception;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getException() {
            return exception;
        }

        public void setException(String exception) {
            this.exception = exception;
        }

        /**
         * 设置：ip地址
         */
        public void setIp(String ip) {
            this.ip = ip;
        }
        /**
         * 获取：ip地址
         */
        public String getIp() {
            return ip;
        }
    
            /**
         * 设置：用户名
         */
        public void setUsername(String username) {
            this.username = username;
        }
        /**
         * 获取：用户名
         */
        public String getUsername() {
            return username;
        }
    
            /**
         * 设置：登陆状态
         */
        public void setType(String type) {
            this.type = type;
        }
        /**
         * 获取：登陆状态
         */
        public String getType() {
            return type;
        }
    
    
    
    
}
