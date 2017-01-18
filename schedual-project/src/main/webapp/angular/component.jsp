<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>组件</title>
		<script type="text/javascript" src="../vendor/angular/angular.min.js"></script>
		<script type="text/javascript">
			angular.module("componentApp", []).component("phoneList", {
				template: "<ul>"+
								"<li ng-repeat='phone in $ctrl.phones'>"+
									"<span>{{phone.name}}</span>"+
									"<p>{{phone.snippet}}</p>"+
								"</li>"+
							"</ul>",
				controller:function phoneList ($scope){
					this.phones = [
				        {
				          name: 'Nexus S',
				          snippet: 'Fast just got faster with Nexus S.'
				        }, {
				          name: 'Motorola XOOM™ with WiFi',
				          snippet: 'The Next, Next Generation tablet.'
				        }, {
				          name: 'MOTOROLA XOOM™',
				          snippet: 'The Next, Next Generation tablet.'
				        }
				      ];
				}
			});
		</script>
	</head>
	<body ng-app="componentApp">
		 <phone-list></phone-list>
	</body>
</html>