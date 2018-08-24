<%@ include file="../common/header.jspf" %>
<%@ include file="../common/navigation.jspf" %>
<!------ WEB DESIGN ---------->

<div class="row content">
	<div class="container col-sm-2"></div>

	<div class="container col-sm-8">
		<h1 class="menu-title">ADD PLACE</h1>

		<div class="form-group row">
			<label for="name" class="col-sm-2 col-form-label">Name</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="name"
					placeholder="Enter place name">
			</div>
		</div>
		<div class="form-group row">
			<label for="description" class="col-sm-2 col-form-label">Description</label>
			<div class="col-sm-10">
				<textarea class="form-control" id="port"
					placeholder="Enter description"></textarea>
			</div>
		</div>
		<div class="form-group row">
			<label for="id" class="col-sm-2 col-form-label">Place number</label>
			<div class="col-sm-10">
				<input type="number" value="0" min="0" max="254"
					class="form-control" aria-describedby="idHelp" id="id"
					placeholder="Enter place number"> <small id="idHelp"
					class="form-text text-muted">Enter place number. If you
					enter 0 then a number will be assigned automatically.</small>
			</div>
		</div>
		<button type="submit" class="btn btn-primary btn-block">Submit</button>
	</div>

	<div class="container col-sm-1"></div>
</div>


<%@ include file="../common/footer.jspf" %>