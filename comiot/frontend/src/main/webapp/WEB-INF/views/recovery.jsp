<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigationLogin.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<form action="/recovery.do" method="post">
		<div class="form-group row">
			<label for="email" class="col-sm-2 col-form-label">Email
				address</label>
			<div class="col-sm-10">
				<input type="email" class="form-control" name="email"
					placeholder="Enter email">
			</div>
		</div>
		<button type="submit" class="btn btn-primary btn-block">Recovery</button>
	</form>
</div>

<%@ include file="../common/footer.jspf"%>