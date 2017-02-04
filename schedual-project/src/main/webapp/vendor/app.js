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

schedual
		.config([
				'$ocLazyLoadProvider',
				function($ocLazyLoadProvider) {
					$ocLazyLoadProvider
							.config({
								debug : true,
								events : true,
								modules : [ {
									name : 'bootstrap.table',
									files : [
											'lib/bootstrap-table/css/bootstrap-table.min.css',
											'lib/bootstrap-table/js/bootstrap-table.min.js',
											'lib/bootstrap-table/js/bootstrap-table-locale-all.min.js' ]
								} ]
							})
				} ]);

schedual.config([ '$stateProvider', function($stateProvider) {
	$stateProvider.state('period/add', {
		url : '/period/add',
		templateUrl : 'view/period/task.add.html',
		resolve : {
			deps : [ '$ocLazyLoad', function($ocLazyLoad) {
				return $ocLazyLoad.load({
					serie : true,
					files : [ 'controller/task.js', 'service/task.service.js' ]
				});
			} ]
		}
	}).state('period/list', {
		url : '/period/list',
		templateUrl : 'view/period/task.list.html',
		resolve : {
			deps : [ '$ocLazyLoad', function($ocLazyLoad) {
				return $ocLazyLoad.load({
					serie : true,
					files : [ 'controller/task.js', 'service/task.service.js' ]
				});
			} ]
		}
	});
} ]);
