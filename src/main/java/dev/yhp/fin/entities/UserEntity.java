package dev.yhp.fin.entities;

import java.util.Date;

public class UserEntity {
    protected String email;
    protected Date createdAt;
    protected Date updatedAt;
    protected String password;
    protected String nickname;
    protected String name;
    protected String birth;
    protected int gender;
    protected String contactCompany;
    protected String contactFirst;
    protected String contactSecond;
    protected String contactThird;
    protected String addressPostal;
    protected String addressPrimary;
    protected String addressSecondary;
    protected boolean isAdmin;
    protected boolean isEmailVerified;
    protected boolean isSuspended;
    protected boolean isDeleted;
    protected boolean isMarketing;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return this.birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getContactCompany() {
        return this.contactCompany;
    }

    public void setContactCompany(String contactCompany) {
        this.contactCompany = contactCompany;
    }

    public String getContactFirst() {
        return this.contactFirst;
    }

    public void setContactFirst(String contactFirst) {
        this.contactFirst = contactFirst;
    }

    public String getContactSecond() {
        return this.contactSecond;
    }

    public void setContactSecond(String contactSecond) {
        this.contactSecond = contactSecond;
    }

    public String getContactThird() {
        return this.contactThird;
    }

    public void setContactThird(String contactThird) {
        this.contactThird = contactThird;
    }

    public String getAddressPostal() {
        return this.addressPostal;
    }

    public void setAddressPostal(String addressPostal) {
        this.addressPostal = addressPostal;
    }

    public String getAddressPrimary() {
        return this.addressPrimary;
    }

    public void setAddressPrimary(String addressPrimary) {
        this.addressPrimary = addressPrimary;
    }

    public String getAddressSecondary() {
        return this.addressSecondary;
    }

    public void setAddressSecondary(String addressSecondary) {
        this.addressSecondary = addressSecondary;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isEmailVerified() {
        return this.isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public boolean isSuspended() {
        return this.isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isMarketing() {
        return this.isMarketing;
    }

    public void setMarketing(boolean marketing) {
        isMarketing = marketing;
    }
}