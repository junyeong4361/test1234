<%@ page import="dev.yhp.fin.enums.user.PasswordRecoverResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="passwordRecoverVo" type="dev.yhp.fin.vos.user.PasswordRecoverVo"--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>비밀번호 재설정</title>
</head>
<body>
<script>
    <c:choose>
        <c:when test="${passwordRecoverVo.result == PasswordRecoverResult.SUCCESS}">
            alert('입력하신 정보와 일치하는 회원의 비밀번호를 재설정할 수 있는 링크를 이메일로 전송하였습니다. 30분 내에 확인해주세요.');
            window.location.href='login';
        </c:when>
        <c:otherwise>
            alert('입력하신 정보와 일치하는 회원을 찾을 수 없습니다.');
            window.history.back();
        </c:otherwise>
    </c:choose>
</script>
</body>
</html>