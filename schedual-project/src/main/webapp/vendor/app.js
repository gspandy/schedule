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
								modules : [{
									name:'toaster',
									files:[
										'lib/modules/angularjs-toaster/toaster.min.css',
										'lib/modules/angularjs-toaster/toaster.min.js'
									]
								}]
							})
				} ]);

