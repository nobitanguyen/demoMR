<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Restaurant</title>
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body ng-app="restaurantApp" class="ng-cloak">

	<%@ include file="/WEB-INF/views/menu.jsp"%>
	<div class="generic-container" style="width: 400px">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">Login Restaurant</span>
			</div>
			<div class="login-container">
				<div class="login-card">
					<div class="login-form">
						<c:url var="loginUrl" value="/login" />
						<br>
						<form action="${loginUrl}" method="post" class="form-horizontal">
							<c:if test="${param.error != null}">
								<div class="alert alert-danger">
									<p>Invalid username and password.</p>
								</div>
							</c:if>
							<c:if test="${param.logout != null}">
								<div class="alert alert-success">
									<p>You have been logged out successfully.</p>
								</div>
							</c:if>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="username"><i
									class="fa fa-user"></i></label> <input type="text" class="form-control"
									id="username" name="username" placeholder="Enter Username"
									required>
							</div>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="password"><i
									class="fa fa-lock"></i></label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="Enter Password" required>
							</div>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<br>
							<div class="form-actions" style="text-align: center;">
								<input type="submit"
									class="btn btn-primary btn-default" value="Login" style="width: 80px;">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>