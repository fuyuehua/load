package com.rip.load.Interceptor;

import com.alibaba.fastjson.JSON;
import com.rip.load.pojo.Permission;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.PermissionService;
import com.rip.load.service.UserService;
import com.rip.load.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String authorization = request.getHeader("authorization");
        if(authorization != null) {
            boolean b = redisUtil.hasKey(authorization);
            if(b) {
                String json = (String) redisUtil.get(authorization);
                if (json != null) {
                    User user = JSON.parseObject(json, User.class);
                    if(user != null){
                        String[] splitRip = requestURI.split("/api");
                        if(splitRip.length == 2){
                            //调用接口了
                            Map<String, Object> map = userService.getRolePermission(user.getId());
                            List<Permission> list = (List<Permission>)map.get("permission");
                            boolean bo = permissionService.hasPermissionUrl(splitRip[1], list);
                            if(bo){
                                UserThreadLocal.set(user);
                                return true;
                            }else{
                                return false;
                            }
                        }else {
                            //没调用接口
//                            UserThreadLocal.set(user);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
