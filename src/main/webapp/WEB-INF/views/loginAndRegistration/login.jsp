<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>login</title>
</head>
<body>
 <center>		
 		<form id="Back" action="${sessionScope.PROJECT_NAME}shop" method="get"></form>
		
        <form method="post" action="login">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"></input>
            <table border="1" width="20%" cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="2">Login</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Login</td>
                        <td><input type="text" name="login" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" value="" /></td>
                    </tr>
                </tbody>
            </table><br>
			<tr><td><input align="top" type="submit"  value="Login" />
			<td><input type="submit" value="Shop" form="Back" /></td></tr><br><br>

				<a href="forgotPassword"><font size="2">Forgot password</font></a><br>
				<a href="forgotUsername"><font size="2">Forgot username</font></a><br>
				<a href="registration"><font size="2">Register</font></a><br>
        </form>

			<c:choose>
				<c:when test="${msg != null}">
					<c:out value="${msg}"></c:out>
				</c:when>
				</c:choose>
				
			</center>
</body>
</html>
