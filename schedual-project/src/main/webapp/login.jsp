<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>UI-GRID</title>
		<link rel="stylesheet" href="lib/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="lib/modules/angularjs-ui-grid/ui-grid.min.css">
		<script type="text/javascript" src="lib/jquery/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="lib/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="lib/angular/angular.min.js"></script>
		<script type="text/javascript" src="lib/angular/angular-touch.min.js"></script>
		<script type="text/javascript" src="lib/modules/angularjs-ui-grid/ui-grid.min.js"></script>
		<script type="text/javascript">
			angular.module('app',['ngTouch', 'ui.grid']).controller('gridCtrl',function($scope){
				$scope.tasks = [
				    {
				        "firstName": "Cox",
				        "lastName": "Carney",
				        "company": "Enormo",
				        "employed": true
				    },
				    {
				        "firstName": "Lorraine",
				        "lastName": "Wise",
				        "company": "Comveyer",
				        "employed": false
				    },
				    {
				        "firstName": "Nancy",
				        "lastName": "Waters",
				        "company": "Fuelton",
				        "employed": false
				    }
				];
			});
		</script>
	</head>
	<body ng-app="app" ng-controller="gridCtrl">
		<div ui-grid="{ data: tasks }"></div>
	</body>
</html>