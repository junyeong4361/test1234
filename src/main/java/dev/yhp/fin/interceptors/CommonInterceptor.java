package dev.yhp.fin.interceptors;

import dev.yhp.fin.exceptions.InvalidClientException;
import dev.yhp.fin.models.ClientModel;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws InvalidClientException {
        ClientModel clientModel = new ClientModel(request, response);
        request.setAttribute("clientModel", clientModel);
        return true;
    }
}