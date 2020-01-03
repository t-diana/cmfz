<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BS-172bf1a3efa1482d9364f61e2923bb62", //替换为您的应用appkey
        });
        goEasy.publish({
            channel: "my_channel", //替换为您自己的channel
            message: "Hello, GoEasy!" //替换为您想要发送的消息内容
        });
        goEasy.subscribe({
            channel: "my_channel", //替换为您自己的channel
            onMessage: function (message) {
                console.log("Channel:" + message.channel + " content:" + message.content);
            }
        });
    </script>
</head>
<body>
</body>
</html>