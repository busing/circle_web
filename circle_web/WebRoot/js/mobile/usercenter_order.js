var app = angular.module('app', []);
   	app.controller('BusinessCtrl', ['$scope','BusinessService', function ($scope,BusinessService) {
	    $scope.pageSize = 10;
	    $scope.pageNo = 1;
	 
	    $scope.load = function () {
	        BusinessService.queryOrders($scope.pageNo,$scope.pageSize).success(function (data) {
	        	if(data){
	        		if(data.code == 1){
						$scope.orderList = data.orderList
						console.log($scope.orderList);
	        		}else{
	        			alert(data.message);
	        		}
	        	}
	        });
	    };
	    
	    $scope.orderDetail = function(orderId){
	    	window.location.href = page_url + "mobile/order_detail.html?orderId=" + orderId;
	    }
	    
	    $scope.load();
	 
	}]);
   	
  //流水业务类
app.factory('BusinessService', ['$http', function ($http) {
    var queryOrders = function (pageNo,pageSize) {
        return $http({
            params: {
        		pageNo: pageNo,
        		pageSize:pageSize
            },
            url: service_url + 'usercenter/morderList.action'
        });
    };
 
    return {
    	queryOrders : function(pageNo,pageSize){
    		return queryOrders(pageNo,pageSize);
    	}
    };
}]);