package dev.yhp.fin.interceptors;

import dev.yhp.fin.models.ClientModel;
import dev.yhp.fin.services.SecurityService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityInterceptor implements HandlerInterceptor {
    @Resource
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ClientModel clientModel = (ClientModel) request.getAttribute("clientModel");
        if (this.securityService.isIpBlocked(clientModel)) {
            response.setStatus(418);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/blocked.jsp");
            dispatcher.forward(request, response);
            return false;
        }
        return true;
    }
}