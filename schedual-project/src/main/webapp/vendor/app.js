angular.module('schedual',
		[ 'ngTouch', 'ngAnimate', 'ui.router', 'oc.lazyLoad' ]);

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