<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>依赖注入</title>
		<script type="text/javascript" src="../../vendor/angular/angular.min.js"></script>
		<script type="text/javascript" src="../../vendor/xhr/phone-filter.module.js"></script>
		<script type="text/javascript" src="../../vendor/xhr/phone-filter.component.js"></script>
		<script type="text/javascript" src="../../vendor/xhr/phone.module.js"></script>
	</head>
	<body ng-app="filterApp">
		<phone-filter></phone-filter>
	</body>
</html>