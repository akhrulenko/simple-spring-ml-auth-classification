<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<c:url value="/loginpage" var="loginUrl" />
	<div class="container">
		<div class="row justify-content-center" style="margin-top: 150px;">
			<form action="${loginUrl}" method="post" id="login_form" class="col"
				style="max-width: 333px;">
				<c:if test="${param.error != null}">
					<p>Invalid username and password.</p>
				</c:if>
				<c:if test="${param.logout != null}">
					<p>You have been logged out.</p>
				</c:if>
				<c:if test="${(code == false) && (captcha == false)}">
					<p>
						<label for="username" style="display: block">Username</label> <input
							type="text" id="username" name="username" style="min-width: 100%" />
					</p>
					<p>
						<label for="password" style="display: block">Password </label> <input
							type="password" id="password" name="password"
							style="min-width: 100%" />
					</p>
				</c:if>
				<c:if test="${code != false}">
					<p>
						<label for="code" style="display: block">Code</label> <input
							type="text" id="code" name="code" style="min-width: 100%" />
					</p>
				</c:if>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

				<c:if test="${captcha != false}">
					<div style="margin-bottom: 20px">
						<div class="g-recaptcha"
							data-sitekey="6Lfz-aIUAAAAAJJ91QFjgY1e_8hdzTAlKxe-YJ2_"></div>
					</div>
				</c:if>
				<button type="submit" class="btn btn-primary"
					style="min-width: 100%;">Log in</button>
			</form>


			<c:if test="${forgot != null}">
				<div class="modal fade show" id="exampleModalCenter" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					style="display: block">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLongTitle">Password
									recovery</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								You can reset your password <a href="#">here</a>
							</div>

							<div class="modal-footer"></div>
						</div>
					</div>
				</div>
				<script>
					var closeBtn = document.getElementsByClassName("close")[0];
					var popup = document.getElementsByClassName("modal")[0];
					closeBtn.onclick = function() {
						var popupBcg = document
								.getElementById("modal-backdrop");
						popup.style.display = 'none';
						popupBcg.style.display = 'none';
					};
				</script>
				<div class="modal-backdrop fade show" id="modal-backdrop"></div>
			</c:if>
		</div>
	</div>



	<script>
		var form = document.getElementById("login_form");
		var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name", "jscode");
		input.setAttribute("value", "${jscode}");
		form.appendChild(input);
	</script>
</body>