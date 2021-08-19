package dev.yhp.fin.entities;

import java.util.Date;

public class PasswordResetKeyEntity {
    protected int index;
    protected Date createdAt = new Date();
    protected Date expiresAt;
    protected boolean isExpired = false;
    protected String key;
    protected String userEmail;
    protected boolean isVerified = false;

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

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isVerified() {
        return this.isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}