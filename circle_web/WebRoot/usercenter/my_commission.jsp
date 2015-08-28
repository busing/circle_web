<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="http://www.xwtech.com/widget" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>我的佣金</title>
     <link href="${basePath }/css/all.css" rel="stylesheet" type="text/css" />
	<link href="${basePath}/css/base.css" rel="stylesheet"  type="text/css"/>
    <script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
    <script type="text/javascript">
    var message="${message}";
    $(document).ready(function(){
		$(".tabs li").live("click",function(){
			window.location.href=$(this).attr("link");
		});
		
		$(".apply").live("click",function(){
			$("#extractShade").show();
			$("#extract").show();
		});
		
		if(message!="")
		{
			alert(message);
		}
	}); 	
    
    function close()
    {
    	$("#extractShade").hide();
		$("#extract").hide();
    }
    
    function addExtractApp()
    {
    	var commission=$("input[name='extractCommission']").val();
    	var account=$("input[name='alipayAccount']").val();
    	var name=$("input[name='alipayName']").val();
    	$.ajax({
    		url:'${basePath}/commission/addExtractApp.action',
    		dataType:'json',
    		type:'post',
    		data:{extractCommission:commission,alipayAccount:account,alipayName:name},
    		success:function(result){
    			alert(result.message);
    			if(result.flag)
   				{
    				window.location.href=window.self.location.href;
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
        <div class="ucenter">
	        <div class="title">
	          	  我的佣金
            </div>
            <%@ include file="/usercenter/menu.jsp" %> 
            <div class="uc_r">
            <div class="yj">
                <div class="mtj">
                    <ul class="tabs">
                        <li link="${basePath}/usercenter/myCommossion.action" class="selected">我的佣金</li>
                        <li link="${basePath}/commission/queryCommissionHistory.action" >查看历史</li>
                    </ul>
                    <div class="stat">
                        <div class="tit">
                            <span class="red">佣金总额：${userCommission }元</span>
                            <button type="button" class="apply">申请提现</button>
                        </div>
                        <p class="intro">邀请佣金：${inviteCommission }元&nbsp;&nbsp;|&nbsp;&nbsp;农场售卖佣金：${sellerCommission }元</p>
                    </div>
                    <div class="ph">
                        <div class="phhid">
                            <div class="item total">
                                <div class="tit">
                          		          佣金总排行榜  
                                </div>
                                <ul class="list">
                                	<c:forEach var="c" items="${commissionTop}">
                                    <li>
                                        <div class="name">
                                            <img src="${c.head_image }" /><a href="#">${c.name }</a></div>
                                        <div class="money">${c.commission }元</div>
                                    </li>
                                   </c:forEach>
                                </ul>
                            </div>
                            <div class="item push">
                                <div class="tit">
                                 	  推广佣金排行榜  
                                </div>
                                <ul class="list">
                                    <c:forEach var="c" items="${inviteCommissionTop}">
                                    <li>
                                        <div class="name">
                                            <img src="${c.head_image }" /><a href="#">${c.name }</a></div>
                                        <div class="money">${c.commission }元</div>
                                    </li>
                                   </c:forEach>
                                </ul>
                            </div>
                            <div class="item sale">
                                <div class="tit">
                     			               售卖佣金排行榜    
                                </div>
                                <ul class="list">
                                    <c:forEach var="c" items="${sellerCommissionTop}">
                                    <li>
                                        <div class="name">
                                            <img src="${c.head_image }" /><a href="#">${c.name }</a></div>
                                        <div class="money">${c.commission }元</div>
                                    </li>
                                   </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
     </div>
    </div>
    
    
    <!-- 弹出 -->
	<div id="extractShade" class="mask" style="display: none;"></div>
	<div id="extract" class="popbox reg" style="width: 630px; height: 300px; margin-left: -353px; display: none;padding-top: 10px;">
		<div class="close">
			<a href="javascript:close();"></a>
		</div>
		<div class="form" style="margin-left: 60px;">
			<div class="field">
				<p style="width: 105px;">
					提现金额:
				</p>
				<div>
					<input type="text" placeholder="提现金额(元),提现金额需大于10元" id="extractCommission" name="extractCommission" class="allInput" />
				</div>
			</div>
			<div class="field">
				<p style="width: 105px;">
					支付宝账户:
				</p>
				<div>
					<input type="text" placeholder="提现的支付宝账户" id="alipayAccount" name="alipayAccount" class="allInput" />
				</div>
			</div>
			<div class="field">
				<p style="width: 105px;">
					支付宝姓名:
				</p>
				<div>
					<input type="text" placeholder="提现的支付宝账户对应的姓名" id="alipayName" name="alipayName" class="allInput" />
				</div>
			</div>
			<div class="field">
				<p>
					&nbsp;
				</p>
				<div>
					<button type="button" class="btn2" style="width: 173px;margin-left: 60px;" onclick="addExtractApp()">
						提交
					</button>
				</div>
			</div>
		</div>
	</div>
    <%@ include file="/common/foot.jsp"%>  
</body>
</html>
