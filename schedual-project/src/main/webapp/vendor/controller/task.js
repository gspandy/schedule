alert(1);
angular.module('schedual').controller('taskCtrl',
		function($scope, taskService) {

			$scope.task = {
				id : '',
				projectName : 'ttt',
				groupName : '',
				taskName : '',
				url : '',
				cron : '',
				taskStatus : '',
				description : '',
				createUser : 'jyl',
				createTime : ''
			};

			$scope.add = function() {
				taskService.add($scope.task).then(function(data) {
					console.log('task');
				}, function(error) {
					console.log('error')
				});
			}

		});