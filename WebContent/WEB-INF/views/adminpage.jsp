<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
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
	<h1>Admin Page</h1>

	<table class="table table-hover">
		<thead class="thead-dark">
			<tr>
				<th>Id</th>
				<th>IP</th>
				<th>Username</th>
				<th>Time</th>
				<th>Role</th>
			</tr>
		</thead>
		<c:forEach var="query" items="${queryList}">
			<tr>
				<td>${query.id}</td>
				<td>${query.ip}</td>
				<td>${query.username}</td>
				<td>${query.time}</td>
				<td>${query.queryClass}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>