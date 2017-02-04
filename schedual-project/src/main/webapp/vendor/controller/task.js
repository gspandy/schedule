angular.module('schedual').controller('TaskCtrl',
		function(taskService) {
			var self = this;
			self.id = '',
			self.projectName = 'ttt',
		    self.groupName = '',
			self.taskName = '',
			self.url = '',
			self.cron = '',
			self.taskStatus = '',
			self.description = '',
			self.createUser = 'jyl',
			self.createTime = '';

			self.add = function() {
				taskService.add($scope.task).then(function(data) {
					console.log('task');
				}, function(error) {
					console.log('error')
				});
			}

		});