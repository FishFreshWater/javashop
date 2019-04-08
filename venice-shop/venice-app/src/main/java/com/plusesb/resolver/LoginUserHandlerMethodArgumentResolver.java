package com.plusesb.resolver;

import com.plusesb.annotation.LoginUser;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.interceptor.AuthorizationInterceptor;
import com.plusesb.service.ShUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author linyuchi
 * @email 532826912@qq.com
 * @date 2017-03-23 22:02
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private ShUserService shUserService;

    public LoginUserHandlerMethodArgumentResolver(ShUserService shUserService){
        this.shUserService =shUserService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(ShUserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory){
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }

        //获取用户信息
        ShUserEntity user = shUserService.getById((Long) object);

        return user;
    }
}
