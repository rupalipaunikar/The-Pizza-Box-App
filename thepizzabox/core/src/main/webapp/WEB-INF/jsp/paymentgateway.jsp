<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form:form name='payment' action="http://localhost:8080/payment/makepayment" method="post" modelAttribute="order">
	
		<table>
                <tr>
                    <td><form:label path="id">Id</form:label></td>
                    <td><form:input path="id"/></td>
                </tr>
                <tr>
                    <td><form:label path="status">Status</form:label></td>
                    <td><form:input path="status"/></td>
                </tr>
                <tr>
                    <td><form:label path="paymentType">Payment Type</form:label></td>
                    <td><form:input path="paymentType"/></td>
                </tr>
                <tr>
                    <td><form:label path="totalAmount">Total Amount</form:label></td>
                    <td><form:input path="totalAmount"/></td>
                </tr>
        </table>
		
		<input type="submit" id="payment" name="Make Payment"
			onclick="validateform()" value="Make Payment"
			style="box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19); font-size: 16px; margin: 4px 2px; cursor: pointer;" />
	</form:form>
</body>
</html>