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
</head>
<body>
    <%@ include file="/common/head.jsp"%>
    <div class="content">
        <div class="center">
            <div class="order com">
                <div class="title">
                    <i class="com"></i>亲爱的，恭喜您订单提交成功^-^  !
                </div>
                <div class="tit">订单信息</div>
                <div class="info">
                    <div class="select">
                        <p>订单编号：${orderNo}<a href="${basePath}/usercenter/orderList.action" class="spangray">【查看我的订单】</a></p>
                        <p>订单金额：<span class="spanmoney">￥${orderTotal }</span><span class="spangray"><!-- （亲爱的，24小时内未付款订单会自动取消哦~） --></span></p>
                    </div>
                </div>
                <div class="tit">订单支付</div>
                <div class="info">
                    <div class="select">
                        <p>您选择了：${pay_type_str }  &nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${pay_type eq 6002 }">(30分钟不支付，订单自动取消)</c:if><!-- <a href="#" class="spangray">【选择其他支付方式】</a> --></p>
                        <!-- <p><span class="spanred">本站支撑在线支付方式 支付宝及多家网银 手机支付</a></p> -->
                    </div>
                </div>
                <c:if test="${pay_type eq 6002 }">
                <div class="infobtn">
                    <button type="button" class="pay" onclick="payOrder();">立刻付款</button>
                </div>
                </c:if>
            </div>
        </div>
    </div>
     <%@ include file="/common/foot.jsp"%>
</body>
<script type="text/javascript">
	function payOrder()
	{
		window.location.href="${basePath}/aliPay/payOrder.action?orderIds=${orderNo}";
	}
</script>
</html>
