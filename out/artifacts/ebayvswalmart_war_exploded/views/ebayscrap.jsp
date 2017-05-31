<%--
  Created by IntelliJ IDEA.
  User: rocks
  Date: 4/9/2017
  Time: 9:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ebay Scrapped</title>
    <style>
        header, footer {
            padding: 1em;
            color: white;
            background-color: black;
            clear: left;
            text-align: center;
        }
    </style>
</head>
<body>
<header>
    <a href="/app?page=next">  <input type="button" value="next" class="btn btn-submit"></a>
    <a href="/app?page=prev">  <input type="button" value="previous" class="btn btn-submit"></a>
    <a href="/">  <input type="button" value="Home" class="btn btn-submit"></a>
    <a href="/app?page=ebayscrap"><input type="button" value="View Scrap From Ebay" class="btn btn-submit"></a>
</header>
<h1>Scrapped data from ebay only</h1>
<br><br>
    ${output}

</body>
</html>
