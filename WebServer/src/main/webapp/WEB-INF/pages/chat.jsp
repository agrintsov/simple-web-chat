<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Simple Chat</title>
</head>
<body>
    <table align="center" style="padding: 5px; vertical-align: top; width: 700px" >
        <tr>
            <td colspan="2"> <h1>Welcome to Simple Chat</h1> </td>
        </tr>
        <tr>
            <td>
                <ul id="messages">
                    <c:forEach var="message" items="${messages}">
                        <c:choose>
                            <c:when test="${message.authorName eq pageContext.request.userPrincipal.name}">
                                <li messageId="${message.id}">[${message.dateStr}] <span style="color: green; font-weight:bold">${message.authorName}</span>: ${message.content}</li>
                            </c:when>
                            <c:otherwise>
                                <li messageId="${message.id}">[${message.dateStr}] ${message.authorName}: ${message.content}</li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
                <textarea id="messageContent" rows="10" cols="45" name="message" maxlength="${messageSizeLimit}"></textarea>
                <button id="sendMessage">Send</button>
            </td>
            <td style="padding: 5px; vertical-align: top">
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
            </td>
        </tr>
    </table>
    <script type="text/javascript" src="/scripts/jquery-1.11.1.js"></script>
    <script>
        var messageLimit = ${messageLimit};
        var userUpdateFrequency= ${userUpdateFrequency};
        var messagesUpdateFrequency = ${messagesUpdateFrequency};

        $(document).ready(function () {
            setInterval(function () {
                $('#users').empty();
                $.getJSON('getOnlineUsers', function(data){
                    if (data.failed) {
                        console.log(data.errorList);
                    } else {
                        for(var i=0;i<data.resultObject.length;i++){
                            if (data.resultObject[i].me) {
                                $('#users').append('<li><span style="color: green; font-weight:bold">' + data.resultObject[i].name + '</span></li>');
                            } else {
                                $('#users').append('<li>' + data.resultObject[i].name + '</li>');
                            }
                        }
                    }
                });
            }, userUpdateFrequency);

            $("#sendMessage").click(function () {
                var messageContent = $("#messageContent").val();
                $.post("saveMessage",{message:messageContent},function(result){
                    if (result.failed) {
                        alert(result.errorList);
                    } else {
                        $("#messageContent").val("");
                    }
                });
            });

            setInterval(function () {
                var oldMessages = $("#messages").children();
                var getMessagesUrl;
                if (oldMessages.length === 0) {
                    getMessagesUrl = 'getMessages';
                } else {
                    var lastMessageId = oldMessages.last().attr('messageId');
                    getMessagesUrl = 'getNextMessages?messageId=' + lastMessageId;
                }
                $.getJSON(getMessagesUrl, function(data){
                    if (data.failed) {
                        console.log(data.errorList);
                    } else {
                        if (data.resultObject.length === 0) {
                            console.log("No new massages")
                        } else {
                            var oldMessagesNumber  = oldMessages.length;
                            var newMessagesNumber  = data.resultObject.length;
                            if(oldMessagesNumber + newMessagesNumber > messageLimit) {
                                var mustBeRemoved = (oldMessagesNumber + newMessagesNumber) - messageLimit;
                                if (mustBeRemoved === oldMessagesNumber) {
                                    $('#messages').empty();
                                } else {
                                    for(var i=0;i<mustBeRemoved;i++){
                                        var oldMessage = oldMessages[i];
                                        oldMessage.remove();
                                    }
                                }
                            }
                            for(var i=0;i<data.resultObject.length;i++){
                                var m = data.resultObject[i];
                                if (m.myMessage) {
                                    $('#messages').append('<li messageId="' + m.id + '">[' + m.date + '] <span style="color: green; font-weight:bold">' + m.authorName + '</span>: ' + m.content + '</li>');
                                } else {
                                    $('#messages').append('<li messageId="' + m.id + '">[' + m.date + '] ' + m.authorName + ': ' + m.content + '</li>');
                                }
                            }
                        }
                    }
                });
            }, messagesUpdateFrequency);
        });
    </script>
</body>
</html>