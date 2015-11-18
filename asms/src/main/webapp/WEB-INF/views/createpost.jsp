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
<title>Create Post</title>
</head>
<jsp:include page="header.jsp"></jsp:include>
<body>
<div class="post">
					<span id="loginerrorCss">
					<c:set var="comparevalue" value="${feedbackinvalid}"/>			
					<c:if test="${fn:contains(comparevalue, 'invalid')}">
						<spring:message code="invalid.email" />		
					</c:if>
					</span>
	<form:form method="post" action="storepost" id="login-form" commandName="user" 
							enctype="multipart/form-data" >
			<div class="form-group">
			<span class="form-field-wrapper"> <form:label
						path="email">
						<spring:message code="label.email" />
					</form:label></span>
				<form:input id="emailId" class="form-control" 
					placeholder="Enter email" path="email" 
					size="18" value="${user.email}" readonly="true"/>
			</div>
			<div class="form-group">
			<span class="form-field-wrapper"> <form:label
						path="post">
						<spring:message code="label.post" />
					</form:label></span><br>
				<form:textarea id="post"  class="form-control"
					placeholder="Please decribe your post" path="post" cols="5" rows="10"
					required="true"/><span class="lasterrorspan"><form:errors
						path="post" cssClass="error" /></span> 
			</div>
			<div class="form-group">
			<span class="form-field-wrapper"> <form:label
						path="postimg">
						<spring:message code="label.postimg" />
					</form:label></span><br>
				<form:input id="postimg"  class="form-control"
					 path="postimg" type="file"
					/><span class="lasterrorspan"><form:errors
						path="postimg" cssClass="error" /></span> 
			</div>
			<div class="form-group">
			<span class="form-field-wrapper"> <form:label
						path="postType">
						<spring:message code="label.postType" />
					</form:label></span><br>
				<form:select
					class="reg-form-control" path="postType" id="postType"
					style="width:260px">
				<form:option value="job">Job</form:option>
				<form:option value="donate">Donate</form:option>
				</form:select>
			</div>
			<div class="form-group">
				<button id="submit" type="submit" 
				style="margin-left:120px !important;padding-left: 35px;padding-right:35px;">Submit</button>
			</div>
			<br>
			</form:form>
	</div>
</body>
</html>