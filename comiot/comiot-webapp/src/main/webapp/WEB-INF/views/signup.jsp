<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationLogin.jspf"%>
<!------ WEB DESIGN ---------->

<div class="container">
	<c:if test="${errorMessage != null }">
		<div class="alert alert-danger mt-3" role="alert">
			${errorMessage}</div>
	</c:if>
	<c:if test="${successMessage != null }">
		<div class="alert alert-success mt-3" role="alert">
			${successMessage}</div>
	</c:if>
	<form action="/signup" method="post">
		<div class="form-group row" style="">
			<label for="email" class="col-sm-2 col-form-label">Email
				address</label>
			<div class="col-sm-10">
				<input type="email" class="form-control" name="email"
					placeholder="Enter email">
			</div>
		</div>

		<div class="form-group row">
			<label for="username" class="col-sm-2 col-form-label">Username</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="username"
					placeholder="Enter username">
			</div>
		</div>
		<div class="form-group row">
			<label for="password" class="col-sm-2 col-form-label">Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" name="password"
					placeholder="Enter Password">
			</div>
		</div>
		<div class="form-group row">
			<label for="confirm" class="col-sm-2 col-form-label">Confirm
				password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" name="confirm"
					placeholder="Confirm password">
			</div>
		</div>
		<button type="submit" class="btn btn-primary btn-block">Sign
			up</button>
	</form>
</div>

<%@ include file="../common/footer.jspf"%>