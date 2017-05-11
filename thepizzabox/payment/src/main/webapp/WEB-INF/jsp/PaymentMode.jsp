<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

<script type="text/javascript">
 function validateForm() {
     var radios = document.getElementsByName("paymentType");
     var formValid = false;
     var i = 0;
     while (!formValid && i < radios.length) {
         if (radios[i].checked) formValid = true;
         i++;        
     }

     if (!formValid) alert("Please choose a payment mode!");
     return formValid;
 }
</script>
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
						<div align="center" style="">
							<h3>Please select the type of payment</h3>
						</div>
						<h5 align="left">
							<c:out value="${Error}"></c:out>
						</h5>

						<form name='paymentMode' action="submit" method="post" onsubmit="return validateForm()">

							<table>
								<tr>
									<td><input type="hidden" name="id" value="${order.id}" /></td>
								<tr>
									<td><input type="hidden" name="status" value="${order.status}" /></td>
								</tr>
								<tr>
									<td><input type="hidden" name="totalAmount" value="${order.totalAmount}" /></td>
								</tr>
								<tr>
									<td><input type="hidden" name="userId" value="${user.userId}" /></td>
								</tr>
								<tr>
									<td><input type="hidden" name="username" value="${user.username}" /></td>
								</tr>
								<tr>
									<td>
									<label style="font-size: 16px; font-weight: 500; font-family: Hind, sans-serif; color: #515769;">
													<input type="radio" name="paymentType" value="CASH" />
													Cash On Delivery</label>
									</td>
								</tr>
								<tr>
									<td>
									<label style="font-size: 16px; font-weight: 500; font-family: Hind, sans-serif; color: #515769;">
													<input type="radio" name="paymentType" value="ONLINE" />
													Online</label>
									</td>
								</tr>

							</table>

							<table>
								<tr>
									<td>Enter your 16 digit card number :</td>
       								<td><input type="text" name="cardNumber" value="${cardDetails.cardNumber}" /></td>
								</tr>
								<tr>
									<td>Expiry Date :</td>
       								<td><input type="text" name="expiryDate" value="${cardDetails.expiryDate}" /></td>
								</tr>
								<tr>
									<td>CVV :</td>
        							<td><input type="password" name="cvv" value="${cardDetails.cvv}" /></td>
								</tr>
								<tr>
									<td>Total Amount :</td>
        							<td><input type="text" name="totalAmount" value="${order.totalAmount}" /></td>
								</tr>
							</table>
							<input type="submit" id="paymentMode" name="Choose payment mode"
								onclick="validateform()" value="Continue"
								style="box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19); font-size: 14px; margin: 4px 2px; cursor: pointer;" />
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