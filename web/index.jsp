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
</head>
<body style="padding-left: 5%;padding-right: 5%">
<form method="post" action="/app">
  <label>Ebay Seller Url
  </label>
  <input type="hidden" name="page" value="urlsubmit">
  <input type="text" name="url" class="form-control">
  <input type="submit" value="Submit" class="btn btn-default" onclick="document.getElementById('message').innerHTML='<h1>Web Scraping ongoing please wait</h1>'">
  <div id="message">

  </div>

</form>
</body>
</html>
