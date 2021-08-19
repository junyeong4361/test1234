package dev.yhp.fin.entities;

import dev.yhp.fin.interfaces.IBoard;

public class BoardEntity implements IBoard {
    protected String code;
    protected String name;
    protected boolean isReadForbidden;
    protected boolean isWriteForbidden;
    protected boolean isCommentForbidden;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReadForbidden() {
        return this.isReadForbidden;
    }

    public void setReadForbidden(boolean readForbidden) {
        isReadForbidden = readForbidden;
    }

    public boolean isWriteForbidden() {
        return this.isWriteForbidden;
    }

    public void setWriteForbidden(boolean writeForbidden) {
        isWriteForbidden = writeForbidden;
    }

    public boolean isCommentForbidden() {
        return this.isCommentForbidden;
    }

    public void setCommentForbidden(boolean commentForbidden) {
        isCommentForbidden = commentForbidden;
    }

    @Override
    public String getBoardCode() {
        return this.code;
    }
}