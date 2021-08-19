package dev.yhp.fin.entities;

import dev.yhp.fin.interfaces.IBoard;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleEntity implements IBoard {
    protected int index;
    protected String userEmail;
    protected String userNickname;
    protected String boardCode;
    protected Date createdAt;
    protected Date updatedAt;
    protected String title;
    protected String content;
    protected int view;
    protected boolean isDeleted;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNickname() {
        return this.userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setBoardCode(String boardCode) {
        this.boardCode = boardCode;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getView() {
        return this.view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String formatCreatedAt() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createdAt);
    }

    public String formatUpdatedAt() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.updatedAt);
    }

    @Override
    public String getBoardCode() {
        return this.boardCode;
    }
}