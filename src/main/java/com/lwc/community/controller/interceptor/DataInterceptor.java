package com.lwc.community.controller.interceptor;

import com.lwc.community.entity.User;
import com.lwc.community.service.DataService;
import com.lwc.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author Liu Wenchang
 */
@Component
public class DataInterceptor implements HandlerInterceptor {

    @Autowired
    private DataService dataService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 统计uv
        String ip = request.getRemoteHost();
        dataService.recordUV(ip);

        // 统计DAU
        User user = hostHolder.getUser();
        if (user !=null ){
            dataService.recordDAU(user.getId());
        }

        return true;
    }
}
