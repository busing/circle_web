<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
<title>${good.title} </title>
<link href="${basePath}/css/base.css" rel="stylesheet" />
<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${basePath}/js/common/downtop.js" ></script>
<script type="text/javascript">
var batchId="${batchSell==null?0:batchSell.id}";
$(document).ready(function(){
	$(".imageChoose").live("click",function(){
		var imagePath=$(this).attr("src");
		$(".proimg img").attr("src",imagePath);
		$(".prolist li").removeClass("selected");
		$(this).parent().addClass("selected");
	});
	
	$(".nav li").live("click",function(){
		$(".nav li").removeClass("selected");
		$(this).addClass("selected");
		$(".prodetail").hide();
		$(".comments").hide();
		var code=$(this).attr("code");
		$("."+code).show();
	});
	
	$("#comments").load(function(){
		var mainheight = $(this).contents().find("body").height()+30;
		//alert($(this).contents().find("body").scrollHeight);
		//$(this).height(mainheight);
	});
	
	//加入购物车事件
	$(".cart").live("click",function(){
		if("${user.id}"=="")
		{
			showLogin();
			return;
		}
		var buyNum=$("input[name='goodNum']").val();
		var goodId='${good.id}';
		var circleId='${circleId}';
		var unitId='${good.unit}';
		$.ajax({
			url:'${basePath}/shopcart/addShopCart.action?buyNum='+buyNum+"&goodId="+goodId+"&circleId="+circleId+"&unitId="+unitId+"&batchId="+batchId,
			dataType:'json',
			success:function(result){
				if(result.flag)
				{
					getShopCartCount();
					$(".mask").fadeIn(100);
					$(".cart").fadeIn(100);
					$(".mask").fadeOut(3000);
					$(".cart").fadeOut(3000);
				}else{
					$(".mask").fadeIn(100);
					$(".circle").fadeIn(100);
					$(".mask").fadeOut(30000);
					$(".circle").fadeOut(30000);
				}
			}
		});
	});
	// end of 加入购物车事件
	
	
	//立即购买事件
	$(".btnbuy").live("click",function(){
		if("${user.id}"=="")
		{
			showLogin();
			return;
		}
		var buyNum=$("input[name='goodNum']").val();
		var goodId='${good.id}';
		var circleId='${circleId}';
		window.location.href='${basePath}/order/addOrder.action?buyNum='+buyNum+"&goodId="+goodId+"&circleId="+circleId;
	});
	// end of 立即购买事件
	
	//继续购物按钮事件
	$(".more").live("click",function(){
		$(".mask").hide();
		$(".joincart").hide();
	});
	
	//关闭加入购物车提醒时间
	$(".close").live("click",function(){
		$(".mask").hide();
		$(".joincart").hide();
	});
	
});

var jiathis_config = { 
		url:"www.comefarm.com", 
		title:"${good.title}", 
		summary:"${good.intro}",
		pic:"${good.image}"
} ;


function joinFarm()
{
	var circleId='${circleId}';
	$.ajax({
		url:'${basePath}/circle/joinCircle.action?circleId='+circleId,
		dataType:'json',
		success:function(result){
			//0加入失败    1加入成功	2未登陆
			if(result.msg=="success"){
				ShowBox("加入成功",1);
				location.reload();
			}else{
				showLogin();
				return;
			}
		}
	});
}
	
</script>
</head>
<body>
    <%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="prod">
                <div class="prol">
                    <div class="proimg">
                        <img src="${good.image }" />
                        <div class="open"><i></i></div>
                    </div>
                    <ul class="prolist">
                    	<c:forEach items="${image_list}" var="img">
                    		<c:if test="${img.path eq good.image}">
                    			<li class="selected"><img src="${img.path}" class="imageChoose" /></li>
                    		</c:if>
                    		<c:if test="${img.path ne good.image}">
                    			 <li><img src="${img.path}" class="imageChoose"/></li>
                    		</c:if>
                    	</c:forEach>
                    </ul>
                </div>
                <div class="pror">
                    <div class="title">${good.title }</div>
                    <div class="pricediv">
                        <!-- <p>优惠&nbsp;&nbsp;满50元,送1颗猕猴桃（赠完即止）</p> -->
                        <p>
                       		价格&nbsp;&nbsp;<span class="rmb">￥</span><span class="price">${good.sell_price }</span>
                        	<span style="text-decoration: line-through;">&nbsp;&nbsp;原价：${good.original_price }<span class="red">元</span></span>
                        </p>
                    </div>
                    <div class="item">
                    	单位&nbsp;&nbsp;${good.unit_str }
                    	<c:if test="${good.unit_intro ne null && good.unit_intro ne ''}"><span style="padding-left: 15px;">${good.unit_intro}</span></c:if>
                    	
                   	</div>
                    <div class="sale">
                        <ul>
                            <li class="i">月销量&nbsp;<span class="red">${good.buy_num }</span></li>
                            <li class="f"></li>
                            <li class="i">累计评价&nbsp;<span class="red">${good.commonCount}</span></li>
                        </ul>
                    </div>
                    <div class="buy">
                        <div class="field">数量</div>
                        <input type="text" value="1" maxlength="2" name="goodNum"/><div class="td"><i class="top"></i><i class="down"></i></div>
                        <!-- <span class="st">库存12423件</span> -->
                    </div>
                    <div class="btns">
                        <button type="button" class="btnbuy">立刻购买</button>
                        <button type="button" class="cart"><i></i>加入购物车</button>
                        <!-- <span class="share"><a href="#"><i></i></a>分享有礼</span> -->
                         <!-- JiaThis Button BEGIN -->
						<div class="jiathis_style" style="padding-top: 12px">
							<a class="jiathis_button_qzone"></a>
							<a class="jiathis_button_tsina"></a>
							<a class="jiathis_button_tqq"></a>
							<a class="jiathis_button_weixin"></a>
							<a class="jiathis_button_renren"></a>
							<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
						</div>
						<!-- JiaThis Button END -->

                    </div>
                   <!--  <div class="item">
                        配送范围&nbsp;&nbsp;送货范围仅限江苏地区
                    </div> -->
                </div>
                <div class="detail">
                    <div class="nav">
                        <ul>
                            <li code="prodetail" class="selected"><a href="javaScript:void(0);">商品详情</a></li>
                            <li code="comments"><a href="javaScript:void(0);">累计评价<span class="green">${good.commonCount }</span></a></li>
                        </ul>
                    </div>
                    <div class="prodetail" style="display: block;">
                    	${good.description}
                    </div>
                    <div class="comments" style="display: none;height: auto;">
                   		<iframe id="comments" scrolling="no" src="${basePath}/good/commentList.action?searchParam[good_id]=${good.id}" width="100%"  height="300px"></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
   <div class="mask" style="display: none;"></div>
   <div class="popbox joincart cart" style="width: 681px; height: 117px; margin-left: -349px;display: none;position: fixed;">
        <div class="title">
            <i></i>
            <p class="succ">成功加入购物车！</p>
            <p>您可以<a href="${basePath}/usercenter/shopcart.action">去购物车结算</a></p>
            <div class="more"><a href="javascript:void(0);">继续购物</a></div>
        </div>
       <!--  <div class="prolist">
            <div class="l"><a href="#"></a></div>
            <div class="m">
                <ul>
                    <li>
                        <img src="images/temp_5.png" />
                        <div class="price"><span>￥18</span></div>
                    </li>
                </ul>
            </div>
            <div class="r"><a href="#"></a></div>
        </div> -->
        <div class="close"><a href="#"></a></div>
    </div>
    
    <div class="popbox joincart circle" style="width: 681px; height: 117px; margin-left: -349px;display: none;position: fixed;">
        <div class="title">
            <i></i>
            <p class="succ">您不是该农场成员，请先加入该农场！</p>
            <p>您可以<a href="javascript:joinFarm();">加入农场</a></p>
            <div class="more"><a href="javascript:void(0);">继续购物</a></div>
        </div>
        <div class="close"><a href="#"></a></div>
    </div>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
</body>
</html>
