/**
 * URI 路由配置信息
 */
schedual.config([
		'$stateProvider',
		'$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {
			$urlRouterProvider.otherwise('/login');
			$stateProvider.state(
					'login',
					{
						url : '/login',
						templateUrl : 'view/user/login.html',
						resolve : {
							deps : [
									'$ocLazyLoad',
									function($ocLazyLoad) {
										return $ocLazyLoad.load({
											serie : true,
											files : [ 'controller/user.js',
													'service/user.service.js' ]
										});
									} ]
						}
					}).state(
					'registe',
					{
						url : '/registe',
						templateUrl : 'view/user/register.html',
						resolve : {
							deps : [
									'$ocLazyLoad',
									function($ocLazyLoad) {
										return $ocLazyLoad.load({
											serie : true,
											files : [ 'controller/user.js',
													'service/user.service.js' ]
										});
									} ]
						}
					}).state(
					'period/add',
					{
						url : '/period/add',
						templateUrl : 'view/period/task.add.html',
						resolve : {
							deps : [
									'$ocLazyLoad',
									function($ocLazyLoad) {
										return $ocLazyLoad.load({
											serie : true,
											files : [ 'controller/task.js',
													'service/task.service.js' ]
										});
									} ]
						}
					}).state(
					'period/list',
					{
						url : '/period/list',
						templateUrl : 'view/period/task.list.html',
						resolve : {
							deps : [
									'$ocLazyLoad',
									function($ocLazyLoad) {
										return $ocLazyLoad.load({
											serie : true,
											files : [ 'controller/task.js',
													'service/task.service.js' ]
										});
									} ]
						}
					});
		} ]);
