package dev.yhp.fin.models;

import dev.yhp.fin.exceptions.InvalidClientException;
import dev.yhp.fin.utils.ClientRegexUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientModel {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final String ip;
    private final String userAgent;
    private final String requestUri;

    public ClientModel(HttpServletRequest request, HttpServletResponse response) throws InvalidClientException {
        this.request = request;
        this.response = response;
        if (request.getRemoteAddr().equals("127.0.0.1")) {
            this.ip = "127.0.0.1";
        } else {
            this.ip = request.getHeader("CF-Connecting-IP");
        }
        this.userAgent = request.getHeader("User-Agent");
        this.requestUri = request.getRequestURI();

        if (!ClientRegexUtil.checkIp(this.ip) ||
                !ClientRegexUtil.checkUserAgent(this.userAgent) ||
                !ClientRegexUtil.checkRequestUri(this.requestUri)) {
            throw new InvalidClientException(String.format("IP : %s\nUser Agent : %s\nRequest URI : %s",
                    this.ip == null ? "(NULL)" : this.ip,
                    this.userAgent == null ? "(NULL)" : this.userAgent,
                    this.requestUri == null ? "(NULL)" : this.requestUri));
        }
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public String getIp() {
        return this.ip;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public String getRequestUri() {
        return this.requestUri;
    }
}