<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="${basePath}/css/base.css" rel="stylesheet" />
<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${basePath}/js/common/downtop.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".imask").live("click",function(){
		var goodId=$(this).attr("goodId");
		var circleId='${circle.id}';
		window.top.location="${basePath}/good/goodDetail.action?goodId="+goodId+"&circleId="+circleId;
	});
	
	//加入购物车事件
	$(".cart").live("click",function(){
		if("${user.id}"=="")
		{
			showLogin();
			return;
		}
		var buyNum=$(this).parent().parent().parent().find("input[name='goodNum']").val();
		var goodId=$(this).parent().parent().parent().attr("goodId");
		var unitId=$(this).parent().parent().parent().attr("unit");
		var circleId='${circle.id}';
		$.ajax({
			url:'${basePath}/shopcart/addShopCart.action?buyNum='+buyNum+"&goodId="+goodId+"&circleId="+circleId+"&unitId="+unitId+"&batchId="+batchId,
			dataType:'json',
			success:function(result){
				if(result.flag)
				{
					getShopCartCount();
					$(".mask",window.parent.document).fadeIn("fast");
					$(".joincart",window.parent.document).fadeIn("fast");
					$(".mask",window.parent.document).fadeOut(5000);
					$(".joincart",window.parent.document).fadeOut(5000);
				}
				else
				{
					//alert(result.msg);
					$("#alert_pop").show();
					$(".alert_btncon").show();
					$("#message").html("您还没有加入当前农场，不能购买，是否立即加入？");
					$(".alert_btncon").find(".alert_ok").attr('onclick','').click( eval(function(){
				        $("#alert_pop").hide();
				        $(".alert_btncon").hide();
				        $("#titleTop").html("");
				        $("#message").html("");
				        joinFarm();//加入农场
			    	}));
			    	$(".alert_btncon").find(".alert_cancel").attr('onclick','').click( eval(function(){
				        $("#alert_pop").hide();
				        $(".alert_btncon").hide();
				        $("#titleTop").html("");
				        $("#message").html("")
			    }));
				}
			}
		});
	});
	// end of 加入购物车事件
	
	
	//立即购买事件
	$("button[name='buyButton']").live("click",function(){
		if("${user.id}"=="")
		{
			showLogin();
			return;
		}
		
		var buyNum=$(this).parent().parent().parent().find("input[name='goodNum']").val();
		var goodId=$(this).parent().parent().parent().attr("goodId");
		var circleId='${circle.id}';
		
		var url = "${basePath}/circle/isJoinFarm.action?circleId=" + circleId + "&t="+new Date().getTime();
	    $.ajax({
	        type : "POST",
	        url : url,
	        dataType : "json",
	        contentType : "text/xml",
	        timeout : 10000,
	        data : '',
	        success : function(jsonObj){
	            if (jsonObj){
	            	window.parent.location.href='${basePath}/order/addOrder.action?buyNum='+buyNum+"&goodId="+goodId+"&circleId="+circleId;
	            }else{
	               $("#alert_pop").show();
					$(".alert_btncon").show();
					$("#message").html("您还没有加入当前农场，不能购买，是否立即加入？");
					$(".alert_btncon").find(".alert_ok").attr('onclick','').click( eval(function(){
				        $("#alert_pop").hide();
				        $(".alert_btncon").hide();
				        $("#titleTop").html("");
				        $("#message").html("");
				        joinFarm();//加入农场
			    	}));
			    	$(".alert_btncon").find(".alert_cancel").attr('onclick','').click( eval(function(){
				        $("#alert_pop").hide();
				        $(".alert_btncon").hide();
				        $("#titleTop").html("");
				        $("#message").html("")
			   		}));
	            }
	        },
	        error : function(jsonobj){
	        }
	    });
		
	});
	// end of 立即购买事件
	
});
</script>
</head>
<body style="min-height: 600px;">
   <div class="list">
	  <div class="listbox">
	 		<!-- start of item -->
	 		<c:forEach var="good" items="${goodList}">
	 		<c:if test="${good.id ne null}">
	      <div class="item" goodId="${good.id}" unit="${good.unit}">
	          <img src="${good.image}" goodId="${good.id}" />
	          <p class="title">${good.title}</p>
	          <p class="pi"><span class="field">价格</span><span><b>${good.sell_price}</b><span class="red">元</span></span>/${good.unit_str}
	          <span style="text-decoration: line-through;">&nbsp;&nbsp;市场价：${good.original_price}<span class="red">元</span></span></p>
			  <p class="title"><c:if test="${good.unit_intro ne null && good.unit_intro ne ''}">${good.unit_intro}</c:if></p>
	          <div class="pi">
	              <span class="field">数量</span><input type="text" name="goodNum" value="1" /><div class="td"><i class="top"></i><i class="down"></i></div>
	              <!-- <span class="st">库存12423件</span> -->
	          </div>
	          <p class="info">商品评价：${good.commonCount }</p>
	          <div class="btns">
	              <div class="btnsbox">
	                  <button type="button" class="buy" name="buyButton">立刻购买</button>
	                  <button type="button" class="cart"><i></i>加入购物车</button>
	              </div>
	          </div>
	          <div class="imask" goodId="${good.id}">
	              <p>
	                  ${good.intro}
	              </p>
	          </div>
		 </div>
	 </c:if>
	 </c:forEach>
	 <!-- end of item -->
	</div>
 </div>
 
</body>
</html>
