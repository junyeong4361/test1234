<%@ page import="dev.yhp.fin.enums.user.LoginResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>로그인</title>
    <link rel="stylesheet" href="../resources/stylesheets/common.css">
    <script defer src="resources/scripts/login.js"></script>
    <script>
        <c:if test="${loginResult != null}">
            <c:choose>
                <c:when test="${loginResult == LoginResult.DELETED}">
                    <c:set var="alert" value="해당 계정은 삭제된 계정입니다. 관리자에게 문의해주세요."/>
                </c:when>
                <c:when test="${loginResult == LoginResult.EMAIL_NOT_VERIFIED}">
                    <c:set var="alert" value="아직 이메일 인증이 완료되지 않았습니다."/>
                </c:when>
                <c:when test="${loginResult == LoginResult.SUSPENDED}">
                    <c:set var="alert" value="해당 계정은 정지되어 있습니다. 관리자에게 문의해주세요."/>
                </c:when>
                <c:otherwise>
                    <c:set var="alert" value="이메일 혹은 비밀번호가 올바르지 않습니다."/>
                </c:otherwise>
            </c:choose>
            alert('${alert}');
            window.history.back();
        </c:if>
    </script>
</head>
<body class="login">
<%@ include file="/WEB-INF/views/header.jsp" %>
<main style="align-items: center; display: flex; flex-direction: column; justify-content: center;">
    <h1>로그인</h1>
    <form method="post" rel="login-form">
        <section>
            <table>
                <tbody>
                <tr>
                    <th style="padding-right: 1rem; text-align: right;">
                        <label for="email-input">이메일</label>
                    </th>
                    <td>
                        <input autofocus id="email-input" maxlength="50" name="email" placeholder="이메일" type="email" value="yoloyhpark@gmail.com">
                    </td>
                </tr>
                <tr>
                    <th style="padding-right: 1rem; text-align: right;">
                        <label for="password-input">비밀번호</label>
                    </th>
                    <td>
                        <input id="password-input" maxlength="50" name="password" placeholder="비밀번호" type="password" value="test1234">
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
        <section style="margin-top: 1rem; text-align: center;">
            <input onclick="window.history.back();" type="button" value="돌아가기">
            <input type="submit" value="로그인">
        </section>
        <section style="margin-top: 1rem;">
            <div>
                <span>아직 계정이 없으신가요?</span>
                <a href="${pageContext.request.contextPath}/user/register">회원가입</a>
            </div>
            <div>
                <span>계정을 분실하셨나요?</span>
                <a href="${pageContext.request.contextPath}/user/recover">계정 복구</a>
            </div>
        </section>
    </form>
</main>
</body>
</html>