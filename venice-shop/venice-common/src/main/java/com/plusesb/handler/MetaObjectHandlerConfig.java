package com.plusesb.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2016年10月27日 下午10:16:19
 */


@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("updateTime", new Date(),metaObject);
        setFieldValByName("createTime", new Date(),metaObject);
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime", new Date(),metaObject);
    }
}