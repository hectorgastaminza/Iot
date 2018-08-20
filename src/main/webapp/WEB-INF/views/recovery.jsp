<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="/styles/comiot.css" type="text/css">
    <title>Recovery</title>
  </head>
  <body>
    <!------ WEB DESIGN ---------->

    <div class="container">
       <nav class="navbar navbar-expand-lg navbar-light bg-primary">
          <a class="navbar-brand" href="https://github.com/hectorgastaminza/Iot" target="_blank">C⊙mI⊙T</a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link" href="/login">Login</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="/signup">Sign up</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link" href="/recovery">Recovery<span class="sr-only">(current)</span></a>
              </li>
            </ul>
          </div>
        </nav>
    </div>
   
    <div class="container  text-center">
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 class="display-4">Welcome to C⊙mI⊙T</h1>
                <p class="lead">What are your devices doing?</p>
            </div>
        </div>
    </div>
   
    <div class="container">
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
        <form action="/recovery.do" method="post">
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email address</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control" name="email" placeholder="Enter email">
                </div>
            </div>
          <button type="submit" class="btn btn-primary btn-block">Recovery</button>
        </form>
    </div>
    
    <!------ BOOTSTRAP INCLUDES -- At the end of body to avoid a slow load -------->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>