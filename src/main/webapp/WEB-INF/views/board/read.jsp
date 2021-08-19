<%@ page import="dev.yhp.fin.enums.board.ReadResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="readVo" type="dev.yhp.fin.vos.board.ReadVo"--%>
<%--@elvariable id="userEntity" type="dev.yhp.fin.entities.UserEntity"--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${readVo.title == null ? "게시글 오류" : readVo.title}</title>
    <c:if test="${readVo.result != ReadResult.SUCCESS}">
        <c:choose>
            <c:when test="${readVo.result == ReadResult.ARTICLE_NOT_DEFINED}">
                <script>
                    alert('존재하지 않는 게시글입니다.');
                    window.history.back();
                </script>
            </c:when>
            <c:when test="${readVo.result == ReadResult.READ_NOT_ALLOWED}">
                <script>
                    alert('게시글을 읽을 권한이 없습니다.');
                    window.history.back();
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    alert('게시글을 확인하는 도중 오류가 발생하였습니다.\n\n잠시 후 다시 시도해주세요.');
                    window.history.back();
                </script>
            </c:otherwise>
        </c:choose>
        <% out.close(); %>
    </c:if>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheets/common.css">
</head>
<body class="read">
<%@ include file="/WEB-INF/views/header.jsp" %>
<main style="align-items: center; display: flex; flex-direction: column; justify-content: flex-start;">
    <h1>${readVo.title}</h1>
    <table style="width: 40rem;">
        <thead>
        <tr>
            <th>제목</th>
            <td colspan="7">${readVo.title}</td>
        </tr>
        <tr>
            <th>작성자</th>
            <td>${readVo.userNickname}</td>
            <th>작성 일시</th>
            <td>${readVo.formatCreatedAt()}</td>
            <th>수정 일시</th>
            <td>${readVo.formatUpdatedAt()}</td>
            <th>조회수</th>
            <td>${readVo.view}</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="7" style="padding: 1rem 0;">${readVo.content}</td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="8">
                <div style="align-items: center; display: flex; flex-direction: row; justify-content: center;">
                    <a href="${pageContext.request.contextPath}/board/list/${readVo.boardCode}/${readVo.boardPage}">목록</a>
                    <span style="flex: 1;"></span>
                    <c:if test="${userEntity.email.equals(readVo.userEmail) || userEntity.admin}">
                        <a href="${pageContext.request.contextPath}/board/modify/${readVo.index}" style="margin-right: 0.5rem;">수정</a>
                        <a href="#" onclick="if (confirm('정말로 게시글을 삭제할까요?')) {window.location.href='${pageContext.request.contextPath}/board/delete/${readVo.index}';}">삭제</a>
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