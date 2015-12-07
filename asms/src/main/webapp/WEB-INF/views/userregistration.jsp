<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<html xmlns="http://www.w3.org/1999/xhtml">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<title>Register User</title>
<link
	href="${pageContext.servletContext.contextPath}/resources/css/Homepage.css"
	rel="stylesheet">
<script type="text/javascript">
	$('document').ready(
			function() {
				var states = new Array(/* "Select", */"Afghanistan",
						"Albania", "Algeria", "Andorra", "Angola",
						"Antarctica", "Antigua&Barbuda", "Argentina",
						"Armenia", "Australia", "Austria", "Azerbaijan",
						"Bahamas", "Bahrain", "Bangladesh", "Barbados",
						"Belarus", "Belgium", "Belize", "Benin", "Bermuda",
						"Bhutan", "Bolivia", "Bosnia&Herzegovina", "Botswana",
						"Brazil", "Brunei", "Bulgaria", "Burkina Faso",
						"Burma", "Burundi", "Cambodia", "Cameroon", "Canada",
						"Cape Verde", "CentralAfrican Rep", "Chad", "Chile",
						"China", "Colombia", "Comoros", "Congo,D R",
						"Congo,Republicofthe", "Costa Rica", "Cote d'Ivoire",
						"Croatia", "Cuba", "Cyprus", "Czech Republic",
						"Denmark", "Djibouti", "Dominica",
						"Dominican Republic", "East Timor", "Ecuador", "Egypt",
						"El Salvador", "Equatorial Guinea", "Eritrea",
						"Estonia", "Ethiopia", "Fiji", "Finland", "France",
						"Gabon", "Gambia", "Georgia", "Germany", "Ghana",
						"Greece", "Greenland", "Grenada", "Guatemala",
						"Guinea", "Guinea-Bissau", "Guyana", "Haiti",
						"Honduras", "Hong Kong", "Hungary", "Iceland", "India",
						"Indonesia", "Iran", "Iraq", "Ireland", "Israel",
						"Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan",
						"Kenya", "Kiribati", "Korea, North", "Korea, South",
						"Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon",
						"Lesotho", "Liberia", "Libya", "Liechtenstein",
						"Lithuania", "Luxembourg", "Macedonia", "Madagascar",
						"Malawi", "Malaysia", "Maldives", "Mali", "Malta",
						"Marshall Islands", "Mauritania", "Mauritius",
						"Mexico", "Micronesia", "Moldova", "Mongolia",
						"Morocco", "Monaco", "Mozambique", "Namibia", "Nauru",
						"Nepal", "Netherlands", "New Zealand", "Nicaragua",
						"Niger", "Nigeria", "Norway", "Oman", "Pakistan",
						"Panama", "Papua New Guinea", "Paraguay", "Peru",
						"Philippines", "Poland", "Portugal", "Qatar",
						"Romania", "Russia", "Rwanda", "Samoa", "San Marino",
						" Sao Tome", "Saudi Arabia", "Senegal",
						"Serbia&Montenegro", "Seychelles", "Sierra Leone",
						"Singapore", "Slovakia", "Slovenia", "Solomon Islands",
						"Somalia", "South Africa", "Spain", "Sri Lanka",
						"Sudan", "Suriname", "Swaziland", "Sweden",
						"Switzerland", "Syria", "Taiwan", "Tajikistan",
						"Tanzania", "Thailand", "Togo", "Tonga",
						"Trinidad and Tobago", "Tunisia", "Turkey",
						"Turkmenistan", "Uganda", "Ukraine",
						"United Arab Emirates", "United Kingdom",
						"United States", "Uruguay", "Uzbekistan", "Vanuatu",
						"Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe",
						"Other");
				$.each(states, function(index, value) {
					$("#country").append(
							"<option value=\""+value+"\">" + value
									+ "</option>");
				});
				/* $("#country").val("United States"); */
				/* $("#country").val("Alabama"); */
				$("#otherState").css("display", "none");
				$("#country").change(function() {
					if ($(this).val() === "United States") {
						$("#state").prop("disabled", false).val("Select");
						$("#otherState").css("display", "none");
					} else {
						$("#state").prop("disabled", true).val("Other");
						$("#otherState").css("display", "block");
					}
				});
				$("#state").change(function() {
					if ($.trim($(this).val()) === "Other") {
						$("#country").val("Other");
						$("#otherState").css("display", "block");
					} else {
						$("#country").val("United States");
						$("#otherState").css("display", "none");
					}
				});

			});
</script>
</head>
<c:choose>
<c:when test="${admin.isAdmin() == true}">
<jsp:include page="adminheader.jsp"></jsp:include>
</c:when>
</c:choose>

<body>
		
		<h1 style="padding-left: 37%">Cleveland State University</h1>
		
		<div class="register">
		
		<h2 style="padding-left: 25%">Create your Alumni Account</h2>
		
		<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong style="margin-left:100px;">${msg}</strong>
		</div>
		</c:if>
		
		<form:form method="post" commandName="registeruser" action="registeruser" 
		id="form-registeruser">
			<span> <span class="form-field-wrapper"> <form:label
						path="csuid">
						<spring:message code="label.csuid" />
						<font color="red">*</font>
					</form:label></span> <span class="form-registeruser"> <form:input
						 path="csuid" class="reg-form-control" maxlength="50" required="true"/>
			</span> <span class="lasterrorspan"><form:errors path="csuid"
						cssClass="error" /> </span>
			</span>
			<br>
			<span> <span class="form-field-wrapper"> <form:label
						path="firstName">
						<spring:message code="label.firstName" />
						<font color="red">*</font>
					</form:label></span> <span class="form-registeruser"> <form:input
						 path="firstName" class="reg-form-control" maxlength="50" />
			</span> <span class="lasterrorspan"><form:errors path="firstName"
						cssClass="error" /> </span>
			</span>
			<br>
			<span><span class="form-field-wrapper"><form:label
						path="lastName">
						<spring:message code="label.lastName" />
						<font color="red">*</font>
					</form:label></span> <span class="form-registeruser"><form:input path="lastName"
						class="reg-form-control" maxlength="50" /></span> <span
				class="lasterrorspan"> <form:errors path="lastName"
						cssClass="error" /></span> 
			</span>
			<br>			
			<span><span class="form-field-wrapper"> <form:label
						path="email">
						<spring:message code="label.email" />
						<font color="red">*</font>
					</form:label></span> <span class="form-registeruser"><form:input path="email"
						class="reg-form-control" maxlength="255" /></span> <span
				class="lasterrorspan"><form:errors path="email"
						cssClass="error" /></span> 
			</span>
			<br>			
			<%-- <span><span class="form-field-wrapper"> <form:label
						path="confirmemail">
						<spring:message code="label.confirmemail" />
					</form:label><font color="red">*</font></span> <span class="form-registeruser"><form:input
				class="reg-form-control" path="confirmemail" maxlength="255" /></span> <span
				class="lasterrorspan"><form:errors path="confirmemail"
						cssClass="error" /></span> </span>
			 --%>
			<span><span class="form-field-wrapper"> <form:label
						path="password">
						<spring:message code="label.password" />
					</form:label><font color="red">*</font></span> <span class="form-registeruser"><form:password
						class="reg-form-control" path="password" maxlength="15"
						showPassword="true" /></span> <span class="lasterrorspan"><form:errors
						path="password" cssClass="error" /></span> 
			</span>
			<br>			
			<span><span class="form-field-wrapper"> <form:label
						path="confirmPwd">
						<spring:message code="label.confirmPwd" />
					</form:label><font color="red">*</font></span> <span class="form-registeruser"><form:password
						class="reg-form-control" path="confirmPwd" maxlength="15"
						showPassword="true" /></span> <span class="lasterrorspan"><form:errors
						path="confirmPwd" cssClass="error" /></span> 
			</span>
			<br>
			<span> <span class="form-field-wrapper"><form:label
						path="phoneNumber">
						<spring:message code="label.phoneNumber" />
					</form:label> <font color="red">*</font></span> <span class="form-registeruser"><form:input
						class="reg-form-control" path="phoneNumber" maxlength="15" /></span> 
					<span class="lasterrorspan"><form:errors path="phoneNumber"
						cssClass="error" /></span> 
			</span> 
			<br>
			<span><span class="form-field-wrapper">
						<form:label path="addrLine1">
							<spring:message code="label.addrLine1" />
						</form:label></span>
						<span class="form-registeruser"><form:input
							class="reg-form-control" path="addrLine1" maxlength="50" /></span> 
							<span class="lasterrorspan"><form:errors path="addrLine1"
							cssClass="error" /></span> 
			</span>		
			<br>
			<span><span class="form-field-wrapper"> <form:label path="addrLine2">
							<spring:message code="label.addrLine2" />
						</form:label></span> <span class="form-registeruser"><form:input
							class="reg-form-control" path="addrLine2" maxlength="50" /></span> 
			<span class="lasterrorspan"><form:errors path="addrLine2"
							cssClass="error" /></span> </span>
			<br>
		   <span> <span class="form-field-wrapper"><form:label path="city">
							<spring:message code="label.city" />
						</form:label><font color="red">*</font></span> 			
				  <span class="form-registeruser">
						<form:input path="city" class="reg-form-control" maxlength="50" /></span> 
						<span class="lasterrorspan"><form:errors path="city"
							cssClass="error" /></span>
			</span> 
			<br>
			<span> <span class="form-field-wrapper"><form:label
							path="zipCode">
							<spring:message code="label.zipCode" />
						</form:label></span> <span class="form-registeruser"><form:input path="zipCode"
							class="reg-form-control" maxlength="8" /></span> <span
					class="lasterrorspan"><form:errors path="zipCode"
							cssClass="error" /></span>
			</span>				
			
			<br>
			<span> <span class="form-field-wrapper"><form:label path="state"
					style="width: 100px;">
					<spring:message code="label.state" />
				</form:label></span>
			<span class="form-registeruser"> <form:select
					class="reg-form-control" path="state" id="state"
					style="width:260px">
					<form:option value="${adduser.state}"></form:option>
					<form:option value="Alabama"></form:option>
					<form:option value="Alaska"></form:option>
					<form:option value="Arizona"></form:option>
					<form:option value="Arkansas"></form:option>
					<form:option value="California"></form:option>
					<form:option value="Colorado"></form:option>
					<form:option value="Connecticut"></form:option>
					<form:option value="Delaware"></form:option>
					<form:option value="Florida"></form:option>
					<form:option value="Georgia"></form:option>
					<form:option value="Hawaii"></form:option>
					<form:option value="Idaho"></form:option>
					<form:option value="Illinois"></form:option>
					<form:option value="Indiana"></form:option>
					<form:option value="Iowa"></form:option>
					<form:option value="Kansas"></form:option>
					<form:option value="Kentucky"></form:option>
					<form:option value="Louisiana"></form:option>
					<form:option value="Maine"></form:option>
					<form:option value="Maryland"></form:option>
					<form:option value="Massachusetts"></form:option>
					<form:option value="Michigan"></form:option>
					<form:option value="Minnesota"></form:option>
					<form:option value="Mississippi"></form:option>
					<form:option value="Missouri"></form:option>
					<form:option value="Montana"></form:option>
					<form:option value="Nebraska"></form:option>
					<form:option value="Nevada"></form:option>
					<form:option value="New Hampshire"></form:option>
					<form:option value="New Jersey"></form:option>
					<form:option value="New Mexico"></form:option>
					<form:option value="New York"></form:option>
					<form:option value="North Carolina"></form:option>
					<form:option value="North Dakota"></form:option>
					<form:option value="Ohio"></form:option>
					<form:option value="Oklahoma"></form:option>
					<form:option value="Oregon"></form:option>
					<form:option value="Pennsylvania"></form:option>
					<form:option value="Rhode Island"></form:option>
					<form:option value="South Carolina"></form:option>
					<form:option value="South Dakota"></form:option>
					<form:option value="Tennessee"></form:option>
					<form:option value="Texas"></form:option>
					<form:option value="Utah"></form:option>
					<form:option value="Vermont"></form:option>
					<form:option value="Virginia"></form:option>
					<form:option value="Washington"></form:option>
					<form:option value="West Virginia"></form:option>
					<form:option value="Wisconsin"></form:option>
					<form:option value="Wyoming"></form:option>
					<form:option value="Other"></form:option>
				</form:select> <span class="lasterrorspan"><form:errors path="state"
						cssClass="error" /></span>
			</span></span>
			<br>
			<%-- <span><form:hidden path="registeredDate" /></span> --%>
		 <span> <span class="form-field-wrapper"><form:label path="typeOfUser"
					style="width: 100px;display:none">
					<spring:message code="label.typeOfUser" />
				</form:label></span>
			<span class="form-registeruser"> <form:select
					class="reg-form-control" path="typeOfUser" id="typeOfUser"
					style="width:260px;display: none">
					<form:option value="Alumni" selected="selected">Alumni</form:option>
					<form:option value="Event Manager">Event Manager</form:option>
					<form:option value="Student">Student</form:option>
					</form:select></span></span>
		
		
			
			<span><div class="form-group">
				<button id="submit" type="submit" 
				style="margin-left:120px !important;padding-left: 35px;padding-right:35px;">Register</button>
			</div></span>
			
		</form:form>
		<br /> <br />
	</div>
</body>
</html>