package dev.yhp.fin.vos.board;

import dev.yhp.fin.entities.ArticleEntity;
import dev.yhp.fin.entities.BoardEntity;
import dev.yhp.fin.enums.board.ListResult;
import dev.yhp.fin.interfaces.IResult;

public class ListVo extends BoardEntity implements IResult<ListResult> {
    private int page;
    private ListResult result;
    private ArticleEntity[] articles;
    private int startPage;
    private int endPage;
    private int maxPage;
    private int queryLimit;
    private int queryOffset;

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArticleEntity[] getArticles() {
        return this.articles;
    }

    public void setArticles(ArticleEntity[] articles) {
        this.articles = articles;
    }

    public int getStartPage() {
        return this.startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return this.endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getMaxPage() {
        return this.maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getQueryLimit() {
        return this.queryLimit;
    }

    public void setQueryLimit(int queryLimit) {
        this.queryLimit = queryLimit;
    }

    public int getQueryOffset() {
        return this.queryOffset;
    }

    public void setQueryOffset(int queryOffset) {
        this.queryOffset = queryOffset;
    }

    @Override
    public ListResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(ListResult result) {
        this.result = result;
    }
}