<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationLogin.jspf"%>
<!------ WEB DESIGN ---------->

<div class="container">
	<c:if test="${errorMessage != null }">
		<div class="alert alert-danger mt-3" role="alert">
			${errorMessage}</div>
	</c:if>
	<form action="/login.do" method="post">
		<div class="form-group row">
			<label for="username" class="col-sm-2 col-form-label">Username</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="username"
					placeholder="Enter username">
			</div>
		</div>
		<div class="form-group row">
			<label for="userPassword" class="col-sm-2 col-form-label">Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" name="password"
					placeholder="Enter Password">
			</div>
		</div>
		<button type="submit" class="btn btn-primary btn-block">Lets
			see them</button>
	</form>
</div>

<%@ include file="../common/footer.jspf"%>