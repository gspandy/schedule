angular.module('schedual').controller(
		'TaskCtrl',
		function($scope, taskService) {
			var self = this;
			self.id = '', self.projectName = 'ttt', self.groupName = '',
					self.taskName = '', self.url = '', self.cron = '',
					self.taskStatus = '', self.description = '',
					self.createUser = 'jyl', self.createTime = '';

			self.gridOptions = {
				data : [ {
					"firstName" : "Cox",
					"lastName" : "Carney",
					"company" : "Enormo",
					"employed" : true
				}, {
					"firstName" : "Lorraine",
					"lastName" : "Wise",
					"company" : "Comveyer",
					"employed" : false
				}, {
					"firstName" : "Nancy",
					"lastName" : "Waters",
					"company" : "Fuelton",
					"employed" : false
				} ],
				columnDefs:[
					{name:'姓氏',field:'firstName',cellTooltip: true},
					{name:'名字',field:'lastName',cellTooltip: true},
					{name:'公司名称',field:'company',cellTooltip: true},
					{name:'是否雇用',field:'employed',cellTooltip: true}
				]
			};

			self.add = function() {
				taskService.add(/*self.buildTask()*/).then(function(data) {
					console.log('task');
				}, function(error) {
					console.log('error')
				});
			}

			/*function buildTask() {
				return {
					projectName : self.projectName,
					groupName : self.groupName,
					taskName : self.taskName,
					url : self.url,
					cron : self.cron,
					taskStatus : self.taskStatus,
					description : self.description,
					createUser : self.createUser
				}
			}*/

		});