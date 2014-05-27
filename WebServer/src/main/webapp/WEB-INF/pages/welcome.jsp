<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Simple Chat</title>
</head>
<body>
    <h1>Simple Chat</h1>
    <c:if test="${pageContext.request.userPrincipal == null}">
        <c:if test="${login}">
            <p style="color: red">To enter the chat you must login</p>
        </c:if>
        <c:if test="${alreadyRegistered}">
            <p style="color: red">User with same name already have been registered</p>
        </c:if>
        <form name="f" action="/j_spring_security_check" method="POST">
            <table>
                <tbody>
                <tr>
                    <td>Enter your name:</td>
                    <td><input type="text" name="j_username" value=""></td>
                </tr>
                <tr>
                    <td colspan="2"><input name="submit" type="submit" value="Login"></td>
                </tr>
                </tbody>
            </table>
        </form>
    </c:if>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <a href="j_spring_security_logout"> Logout</a> </br> <a href="/chat"> -= Go to chat =- </a>
    </c:if>
</body>
</html>
