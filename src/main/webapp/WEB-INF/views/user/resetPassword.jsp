<%@ page import="dev.yhp.fin.enums.user.PasswordResetResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="passwordResetVo" type="dev.yhp.fin.vos.user.PasswordResetVo"--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>비밀번호 재설정</title>
    <link rel="stylesheet" href="../resources/stylesheets/common.css">
    <c:choose>
        <c:when test="${passwordResetVo.result == PasswordResetResult.KEY_INVALID}">
            <script>
                alert('비밀번호를 재설정할 수 있는 링크가 만료되었거나 올바르지 않은 접근입니다.');
                window.close();
            </script>
        </c:when>
        <c:when test="${passwordResetVo.result == PasswordResetResult.KEY_VALID}"/>
        <c:when test="${passwordResetVo.result == PasswordResetResult.SUCCESS}">
            <script>
                alert('비밀번호가 성공적으로 재설정되었습니다.');
                window.location.href = 'login';
            </script>
        </c:when>
        <c:otherwise>
            <script>
                alert('알 수 없는 이유로 비밀번호를 재설정하지 못하였습니다.');
                window.close();
            </script>
        </c:otherwise>
    </c:choose>
    <c:if test="${passwordResetVo.result != PasswordResetResult.KEY_VALID}">
        <% out.close(); %>
    </c:if>
    <script defer src="resources/scripts/resetPassword.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<main style="align-items: center; display: flex; flex-direction: column; justify-content: center;">
    <h1>비밀번호 재설정</h1>
    <form action="reset-password" method="post" rel="reset-form" style="width: 20rem;">
        <input name="key" type="hidden" value="${passwordResetVo.key}">
        <table>
            <tbody>
            <tr>
                <th>
                    <label for="password-input">비밀번호</label>
                </th>
                <td>
                    <input autofocus id="password-input" maxlength="50" name="password" placeholder="비밀번호" type="password">
                </td>
            </tr>
            <tr>
                <th>
                    <label for="password-check-input">비밀번호 재입력</label>
                </th>
                <td>
                    <input id="password-check-input" maxlength="50" name="passwordCheck" placeholder="비밀번호 재입력" type="password">
                </td>
            </tr>
            </tbody>
        </table>
        <section style="margin-top: 1rem; text-align: center;">
            <input type="submit" value="비밀번호 재설정">
        </section>
    </form>
</main>
</body>
</html>