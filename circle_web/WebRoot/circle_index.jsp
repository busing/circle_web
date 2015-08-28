<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>团农场首页  ${circle.name }</title>
<link href="${basePath}/css/base.css?v=2015050301" rel="stylesheet" />
<link href="${basePath}/css/common.css?v=2015051401" rel="stylesheet" />
<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${basePath}/js/common/datepicker/WdatePicker.js"></script>
<script>
var circleId="${circle.id}";
var msg="${msg}";
var batchId="${batchSell.id}";
var remainderTimer;
$(document).ready(function(){
	var intDiff = parseInt("${batchSell.remainderSeconds}");//倒计时总秒数量
	//开始倒计时
	timer(intDiff);
	$("#good_list").load(function(){
		var mainheight = $(this).contents().find("body").height()+30;
		//alert($(this).contents().find("body").scrollHeight());
		$(this).height(mainheight);
	});
	
	$(".search").live("click",function(){
		var keyWord=$("#keyWord").val();
		window.location.href="${basePath}/circle/circleIndex.action?circleId=${circle.id}&searchParam[keyWord]="+encodeURIComponent(keyWord);
		//$("#good_list").attr("src","${basePath}/good/goodList.action?circleId=${circle.id}&searchParam[keyWord]="+keyWord);
		//$("#good_list").load();
	});
	
	$("#wgt-exp-share").live("click",function(){
		console.log($(".wgt-exp-share-qrcode").css("display"));
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
	
	if(msg!="")
	{
		ShowBox(msg,2);
	}
	
});

function timer(intDiff){
	window.setInterval(function(){
	var day=0,
		hour=0,
		minute=0,
		second=0;//时间默认值		
	if(intDiff > 0){
		day = Math.floor(intDiff / (60 * 60 * 24));
		hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
		minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
		second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
	}
	else
	{
		window.clearInterval(remainderTimer);
		disableButton($(".buy"));
		disableButton($(".cart"));
	}
	if (minute <= 9) minute = '0' + minute;
	if (second <= 9) second = '0' + second;
	$('#day_show').html(day+"天");
	$('#hour_show').html('<s id="h"></s>'+hour+'时');
	$('#minute_show').html('<s></s>'+minute+'分');
	$('#second_show').html('<s></s>'+second+'秒');
		intDiff--;
	}, 1000);
} 


function disableButton(but)
{
	var $but=$(but);
	$but.attr("disabled","disabled");
}

function enableButton(but)
{
	var $but=$(but);
	$but.removeAttr("disabled");
}

function joinFarm()
{
	$.ajax({
		url:basePath+'/circle/joinCircle.action?circleId=${circle.id}',
		dataType:'json',
		success:function(result){
			//0加入失败    1加入成功	2未登陆
			if(result.msg == "")
			{
				$(".mask").show();
				$(".login").show();
				$("#viewId").val("1");
			}else if(result.msg=="success"){
				getMyCircle();
				$("#alert_pop").show();
					$(".alert_btncon2").show();
					$("#message").html("加入农场成功，请继续选购商品。");
			    	$(".alert_btncon2").find(".alert_ok").attr('onclick','').click( eval(function(){
				        $("#alert_pop").hide();
				        $(".alert_btncon2").hide();
				        $("#titleTop").html("");
				        $("#message").html("")
			    }));
			}else{
				alert(result.msg);
			}
		}
	});
}


function publishCircle(cid,issueTime,issueAddress){
	$("#issueTime").val(issueTime);
	$("#issueAddress").val(issueAddress);
	$("#id").val(cid);
	$("#divMask").css("display", "block");
	$("#divCirclePublish").css("display", "block");
}


function closeDiv(id){
	$("#divMask").css("display", "none");
	$("#" + id).css("display", "none");
}


function saveCirclePublishInfo(){
	$.ajax({
		url: "${basePath }/usercenter/saveCirclePublishInfo.action",
		type: "post",
		dataType: "text",
		data: $("#circleEditFrm").serialize(),
		success: function(res){
			if(res && res == 1){
				ShowBox("发布成功",1,true);
				closeDiv("divCirclePublish");
				window.location.href=self.location.href;
			}else
				ShowBox("发布失败,请检查",3);
		}
	});
}

</script>
</head>
<body>
    <%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="userinfo">
                <div class="uc" style="display: none;">
                    <div class="icon">
                        <a href="#">
                            <img src="${circle.headPath }" />
                        </a>
                    </div>
                    <div class="name">
                        <div class="ucuser"><a href="javaScript:void(0)">${circle.name }<i><img src="${basePath}/images/heart.png" /></i></a></div>
                        （心形符号代表自己创建的农场）
                    </div>
                    <div class="link">
                        <a href="#"><i></i>查看农场资料</a>
                    </div>
                </div>
                <div class="info">
                    <p class="i1" style="width: 328px;">离特卖结束还有：
                    	<span id="day_show">0天</span><span id="hour_show"><s id="h"></s>0时</span><span id="minute_show"><s></s>00分</span><span id="second_show"><s></s>00秒</span>
                    </p>
                    <c:if test="${isOutDate}">
                   		 <p class="i2">货物发放时间、地点：已过期,等待农场主发布</p>
                   		 <c:if test="${isCircleAdmin }">
                   			 <p style="float: right;padding-right: 5px; cursor: pointer;" onclick="publishCircle(${circle.id},'${circle.issueTime }','${circle.issueAddress }');" >发布</p>
                   		 </c:if>
                    </c:if>
                    
                    <c:if test="${!isOutDate}">
                    	<p class="i2">货物发放时间、地点：${circle.issueTime },${circle.issueAddress}</p>
                    </c:if>
                </div>
            </div>
            <div class="seartit">
                <div class="sear_l">
               		   商品展示
                </div>
                <div class="sear_r">
                    <div class="search"><a href="javaScript:void(0);"></a></div>
                    <div class="ipt"><input type="text" value="${page.searchParam.keyWord}" id="keyWord" placeholder="搜索您想要的商品" /></div>
                </div>
            </div>
            <%@ include file="/good_list.jsp"%> 
            <%-- <iframe name="good_list" id="good_list" src="${basePath}/good/goodList.action?circleId=${circle.id}" height="100%" width="100%"  scrolling="no"></iframe> --%>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
    
    <!-- 加入购物车提示 --> 
   <div class="mask" style="display: none;"></div>
   <div class="popbox joincart" style="width: 681px; height: 117px; margin-left: -349px;display: none;position: fixed;">
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
        <div class="close"><a href="javaScript:void(0);"></a></div>
    </div>
   
    <!-- end of 加入购物车提示 --> 
    
    
    <!--发布时间地点-->
    <form id="circleEditFrm" name="circleEditFrm" method="post" action="">
	<input type="hidden" id="id" name="id">
	<div id="divCirclePublish" class="popbox publishtime" style="width: 654px; height: 236px; margin-left: -327px; margin-top: 130px; display: none;">
		<div class="items">
			<div class="item">
				<!-- <i class="time"></i> -->
				<span style="color: #867979;font-size: 16px;">发放时间：</span>
				<input style="border: solid 1px #ebebeb;height:38px;width: 455px;font-size: 16px;" id="issueTime" name="issueTime" class="Wdate" readonly="readonly" onclick="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m'})"/>
			</div>
			<div class="item">
				<!-- <i class="man"></i> -->
				<span style="color: #867979;font-size: 16px;">发放地点：</span>
				<input style="border: solid 1px #ebebeb;height:38px;width: 455px;font-size: 16px;" id="issueAddress" name="issueAddress"/>
			</div>
			<div class="btns">
				<input type="button" value="发布" class="btnred" onclick="saveCirclePublishInfo()" />
			</div>
		</div>
		<div class="close">
			<a href="javascript:closeDiv('divCirclePublish');"></a>
		</div>
	</div>
	</form>
			
    
    
    <%-- <c:if test="${circle.weixinImage ne null &&  circle.weixinImage ne ''}">
    <div class="floatweixin">
    	<img src="${circle.weixinImage }" title="微信号：${circle.weixinName }" width="320px" height="320px" />
    	<div class="floatweixincode">农场主微信</div>
    </div>
    </c:if> --%>
    
    <c:if test="${circle.weixinImage ne null &&  circle.weixinImage ne ''}">
    <div id="wgt-exp-share" class="wgt-exp-share" style="position: fixed; display: block; top: 300px; right: 25px;">
    	关注微信
	</div>
	
	<div id="wgt-exp-share-qrcode" class="wgt-exp-share-qrcode" >
    	<img src="${circle.weixinImage }" title="微信号：${circle.weixinName }" width="320px" height="320px" />
    	<div class="floatweixincode">农场主微信(微信中长按可自动识别)</div>
	</div>
	</c:if>
	
	<%-- <c:if test="${!isMember }">
	<div class="wgt-exp-share" style="position: fixed; display: block; top: 360px; right: 25px;" onclick="joinFarm();">
    	加入农场
	</div>
	</c:if> --%>
<!--弹出框开始-->
<div id="alert_pop" style="display:none;font-size:18px;">
	<div class="alert_box">
		<p class="alert_top" id="titleTop">提示信息</p>
		<p class="alert_tip" id="message">
			
		</p>
		<div class="alert_btncon" style="display:none;">
			<a href="javascript:void(0)" class="alert_btn alert_ok">立即加入</a>
			<a href="javascript:void(0)" class="alert_btn alert_cancel">取消</a>
		</div>
		<div class="alert_btncon2" style="display:none">
			<a href="javascript:void(0)" class="alert_btn alert_ok">确定</a>
		</div>
	</div>
</div>
<!--弹出框结束-->
</body>
</html>
