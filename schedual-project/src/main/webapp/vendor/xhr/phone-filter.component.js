/**
 * 
 */
angular.module("phoneFilter").component("phoneFilter", {
	templateUrl : "../../vendor/xhr/phone-filter.template.html",
	controller : [ '$http', function phoneFilter($http) {
		var self = this;
		self.order = "age";
		$http.get("../../vendor/xhr/phones.json").then(function(response) {
			self.phones = response.data;
		});
	} ]
});