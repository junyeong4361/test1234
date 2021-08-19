<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="userEntity" type="dev.yhp.fin.entities.UserEntity"--%>
<header>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/board/list/notice">공지사항</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/board/list/free">자유게시판</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/board/list/qna">Q&A</a>
        </li>
    </ul>
    <div class="spring"></div>
    <ul>
        <c:if test="${userEntity == null}">
        <li>
            <a href="${pageContext.request.contextPath}/user/login">로그인</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/user/register">회원가입</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/user/recover">계정 복구</a>
        </li>
        </c:if>

        <c:if test="${userEntity != null}">
            <li>
                <b>${userEntity.nickname}</b><a>님 환영합니다.</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/user/modify">정보 수정</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/user/logout">로그아웃</a>
            </li>
        </c:if>
    </ul>
</header>