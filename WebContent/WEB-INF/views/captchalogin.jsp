<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<c:url value="/loginpage" var="loginUrl" />

	<form action="${loginUrl}" method="post" id="login_form">
		<div>
			<div class="g-recaptcha"
				data-sitekey="6Lfz-aIUAAAAAJJ91QFjgY1e_8hdzTAlKxe-YJ2_"></div>
		</div>
		<button type="submit" class="btn">Log in</button>
	</form>
</body>