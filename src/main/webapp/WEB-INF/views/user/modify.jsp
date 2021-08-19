<%@ page import="dev.yhp.fin.enums.user.ModifyResult" %>
<jsp:useBean id="userEntity" scope="session" type="dev.yhp.fin.entities.UserEntity"/>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="modifyResult" type="dev.yhp.fin.enums.user.ModifyResult"--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>정보 수정</title>
    <style>
        input {
            margin-bottom: 0.25rem;
        }
    </style>
    <c:if test="${modifyResult != null}">
        <c:choose>
            <c:when test="${modifyResult == ModifyResult.NICKNAME_DUPLICATE}">
                <script>
                    alert('이미 사용 중인 별명입니다.');
                    window.history.back();
                </script>
            </c:when>
            <c:when test="${modifyResult == ModifyResult.SUCCESS}">
                <script>
                    alert('회원 정보가 성공적으로 수정되었습니다.');
                    window.location.href = 'modify';
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    alert('회원 정보를 수정하지 못하였습니다.\n\n현재 비밀번호가 올바른지 확인해주세요.');
                    window.history.back();
                </script>
            </c:otherwise>
        </c:choose>
        <% out.close(); %>
    </c:if>
    <link rel="stylesheet" href="../resources/stylesheets/common.css">
    <link rel="stylesheet" href="resources/stylesheets/modify.css">
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="../resources/scripts/ajax.class.js"></script>
    <script defer src="resources/scripts/modify.js"></script>
</head>
<body class="modify">
<%@ include file="/WEB-INF/views/header.jsp" %>
<div rel="cover" style="width: 100%; height: 100%; background-color: #00020475; display:none; position: fixed; z-index: 1;"></div>
<div rel="address-container" style="top: 50%; left: 50%; width: 30rem; height: 30rem; display: none; position: fixed; transform: translate(-50%, -50%); z-index: 2;"></div>
<main style="align-items: center; display: flex; flex-direction: column; justify-content: center;">
    <h1>정보 수정</h1>
    <form method="post" rel="modify-form" style="width: 40rem;">
        <section>
            <h2>기본 정보</h2>
            <table>
                <tbody>
                <tr>
                    <th>
                        <label for="email-input">이메일</label>
                    </th>
                    <td>
                        <input id="email-input" readonly type="text" value="${userEntity.email}">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="current-password-input">현재 비밀번호</label>
                    </th>
                    <td>
                        <input autofocus id="current-password-input" maxlength="50" name="currentPassword" placeholder="현재 비밀번호" type="password">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="password-input">비밀번호</label>
                    </th>
                    <td>
                        <input id="password-input" maxlength="50" name="password" placeholder="비밀번호" type="password">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="password-check-input">비밀번호 재입력</label>
                    </th>
                    <td>
                        <input id="password-check-input" maxlength="50" name="passwordCheck" placeholder="비밀번호 재입력" type="password">
                        <span class="input-message good" rel="password-message">항목을 비어두면 비밀번호를 변경하지 않습니다.</span>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="nickname-input">별명</label>
                    </th>
                    <td>
                        <input id="nickname-input" maxlength="10" name="nickname" placeholder="별명" type="text" value="${userEntity.nickname}">
                        <span class="input-message good" rel="nickname-message">현재 회원님의 별명입니다.</span>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="name-input">실명</label>
                    </th>
                    <td>
                        <input id="name-input" maxlength="10" name="name" placeholder="실명" type="text" value="${userEntity.name}">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="birth-input">생년월일</label>
                    </th>
                    <td>
                        <input id="birth-input" maxlength="6" name="birth" placeholder="생년월일" type="text" value="${userEntity.birth}">
                        <span>-</span>
                        <input id="gender-input" maxlength="1" name="gender" style="width: 1rem; text-align: center;" type="text" value="${userEntity.gender}">
                        <span>●●●●●●</span>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="contact-input">연락처</label>
                    </th>
                    <td>
                        <select id="contact-input" name="contactCompany">
                            <option value="skt" ${userEntity.contactCompany.equals("skt") ? "selected" : ""}>SKT</option>
                            <option value="kt" ${userEntity.contactCompany.equals("kt") ? "selected" : ""}>KT</option>
                            <option value="lgu" ${userEntity.contactCompany.equals("lgu") ? "selected" : ""}>LGU+</option>
                            <option value="etc" ${userEntity.contactCompany.equals("etc") ? "selected" : ""}>기타</option>
                        </select>
                        <select name="contactFirst">
                            <option selected value="010">010</option>
                        </select>
                        <input maxlength="4" name="contactSecond" placeholder="0000" style="width: 2rem; text-align: center;" type="text" value="${userEntity.contactSecond}">
                        <input maxlength="4" name="contactThird" placeholder="0000" style="width: 2rem; text-align: center;" type="text" value="${userEntity.contactThird}">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="address-input">주소</label>
                    </th>
                    <td>
                        <input id="address-input" maxlength="5" name="addressPostal" placeholder="우편번호" readonly style="width: 3.5rem;" type="text" value="${userEntity.addressPostal}">
                        <input rel="address-find-button" type="button" value="주소 찾기">
                        <br>
                        <input name="addressPrimary" placeholder="기본 주소" readonly style="width: 25rem;" type="text" value="${userEntity.addressPrimary}">
                        <br>
                        <input name="addressSecondary" placeholder="상세 주소" style="width: 25rem;" type="text" value="${userEntity.addressSecondary}">
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
        <section style="margin-top: 1rem; text-align: center;">
            <input onclick="window.history.back();" type="button" value="돌아가기">
            <input type="submit" value="정보 수정">
        </section>
    </form>
</main>
</body>
</html>