<%@ page language="java"  pageEncoding="UTF-8"%>
<div class="uc_l">
    <ul>
        <li menuId="1">
            <a href="${basePath}/usercenter/userCenter.action"><i class="i_frame"></i>我的农场</a>
        </li>
        <li menuId="2">
            <a href="${basePath}/usercenter/orderList.action"><i class="i_order"></i>我的订单</a>
        </li>
        <li menuId="3">
            <a href="${basePath}/usercenter/shopcart.action"><i class="i_cart"></i>购物车</a>
        </li>
        <li menuId="4">
            <a href="${basePath}/usercenter/info.action"><i class="i_info"></i>个人资料</a>
        </li>
        <li menuId="5">
            <a href="${basePath}/usercenter/messageCenter.action"><i class="i_msg"></i>消息中心</a>
        </li>
        <li menuId="6">
            <a href="${basePath}/usercenter/myCommossion.action"><i class="i_yj"></i>我的佣金</a>
        </li>
        <li menuId="7">
            <a href="${basePath}/commission/makeCommission.action"><i class="i_zqyj"></i>赚取佣金</a>
        </li>
    </ul>
</div>
<script>
	var menuId="${menuId}";
</script>
<script type="text/javascript" src="${basePath}/js/usercenter.js"></script>