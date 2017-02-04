var schedual = angular
		.module('schedual', [ 'ui.router', 'oc.lazyLoad' ])
		.config(
				[
						'$stateProvider',
						function($stateProvider) {
							$stateProvider
									.state(
											'period/add',
											{
												url : 'period/add',
												templateUrl : 'view/period/task.add.html',
												rosolve : {
													deps : [
															'$ocLazyLoad',
															function(
																	$ocLazyLoad) {
																return $ocLazyLoad
																		.load(
																				[])
																		.then(
																				function() {
																					return $ocLazyLoad
																							.load({
																								serie : true,
																								files : [
																										'service/task.service.js',
																										'controller/task.js' ]
																							});
																				});
															} ]
												}
											});
						} ]);

schedual.controller('appCtrl', function($scope) {

})
