/**
 * 
 */
angular.module("taskApp", []).controller(
		"addTask",
		function($scope, $http) {
			$scope.task = {
				taskName : "",
				groupName : "",
				projectName : "",
				url : "",
				cron : "",
				startTime : "",
				description : ""
			};
			$scope.add = function() {
				$http.put("./period/add", $scope.task).then(
						function(resp) {
							console.log(resp);
						}).then(function(resp) {
					console.log(resp);
				});
			};
			$scope.reset = function() {

			};
		});