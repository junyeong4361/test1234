package dev.yhp.fin.vos.board;

import dev.yhp.fin.entities.ArticleEntity;
import dev.yhp.fin.enums.board.ReadResult;
import dev.yhp.fin.interfaces.IResult;

public class ReadVo extends ArticleEntity implements IResult<ReadResult> {
    private ReadResult result;
    private int boardPage;

    public int getBoardPage() {
        return this.boardPage;
    }

    public void setBoardPage(int boardPage) {
        this.boardPage = boardPage;
    }

    @Override
    public ReadResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(ReadResult result) {
        this.result = result;
    }
}