<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<html>
<head>
<link href="${pageContext.servletContext.contextPath}/resources/css/Homepage.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/material.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/userdashboard.css">
    
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Posts</title>
</head>
<jsp:include page="header.jsp"></jsp:include>
<body>
<div class="post">

					<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<!-- <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			 --><strong>${msg}</strong>
		</div>
	</c:if>

<a href="createpost">Create Post</a>
<br>
<a href="getposts">View Posts</a>
<br>
<a href="viewallposts">View All</a>
</div>
</body>
</html>