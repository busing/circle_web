<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="http://www.xwtech.com/widget" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <title>用户中心-我的订单</title>
    <link href="${basePath }/css/all.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/css/base.css" rel="stylesheet" />
    <script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
    <script type="text/javascript" src="${basePath}/js/circle.js"></script>
    <script type="text/javascript">
    	var basePath="${basePath}";
    	var ship_status="${page.searchParam.ship_status}";
    	$(document).ready(function(){
    		if("${message}"!="")
   			{
    			ShowBox("${message}");
   			}
    		
    		if(ship_status!="")
   			{
    			$(".nav li").removeClass();
    			$(".nav li[ship_status='"+ship_status+"']").addClass("selected");
   			}
    		
    		//确认收货xinx
    		$(".reciveGood").live("click",function(){
   				var order_id=$(this).parents("tr").attr("order_id");
   	    		if(confirm("是否确认已经收到货物"))
   	   			{
   	    			$.ajax({
   	    				type:'post',
   	    				url:basePath+'/order/reciveGood.action',
   	    				dataType:'json',
   	    				data:{'order_id':order_id},
   	    				success:function(result)
   	    				{
   	    					if(result.result)
    						{
   	    						window.location.href=window.self.location.href;
    						}
   	    					else
	    					{
   	    						ShowBox("确认收货失败,请重试");
	    					}
   	    				}
   	    			});
   	   			}
    		});
    		
    	});
    	
    </script>
</head>
<body>
    <%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="ucenter">
                <div class="title">
             		  我的订单
                </div>
                <%@ include file="/usercenter/menu.jsp" %> 
                <div class="uc_r">
                    <div class="cart">
                        <ul class="nav">
                            <li class="selected" ship_status="0"><a href="${basePath}/usercenter/orderList.action">所有订单</a></li>
                            <li ship_status="5004"><a href="${basePath}/usercenter/orderList.action?searchParam[ship_status]=5004">待收货</a></li>
                            <li ship_status="5005"><a href="${basePath}/usercenter/orderList.action?searchParam[ship_status]=5005&searchParam[status]=3003&searchParam[commentStatus]=0">待评价</a></li>
                        </ul>
                        <table class="tabth">
                            <tr>
                                <td style="width: 352px;">宝贝快照</td>
                                <td style="width: 153px;">实付款（元） </td>
                                <td style="width: 146px;">付款方式</td>
                                <td style="width: 130px;">订单状态</td>
                            </tr>
                        </table>
                        <c:forEach var="o" items="${orderList}">
                        <table class="tablist">
                            <thead>
                                <tr>
		                            <td colspan="5">
		                            	<span class="intro">下单时间：${o.order_time } </span>
		                            	<span class="intro">订单号：${o.order_no }</span>
		                            	<span><i></i>${o.circle_name }</span>
		                            	<%-- <span>农场主：${o.name }	${o.mobilePhone}</span> --%>
		                           	</td>
		                        </tr>
		                        <tr>
		                            <td colspan="5">
		                            	<span>领货时间：${o.issue_time }</span>
		                            	<span>领货地点：${o.issue_address }</span>
		                            	<!-- 订单状态 -->
		                            	<span>订单状态：${o.status_str}</span>
		                            	<!-- 在线支付--未付款 -->
		                            	<c:if test="${o.pay_status eq 0 && o.pay_type eq 6002 }">
                                        	<span style="float: right;padding-right: 35px;"><a style="color: black" href="${basePath}/aliPay/payOrder.action?orderIds=${o.order_no }" >立即付款</a></span>
                                        </c:if>
		                           	</td>
		                        </tr>
                                
                            </thead>
                            <tbody>
                            	<c:forEach var="od" items="${orderDetailList[o.order_id]}">
                                <tr order_id="${o.order_id}">
                                    <td class="pro" style="width: 352px;">
                                        <a href="${basePath}/good/goodDetail.action?goodId=${od.good_id}&circleId=${o.circle_id}"><img src="${od.image}" /></a>
                                        <p class="name">${od.title}</p>
                                        <p>单位：${od.good_unit_str}</p>
                                        <p>数量：${od.buy_num}</p>
                                    </td>
                                    <td class="price">${od.total} 元
                                    </td>
                                    <td class="status">
                                        <p>${o.pay_type_str }</p>
                                        <p class="gray">
                                        <c:if test="${o.pay_status eq 1 }">
                                        	已付款
                                        </c:if>
                                        <c:if test="${o.pay_status eq 0 }">
                                        	未付款
                                        </c:if>
                                        </p>
                                    </td>
                                    <td class="op">
                                        <c:if test="${o.status eq 3003 && (o.comment_status eq 0 || o.comment_status eq null)}">
                                        	<p class="gray"><a href="${basePath}/order/orderComment.action?id=${od.id}" style="color: gray;">评价</a></p>
                                        </c:if>
                                        <!-- 发货状态：已发货 订单状态：交易中 -->
                                        <c:if test="${o.ship_status eq 5004 && o.status eq 3002}">
                                        	<p class="gray reciveGood"><a href="javaScript:void(0);" style="color: gray;">确认收货</a></p>
                                        </c:if>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        </c:forEach>
                        
						<!-- 分页控件 -->
                        <form id="orderForm" action="${basePath}/usercenter/orderList.action?searchParam[ship_status]=${page.searchParam.ship_status}" method="post" >
                        <div class="pager">
						    <w:turnPage page="${page}"/>
						</div>
						</form>
						<!-- 分页控件 -->
						
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
</body>
</html>
