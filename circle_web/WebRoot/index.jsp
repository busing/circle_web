<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.circle.pojo.user.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta name="baidu-site-verification" content="ZGhVLJakgz" />
<title>团农网-最新鲜的水果农场</title>
<meta name="Keywords" content="团农,团农网,我的农场,水果,生鲜" />
<meta name="Description" content="团农场基于您所在的位置创建属于自己特色社区电子农场（团农场），为自己身边的邻居、同事或者同学提供自己农场展示的产品，按照您发起的时间，按时接收并分发成员订单，同时作为农场主的您享受一定回报，让大家在亲密的分享关系中享受到新鲜便宜的健康水果，同时作为农场主的您享受5%销售额返利。 " />
<link href="${basePath}/css/base.css?v=2015050301" rel="stylesheet" type="text/css" />
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
	$(document).ready(function(){
		
		$(".wgt-exp-share").live("click",function(){
			if($(".wgt-exp-share-qrcode").css("display")=="none")
			{
				$(".wgt-exp-share-qrcode").show();
				$(this).text("点击关闭");
			}
			else
			{
				$(".wgt-exp-share-qrcode").hide();
				$(this).text("关注微信");
			}
		});
		
		$(".wgt-exp-share").click();
	});
</script>
</head>
<body onkeydown="search()">
    <%@include file="/common/head.jsp" %>
    <div class="content">
        <div class="farm">
            <div class="center">
                <div class="search">
                    <input type="text"  value=""  placeholder="输入您的地址，发现您周围的水果团购" id="searchTxt" name="searchTxt"/><a href="javascript:void(0);" title="搜索" onclick="searchMap()"></a>
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
                    <a href="javaScript:void(0);">我要创建团购农场<span>+</span>（创建即送一斤苹果）</a>
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
                	     基于您所在的位置发起团购，即创建属于自己特色社区电子农场（团农场），为自己身边的邻居、同事或者同学提供自己农场展示的产品，按照您发起的团购时间，按时接收并分发成员订单，作为农场主的您享受一定回报，让大家在亲密的分享关系中享受到新鲜便宜的健康水果，同时享受5%销售额返利。
                    </p>
                    <p class="title">农场成员描述</p>
                    <p>
                	     您根据自己所在工作或学习的地址搜索附近的农场主，根据他们农场主页面的商品及分发时间等信息，可选择加入并购买，共同享受水果团购的低价优质的益处。
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
                        <input type="text" value=""  placeholder="输入您的地址，发现您周围的水果团购" id="searchTxt2" name="searchTxt2"/><a href="javascript:void(0);" title="搜索" onclick="searchMap2()"></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <div style="display:none;">
    	<p>怎样挑选水果保证新鲜营养呢?</p>
    	<p>好的水果一般都是中等大小的。太小的可能是发育不良，就算发育良好品质也很不均匀，而且偏酸的多。太大的由于营养过剩，可能有些不该长的地方也长过头了，这就降低了可食用率：比如大柚子内部的木质化;有时水果个头出奇的大是因为水热条件好而生长过于迅速，这可能导致积累不足，味道偏淡，或者根本就是空心的——这个现象在草莓身上十分常见
    	挑那些最接近植物分类学描述的水果肯定是没错的，这至少证明了它发育良好。有俗谚云“歪瓜咧枣才甜”，不可否认虫蛀的果子(尤其是桃子)由于代谢旺盛、积累多，是比正常的果子甜，但是绝大多数的“歪瓜咧枣”是发育不均匀，部分没有长开(或者说长僵了)——这样的果子相当于有半个没熟，能好吃吗?而且上面有虫眼的果子通常也不会有人要
    	好的水果一定是色泽均匀的。看过《白雪公主》的人应该知道，恶毒的王后是用什么闹死了白雪公主?对，是半红半白的苹果!……扯远了。其实我的意思是说，水果上出现差别太大的两种颜色(比如深红和深绿)，证明这个果子的成长历程一定充满了艰辛，这样的果子吃起来不是偏酸就是偏涩，甚至有可能甜过头。以桃子为例，半熟的桃子尖是红的，下面都是绿的，绿的部分吃起来味同嚼蜡，红的部分相对它的颜色也令人失望;而成熟的桃子有时甚至全身都是白的(水蜜桃)，但是绝对味甜多汁。另外有一种叫“红花桃”的本地品种，果肉是红的，偏涩但回味极佳——
    	这个品种有时完全成熟了也会半红半绿，这个样子的红花桃吃起来味道和磺胺相去不远。均匀的前提下，就是颜色要浅，尤其是绿色要浅，因为水果的成熟伴随着叶绿素的降解。触感上主要针对一些需要去皮的水果，比如柑橘类和西瓜。过于粗糙的表面代表着皮厚，因为粗糙是果皮组织增生所致，体现在柑橘上就是油泡大而突出，西瓜就是表面坑坑洼洼(所谓“癞瓜”)——都增生了你说能不厚吗?不过过于光滑也不好，这说明果子可能还没成熟，比如柚子，表面溜光水滑的通常都能把牙酸掉。最后要提醒各位，癞瓜(不是歪瓜)往往是沙瓤而甜的，不要因为外表难看就遗弃人家哦，再说西瓜皮或凉拌或炒都是挺好吃的。
    	挑大型水果的时候按一下是很有用的(不要去按榴莲……)。对于柚子，如果按下去感觉深不见底(皮厚)或者手指生疼(生的)，最好还是还给水果店老板;如果感觉软但是很快就有比较硬的东西阻止你按下去的感觉，那么恭喜你，这个柚子皮很薄。对于西瓜，生的是按不动的，一按一个坑的是熟过头的，那种弹性十足的瓜通常能满足你的要求。此外对于较小型的水果也可以按一下，因为在水果成熟过程中伴随着果酸和果胶的降解，硬度和弹性都是会下降的，根据这个可以选择你所需要的口味和口感，比如脆苹果和面苹果。
    	我们可以保证我们所提供的水果的新鲜和质量的优等！
    	</p>
    </div>
    
    <%@include file="/common/foot.jsp" %>
    
     <div id="wgt-exp-share" class="wgt-exp-share" style="position: fixed; display: block; top: 300px; right: 25px;">
    	关注微信
	</div>
	
	<div id="wgt-exp-share-qrcode" class="wgt-exp-share-qrcode" >
    	<img src="${basePath}/images/qrcode_for_tuannong.jpg" title="关注团农微信公众号" width="320px" height="320px" />
    	<div class="floatweixincode">团农微信(微信中长按可自动识别)</div>
	</div>
</body>
</html>
