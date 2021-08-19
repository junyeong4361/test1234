<%@ page import="dev.yhp.fin.enums.board.ListResult" %>
<%@ page import="java.util.Arrays" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="listVo" type="dev.yhp.fin.vos.board.ListVo"--%>
<%--@elvariable id="userEntity" type="dev.yhp.fin.entities.UserEntity"--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${listVo.name == null ? "게시판 오류" : listVo.name}</title>
    <c:if test="${listVo.result != ListResult.SUCCESS}">
        <c:choose>
            <c:when test="${listVo.result == ListResult.BOARD_NOT_DEFINED}">
                <script>
                    alert('존재하지 않는 게시판입니다.');
                    window.history.back();
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    alert('게시판을 확인하는 도중 오류가 발생하였습니다.\n\n잠시 후 다시 시도해주세요.');
                    window.history.back();
                </script>
            </c:otherwise>
        </c:choose>
        <% out.close(); %>
    </c:if>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheets/common.css">
</head>
<body class="list">
<%@ include file="/WEB-INF/views/header.jsp" %>
<main style="align-items: center; display: flex; flex-direction: column; justify-content: flex-start;">
    <h1>${listVo.name}</h1>
    <table style="width: 40rem;">
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성 일시</th>
            <th>조회수</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${Arrays.stream(listVo.articles).count() == 0}">
            <tr>
                <td colspan="5" style="color:#606264; cursor:default; font-size: 1rem; padding: 2.5rem 0; text-align: center;"><i>작성된 게시글이 없습니다.</i></td>
            </tr>
        </c:if>
        <c:forEach var="article" items="${listVo.articles}">
            <tr>
                <td>${article.index}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/board/read/${article.index}" style="text-decoration: underline;">${article.title}</a>
                </td>
                <td>${article.userNickname}</td>
                <td>${article.formatCreatedAt()}</td>
                <td>${article.view}</td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <c:if test="${listVo.maxPage > 0}">
            <tr>
                <td colspan="5">
                    <div style="align-items: center; display: flex; flex-direction: row; justify-content: center; margin-top: 1rem;">
                        <c:if test="${listVo.page > 1}">
                            <a href="${pageContext.request.contextPath}/board/list/${listVo.code}/1" style="background-color: #f0f2f4; border-radius: 0.25rem; padding: 0.33rem 0.5rem; margin-right: 0.5rem;">&lt;&lt;</a>
                            <a href="${pageContext.request.contextPath}/board/list/${listVo.code}/${listVo.page - 1}" style="background-color: #f0f2f4; border-radius: 0.25rem; padding: 0.33rem 0.5rem; margin-right: 0.5rem;">&lt;</a>
                        </c:if>
                        <c:forEach var="page" begin="${listVo.startPage}" end="${listVo.endPage}">
                            <c:choose>
                                <c:when test="${listVo.page == page}">
                                    <b style="background-color: #e0e2e4; border-radius: 0.25rem; padding: 0.33rem 0.5rem; margin-right: 0.5rem;">${page}</b>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/board/list/${listVo.code}/${page}" style="background-color: #f0f2f4; border-radius: 0.25rem; padding: 0.33rem 0.5rem; margin-right: 0.5rem;">${page}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${listVo.page < listVo.maxPage}">
                            <a href="${pageContext.request.contextPath}/board/list/${listVo.code}/${listVo.page + 1}" style="background-color: #f0f2f4; border-radius: 0.25rem; padding: 0.33rem 0.5rem; margin-right: 0.5rem;">&gt;</a>
                            <a href="${pageContext.request.contextPath}/board/list/${listVo.code}/${listVo.maxPage}" style="background-color: #f0f2f4; border-radius: 0.25rem; padding: 0.33rem 0.5rem; margin-right: 0.5rem;">&gt;&gt;</a>
                        </c:if>
                    </div>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="5">
                <div style="align-items: center; display: flex; flex-direction: row; justify-content: center;">
                    <a href="#" onclick="window.location.reload();">새로고침</a>
                    <span style="flex: 1;"></span>
                    <c:if test="${userEntity != null && (userEntity.admin || !listVo.writeForbidden)}">
                        <a href="${pageContext.request.contextPath}/board/write/${listVo.code}">글쓰기</a>
                    </c:if>
                </div>
            </td>
        </tr>
        </tfoot>
    </table>
</main>
</body>
</html>
<!--
3. 한 페이지에 표시될 게시글을 수를 20개로 지정하고 페이징하기.
-->