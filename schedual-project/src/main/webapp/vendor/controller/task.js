angular
		.module('schedual')
		.controller(
				'TaskCtrl',
				function($scope, taskService, i18nService) {
					var self = this;
					self.id = '', self.projectName = 'ttt',
							self.groupName = '', self.taskName = '',
							self.url = '', self.cron = '',
							self.taskStatus = '', self.description = '',
							self.createUser = 'jyl', self.createTime = '';

					// 国际化；
					i18nService.setCurrentLang("zh-cn");
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
						columnDefs : [
								{
									name : '姓氏',
									field : 'firstName',
									cellTooltip : true
								},
								{
									name : '名字',
									field : 'lastName',
									cellTooltip : true
								},
								{
									name : '公司名称',
									field : 'company',
									cellTooltip : true
								},
								{
									name : '是否雇用',
									field : 'employed',
									cellTooltip : true
								},
								{
									name : '操作',
									cellTemplate : '<div class="container-fluid"><div class="row cell-action-style"><div class="col-xs-3 text-center"><div class="div-click"  ng-click="grid.appScope.goToUpdate(row)"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></div></div><div class="col-xs-3 text-center" ><div class="div-click"  ng-click="grid.appScope.goToDelete(row)"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></div></div><div></div></div></div>'
								} ],
						enableSorting : true, // 是否排序
						useExternalSorting : false, // 是否使用自定义排序规则
						enableGridMenu : true, // 是否显示grid 菜单
						enableHorizontalScrollbar : 1, // grid水平滚动条是否显示, 0-不显示 1-显示
						enableVerticalScrollbar : 0, // grid垂直滚动条是否显示, 0-不显示 1-显示
						enablePagination : true, // 是否分页，默认为true
						enablePaginationControls : true, // 使用默认的底部分页
						paginationPageSizes : [ 10, 15, 20 ], // 每页显示个数可选项
						paginationCurrentPage : 1, // 当前页码
						paginationPageSize : 10, // 每页显示个数
						totalItems : 0, // 总数量
						useExternalPagination: true //是否使用分页按钮
					};

					self.add = function() {
						taskService.add(buildTask()).then(function(data) {
							console.log('task');
						}, function(error) {
							console.log('error')
						});
					}

					function buildTask() {
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
					}

				});