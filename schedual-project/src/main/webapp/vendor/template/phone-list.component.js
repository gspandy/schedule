/**
 * 定义模块
 */
angular.module("phoneList")
	   .component("phoneList",{
		  templateUrl:"../../vendor/template/phone-list.template.html",
		  controller:function phoneList ($scope){
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