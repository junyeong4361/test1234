<%@ page import="dev.yhp.fin.enums.user.EmailVerificationResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>이메일 인증</title>
    <script>
        <c:choose>
            <c:when test="${emailVerificationResult == EmailVerificationResult.SUCCESS}">
                <c:set var="alert" value="이메일을 인증하였습니다. 로그인해주세요."/>
            </c:when>
            <c:otherwise>
                <c:set var="alert" value="이메일 인증에 실패하였습니다."/>
            </c:otherwise>
        </c:choose>
        alert('${alert}');
        <c:choose>
            <c:when test="${emailVerificationResult == EmailVerificationResult.SUCCESS}">
                window.location.href='login';
            </c:when>
            <c:otherwise>
                window.close();
            </c:otherwise>
        </c:choose>
    </script>
</head>
</html>