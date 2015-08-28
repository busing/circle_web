<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<meta name="viewport" content="width=1028">
<div class="header">
	<div class="center">
		<div class="h_left">
			<a href="${basePath}"> <img src="${basePath }/images/logo.png" alt="团农场" />
			</a>
		</div>
		<c:if test="${user eq null }">
			<div class="h_right">
				<span class="txt"><i class="phone"></i>
				<!-- 订购热线 <span class="i">15051885903</span>- -->
					<span class="b i">15051885903</span>
				</span>
				<a href="javaScript:void(0);" class="link" onclick="showReg();">注册</a>
				<a href="javaScript:void(0);" class="link" onclick="showLogin();">登录</a>
			</div>
		</c:if>
		<c:if test="${user ne null }">
			<div class="h_right">
				<div class="btns">
					<ul>
						<li class="user">
							<a href="${basePath }/usercenter/userCenter.action"><i></i>个人中心</a>
						</li>
						<li class="cart">
							<a href="${basePath }/usercenter/shopcart.action"><i></i><b
								class="cartnum"></b>购物车</a>
						</li>
						<li class="message" style="width: 17px; margin-left: 0px;">
							<a href="${basePath }/usercenter/messageCenter.action"><i></i>
							</a>
						</li>
					</ul>
					<span style="float: left; padding-top: 20px;"></span>
					<span style="float: left; padding-top: 26px; margin-left: 19px; cursor: pointer;">
						<a href="${basePath }/login/logout.action">退出</a>
					</span>
				</div>
				<!-- 当前圈子购买人数 -->
				<c:if test="${buyNum ne null}">
					<div class="buy">
						<div class="top">
							<span>${buyNum }</span>人
						</div>
						<div class="bottom">
							已购买
						</div>
					</div>
				</c:if>
				<!-- end of 当前圈子购买人数 -->

				<!-- 我的圈子 -->
				<div class="icon">
					<!--  -->
					<div class="flag">
						<div class="downbox">

						</div>
					</div>
					<!-- 非圈子首页 -->
					<c:if test="${circle eq null }">
						<div class="username">
							<a href="javaScript:void(0)">${user.name}</a>
						</div>
						<div class="user">
							<img style="width: 35px; height: 35px;" src="${user.headImage}" />
						</div>
					</c:if>

					<!-- 圈子首页 -->
					<c:if test="${circle ne null }">
						<div class="username">
							<a href="${basePath}/circle/circleIndex.action?circleId=${circle.id}">
								<span>${circle.name } </span>
								<!-- 如果是我创建的 显示爱心 -->
								<c:if test="${circle.createUserId eq user.id }">
									<i>
										<img src="${basePath}/images/heart.png" />
									</i>
								</c:if>
							</a>
						</div>
						<div class="user">
							<img style="width: 35px; height: 35px;" src="${circle.headPath}" />
						</div>
					</c:if>
				</div>
			</div>
		</c:if>
		<!-- 登录用户 -->
	</div>
</div>

<!-- 登陆注册 -->
<div class="mask" style="display: none;"></div>
<div class="popbox login" style="display: none;">
	<div class="logo">
		<img src="${basePath}/images/logo2.png" />
	</div>
	<div class="close">
		<a href="javascript:void(0);"></a>
	</div>
	<form id="contentForm" name="contentForm" method="post" action="">
		<div class="form">
			<div class="field">
				<p>
					账&nbsp;&nbsp;&nbsp;&nbsp;号
				</p>
				<div>
					<input type="hidden" id="viewId" name="viewId" />
					<input type="hidden" id="pId" name="pId" />
					<input type="hidden" id="cId" name="cId" />
					<input type="text" placeholder="手机号码" id="mobilePhone" name="mobilePhone"
						onkeydown="if(event.keyCode == 13) event.keyCode = 9;" datatype="m" nullmsg="请填写手机号码" errormsg="请输入正确的手机号码" />
				</div>
			</div>
			<div class="field">
				<p>
					密&nbsp;&nbsp;&nbsp;&nbsp;码
				</p>
				<div>
					<input type="password" placeholder="密码" id="password" name="password" onkeydown="if(event.keyCode == 13) login()"
						datatype="s6-12" nullmsg="密码不能为空" errormsg="请输入6-12位的密码" />
				</div>
			</div>
			<div class="field">
				<p>
					&nbsp;
				</p>
				<div>
					<button id="btnLogin" type="button" class="btn2" style="width: 173px;" onclick="login()">
						登&nbsp;录
					</button>
					<button type="button" class="btn1" style="width: 118px;" name="toLogin">
						注册
					</button>
				</div>
				<p>
					&nbsp;
				</p>
				<div class="forgetPass">
					<span>
						<a href="javaScript:void(0);" style="color: red; padding-right: 31px;"
							onclick="showUpdatePass();">
							忘记密码
						</a>
					</span>
				</div>
			</div>
		</div>
	</form>
</div>
<form id="regForm">
	<div class="popbox reg"
		style="width: 706px; height: 660px; margin-left: -353px; display: none;">
		<div class="logo">
			<a href="javascript:void(0);">
				<img src="${basePath}/images/logo2.png" />
			</a>
		</div>
		<div class="close">
			<a href="javascript:void(0);"></a>
		</div>
		<div class="intro" style="text-align: center;">
			注册就送一个苹果，创建农场，更多惊喜！
		</div>
		<div class="form">
			<div class="field">
				<p>
					手机号
				</p>
				<div>
					<input type="text" placeholder="请输入您的手机号" id="mobilePhone"
						name="mobilePhone" datatype="m" nullmsg="请填写手机号码" errormsg="请输入正确的手机号码" />
				</div>
			</div>
			<div class="field">
				<p>
					验证码
				</p>
				<div>
					<input type="hidden" id="vCode" name="vCode" />
					<input type="text" placeholder="请输入您刚刚收到短信验证码" id="verificationCode" name="verificationCode" datatype="n6-6"
						nullmsg="请填写验证码" errormsg="请输入正确的验证码" readonly="readonly" />
				</div>
			</div>
			<div class="field">
				<p>
					姓名
				</p>
				<div>
					<input type="text" placeholder="请输入您的姓名" id="name" name="name" datatype="s2-6" nullmsg="用户名不能为空" errormsg="请输入正确的姓名" />
				</div>
			</div>
			<div class="field">
				<p>
					密&nbsp;&nbsp;&nbsp;&nbsp;码
				</p>
				<div>
					<input type="password" placeholder="至少6个字符" id="password"
						name="password" datatype="s6-12" nullmsg="密码不能为空" errormsg="请输入6-12位的密码" />
				</div>
			</div>
			<div class="field">
				<p>
					重复密码
				</p>
				<div>
					<input type="password" placeholder="至少6个字符" id="repassword"
						name="repassword" datatype="s6-12" nullmsg="密码不能为空" errormsg="两次密码不一致" recheck="password" />
				</div>
			</div>
			<div class="field">
				<p>
					邀请码
				</p>
				<div>
					<input type="text" placeholder="请输入邀请码(选填)" id="inviteCode" name="inviteCode" />
				</div>
			</div>
			<div class="field">
				<p>
					&nbsp;
				</p>
				<div>
					<button type="button" id="regButtom" class="btn2" style="width: 173px;">
						提交
					</button>
				</div>
			</div>
		</div>
		<div style="float: left; padding-top: 17px;">
			<button type="button" class="btn2" style="width: 84px; font-size: 2px;" name="sendMsg" id="sendMsg">
				<font>发送验证码</font>
			</button>
		</div>
	</div>
</form>

<form id="updatePassForm">
	<div class="popbox updatePass"
		style="width: 706px; height: 520px; margin-left: -353px; display: none;">
		<div class="logo">
			<a href="javascript:void(0);">
				<img src="${basePath}/images/logo2.png" />
			</a>
		</div>
		<div class="close">
			<a href="javascript:void(0);"></a>
		</div>
		<div class="form">
			<div class="field">
				<p>
					手机号
				</p>
				<div>
					<input type="text" placeholder="请输入您的手机号" id="mobilePhone"
						name="mobilePhone" datatype="m" nullmsg="请填写手机号码" errormsg="请输入正确的手机号码" />
				</div>
			</div>
			<div class="field">
				<p>
					验证码
				</p>
				<div>
					<input type="text" placeholder="请输入您刚刚收到短信验证码" id="verificationCode" name="verificationCode" datatype="n6-6"
						nullmsg="请填写验证码" errormsg="请输入正确的验证码" readonly="readonly" />
				</div>
			</div>
			<div class="field">
				<p>
					输入密码
				</p>
				<div>
					<input type="password" placeholder="至少6个字符" id="password"
						name="password" datatype="s6-12" nullmsg="密码不能为空" errormsg="请输入6-12位的密码" />
				</div>
			</div>
			<div class="field">
				<p>
					重复密码
				</p>
				<div>
					<input type="password" placeholder="至少6个字符" id="repassword"
						name="repassword" datatype="s6-12" nullmsg="密码不能为空" errormsg="两次密码不一致" recheck="password" />
				</div>
			</div>
			<div class="field">
				<p>
					&nbsp;
				</p>
				<div>
					<button type="button" id="updatePassButtom" class="btn2" style="width: 173px;">
						提交
					</button>
				</div>
			</div>
		</div>
		<div style="float: left; padding-top: 17px;">
			<button type="button" class="btn2" style="width: 84px; font-size: 2px;" name="sendUpdatePassMsg" id="sendUpdatePassMsg">
				<font>发送验证码</font>
			</button>
		</div>
	</div>
</form>

<div id="success" class="tc" style="display: none;">
	<div>
		<h2>
			<img src="${basePath}/images/logo2.png" />
		</h2>
		<p>
			注册成功,现在您可以创建农场或者加入您附近的农场购物了!
		</p>
		<h5>
			<a href="javaScript:void(0);"><span>我知道了</span>
			</a>
		</h5>
	</div>
</div>
<!-- 登陆注册 -->
<script type="text/javascript" src="${basePath}/js/login.js"></script>
<script type="text/javascript" src="${basePath}/js/circle.js"></script>
<script type="text/javascript" src="${basePath}/js/contextMenu.js"></script>
<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
<script>
	var basePath="${basePath}";
	var userId="${user.id}";
	
	 var jq$ = jQuery.noConflict(); //防止$符号与其他的冲突
     jq$(document).ready(function () {   //防止在DOM元素加载完成就执行jQuery代码，从而避免产生不必要的错误
         jq$("*").keydown(function (e) {//判断按键
             e = window.event || e || e.which;
             if (e.keyCode == 112 || e.keyCode == 123 || e.keyCode ==93) {
                 e.keyCode = 0;
                 return false;
             }
         });
         //document.onhelp = function () { return false };//
         window.onhelp = function () { return false };//ie下面不能屏蔽f1键的补充方法,和上面的一行的效果是一样的，选其一
     });
	 
	//查询出我的圈子
	function getMyCircle(){
	 	var userId="${user.id}";
	 	var html=new Array();
	 	$.ajax({
	 		url:"${basePath}/circle/getMyCircle.action",
	 		type:'post',
	 		dataType:'json',
	 		success:function(result){
	 			if(result!=""){
	 				html.push("<ul>");
	 				for(var i in result){
	 					html.push("<li>");
	 					html.push("<a href=\"${basePath}/circle/circleIndex.action?circleId="+result[i].id+"\"><img src=\""+result[i].headPath+"\" class=\"f\" /><span>"+result[i].name+"</span>");
	 					html.push("<i>");
	 					if(userId==result[i].createUserId)
	 						html.push("<img src=\"${basePath}/images/heart.png\" />");
	 					html.push("</i>");
	 					html.push("</a>");
	 					html.push("</li>");
	 				}
	 				html.push("</ul>");
	 				$(".downbox").empty().append(html.join(""));
	 			}
	 		}
	 	});
	 }
	
//查询出我消息总数
	function getMsgCount(){
	 	$.ajax({
	 		url:"${basePath}/msg/getmsgcount.action",
	 		type:'post',
	 		dataType:'json',
	 		success:function(result){
	 			if(result)
	 				$(".btns span").eq(0).html(result);
	 		}
	 	});
	}
	
	//查询出我的购物车总数
	function getShopCartCount()
	{
		if("${user.id}"=="")
		{
			return;
		}
	 	$.ajax({
	 		url:"${basePath}/shopcart/getShopCartCount.action",
	 		type:'post',
	 		dataType:'json',
	 		success:function(result){
	 			if(result)
	 			{
					$(".cartnum").empty().html(result.count);
	 			}
	 		}
	 	});
	}
	
	var inviteCode = "<%= request.getParameter("vcode") == null ? "" : request.getParameter("vcode") %>";
	var flag = ${user == null };
	if(inviteCode && flag){
		showReg();
		$("#inviteCode").val(inviteCode);
	}
</script>

