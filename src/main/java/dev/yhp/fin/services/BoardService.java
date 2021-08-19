package dev.yhp.fin.services;

import dev.yhp.fin.entities.ArticleEntity;
import dev.yhp.fin.entities.BoardEntity;
import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.board.ListResult;
import dev.yhp.fin.enums.board.ReadResult;
import dev.yhp.fin.mappers.IBoardMapper;
import dev.yhp.fin.vos.board.ListVo;
import dev.yhp.fin.vos.board.ReadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "dev.yhp.fin.services.BoardService")
public class BoardService {
    private static class Config {
        public static final int ARTICLE_COUNT_PER_PAGE = 20;

        private Config() {
        }
    }

    private static class RegExp {
        public static final String CODE = "^([a-z]{2,50})$";
        public static final String PAGE = "^([1-9]([0-9]{0,3})?)$";
        public static final String ARTICLE_INDEX = "^([1-9]([0-9]{0,4})?)$";

        private RegExp() {
        }
    }

    private final IBoardMapper boardMapper;

    @Autowired
    public BoardService(IBoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public static boolean checkCode(String s) {
        return s != null && s.matches(RegExp.CODE);
    }

    public static boolean checkPage(String s) {
        return s != null && s.matches(RegExp.PAGE);
    }

    public static boolean checkArticleIndex(String s) {
        return s != null && s.matches(RegExp.ARTICLE_INDEX);
    }

    public void list(ListVo listVo) {
        if (!BoardService.checkCode(listVo.getCode()) ||
                !BoardService.checkPage(String.valueOf(listVo.getPage()))) {
            listVo.setResult(ListResult.NORMALIZATION_FAILURE);
            return;
        }
        BoardEntity boardEntity = this.boardMapper.selectBoard(listVo);
        if (boardEntity == null) {
            listVo.setResult(ListResult.BOARD_NOT_DEFINED);
            return;
        }
        listVo.setCode(boardEntity.getCode());
        listVo.setName(boardEntity.getName());
        listVo.setWriteForbidden(boardEntity.isWriteForbidden());
        int articleCount = this.boardMapper.selectArticleCount(listVo);
        int maxPageOffset = articleCount % Config.ARTICLE_COUNT_PER_PAGE == 0 ? 0 : 1;
        int maxPage = articleCount / Config.ARTICLE_COUNT_PER_PAGE + maxPageOffset;
        if (maxPage > 0) {
            int startPage = (listVo.getPage() / 10 + 1) * 10 - 9;
            int endPage = Math.min(startPage + 9, maxPage);
            listVo.setMaxPage(maxPage);
            if (listVo.getPage() > maxPage) {
                listVo.setPage(maxPage);
            }
            listVo.setStartPage(startPage);
            listVo.setEndPage(endPage);
            listVo.setQueryLimit(Config.ARTICLE_COUNT_PER_PAGE);
            listVo.setQueryOffset((listVo.getPage() - 1) * Config.ARTICLE_COUNT_PER_PAGE);
        }
        listVo.setArticles(this.boardMapper.selectArticlesByList(listVo));
        listVo.setResult(ListResult.SUCCESS);
    }

    public void read(UserEntity userEntity, ReadVo readVo) {
        if (!BoardService.checkArticleIndex(String.valueOf(readVo.getIndex()))) {
            readVo.setResult(ReadResult.NORMALIZATION_FAILURE);
            return;
        }
        ArticleEntity articleEntity = this.boardMapper.selectArticle(readVo);
        if (articleEntity == null || articleEntity.isDeleted()) {
            readVo.setResult(ReadResult.ARTICLE_NOT_DEFINED);
            return;
        }
        BoardEntity boardEntity = this.boardMapper.selectBoard(articleEntity);
        if (boardEntity.isReadForbidden() && (userEntity == null || !userEntity.isAdmin())) {
            readVo.setResult(ReadResult.READ_NOT_ALLOWED);
            return;
        }
        this.boardMapper.updateArticleView(readVo);
        readVo.setUserEmail(articleEntity.getUserEmail());
        readVo.setUserNickname(articleEntity.getUserNickname());
        readVo.setBoardCode(articleEntity.getBoardCode());
        readVo.setCreatedAt(articleEntity.getCreatedAt());
        readVo.setUpdatedAt(articleEntity.getUpdatedAt());
        readVo.setTitle(articleEntity.getTitle());
        readVo.setContent(articleEntity.getContent());
        readVo.setView(articleEntity.getView() + 1);
        readVo.setDeleted(articleEntity.isDeleted());
        readVo.setBoardPage(this.boardMapper.selectArticleCountGreaterThan(readVo) / Config.ARTICLE_COUNT_PER_PAGE + 1);
        readVo.setResult(ReadResult.SUCCESS);
    }
}