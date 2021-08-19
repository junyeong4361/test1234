<%@ page import="dev.yhp.fin.enums.user.RegisterResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>회원가입</title>
    <style>
        input {
            margin-bottom: 0.25rem;
        }
    </style>
    <script>
        <c:if test="${registerResult != null}">
        <c:choose>
        <c:when test="${registerResult == RegisterResult.SUCCESS}">
        <c:set var="alert" scope="request" value="가입에 사용한 이메일 주소로 인증 메일을 전송하였습니다.\\n\\n이메일 인증을 완료한 뒤 계정을 사용할 수 있습니다."/>
        </c:when>
        <c:when test="${registerResult == RegisterResult.EMAIL_DUPLICATE}">
        <c:set var="alert" scope="request" value="이미 사용 중인 이메일입니다."/>
        </c:when>
        <c:when test="${registerResult == RegisterResult.NICKNAME_DUPLICATE}">
        <c:set var="alert" scope="request" value="이미 사용 중인 별명입니다."/>
        </c:when>
        <c:otherwise>
        <c:set var="alert" scope="request" value="회원가입 도중 알 수 없는 오류가 발생하였습니다. 잠시 후 다시 시도하시거나 관리자에게 문의해주세요."/>
        </c:otherwise>
        </c:choose>
        alert('${alert}');
        <c:choose>
        <c:when test="${registerResult == RegisterResult.SUCCESS}">
        window.location.href = 'login';
        </c:when>
        <c:otherwise>
        window.history.back();
        </c:otherwise>
        </c:choose>
        </c:if>
    </script>
    <link rel="stylesheet" href="../resources/stylesheets/common.css">
    <link rel="stylesheet" href="resources/stylesheets/register.css">
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="../resources/scripts/ajax.class.js"></script>
    <script defer src="resources/scripts/register.js"></script>
</head>
<body class="register">
<%@ include file="/WEB-INF/views/header.jsp" %>
<div rel="cover" style="width: 100%; height: 100%; background-color: #00020475; display:none; position: fixed; z-index: 1;"></div>
<div rel="address-container" style="top: 50%; left: 50%; width: 30rem; height: 30rem; display: none; position: fixed; transform: translate(-50%, -50%); z-index: 2;"></div>
<main style="align-items: center; display: flex; flex-direction: column; justify-content: center;">
    <h1>회원가입</h1>
    <form method="post" rel="register-form" style="width: 40rem;">
        <section>
            <h2>기본정보</h2>
            <table>
                <tbody>
                <tr>
                    <th>
                        <label for="email-input">이메일</label>
                    </th>
                    <td>
                        <input autofocus id="email-input" maxlength="50" name="email" placeholder="이메일" type="email">
                        <span class="input-message" rel="email-message">이미 사용 중인 이메일입니다.</span>
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
                        <span class="input-message" rel="password-message">재입력한 비밀번호가 일치하지 않습니다.</span>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="nickname-input">별명</label>
                    </th>
                    <td>
                        <input id="nickname-input" maxlength="10" name="nickname" placeholder="별명" type="text">
                        <span class="input-message" rel="nickname-message">이미 사용 중인 별명입니다.</span>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="name-input">실명</label>
                    </th>
                    <td>
                        <input id="name-input" maxlength="10" name="name" placeholder="실명" type="text">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="birth-input">생년월일</label>
                    </th>
                    <td>
                        <input id="birth-input" maxlength="6" name="birth" placeholder="생년월일" type="text">
                        <span>-</span>
                        <input id="gender-input" maxlength="1" name="gender" style="width: 1rem; text-align: center;" type="text">
                        <span>●●●●●●</span>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="contact-input">연락처</label>
                    </th>
                    <td>
                        <select id="contact-input" name="contactCompany">
                            <option selected value="skt">SKT</option>
                            <option value="kt">KT</option>
                            <option value="lgu">LGU+</option>
                            <option value="etc">기타</option>
                        </select>
                        <select name="contactFirst">
                            <option selected value="010">010</option>
                        </select>
                        <input maxlength="4" name="contactSecond" placeholder="0000" style="width: 2rem; text-align: center;" type="text">
                        <input maxlength="4" name="contactThird" placeholder="0000" style="width: 2rem; text-align: center;" type="text">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="address-input">주소</label>
                    </th>
                    <td>
                        <input id="address-input" maxlength="5" name="addressPostal" placeholder="우편번호" readonly style="width: 3.5rem;" type="text">
                        <input rel="address-find-button" type="button" value="주소 찾기">
                        <br>
                        <input name="addressPrimary" placeholder="기본 주소" readonly style="width: 25rem;" type="text">
                        <br>
                        <input name="addressSecondary" placeholder="상세 주소" style="width: 25rem;" type="text">
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
        <section>
            <h2>약관</h2>
            <table class="term">
                <thead>
                <tr>
                    <th>
                        <h3>개인정보처리방침</h3>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <textarea readonly>개인정보처리방침</textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input name="agreePrivacy" type="checkbox">
                            <span>[필수] 개인정보처리방침을 읽어보았으며 이해하고 동의합니다.</span>
                        </label>
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="term">
                <thead>
                <tr>
                    <th>
                        <h3>이용약관</h3>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <textarea readonly>이용약관</textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input name="agreeTerm" type="checkbox">
                            <span>[필수] 이용약관을 읽어보았으며 이해하고 동의합니다.</span>
                        </label>
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="term">
                <thead>
                <tr>
                    <th>
                        <h3>개인정보수집 및 마케팅목적활용</h3>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <textarea readonly>개인정보수집 및 마케팅목적활용</textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input name="agreeMarketing" type="checkbox">
                            <span>[선택] 개인정보수집 및 마케팅목적활용을 읽어보았으며 이해하고 동의합니다.</span>
                        </label>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
        <section style="margin-top: 1rem; text-align: center;">
            <input onclick="window.history.back();" type="button" value="돌아가기">
            <input type="reset" value="다시 작성">
            <input type="submit" value="회원가입">
        </section>
    </form>
</main>
</body>
</html>