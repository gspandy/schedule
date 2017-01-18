/**
 * 
 */
angular.module("phoneFilter").component("phoneFilter",{
	templateUrl:"../../vendor/filter/phone-filter.template.html",
	controller:function phoneFilter($scope){
		this.phones = [
	        {
	          name: 'Nexus S',
	          snippet: 'Fast just got faster with Nexus S.'
	        }, {
	          name: 'Motorola XOOM™ with WiFi',
	          snippet: 'The Next, Next Generation tablet.'
	        }, {
	          name: 'MOTOROLA XOOM™',
	          snippet: 'The Next, Next Generation tablet.'
	        }
	      ];
	}
});