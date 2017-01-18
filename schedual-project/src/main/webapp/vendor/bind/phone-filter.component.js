/**
 * 
 */
angular.module("phoneFilter").component("phoneFilter", {
	templateUrl : "../../vendor/bind/phone-filter.template.html",
	controller : function phoneFilter($scope) {
		this.phones = [ {
			name : 'Nexus S',
			snippet : 'Fast just got faster with Nexus S.',
			age : 1
		}, {
			name : 'Motorola XOOM™ with WiFi',
			snippet : 'The Next, Next Generation tablet.',
			age : 2
		}, {
			name : 'MOTOROLA XOOM™',
			snippet : 'The Next, Next Generation tablet.',
			age : 3
		} ];
		this.order = "age";
	}
});