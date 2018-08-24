<%@ include file="../common/header.jspf" %>
<%@ include file="../common/navigation.jspf" %>
<!------ WEB DESIGN ---------->

<!------ ELEMENTS : See Dropdowns ---------->
<div class="row content">
	<div class="container col-sm-2"></div>

	<div class="container col-sm-8">
		<h1 class="menu-title">USER CONFIGURATION</h1>

		<form action="/userconfig.do" method="post">
			<div class="form-group row" style="">
				<label for="email" class="col-sm-2 col-form-label">Email
					address</label>
				<div class="col-sm-10">
					<input type="email" class="form-control" name="email"
						value="${email}">
				</div>
			</div>
			<div class="form-group row">
				<label for="username" class="col-sm-2 col-form-label">Username</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="username"
						value="${username}">
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
			<button type="submit" class="btn btn-primary btn-block">Submit</button>
		</form>
	</div>

	<div class="container col-sm-1"></div>
</div>


<%@ include file="../common/footer.jspf" %>