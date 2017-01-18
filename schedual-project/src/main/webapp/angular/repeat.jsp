<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>模版</title>
		<script type="text/javascript" src="../vendor/angular/angular.min.js"></script>
		<script type="text/javascript">
			angular.module("repeatApp", []).controller("repeatController", function($scope) {
				$scope.phones = [
					{
				      name: 'Nexus S',
				      snippet: 'Fast just got faster with Nexus S.'
				    }, {
				      name: 'Motorola XOOM™ with Wi-Fi',
				      snippet: 'The Next, Next Generation tablet.'
				    }, {
				      name: 'MOTOROLA XOOM™',
				      snippet: 'The Next, Next Generation tablet.'
				    }
				];
			});
		</script>
	</head>
	<body ng-app="repeatApp">
		<div ng-controller="repeatController">
			<ul>
				<li ng-repeat="phone in phones">
					<span>{{phone.name}}</span>
					<p>{{phone.snippet}}</p>
				</li>
			</ul>
		</div>
	</body>
</html>