<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Router</title>
		<script type="text/javascript" src="lib/angular/angular.min.js"></script>
		<script type="text/javascript" src="lib/angular/angular-ui-router.js"></script>
		<script type="text/javascript">
			var myApp = angular.module('helloworld', ['ui.router']);
			myApp.config(function($stateProvider) {
				  var helloState = {
				    name: 'hello',
				    url: '/hello',
				    template: '<h3>hello world!</h3>'
				  }

				  var aboutState = {
				    name: 'about',
				    url: '/about',
				    template: '<h3>Its the UI-Router hello world app!</h3>'
				  }

				  $stateProvider.state(helloState);
				  $stateProvider.state(aboutState);
				});
		</script>
		<style>.active { color: red; font-weight: bold; }</style>
	</head>
	<body ng-app="helloworld">
	    <a ui-sref="hello" ui-sref-active="active">Hello</a>
	    <a ui-sref="about" ui-sref-active="active">About</a>
	
	    <ui-view></ui-view>
	 </body>
</html>