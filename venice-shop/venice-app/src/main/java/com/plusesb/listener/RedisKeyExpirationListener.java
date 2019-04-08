package com.plusesb.listener;

import com.plusesb.constant.ShSysConstant;
import com.plusesb.service.ShOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


/**
 * 监听所有db的过期事件__keyevent@*__:expired"
 * @author linyuchi
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    @Autowired
    ShOrderService shOrderService;
    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        log.info("======================redis time out========================");
        if(expiredKey.startsWith(ShSysConstant.ORDER_PENDING)){
            //如果是Order:开头的key，进行处理
            String orderNumber = expiredKey.substring(6);
            log.info("======================"+orderNumber+"======================");
            shOrderService.cancelOrderByRedis(orderNumber);
        }
    }
}