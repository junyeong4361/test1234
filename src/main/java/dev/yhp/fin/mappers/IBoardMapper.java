package dev.yhp.fin.mappers;

import dev.yhp.fin.entities.ArticleEntity;
import dev.yhp.fin.entities.BoardEntity;
import dev.yhp.fin.interfaces.IBoard;
import dev.yhp.fin.vos.board.ListVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@SuppressWarnings("unused")
public interface IBoardMapper {
    ArticleEntity selectArticle(ArticleEntity articleEntity);

    ArticleEntity[] selectArticlesByList(ListVo listVo);

    int selectArticleCount(BoardEntity boardEntity);

    int selectArticleCountGreaterThan(ArticleEntity articleEntity);

    BoardEntity selectBoard(IBoard board);

    int updateArticleView(ArticleEntity articleEntity);
}