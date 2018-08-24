<%@ include file="../common/header.jspf" %>
<%@ include file="../common/navigation.jspf" %>
<!------ WEB DESIGN ---------->

<!------ ELEMENTS : See Dropdowns ---------->
<div class="row content">
	<div class="container col-sm-2"></div>

	<div class="container col-sm-8">
		<h1 class="menu-title">ADD DEVICE</h1>

		<div class="form-group row">
			<label for="name" class="col-sm-2 col-form-label">Name</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="deviceName"
					value="${deviceName}" placeholder="Enter device name">
			</div>
		</div>
		<div class="form-group row">
			<label for="description" class="col-sm-2 col-form-label">Description</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="deviceDescription"
					value="${deviceDescription}" placeholder="Enter description">
			</div>
		</div>
		<div class="form-group row">
			<label for="id" class="col-sm-2 col-form-label">Device number</label>
			<div class="col-sm-10">
				<input type="number" min="0" max="254" class="form-control"
					aria-describedby="idHelp" name="deviceId" value="${deviceId}"
					placeholder="Enter device number"> <small id="idHelp"
					class="form-text text-muted">Enter device number. If you
					enter 0 then a number will be assigned automatically.</small>
			</div>
		</div>
		<button type="submit" class="btn btn-primary btn-block">Submit</button>
	</div>

	<div class="container col-sm-1"></div>
</div>


<%@ include file="../common/footer.jspf" %>