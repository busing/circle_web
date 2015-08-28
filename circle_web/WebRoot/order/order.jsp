<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>提交订单</title>
    <link href="${basePath}/css/base.css" rel="stylesheet" />
    <script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
    <script type="text/javascript">
    var pay_ship_type="${pay_ship_type}";
    var receiveList="${receiveList}";
    $(document).ready(function(){
		$("a[name='receive']").live("click",function(){
			$("div[name='receiveEdit']").show();
			var receiveId=$(this).attr("receiveId");
			var receiveName=$(this).attr("receiveName");
			var receivePhone=$(this).attr("receivePhone");
			
			$("div[name='receiveEdit']").attr("receiveId",receiveId);
			$(".sel").find("span").html(receiveName+","+receivePhone);
			$(".sel").find("a").show();
			$("input[name='receive_name']").val(receiveName);
			$("input[name='receive_phone']").val(receivePhone);
		});
		$("a[name='payAndship']").live("click",function(){
			$("div[name='payAndShipEdit']").show();
			$(".infobtn").show();
			$(".info[name='pay_ship_type']").find(".select").hide();
			$(this).hide();
		});
		
		
		if(pay_ship_type=="")
		{
			$(".info[name='pay_ship_type']").find(".edit").show();
			$("a[name='payAndship']").hide();
			//选中支付方式和配送方式
			$("input[name='radPay']").last().attr("checked","true");
			$("input[name='radExpress']").last().attr("checked","true");
		}
		else
		{
			$(".infobtn").hide();
			$("input[name='radPay'][value='${pay_ship_type.pay_type}']").attr("checked","true");
			$("input[name='radExpress'][value='${pay_ship_type.ship_type}']").attr("checked","true");
		}
    });
    
    //提交订单
   	function submitOrder()
   	{
   		var jsonArr=new Array();
   		jsonArr.push("[");
   		$("input[name='remark']").each(function(i){
   			jsonArr.push("{");
   			jsonArr.push("circleId:");
   			jsonArr.push($(this).attr("circleId"));
   			jsonArr.push(",");
   			jsonArr.push("remark:'");
   			jsonArr.push($(this).val());
   			jsonArr.push("'}");
   			jsonArr.push(",");
   		});
   		jsonArr.pop();
   		jsonArr.push("]");
   		$("input[name='remarkInfo']").val(jsonArr.join(""));
   		
   		var receiveName=$("input[name='receiveRadio']:checked").parent().attr("receiveName");
		var receivePhone=$("input[name='receiveRadio']:checked").parent().attr("receivePhone");
		
		var payType=$("div[name='pay_ship_type']").find(".select li").eq(0).attr("payType");
		var shipType=$("div[name='pay_ship_type']").find(".select li").eq(1).attr("shipType");
   		
   		$("input[name='receiveName']").val(receiveName);
   		$("input[name='receivePhone']").val(receivePhone);
   		$("input[name='payType']").val(payType);
   		$("input[name='shipType']").val(shipType);
   		
   		$('form[name=orderForm]').submit();
   	}
   	
   	//保存配送和支付方式
   	function savePayAndShip()
   	{
   		var payId=$("input[name='radPay']:checked").val();
   		var shipId=$("input[name='radExpress']:checked").val();
   		$.ajax({
   			url:basePath+'/receive/savePayAndShip.action',
			data:{payType:payId,shipType:shipId},
			dataType:'json',
			success:function(result){
				if(result.result)
				{
					var pay_ship_type=result.pay_ship_type;
					$(".info[name='pay_ship_type']").find(".edit").hide();
					$("a[name='payAndship']").show();
					//选中支付方式和配送方式
					$("input[name='radPay'][value='"+pay_ship_type.pay_type+"']").attr("checked","true");
					$("input[name='radExpress'][value='"+pay_ship_type.ship_type+"']").attr("checked","true");
					
					var html=new Array();
					html.push("<div class=\"select\">");
					html.push("<ul>");
					html.push("<li payType='"+pay_ship_type.pay_type+"'>");
					html.push("<label>"+pay_ship_type.pay_info+"</label><span>"+pay_ship_type.pay_info_remark +"</span>");
					html.push("</li>");
					html.push("<li shipType='"+pay_ship_type.ship_type+"'>");
					html.push("<label>"+pay_ship_type.ship_info +"</label><span>"+pay_ship_type.ship_info_remark +"</span>");
					html.push("</li>");
					html.push("</ul>");
					html.push("</div>");
					$(".info[name='pay_ship_type']").find(".select").remove();
					$(".info[name='pay_ship_type']").prepend(html.join(""));
					$(".infobtn").hide();
				}
			}
   		});
   		
   	}
   	
   	//修改保存收货人信息
   	function saveReceiveInfo()
   	{
   		var receive_id=$("div[name='receiveEdit']").attr("receiveId");
		var receive_name=$("input[name='receive_name']").val();
		var receive_phone=$("input[name='receive_phone']").val();
		$.ajax({
   			url:basePath+'/receive/saveReceive.action',
			data:{receiveName:receive_name,receivePhone:receive_phone,id:receive_id},
			success:function(result){
				if(result=="true")
				{
					$("div[name='receiveEdit']").hide();
					loadReceive();
				}
				else
				{
					ShowBox("保存失败,请重试");
				}
			}
		});
   	}
   	
   	
   	//reload 收货人信息
   	function loadReceive()
   	{
   		$.ajax({
   			url:basePath+'/receive/queryReceive.action',
   			dataType:'json',
			success:function(result){
				var html=new Array();
				html.push("<ul>");
				for(var i=0;i<result.length;i++)
				{
					
					html.push("<li receiveId=\""+result[i].id+"\" receiveName=\""+result[i].receive_name +"\"  receivePhone=\""+result[i].receive_phone+"\">");
					if(result[i].is_default==1)
					{
						html.push("<input type=\"radio\" name=\"receiveRadio\" id=\"reveive"+i+"\" checked=\"checked\" class=\"receiveinfo\"/>");
					}
					else
					{
						html.push("<input type=\"radio\" name=\"receiveRadio\" id=\"reveive"+i+"\"  class=\"receiveinfo\"/>");
					}
					html.push("<label for=\"reveive"+i+"\">"+result[i].receive_name +"</label><span style=\"float: left;\">"+result[i].receive_phone +"</span>");
					html.push("<div class=\"link\" style=\" float: right; padding-top: 3px; \"><a name=\"receive\" receiveId=\""+result[i].id+"\" receiveName=\""+result[i].receive_name +"\"  receivePhone=\""+result[i].receive_phone+"\" href=\"javaScript:void(0);\">[修改]</a></div>");
					html.push("</li>");
				}
				html.push("</ul>");
				$(".address").find(".select").empty().append(html.join(""));
			}
		});
   	}
   	
   	//删除收货人信息
   	function deleteReceive()
   	{
   		var receiveId=$("div[name='receiveEdit']").attr("receiveid");
   		$.ajax({
   			url:basePath+'/receive/deleteReceive.action',
   			data:{id:receiveId},
   			dataType:'json',
   			success:function(result){
   				if(result=="false")
				{
   					ShowBox("删除失败,请重试");
				}
   				else
				{
   					$("div[name='receiveEdit']").hide();
   					loadReceive();
				}
   				
   			}
   		});
   	}
   	
   	//设置默认收货人信息
   	function defaultReceive()
   	{
   		var receiveId=$("div[name='receiveEdit']").attr("receiveid");
   		$.ajax({
   			url:basePath+'/receive/defaultReceive.action',
   			data:{id:receiveId},
   			dataType:'json',
   			success:function(result){
   				if(result=="false")
				{
   					ShowBox("设置失败,请重试");
				}
   				else
				{
   					$("div[name='receiveEdit']").hide();
   					loadReceive();
				}
   				
   			}
   		});
   	}
   	
   	//添加链接事件
   	function addReceive()
   	{
   		$("div[name='receiveEdit']").show();
		$("div[name='receiveEdit']").attr("receiveId","0");
		$(".sel").find("span").html("");
		$(".sel").find("a").hide();
		$("input[name='receive_name']").val("");
		$("input[name='receive_phone']").val("");
   	}
   	
   	//取消编辑收货人信息
   	function cancaleReceive()
   	{
   		$("div[name='receiveEdit']").hide();
		$("div[name='receiveEdit']").attr("receiveId","0");
		$(".sel").find("span").html("");
		$("input[name='receive_name']").val("");
		$("input[name='receive_phone']").val("");
   	}
    </script>
</head>
<body>	
	<%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="order">
                <div class="title">
                   	 订单信息
                </div>
                <div class="tit">收货人信息</div>
				<div class="info" name="receive_info" >
                <div class="address">
                    <!--显示状态-->
                  	 <div class="select">
                       <%-- <p>收货人：${receive.receive_name }, ${receive.receive_phone }</p><div class="link"><a name="receive" href="javaScript:void(0);">[修改]</a></div> --%>
                       <ul>
                         <c:forEach items="${receiveList }" var="receive" varStatus="s">
                  		<li  receiveId="${receive.id }" receiveName="${receive.receive_name }"  receivePhone="${receive.receive_phone }">
                  			<c:if test="${receive.is_default eq 1 }"><input type="radio" name="receiveRadio" id="reveive${s.index}" checked="checked" class="receiveinfo"/></c:if>
                  			<c:if test="${receive.is_default ne 1 }"><input type="radio" name="receiveRadio" id="reveive${s.index}"  class="receiveinfo"/></c:if>
                  			<label for="reveive${s.index}">${receive.receive_name }</label><span style="float: left;">${receive.receive_phone }</span>
                  			<div class="link" style=" float: right; padding-top: 3px; "><a name="receive" receiveId="${receive.id }" receiveName="${receive.receive_name }"  receivePhone="${receive.receive_phone }" href="javaScript:void(0);">[修改]</a></div>
                  		</li>
                  		</c:forEach>
                  		</ul>
                   	</div>
                   	<div class="link" style="float: right;"><a style="color: #4d9ac6;font-size: 12px;text-decoration: none;" href="javaScript:addReceive();">[添加]</a></div>
                    <!--编辑状态-->
                    <div class="edit" name="receiveEdit" receiveId="">
                        <p class="sel" style="line-height: 23px;"><span></span><a style="padding-left: 15px;" href="javaScript:defaultReceive();">设为默认</a><a href="javaScript:deleteReceive();">删除</a><a href="javaScript:cancaleReceive();">取消</a></p>
                        <div class="editform">
                            <div class="field">
                                <p>收货人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                                <div>
                                    <input type="text" name="receive_name" />
                                </div>
                            </div>
                            <div class="field">
                                <p>手机号码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                                <div>
                                    <input type="text"  name="receive_phone"/>
                                </div>
                            </div>
                            <div class="field">
                                <p>&nbsp;</p>
                                <div>
                                    <button type="button" class="saveaddr" onclick="saveReceiveInfo();">保存收货人信息</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
				</div>
                <div class="tit">支付与配送方式<div class="link"><a href="javaScript:void(0);" name="payAndship">[修改]</a></div></div>
                <div class="info" name="pay_ship_type" >
                	<c:if test="${pay_ship_type ne null}">
                    <!--显示状态-->
                    <div class="select">
                    	<ul>
                    		<li payType="${pay_ship_type.pay_type }">
                    			<label>${pay_ship_type.pay_info }</label><span>${pay_ship_type.pay_info_remark }</span>
                    		</li>
                    		<li shipType="${pay_ship_type.ship_type }">
                    			<label>${pay_ship_type.ship_info }</label><span>${pay_ship_type.ship_info_remark }</span>
                    		</li>
                    	</ul>
                    </div>
                    </c:if>
                   
                    <!--编辑状态-->
                    <div class="edit"  name="payAndShipEdit">
                        <p class="t">支付方式</p>
                        <ul>
                        	<c:forEach var="pay" items="${payType }" varStatus="s">
                        		<li><input type="radio"  name="radPay" value="${pay.type_id}" id="radPay${s.index}"/><label for="radPay${s.index}">${pay.type_name }</label><span>${pay.remark }</span></li>
                        	</c:forEach>
                        </ul>
                        <p class="t">配送方式</p>
                        <ul>
                       		 <c:forEach var="ship" items="${shipType }" varStatus="s">
                        		<li><input type="radio"  name="radExpress" value="${ship.type_id}" id="radExpress${s.index}" /><label for="radExpress${s.index}">${ship.type_name }</label><span>${ship.remark }</span></li>
                        	</c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="infobtn">
                    <button type="button" onclick="savePayAndShip();" class="save">保存支付与配送方式</button>
                </div>
                <div class="tit">订单信息</div>
                 <!-- 收货信息和支付方式 -->
                
                <table class="tabth">
                    <tr>
                        <td>农场商品信息</td>
                        <td>单价（元） </td>
                        <td>数量</td>
                        <td>金额（元）</td>
                        <td>配送方式</td>
                    </tr>
                </table>
                <c:forEach items="${circleList}" var="circle">
                <table class="tablist">
                    <thead>
                        <tr>
                            <td colspan="5">
                            	<span><i></i>${circle.name }</span>
                            	<span>农场主：${circle.createUser }	${circle.mobilePhone}</span>
                           	</td>
                        </tr>
                        <tr>
                            <td colspan="5">
                            	<span>领货时间：${circle.issueTime }</span>
                            	<span>领货地点：${circle.issueAddress }</span>
                           	</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:forEach items="${shopCartList[circle.id]}" var="order">
                        <tr>
                            <td class="pro">
                            	<a href="${basePath}/good/goodDetail.action?goodId=${order.good_id}&circleId=${order.circle_id}"><img src="${order.image}" /></a>
                                <p>${order.title }</p>
                                <p>单位：${order.unit_str }</p>
                            </td>
                            <td class="price">${order.sell_price }  元
                            </td>
                            <td class="num" style="font-size: 20px;color: #505050;">
                                ${order.buy_num } 
                            </td>
                            <td class="money">${order.total }   元
                            </td>
                            <td class="status">
                                <p class="gray">请到指定地点按时自提</p>
                            </td>
                        </tr>
                        </c:forEach>
                        <tr>
                            <td class="pro" colspan="5">
                            	<span class="spanRemark">订单备注：</span>
                            	<input class="orderRemark" type="text" name="remark" circleId="${circle.id}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                </c:forEach>
                <div class="total">实付款：<span class="rmb">￥</span><span class="price">${total }</span></div>
                <div class="btns">
                	<form action="${basePath}/order/submitOrder.action" method="post" name="orderForm">
                	<input type="hidden" value="${calCartId}" name="calCartId"/>
                	<input type="hidden" value="" name="remarkInfo"/>
                	<!-- 收货人信息 -->
                	<input type="hidden" value="" name="receiveName"/>
                	<input type="hidden" value="" name="receivePhone"/>
                	<!-- 支付方式和配送方式 -->
                	<input type="hidden" value="" name="shipType"/>
                	<input type="hidden" value="" name="payType"/>
                	<!-- 直接提交订单参数 -->
                	<input type="hidden" value="${goodId}" name="goodId"/>
                	<input type="hidden" value="${circleId}" name="circleId"/>
                	<input type="hidden" value="${buyNum}" name="buyNum"/>
                	<!-- end 直接提交订单参数 -->
                    <button type="button" class="post" onclick="submitOrder();">提交订单</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
</body>
</html>
