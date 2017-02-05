/**
 * 用户登录控制器
 */
schedual.controller('UserCtrl', function($scope,UserService,toaster) {
	var self = this;
	self.username = '';
	self.password = '';

	self.login = function() {
		toaster.pop('error', "title", "text");
		
	}

	self.loginout = function() {
		
	}

});