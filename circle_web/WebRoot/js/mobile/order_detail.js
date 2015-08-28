var app = angular.module('app', []);
   	app.controller('BusinessCtrl', ['$scope','BusinessService', function ($scope,BusinessService) {
	    $scope.orderId = requestParams()["orderId"];;
	 
	    $scope.load = function () {
	        BusinessService.queryOrder($scope.orderId).success(function (data) {
	        	if(data){
	        		if(data.code == 1){
						$scope.order = data.order;
	        		}else{
	        			alert(data.message);
	        		}
	        	}
	        });
	    };
	    
	    $scope.load();
	 
	}]);
   	
  //流水业务类
app.factory('BusinessService', ['$http', function ($http) {
    var queryOrder = function (orderId) {
        return $http({
            params: {
        		orderId: orderId
            },
            url: service_url + 'order/mqueryorder.action'
        });
    };
 
    return {
    	queryOrder : function(orderId){
    		return queryOrder(orderId);
    	}
    };
}]);