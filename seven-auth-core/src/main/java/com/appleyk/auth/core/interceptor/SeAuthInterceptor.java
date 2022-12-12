package com.appleyk.auth.core.interceptor;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.util.SeJsonUtils;
import com.appleyk.auth.core.annotation.SeToken;
import com.appleyk.auth.core.helper.SeAuthHelper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * <p>Controller方法拦截器，主要拦截带@SeToken的方法，验证调用端是否有传递正确的token/p>
 *
 * @author appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午8:53 2022/12/12
 */
public class SeAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            SeToken token = method.getAnnotation(SeToken.class);
            if (token != null){
                /**这里可以拿到annotation的属性值，做一些其他额外的事情*/
                String currentToken = SeAuthHelper.getCurrentToken();
                /**只有当headers中没有token的时候，才会走下面的逻辑，除此之外SeAuthHelper.getCurrentToken()已经处理过一遍了*/
                if (currentToken == null){
                   return wrapException(request,response,SeResult.fail(ESeResponseCode.INVALID_TOKEN.getCode(),"用户令牌为空！"));
                }
            }

        }
        return true;
    }

    private boolean wrapException(HttpServletRequest request, HttpServletResponse response, SeResult result) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        /**支持跨域*/
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        try {
            writer = response.getWriter();
            String json = SeJsonUtils.object2Json(result);
            writer.print(json);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        /**返回false后，将不会向下执行指定Controller类中的method*/
        return false;
    }
}
