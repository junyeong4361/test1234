<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>계정 복구</title>
    <link rel="stylesheet" href="../resources/stylesheets/common.css">
    <script defer src="resources/scripts/recover.js"></script>
    <style>
        form {
            align-items: stretch;
            background-color: #f6f8fa;
            border: 0.0625rem solid #f0f2f4;
            border-radius: 0.25rem;
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            overflow: hidden;
            padding: 2rem;
        }

        th {
            padding-right: 0.5rem;
            white-space: nowrap;
            word-break: keep-all;
        }

        td {
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<main style="align-items: center;">
    <h1>계정 복구</h1>
    <section style="width: 50rem; display: grid; grid-gap: 1rem; grid-template-columns: 1fr 1fr; grid-template-rows: 1fr;">
        <form action="recover-email" method="post">
            <h2 style="text-align: center;">이메일 복구</h2>
            <table>
                <tbody>
                <tr>
                    <th>
                        <label for="e-name-input">실명</label>
                    </th>
                    <td>
                        <input id="e-name-input" maxlength="10" name="name" placeholder="실명" type="text">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="e-birth-input">생년월일</label>
                    </th>
                    <td>
                        <input id="e-birth-input" maxlength="6" name="birth" placeholder="생년월일" type="text">
                        <span>-</span>
                        <input id="e-gender-input" maxlength="1" name="gender" style="width: 1rem; text-align: center;" type="text">
                        <span>●●●●●●</span>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="e-contact-input">연락처</label>
                    </th>
                    <td>
                        <select id="e-contact-input" name="contactCompany">
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
                </tbody>
            </table>
            <section style="margin-top: 1rem; text-align: center;">
                <input type="submit" value="이메일 복구">
            </section>
        </form>
        <form action="recover-password" method="post">
            <h2 style="text-align: center;">비밀번호 복구</h2>
            <table>
                <tbody>
                <tr>
                    <th>
                        <label for="p-email-input">이메일</label>
                    </th>
                    <td>
                        <input id="p-email-input" maxlength="50" name="email" placeholder="이메일" type="email">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="p-name-input">실명</label>
                    </th>
                    <td>
                        <input id="p-name-input" maxlength="10" name="name" placeholder="실명" type="text">
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="p-birth-input">생년월일</label>
                    </th>
                    <td>
                        <input id="p-birth-input" maxlength="6" name="birth" placeholder="생년월일" type="text">
                        <span>-</span>
                        <input id="p-gender-input" maxlength="1" name="gender" style="width: 1rem; text-align: center;" type="text">
                        <span>●●●●●●</span>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="p-contact-input">연락처</label>
                    </th>
                    <td>
                        <select id="p-contact-input" name="contactCompany">
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
                </tbody>
            </table>
            <section style="margin-top: 1rem; text-align: center;">
                <input type="submit" value="비밀번호 복구">
            </section>
        </form>
    </section>
</main>
</body>
</html>