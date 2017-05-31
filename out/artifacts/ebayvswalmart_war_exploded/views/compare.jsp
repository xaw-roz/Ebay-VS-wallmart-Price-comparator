<%--
  Created by IntelliJ IDEA.
  User: rocks
  Date: 4/5/2017
  Time: 7:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ebay vs Walmart Price comparator</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div class="container">

    <header>
        <a href="/app?page=next">  <input type="button" value="next" class="btn btn-submit"></a>
        <a href="/app?page=prev">  <input type="button" value="previous" class="btn btn-submit"></a>
        <a href="/">  <input type="button" value="Home" class="btn btn-submit"></a>
        <a href="/app?page=ebayscrap"><input type="button" value="View Scrap From Ebay" class="btn btn-submit"></a>
    </header>

<h1>Ebay item</h1>
        ${ebayTitle}<br> <div style="font-size: xx-large">Price:${ebayPrice}</div><br>${ebayInfo}
<hr>
    <br>
    <br>
    <article>
        <h1>Similar  Walmart Items</h1>
        ${walmartInfo}
    </article>

</body>
</html>
