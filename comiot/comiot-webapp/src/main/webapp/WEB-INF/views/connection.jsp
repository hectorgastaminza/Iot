<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>
<!------ WEB DESIGN ---------->

<!------ ELEMENTS : See Dropdowns ---------->
<div class="row content">
	<div class="container col-sm-2"></div>

	<div class="container col-sm-8">
		<h1 class="menu-title">MQTT CONFIGURATION</h1>

		<form action="/app/connection" method="post">
			<div class="form-group row">
				<label for="host" class="col-sm-2 col-form-label">Broker</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="mqtthost"
						value="${mqttConfig.getBrokerHost()}" placeholder="Enter MQTT broker host url">
				</div>
			</div>
			<div class="form-group row">
				<label for="port" class="col-sm-2 col-form-label">Port</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" name="mqttport"
						value="${mqttConfig.getBrokerPort()}" placeholder="Enter host port number">
				</div>
			</div>
			<div class="form-group row">
				<label for="username" class="col-sm-2 col-form-label">Username</label>
				<div class="col-sm-10">
					<input type="username" class="form-control" name="mqttusername"
						value="${mqttConfig.getUsername()}" placeholder="Enter username">
				</div>
			</div>
			<div class="form-group row">
				<label for="userPassword" class="col-sm-2 col-form-label">Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="mqttpassword"
						value="${mqttConfig.getPasswordStr()}" placeholder="Enter Password">
				</div>
			</div>
			<div class="form-group row">
				<label for="topic" class="col-sm-2 col-form-label">Root
					topic</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="mqtttopic"
						value="${mqttConfig.getRootTopic()}" placeholder="Enter root topic">
				</div>
			</div>
			<button type="submit" class="btn btn-primary btn-block">Submit</button>
		</form>

	</div>

	<div class="container col-sm-1"></div>
</div>

<%@ include file="../common/footer.jspf"%>