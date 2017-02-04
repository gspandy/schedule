<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>调度中心</title>
		<link rel="stylesheet" href="lib/bootstrap/css/bootstrap.min.css">
	</head>
	<body ng-app="schedual">
		<nav class="navbar navbar-default">
		  <div class="container-fluid">
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav">
		        <li class="dropdown">
		          <a href="javascript:viod()" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">周期任务<span class="caret"></span></a>
		          <ul class="dropdown-menu">
		            <li><a ui-sref="period/add">添加周期任务</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a ui-sref="period/list">周期任务列表</a></li>
		          </ul>
		        </li>
		        <li class="dropdown">
		        	<a href="javascript:void()" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">延时任务<span class="caret"></span></a>
			          <ul class="dropdown-menu">
			            <li><a>添加延时任务</a></li>
			            <li role="separator" class="divider"></li>
			            <li><a href="delay/list">延时任务列表</a></li>
			          </ul>
		        </li>
		      </ul>
		    </div>
		  </div>
		</nav>
		<div ui-view></div>
	</body>
	<script type="text/javascript" src="lib/jquery/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="lib/angular/angular.min.js"></script>
	<script type="text/javascript" src="lib/angular/angular-ui-router.js"></script>
	<script type="text/javascript" src="lib/angular/ocLazyLoad.min.js"></script>
	<script type="text/javascript" src="app.js"></script>
	<script type="text/javascript" src="config.router.js"></script>
</html>