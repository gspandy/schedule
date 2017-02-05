/**
 * 模块延迟加载
 */
schedual
		.config([
				'$ocLazyLoadProvider',
				function($ocLazyLoadProvider) {
					$ocLazyLoadProvider
							.config({
								debug : true,
								events : true,
								modules : [
										{
											name : 'toaster',
											files : [
													'lib/modules/angularjs-toaster/toaster.min.css',
													'lib/modules/angularjs-toaster/toaster.min.js' ]
										},
										{
											name : 'ui.grid',
											files : [
													'lib/modules/angularjs-ui-grid/ui-grid.min.css',
													'lib/modules/angularjs-ui-grid/ui-grid.min.js' ]
										} ]
							})
				} ]);
