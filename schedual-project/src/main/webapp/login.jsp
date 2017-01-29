<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>登陆</title>
		<script type="text/javascript" src="lib/angular/angular.min.js"></script>
		<script type="text/javascript">
			angular.module("app",[]).controller("loginCtrl",function($scope,notify){
				$scope.add = function(msg){
					notify('YES');
				}
			}).factory('notify',function(){
				return function(msg){
					alert(msg);
				};
			});
		</script>
	</head>
	<body ng-app='app'>
		<div ng-controller='loginCtrl'>
			<button type="button" ng-click="add()">Click</button>
		</div>
	</body>
</html>