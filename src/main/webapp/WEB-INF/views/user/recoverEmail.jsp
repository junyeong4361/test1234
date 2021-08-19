<%@ page import="dev.yhp.fin.enums.user.EmailRecoverResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="emailRecoverVo" type="dev.yhp.fin.vos.user.EmailRecoverVo"--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>이메일 복구</title>
</head>
<body>
<script>
    <c:choose>
        <c:when test="${emailRecoverVo.result == EmailRecoverResult.SUCCESS}">
            const email = '${emailRecoverVo.email}';
            const href = `login?re=\${email}`;
            if (confirm(`입력하신 정보로 찾은 이메일은 <\${email}>입니다.\n\n확인을 클릭하면 이메일을 복사한 뒤 로그인 페이지로 이동합니다.`)) {
                setTimeout(() => {
                    window.navigator.clipboard.writeText(email);
                    window.location.href=href;
                }, 10);
            } else {
                window.location.href=href;
            }
        </c:when>
        <c:otherwise>
            alert('입력하신 정보와 일치하는 회원을 찾을 수 없습니다.');
            window.history.back();
        </c:otherwise>
    </c:choose>
</script>
</body>
</html>