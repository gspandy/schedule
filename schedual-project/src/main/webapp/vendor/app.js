angular.module('schedual', [ 'ui.router', 'oc.lazyLoad' ]);

var schedual = angular.module('schedual').config(
		[
				'$controllerProvider',
				'$compileProvider',
				'$filterProvider',
				'$provide',
				function($controllerProvider, $compileProvider,
						$filterProvider, $provide) {
					schedual.controller = $controllerProvider.register;
					schedual.directive = $compileProvider.directive;
					schedual.filter = $filterProvider.register;
					schedual.factory = $provide.factory;
					schedual.service = $provide.service;
					schedual.constant = $provide.constant;
					schedual.value = $provide.value;
				} ]);

schedual.config(
		[ '$ocLazyLoadProvider', function($ocLazyLoadProvider) {
			$ocLazyLoadProvider.config({
				debug : false,
				events : true,
				modules : []
			})
		} ]);

schedual.config([ '$stateProvider', function($stateProvider) {
	$stateProvider.state('period/add', {
		url : '/period/add',
		templateUrl : 'view/period/task.add.html',
		rosolve : {
			deps : [ '$ocLazyLoad', function($ocLazyLoad) {
				return $ocLazyLoad.load({
					serie:true,
					files:[
						'controller/task.js',
						'service/task.service.js'
					]
				});
			} ]
		}
	});
} ]);
