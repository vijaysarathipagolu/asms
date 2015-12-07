<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>

<head>
<title>Student Alumni Management System Home Page</title>
<link
	href="${pageContext.servletContext.contextPath}/resources/css/Homepage.css"
	rel="stylesheet"/>
</head>
<body>
	<h1 style="padding-left: 37%">Cleveland State University</h1>
	
	<div class="login">
		<h2 style="padding-left: 10%">An Alumni account for all CSU</h2>
		<img
			src="${pageContext.servletContext.contextPath}/resources/images/user.jpg"
			title="user" style="padding-left: 35%;">
			<div id="loginerrorCss">
				<c:if test="${! empty info}">
						<c:set var="comparevalue" value="${info}"/>
						<c:if test="${fn:contains(comparevalue, 'doesnotexists')}">
						<spring:message code="email.doesnotexists" />		
						</c:if>
						<c:if test="${fn:contains(comparevalue, 'notvalid')}">
						<p><spring:message code="invalid.user" /></p>
						</c:if>		
				</c:if>	
			</div>	
		<form:form method="post" action="login" id="login-form" commandName="user">
			<div id="u" id="form-group">
				<form:input id="username"  class="form-control"
					placeholder="Enter CSU ID" path="csuid" type="text" size="18"
					required="true"/> 
					<span class="lasterrorspan"><form:errors path="csuid"
						cssClass="error" /> </span>
			</div>
			<div id="p" class="form-group">
				<form:password id="password" class="form-control" 
					placeholder="Enter a password" path="password" 
					size="18" required="true"/>
					<span class="lasterrorspan"><form:errors
						path="password" cssClass="error" /></span>
			</div>
			<div class="form-group">
				<button id="submit" type="submit" 
				style="margin-left:120px !important;padding-left: 35px;padding-right:35px;">Login</button>
			</div>
			<br>
			<span>
			<a href="registeruser">Create an account</a>
			</span>
			<br>
			<span><a href="forgotpassword">Forgot password?</a></span>
			
		</form:form>

	</div>

</body>
</html>