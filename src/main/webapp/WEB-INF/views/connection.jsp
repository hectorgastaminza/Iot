<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="/styles/comiot.css" type="text/css">
    <title>Connection</title>
  </head>
  <body>
    <!------ WEB DESIGN ---------->
	<div class="container">
		<div class="row content">
	    <div class="container">
	       	<nav class="navbar navbar-expand-lg navbar-light bg-primary">
	          <a class="navbar-brand" href="/home.do">C⊙mI⊙T HOME</a>
	          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
	            <span class="navbar-toggler-icon"></span>
	          </button>
	        </nav>
    	</div>
    	<!------ ALERT MESSAGES ---------->
    	<c:if test="${errorMessage != null }">
			<div class="alert alert-danger mt-3" role="alert">
	  			${errorMessage}
			</div>
		</c:if>
		<c:if test="${successMessage != null }">
			<div class="alert alert-success mt-3" role="alert">
	  			${successMessage}
			</div>
		</c:if>
	</div>		
				
   <!------ ELEMENTS : See Dropdowns ---------->
   <div class="row content">
        <div class="container col-sm-2">
        </div>
       
        <div class="container col-sm-8">
            <h1 class="menu-title">MQTT CONFIGURATION</h1>
           
            <div class="form-group row">
                <label for="host" class="col-sm-2 col-form-label">Broker</label>
                <div class="col-sm-10">
                    <input type="url" class="form-control" id="host" placeholder="Enter MQTT broker host url">
                </div>
            </div>
            <div class="form-group row">
                <label for="port" class="col-sm-2 col-form-label">Port</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" id="port" placeholder="Enter host port number">
                </div>
            </div>
            <div class="form-group row">
                <label for="username" class="col-sm-2 col-form-label">Username</label>
                <div class="col-sm-10">
                    <input type="username" class="form-control" id="username" placeholder="Enter username">
                </div>
            </div>
            <div class="form-group row">
                <label for="userPassword" class="col-sm-2 col-form-label">Password</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="userPassword" placeholder="Enter Password">
                </div>
            </div>
            <div class="form-group row">
                <label for="topic" class="col-sm-2 col-form-label">Root topic</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="topic" placeholder="Enter root topic">
                </div>
            </div>
          <button type="submit" class="btn btn-primary btn-block">Submit</button>
        </div>
        
        <div class="container col-sm-1">
        </div>
    </div>

    <!------ BOOTSTRAP INCLUDES -- At the end of body to avoid a slow load -------->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
        
  </body>
</html>