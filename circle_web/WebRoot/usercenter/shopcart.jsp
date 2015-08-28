<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="http://www.xwtech.com/widget" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <title>用户中心-购物车</title>
    <link href="${basePath }/css/all.css" rel="stylesheet" type="text/css" />
	<link href="${basePath}/css/base.css" rel="stylesheet" />
    <script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
    <script type="text/javascript" src="${basePath}/js/shopcart.js" ></script>
    <script type="text/javascript">
    	var basePath="${basePath}";
    </script>
   
</head>
<body>
	<%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="ucenter">
                <div class="title">
                    购物车
                </div>
                <%@ include file="/usercenter/menu.jsp" %> 
                <div class="uc_r">
                    <div class="cart">
                        <ul class="nav">
                            <li class="selected"><a href="#">全部商品 </a></li>
                           <!--  <li><a href="#">库存紧张1</a></li> -->
                        </ul>
                        <table class="tabth">
                            <tr>
                                <td style="width: 92px;">
                                    <div class="chkbox" name="allCheck"></div>
                                                                                                全选</td>
                                <td style="width: 211px;">商品信息</td>
                                <td style="width: 138px;">单价（元） </td>
                                <td style="width: 103px;">数量</td>
                                <td style="width: 151px;">金额（元）</td>
                                <td style="width: 85px;">操作</td>
                            </tr>
                        </table>
                        <c:forEach items="${shopCartList }" var="s">
                        <table class="tablist" cartId="${s.cart_id}">
                            <thead>
                                <tr>
                                    <td colspan="5">
                                        <div class="chkbox" name="cartCheck" cartId="${s.cart_id}"></div>
                                        <span><i></i>${s.name }</span></td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="pro">
                                        <a href="${basePath}/good/goodDetail.action?goodId=${s.good_id}&circleId=${s.circle_id}"><img src="${s.image}" /></a>
                                        <p class="name">${s.title }</p>
                                        <p>单位：${s.unit_str }</p>
                                    </td>
                                    <td class="price">${s.sell_price }  元
                                    </td>
                                    <td class="num">
                                        <input type="text" cartId="${s.cart_id}" name="goodNum" maxlength="2" value="${s.buy_num }" />
                                        <div class="td" cartId="${s.cart_id}">
                                            <i class="top"></i><i class="down"></i>
                                        </div>
                                    </td>
                                    <td class="money">${s.total }  元
                                    </td>
                                    <td class="op">
                                        <a href="javaScript:void(0);" cartId="${s.cart_id}" class="gray">删除</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        </c:forEach>
                       
                        <div class="total">
                            <div class="tl">
                                <div class="chkbox" name="allCheck"></div>
                           		     全选&nbsp;&nbsp;&nbsp;&nbsp;
                                <!-- <a href="#">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">分享</a> -->
                            </div>
                            <div class="tr">
                                已选商品<span class="red">0</span>件
                                <!-- <div class="prolist">
                                    <a href="javascript:;"></a>
                                    <div class="box">
                                        <ul>
                                            <li><a href="#">
                                                <img src="images/temp_5.png" /></a></li>
                                            <li><a href="#">
                                                <img src="images/temp_5.png" /></a></li>
                                            <li><a href="#">
                                                <img src="images/temp_5.png" /></a></li>
                                        </ul>
                                    </div>
                                </div> -->
                                合计：<span class="red">￥0</span>
                                <form action="${basePath}/order/calOrder.action" name="calForm" method="post" onsubmit="return calcart();">
                                	<input type="hidden" name="calCartId"/>
                                	<button class="post">结算</button>
                                </form>
                            </div>

                        </div>
                        
                        <!-- 分页控件 -->
                        <form id="shopCartForm" action="${basePath}/usercenter/shopcart.action" method="post" >
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
