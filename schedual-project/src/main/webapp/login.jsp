<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>登陆</title>
		<!-- <base href="/schedual/" /> -->
		<script type="text/javascript" src="lib/angular/angular.min.js"></script>
		<script type="text/javascript" src="lib/angular/angular-route.min.js"></script>
		<script type="text/javascript">
			/*angular.module('app',['ngRoute']).controller("loginCtrl",function($scope,$location,notify){
				$scope.add = function(msg){
					//notify($location.path());
					//$location.path('/period');
					alert($location.absUrl());
				}
			}).factory('notify',function(){
				return function(msg){
					alert(msg);
				};
			});
			angular.module('app').controller('schoolCtrl',function($scope){
				$scope.school = {
					name : '民族中学',
					address : '湖南省永州市宁远县'
				};
			});
			angular.module('app').config(['$locationProvider', '$routeProvider',function ($locationProvider,$routeProvider){
				$locationProvider.html5Mode({
					enabled: true,
			        requireBase: false
			       // rewriteLinks
				});
				$routeProvider.when('/schools',{
					controller : 'schoolCtrl',
					template : '<b>Welcome to school</b>'
				}).when('/',{
					controller :'loginCtrl',
					template : '<b>Hello word!</b>'
				}).otherwise({
					controller :'loginCtrl',
					template:'<b>没有对应的uri</b>'
				});
			}]);*/
		</script>
	</head>
	<body ng-app='app'>
		<!-- <div ng-controller='loginCtrl'>
			<button type="button" ng-click="add()">Click</button>
			<a href="#/period">test</a>
		</div>
		<div ng-controller='loginCtrl'>
			<div>
				<a href="#/" target="_self">School Buddy</a>
			    <a href="#/schools" target="_self">Schools</a>
			    <a href="#/classrooms" target="_self">Classrooms</a>
			    <a href="/activities" target="_self">Activities</a>
			</div>
			<div ng-view></div> 
		</div>
		<ng-view></ng-view> -->
	</body>
</html>