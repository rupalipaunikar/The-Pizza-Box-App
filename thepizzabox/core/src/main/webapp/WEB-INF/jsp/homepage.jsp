<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
<head>
<meta charset="utf-8" />
<title>The Pizza Box</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />

<!-- GLOBAL MANDATORY STYLES -->
<link
	href="http://fonts.googleapis.com/css?family=Hind:300,400,500,600,700"
	rel="stylesheet" type="text/css">
<link href="vendor/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />

<!-- PAGE LEVEL PLUGIN STYLES -->
<link href="css/animate.css" rel="stylesheet">
<link href="vendor/swiper/css/swiper.min.css" rel="stylesheet"
	type="text/css" />

<!-- THEME STYLES -->
<link href="css/layout.min.css" rel="stylesheet" type="text/css" />

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico" />
<title>Home Page</title>

</head>
<body>
	<!--========== HEADER ==========-->
	<header class="header"> <!-- Navbar --> <nav class="navbar"
		role="navigation">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="menu-container">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".nav-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="toggle-icon"></span>
			</button>

			<!-- Logo -->
			<div class="navbar-logo">
				<a class="navbar-logo-wrap" href="index.html"> <img
					class="navbar-logo-img" src="img/PizzaBox.png"
					alt="The Pizza Box Logo">
				</a>
			</div>
			<!-- End Logo -->
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse nav-collapse">
			<div class="menu-container">
				<ul class="navbar-nav navbar-nav-right">
					<!-- Home -->
					<li class="nav-item"><a class="nav-item-child active"
						href="index.html"> Home </a></li>
					<!-- End Home -->

					<!-- About -->
					<li class="nav-item"><a class="nav-item-child" href="login">
							Login </a></li>
					<!-- End About -->

					<!-- Work -->
					<li class="nav-item"><a class="nav-item-child"
						href="work.html"> Work </a></li>
					<!-- End Work -->

					<!-- Contact -->
					<li class="nav-item"><a class="nav-item-child"
						href="contact.html"> Contact </a></li>
					<!-- End Contact -->
				</ul>
			</div>
		</div>
		<!-- End Navbar Collapse -->
	</div>
	</nav> <!-- Navbar --> </header>
	<!--========== END HEADER ==========-->


	<!--CSRF parameter for security  -->
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />

	<c:url value="j_spring_security_check" var="loginUrl" />

	<div class="bg-color-sky-light">
		<div class="section-seperator">
			<div class="content-md container">
				<div class="row">
					<div class="col-sm-4 sm-margin-b-50">
						<form name='loginForm' action="makeorder" method="post">
						
						<%-- <c:forEach var="item" items="${ItemList}">
						<c:out value="${item.type}"/>
							<c:if test="${item.type=='PIZZA'}">
							
													<label style="font-size: 16px; font-weight: 500; font-family: Hind, sans-serif; color: #515769;">
													<input type="checkbox" name="itemChechBox" value='<c:out value="${item.itemId}"/>' />
													<c:out value="${item.name}" /></label>
													<input type="text" name="quantity" value='<c:out value="${item.quantity}"/>' />
											<input type="text" name="name" value='<c:out value="${item.name}"/>'/>
											<input type="text" name="price" value='<c:out value="${item.price}"/>' />
											<input type="text" name="type" value='<c:out value="${item.type}"/>'/>
											</c:if>
						</c:forEach> --%>
						
<%-- 						 <c:forEach var="item" items="${ItemList}" varStatus="status"> --%>
<%-- 						 	<c:if test="${item.type=='PIZZA'}"> --%>
<%--         <form:input path="item[${status.index}].name" name="name" value="${item.name}" /> --%>
<%--         <form:input path="item[${status.index}].quantity" name="quantity" value="${item.quantity}" /> --%>
<%--         </c:if> --%>
<%--         </c:forEach> --%>
						
							<%-- <c:forEach var="itemTypeMap" items="${ItemTypeMap}">
								<fieldset style="border-width: 6px">
									<legend>
										<c:out value="${itemTypeMap.key}" />
									</legend>
									<table>
										<c:forEach var="item" items="${itemTypeMap.value}">
										
											<tr>
												<td >
													<label style="font-size: 16px; font-weight: 500; font-family: Hind, sans-serif; color: #515769;">
													<input type="checkbox" name="itemChechBox" value='<c:out value="${item.itemId}"/>' />
													<c:out value="${item.name}" /></label>
												</td>
												<td>
												&nbsp;
												</td>
												<td>
												&nbsp;
												</td>
												
												<td>	
													<input type="text" name="quantity" value='<c:out value="${item.quantity}"/>' />
												</td>
											</tr>	
											<input type="hidden" name="name" value='<c:out value="${item.name}"/>'/>
											<input type="hidden" name="price" value='<c:out value="${item.price}"/>' />
											<input type="hidden" name="type" value='<c:out value="${item.type}"/>'/>
											
											
											
										</c:forEach>
									</table>
								</fieldset>
								<br>
							</c:forEach> --%>
							
						
							<input type="submit" id="payment" name="Proceed for Payment"
								onclick="validateform()" value="Proceed for Payment"
								style="box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19); font-size: 16px; margin: 4px 2px; cursor: pointer;" />
						</form>
						
						
					</div>
				</div>
			</div>
			<!--// end row -->
		</div>
	</div>



	<!--========== FOOTER ==========-->
	<footer class="footer"> <!-- Links -->
	<div class="section-seperator">
		<div class="content-md container">
			<div class="row">
				<div class="col-sm-2 sm-margin-b-30">
					<!-- List -->
					<ul class="list-unstyled footer-list">
						<li class="footer-list-item"><a href="#">Home</a></li>
						<li class="footer-list-item"><a href="#">About</a></li>
						<li class="footer-list-item"><a href="#">Work</a></li>
						<li class="footer-list-item"><a href="#">Contact</a></li>
					</ul>
					<!-- End List -->
				</div>
				<div class="col-sm-2 sm-margin-b-30">
					<!-- List -->
					<ul class="list-unstyled footer-list">
						<li class="footer-list-item"><a href="#">Twitter</a></li>
						<li class="footer-list-item"><a href="#">Facebook</a></li>
						<li class="footer-list-item"><a href="#">Instagram</a></li>
						<li class="footer-list-item"><a href="#">YouTube</a></li>
					</ul>
					<!-- End List -->
				</div>
				<div class="col-sm-3">
					<!-- List -->
					<ul class="list-unstyled footer-list">
						<li class="footer-list-item"><a href="#">Subscribe to Our
								Newsletter</a></li>
						<li class="footer-list-item"><a href="#">Privacy Policy</a></li>
						<li class="footer-list-item"><a href="#">Terms &amp;
								Conditions</a></li>
					</ul>
					<!-- End List -->
				</div>
			</div>
			<!--// end row -->
		</div>
	</div>
	<!-- End Links --> <!-- Copyright -->
	<div class="content container">
		<div class="row">
			<div class="col-xs-6">
				<img class="footer-logo" src="img/PizzaBox.png" alt="Acidus Logo">
			</div>
			<div class="col-xs-6 text-right">
				<p class="margin-b-0">
					<a class="fweight-700" href="http://keenthemes.com/preview/acidus/">Pizza
						Box</a> Theme Powered by: <a class="fweight-700"
						href="http://www.keenthemes.com/">KeenThemes.com</a>
				</p>
			</div>
		</div>
		<!--// end row -->
	</div>
	<!-- End Copyright --> </footer>
	<!--========== END FOOTER ==========-->

	<!-- Back To Top -->
	<a href="javascript:void(0);" class="js-back-to-top back-to-top">Top</a>

	<!-- JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- CORE PLUGINS -->
	<script src="vendor/jquery.min.js" type="text/javascript"></script>
	<script src="vendor/jquery-migrate.min.js" type="text/javascript"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>

	<!-- PAGE LEVEL PLUGINS -->
	<script src="vendor/jquery.easing.js" type="text/javascript"></script>
	<script src="vendor/jquery.back-to-top.js" type="text/javascript"></script>
	<script src="vendor/jquery.smooth-scroll.js" type="text/javascript"></script>
	<script src="vendor/jquery.wow.min.js" type="text/javascript"></script>
	<script src="vendor/swiper/js/swiper.jquery.min.js"
		type="text/javascript"></script>
	<script src="vendor/masonry/jquery.masonry.pkgd.min.js"
		type="text/javascript"></script>
	<script src="vendor/masonry/imagesloaded.pkgd.min.js"
		type="text/javascript"></script>

	<!-- PAGE LEVEL SCRIPTS -->
	<script src="js/layout.min.js" type="text/javascript"></script>
	<script src="js/components/wow.min.js" type="text/javascript"></script>
	<script src="js/components/swiper.min.js" type="text/javascript"></script>
	<script src="js/components/masonry.min.js" type="text/javascript"></script>

</body>
</html>