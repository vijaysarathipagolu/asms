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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<%-- <link href="${pageContext.servletContext.contextPath}/resources/css/Homepage.css" rel="stylesheet"> --%>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/material.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/userdashboard.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/admindashboard.css"> 

<title>Posts</title>
</head>

<c:choose>
<c:when test="${user.isAdmin() == true}">
<jsp:include page="adminheader.jsp"></jsp:include>
</c:when>
<c:otherwise>
<jsp:include page="header.jsp"></jsp:include>
</c:otherwise>
</c:choose>


<body>
<body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<main class="mdl-layout__content">
<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
			<!-- <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			 --><strong>${msg}</strong>
			</div>
			</c:if>
<div class="mdl-layout__tab-panel is-active" id="overview">
<div class=" mdl-cell mdl-cell--12-col mdl-grid">	
<c:if test="${user.getTypeOfUser() == 'Alumni'}"> 
<div class="demo-card-event mdl-card mdl-shadow--2dp">
  <div class="mdl-card__title mdl-card--expand">
    <h4 style="padding-left:40px;">
      <br>Create Post
    </h4>
  </div>
  <div class="mdl-card__actions mdl-card--border">
    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="createpost">
      click here
    </a>
    <div class="mdl-layout-spacer"></div>
    <button class="mdl-button mdl-js-button mdl-button--fab mdl-button--colored">
  			<i class="material-icons">add</i>
		</button>
  </div>
  </div>

<div class="demo-card-event mdl-card mdl-shadow--2dp">
  <div class="mdl-card__title mdl-card--expand">
    <h4 style="padding-left:40px;">
      <br>View Posts
    </h4>
  </div>
  <div class="mdl-card__actions mdl-card--border">
    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="getposts">
      click here
    </a>
    <div class="mdl-layout-spacer"></div>
    <i class="material-icons">pageview</i>
  </div>
  </div>
 </c:if> 
<div class="demo-card-event mdl-card mdl-shadow--2dp">
  <div class="mdl-card__title mdl-card--expand">
    <h4 style="padding-left:30px;">
      <br>View All Posts
    </h4>
  </div>
  <div class="mdl-card__actions mdl-card--border">
    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="getAllposts" >
      click here
    </a>
    <div class="mdl-layout-spacer"></div>
    <i class="material-icons">pageview</i>
  </div>
  </div>
<br>
</div>
</div></main>
</body>
</html>