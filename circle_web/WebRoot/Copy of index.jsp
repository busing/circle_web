<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<title>团农场</title>
<link href="${basePath}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${basePath }/css/style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${basePath}/js/common/common.js"></script>
<script type="text/javascript" src="${basePath}/js/index.js"></script>
<script type="text/javascript" src="${basePath}/js/common/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${basePath}/js/common/validform.js"></script>
<script type="text/javascript" src="${basePath }/js/common/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath}/js/common/validate.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=IDvNBsejl9oqMbPF316iKsXR"></script>
<script>
	var basePath = "${basePath}";
</script>
</head>
<body onkeydown="search()">
    <%@include file="/common/head.jsp" %>
    <div class="content">
        <div class="farm">
            <div class="center">
                <div class="search">
                    <input type="text"  value=""  placeholder="输入您的地址，发现您周围的团农场" id="searchTxt" name="searchTxt"/><a href="#" title="搜索" onclick="searchMap()"></a>
                </div>
                <div id="mapContainer" class="map">
                </div>
                <!-- 附近的农场 -->
                <div class="farms" style="display: none;">
                    <div class="near">
                        <div class="n_left">
                           	 附近的农场
                        </div>
                        <div class="n_right">
                            <a href="javaScript:void(0);">更多农场<i></i></a>
                        </div>
                    </div>
                </div>
                <!-- end 附近的农场 -->
                <div class="joins">
                    <a href="javaScript:void(0);">我要创建团农场<span>+</span>（创建即送一斤苹果）</a>
                </div>
                <div class="pic1">
                    <ul>
                        <li><a href="#">
                            <img src="${basePath}/images/pepole_1.png" alt="各种水果价格低" /></a></li>
                        <li><a href="#">
                            <img src="${basePath}/images/pepole_2.png" alt="带领大家一起买" /></a></li>
                        <li><a href="#">
                            <img src="${basePath}/images/pepole_3.png" alt="帮助别人自己赚" /></a></li>
                    </ul>
                </div>
                <div class="intro">
                    <p class="title">农场主描述</p>
                    <p>
                	     基于您所在的位置创建属于自己特色社区电子农场（团农场），为自己身边的邻居、同事或者同学提供自己农场展示的产品，按照您发起的时间，按时接收并分发成员订单，同时作为农场主的您享受一定回报，让大家在亲密的分享关系中享受到新鲜便宜的健康水果，同时作为农场主的您享受5%销售额返利。
                    </p>
                </div>
            </div>
        </div>
        <div class="workflow">
            <div class="center">
                <div class="wc">
                    <p class="title">购物流程</p>
                    <ul>
                        <li class="l">
                            <img src="${basePath}/images/lc_1.png" alt="地址" />
                            <p>第一步：在搜索框里填上您所在社区（小区、公司或学校）的详细地址，寻找您周围的团农场。</p>
                        </li>
                        <li class="r">
                            <img src="${basePath}/images/lc_2.png" alt="注册" />
                            <p>第二步：根据搜索显示，您可创建一个属于您直接管理的团农场成为农场主，也可直接加入已存在团农场，成为本站用户。</p>
                        </li>
                        <li class="l">
                            <img src="${basePath}/images/lc_3.png" alt="签收" />
                            <p>第三步：当您所在的团农场订单达到5人及5人以上，团农场订单才生效，我们将货物直接送达农场主指定地方，农场主签收。</p>
                        </li>
                        <li class="r">
                            <img src="${basePath}/images/lc_4.png" alt="收货" />
                            <p>第四步：大家按照团农场规定的时间，农场主将大家各自的产品分发给大家。</p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="special">
            <div class="center">
                <div class="sc">
                    <p class="title">特色优势</p>
                    <ul>
                        <li>
                            <img src="${basePath}/images/icon_1.png" />
                            <p>我们致力于为你们提供新鲜便宜的健康水果，我们所卖任何水果都是经过我们精挑细选,让您和家人吃了放心。</p>
                        </li>
                        <li>
                            <img src="${basePath}/images/icon_2.png" />
                            <p>提前订购能够让我们提前会根据您订单要求进行挑选，保证在您规定的时间里为您送上新鲜健康的水果。</p>
                        </li>
                        <li>
                            <img src="${basePath}/images/icon_3.png" />
                            <p>不设有实体店，我们能做的就是可以给您更多优惠的价格，以及及时买到新鲜美味农产品给家人品尝。</p>
                        </li>
                    </ul>
                    <div class="keysearch">
                        <input type="text" value=""  placeholder="输入您的地址，发现您周围的团农场" id="searchTxt2" name="searchTxt2"/><a href="#" title="搜索" onclick="searchMap2()"></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="/common/foot.jsp" %>
    
   	
</body>
</html>
