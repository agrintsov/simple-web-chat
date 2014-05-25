<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

    <script>
        $(document).ready(function () {
            $("#update").click(function () {
                $.getJSON('getOnlineUsers', function(data){
                    var items = [];
                    var getUsersResult = jQuery.parseJSON(data);
                    alert("addCompanyPriceType ok");
                });
            });

        });
    </script>

    <table>
        <h1>Welcome to Simple Chat</h1>
        <tr>
            <td>
                <ul id="messages">
                    <c:forEach var="message" items="${messages}">
                        <c:choose>
                            <c:when test="${message.authorName eq pageContext.request.userPrincipal.name}">
                                <li>[${message.dateStr}] <span style="color: green; font-weight:bold">${message.authorName}</span>: ${message.content}</li>
                            </c:when>
                            <c:otherwise>
                                <li>[${message.dateStr}] ${message.authorName}: ${message.content}</li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
                <form  action="/saveMessage" method="POST">
                    <textarea rows="10" cols="45" name="message"></textarea>
                    <input name="submit" type="submit" value="Send">
                </form>
            </td>
            <td>
                <h2><a href="j_spring_security_logout"> Logout</a></h2>
                <ul id="users">
                    <c:forEach var="user" items="${users}">
                        <c:choose>
                            <c:when test="${user.name eq pageContext.request.userPrincipal.name}">
                                <li><span style="color: green; font-weight:bold">${user.name}</span></li>
                            </c:when>
                            <c:otherwise>
                                <li>${user.name}</li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
                <%--<button id="update">UPDATE</button>--%>
            </td>
        </tr>
    </table>


</body>
</html>