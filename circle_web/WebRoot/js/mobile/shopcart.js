var app = angular.module('app', []);
   	app.controller('BusinessCtrl', ['$scope','BusinessService', function ($scope,BusinessService) {
	    $scope.pageNo = 1;
	    $scope.pageSize = 10;
	 
	    $scope.load = function () {
	        BusinessService.queryShopCart($scope.pageNo,$scope.pageSize).success(function (data) {
	        	if(data){
					//$scope.farmInfo = data;
	        		console.log(data);
	        		$scope.myShopcart = data.list;
	        		$scope.circle = data.circle;
	        		console.log($scope.circle);
	        		$scope.user = data.user;
	        		
					for(var i=0;i<$scope.myShopcart.length;i++){
						$scope.cart[i] = $scope.myShopcart[i].sell_price*$scope.myShopcart[i].buy_num;
			    		$scope.total();
					}
	        	}
	        });
	    };
	    
	    $scope.order = function () {
	    	var goodIds = "";
	    	var goodNums = "";
	    	
	    	$("input[name='goodId']").each(
    			function(){
    				if($(this).val()){
    					goodIds += $(this).val() + ",";
    				}else{
    					goodIds += "0,"
    				}
			});
	    	$(".m").each(
    			function(){
    				if($(this).html()){
    					goodNums += $(this).html() + ",";
    				}else{
    					goodNums += "0,"
    				}
			});
	    	
	        BusinessService.order(goodIds,goodNums).success(function (data) {
	        	if(data){
					if(data.code=1){
						window.location.href = page_url + "mobile/order_success.html?orderId=" + data.orderId;
					}else{
						alert(data.message);
					}
	        	}
	        });
	    };
	    
	    $scope.cart = new Array(100);
	    $scope.totalPrice = 0;
	    $scope.cartNum = 0;
	    
	    $scope.addNum = function(event,index,price){
	    	var target = angular.element(event.target);
	    	var buyNum = target.prev().html();
	    	if(!buyNum){
	    		buyNum = 0;
	    	}
	    	if(buyNum < 99){
	    		buyNum++;
	    		target.prev().html(buyNum);
	    		$scope.cart[index] = buyNum*price;
	    		$scope.total();
			}
	    };
	    
	    $scope.minusNum = function(event,index,price){
	    	var target = angular.element(event.target);
	    	var buyNum = target.next().html();
	    	if(!buyNum){
	    		buyNum = 0;
	    	}
	    	
	    	if(buyNum > 1){
	    		buyNum--;
	    		if(buyNum==0){
	    			buyNum = "";
	    		}
	    		target.next().html(buyNum);
	    		$scope.cart[index] = buyNum*price;
	    		$scope.total();
			}
	    };
	    
	    $scope.total = function(){
		    $scope.totalPrice = 0;
	    	for(var i = 0; i < $scope.cart.length;i++){
	    		if($scope.cart[i] == 0){
	    			console.log("$scope.cart[i]:" + $scope.cart[i]);
	    			$scope.totalPrice += $scope.cart[i];
	    		}
	    		if($scope.cart[i]){
	    			console.log("$scope.cart[i]:" + $scope.cart[i]);
	    			$scope.totalPrice += $scope.cart[i];
	    		}
	    	}
	    };
	    
	    $scope.load();
	 
	}]);
   	
   	app.filter("htmlFilter",['$sce',function($sce){
   		return function(text){
   			return $sce.trustAsHtml(text);  
   		}
   	}]);

//流水业务类
app.factory('BusinessService', ['$http', function ($http) {
    var queryShopCart = function (pageNo,pageSize) {
        return $http({
            params: {
        		pageNo:pageNo,
        		pageSize:pageSize
            },
            url: service_url + 'shopcart/mshopcart.action'
        });
    };
    
    var order = function(goodIds,goodNums){
    	return $http({
    		 params: {
    			goodIds:goodIds,
    			goodNums:goodNums
	        },
    		url:service_url + 'order/morder.action'
    	});
    }
 
    return {
    	queryShopCart: function (pageNo,pageSize) {
            return queryShopCart(pageNo,pageSize);
        },
        order :function(goodIds,goodNums){
        	return order(goodIds,goodNums);
        }
    };
}]);