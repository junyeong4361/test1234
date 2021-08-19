package dev.yhp.fin.entities;

import java.util.Date;

public class VerificationCodeEntity {
    protected int index;
    protected Date createdAt;
    protected Date expiresAt;
    protected boolean isExpired;
    protected String userEmail;
    protected String code;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return this.isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}