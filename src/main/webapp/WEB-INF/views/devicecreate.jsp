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
    <title>Create device</title>
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
               <h1 class="menu-title">ADD DEVICE</h1>
               
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label">Name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="name" placeholder="Enter device name">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="description" class="col-sm-2 col-form-label">Description</label>
                    <div class="col-sm-10">
                        <textarea class="form-control" id="port" placeholder="Enter description"></textarea>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="id" class="col-sm-2 col-form-label">Place number</label>
                    <div class="col-sm-10">
                        <input type="number" value="0" min="0" max="254" class="form-control" aria-describedby="idHelp" id="id" placeholder="Enter device number">
                        <small id="idHelp" class="form-text text-muted">Enter device number. If you enter 0 then a number will be assigned automatically.</small>
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