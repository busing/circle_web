var app = angular.module('app', []);
   	app.controller('BusinessCtrl', ['$scope','BusinessService', function ($scope,BusinessService) {
	    $scope.circleId = requestParams()["circleId"];
	    $scope.pageSize = 10;
	    $scope.day=0;
	    $scope.hour=0;
	    $scope.minute=0;
	    $scope.second=0;//时间默认值	
	 
	    $scope.load = function () {
	        BusinessService.info($scope.circleId,$scope.pageSize).success(function (data) {
	        	if(data){
					//$scope.farmInfo = data;
	        		console.log(data);
					$scope.circle = data.circle;
					$scope.goodList = data.goodList
					console.log($scope.circle);
					console.log($scope.goodList);
					$scope.timer(data.batchSell.remainderSeconds);
					
	        	}
	        });
	    };
	    
	    $scope.addToShopCart = function(){
	    	var goodIds = "";
	    	var goodNums = "";
	    	
	    	var flag = false;
	    	$("input[name='goodId']").each(
    			function(){
    				if($(this).val()){
    					goodIds += $(this).val() + ",";
    				}else{
    					goodIds += "0,"
    				}
			});
	    	$(".goodnum").each(
    			function(){
    				if($(this).html()){
    					flag = true;
    					goodNums += $(this).html() + ",";
    				}else{
    					goodNums += "0,"
    				}
			});
	    	
	    	console.log("flag:" + flag);
	    	if(!flag){
	    		alert("请至少选择一件商品");
	    	}else{
	    		BusinessService.addShopCart(goodIds,goodNums).success(function(data) {
		        	if(data){
		        		console.log(data);
		        		window.location.href = page_url + "mobile/shopcart.html";
		        	}
		        });
	    	}
	    };
	    
	    $scope.showDetail = function(event){
	    	var target = angular.element(event.target);
	    	if (target.parent().parent().next().hasClass("hidden")) {
            	target.parent().parent().next().one("click", function () {
                    target.parent().parent().next().addClass("hidden");
                    target.prev().find(".arrow").removeClass("selected").addClass("unselected");
            	});
                target.parent().parent().next().removeClass("hidden");
                target.removeClass("unselected").addClass("selected");
            } else {
                target.parent().parent().next().addClass("hidden");
                target.removeClass("selected").addClass("unselected");
            }
	    };
	    
	    $scope.cart = new Array(100);
	    $scope.totalPrice = 0;
	    $scope.cartNum = 0;
	    
	    $scope.addNum = function(event,index,price){
	    	var target = angular.element(event.target);
	    	var buyNum = target.parent().next().html();
	    	if(!buyNum){
	    		buyNum = 0;
	    	}
	    	console.log("old buy num:" + buyNum);
	    	console.log("index:" + index);
	    	console.log("price:" + price);
	    	if(buyNum < 99){
	    		buyNum++;
	    		console.log("new buy num:" + buyNum);
	    		target.parent().next().html(buyNum);
	    		$scope.cart[index] = buyNum*price;
	    		console.log("$scope.cart:" + $scope.cart);
	    		$scope.total();
			}
	    };
	    
	    $scope.minusNum = function(event,index,price){
	    	var target = angular.element(event.target);
	    	var buyNum = target.parent().next().html();
	    	console.log("old buy num:" + buyNum);
	    	if(!buyNum){
	    		buyNum = 0;
	    	}
	    	
	    	if(buyNum > 0){
	    		buyNum--;
	    		console.log("new buy num:" + buyNum);
	    		if(buyNum==0){
	    			buyNum = "";
	    		}
	    		target.parent().next().html(buyNum);
	    		$scope.cart[index] = buyNum*price;
	    		console.log("$scope.cart:" + $scope.cart);
	    		$scope.total();
			}
	    };
	    
	    $scope.total = function(){
		    $scope.totalPrice = 0;
		    $scope.cartNum = 0;
	    	for(var i = 0; i < $scope.cart.length;i++){
	    		if($scope.cart[i] == 0){
	    			console.log("$scope.cart[i]:" + $scope.cart[i]);
	    			$scope.totalPrice += $scope.cart[i];
	    		}
	    		if($scope.cart[i]){
	    			$scope.cartNum++; 
	    			console.log("$scope.cart[i]:" + $scope.cart[i]);
	    			$scope.totalPrice += $scope.cart[i];
	    		}
	    	}
	    };
	    
	    $scope.timer = function(intDiff){
	    	window.setInterval(function(){
	    		if(intDiff > 0){
	    			$scope.day = Math.floor(intDiff / (60 * 60 * 24));
	    			$scope.hour = Math.floor(intDiff / (60 * 60)) - ($scope.day * 24);
	    			$scope.minute = Math.floor(intDiff / 60) - ($scope.day * 24 * 60) - ($scope.hour * 60);
	    			$scope.second = Math.floor(intDiff) - ($scope.day * 24 * 60 * 60) - ($scope.hour * 60 * 60) - ($scope.minute * 60);
	    		}
	    		else
	    		{
	    			window.clearInterval(remainderTimer);
	    			disableButton($(".buy"));
	    			disableButton($(".cart"));
	    		}
	    		if ($scope.minute <= 9) $scope.minute = '0' + $scope.minute;
	    		if ($scope.second <= 9) $scope.second = '0' + $scope.second;
	    		$scope.$apply();
    			intDiff--;
	    		}, 1000);
	    	} 
	    
	    $scope.load();
	 
	}]);
   	
   	app.filter("htmlFilter",['$sce',function($sce){
   		return function(text){
   			return $sce.trustAsHtml(text);  
   		}
   	}]);

  //流水业务类
app.factory('BusinessService', ['$http', function ($http) {
    var info = function (circleId,pageSize) {
        return $http({
            params: {
        		circleId: circleId,
        		pageSize:pageSize
            },
            url: service_url + 'circle/farmIndex.action'
        });
    };
    
    var addShopCart = function(goodIds,goodNums){
    	return $http({
    		 params: {
    			goodIds: goodIds,
    			goodNums:goodNums
        },
        url: service_url + 'shopcart/maddShopCart.action'
    	});
    }
 
    return {
    	info : function(circleId,pageSize){
    		return info(circleId,pageSize);
    	},
    	addShopCart: function (goodIds,goodNums) {
            return addShopCart(goodIds,goodNums);
        }
    };
}]);