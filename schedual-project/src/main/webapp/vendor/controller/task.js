angular.module('schedual').controller('taskCtrl',
		function($scope, taskService) {
			var self = this;

			$scope.task = {
				id : '',
				projectName : '',
				groupName : '',
				taskName : '',
				url : '',
				cron : '',
				taskStatus : '',
				description : '',
				createTime : ''
			};

			$scope.add = function() {
				taskService.add($scope.task).then(function(data) {
					console.log(data);
				}, function(error) {

				});
			}

		});