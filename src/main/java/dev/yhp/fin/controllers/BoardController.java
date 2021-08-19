package dev.yhp.fin.controllers;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.services.BoardService;
import dev.yhp.fin.vos.board.ListVo;
import dev.yhp.fin.vos.board.ReadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller(value = "dev.yhp.fin.controllers.BoardController")
@RequestMapping(value = "/board")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = {
            "/list/{boardCode}",
            "/list/{boardCode}/{boardPage}"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String listGet(@PathVariable(name = "boardCode") String boardCode,
                          @PathVariable(name = "boardPage") Optional<Integer> boardPage,
                          HttpServletRequest request) {
        ListVo listVo = new ListVo();
        listVo.setCode(boardCode);
        listVo.setPage(boardPage.orElse(1));
        this.boardService.list(listVo);
        request.setAttribute("listVo", listVo);
        return "board/list";
    }

    @RequestMapping(value = "/read/{articleIndex}")
    public String readGet(@PathVariable(name = "articleIndex") int articleIndex,
                          @SessionAttribute(name = "userEntity", required = false) UserEntity userEntity,
                          HttpServletRequest request) {
        ReadVo readVo = new ReadVo();
        readVo.setIndex(articleIndex);
        this.boardService.read(userEntity, readVo);
        request.setAttribute("readVo", readVo);
        return "board/read";
    }
}