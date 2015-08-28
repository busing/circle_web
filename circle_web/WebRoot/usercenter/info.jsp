<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>用户中心-个人资料</title>
		<link href="${basePath}/css/all.css" rel="stylesheet" type="text/css" />
		<link href="${basePath}/css/base.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${basePath}/js/common/component/uploadify/uploadify.css" />
		<style type="text/css">
			#upload_weixin{padding-top: 7px;padding-left: 15px;}
		</style>
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/component/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/common.js"></script>
		<script type="text/javascript" src="${basePath}/js/circle.js"></script>
		
		
		<script type="text/javascript">
	    	function edit(type){
				$("#curInfoPassword").val("");
	    		$("#infoPassword").val("");
	    		$("#reInfoPassword").val("");
	    		$("#mobilePhone").val("");
	    		$("#verificationCode").val("");
	    		$("#type").val(type);
	    		if(type == 0){
	    			$("#divShade").css("display","block");
	    			$("#divEdit").css("display","block");
	    			$("#divEdit").css("height","225");
	    			$("#divMobilePhone").css("display","block");
	    			$("#divVCode").css("display","block");
	    			$("#divSendCode").css("display","block");
	    			$("#divCurPassword").css("display","none");
	    			$("#divPassword").css("display","none");
	    			$("#divRepassword").css("display","none");
	    		}else{
	    			$("#divShade").css("display","block");
	    			$("#divEdit").css("display","block");
	    			$("#divEdit").css("height","292");
	    			$("#divMobilePhone").css("display","none");
	    			$("#divVCode").css("display","none");
	    			$("#divSendCode").css("display","none");
	    			$("#divCurPassword").css("display","block");
	    			$("#divPassword").css("display","block");
	    			$("#divRepassword").css("display","block");
	    		}
	    	}
	    	
	    	function close(){
	    		$("#divShade").css("display","none");
	   			$("#divEdit").css("display","none");
	    	}
	    	
	    	function clear(){
				$("#curInfoPassword").val("");
	    		$("#infoPassword").val("");
	    		$("#reInfoPassword").val("");
	    		$("#mobilePhone").val("");
	    		$("#verificationCode").val("");
	    	}
	    	
	    	function save(){
				$.ajax({
					url : "${basePath }/user/updateUserInfo.action",
					type : "post",
					dataType : "json",
					data : $("#editForm").serialize(),
					success:function(result){
						ShowBox(result == 1 ? "保存成功" : "保存失败");
						if(result == 1)
							window.location.href = window.location.href;
					}
				});
	    	}
	    	
	    	$(document).ready(function(){
				//初始化图片上传控件
				initUploadFileCom('upload_btn','${basePath}',{
					'fileObjName':'file',
					'fileTypeExts':'*.*',
					'fileSizeLimit':'10240',
					'multi':false,
					'uploader':'${basePath}/usercenter/uploadFile.action;jsessionid=${pageContext.session.id}',
					'onUploadSuccess':function(file,obj,response){
						var data = eval('(' + obj + ')'); // string 转为json对象
						$("#headImage").val(data.imgPath);
						$("#imgImagePath").attr("src", data.path);
					}
				});
				
				//初始化图片上传控件
				initUploadFileCom('upload_weixin','${basePath}',{
					'fileObjName':'file',
					'fileTypeExts':'*.*',
					'fileSizeLimit':'10240',
					'multi':false,
					'uploader':'${basePath}/usercenter/uploadFile.action;jsessionid=${pageContext.session.id}',
					'onUploadSuccess':function(file,obj,response){
						var data = eval('(' + obj + ')'); // string 转为json对象
						$("#weixinImage").val(data.imgPath);
						//$("#imgImagePath").attr("src", data.path);
					}
				});
				
				
			});
			
			function submitData(){
	    		var type = $("#type").val();
	    		if(type == 1){
					var curInfoPassword = $("#curInfoPassword").val();
					var infoPassword = $("#infoPassword").val();
					var reInfoPassword = $("#reInfoPassword").val();
					
					if(!curInfoPassword){
						ShowBox("请输入当前密码");
						$("#curInfoPassword").focus();
						return;
					}
					if(!infoPassword){
						ShowBox("请输入新密码");
						$("#infoPassword").focus();
						return;
					}
					if(!reInfoPassword){
						ShowBox("请输入确认密码");
						$("#reInfoPassword").focus();
						return;
					}
					if(infoPassword != reInfoPassword){
						ShowBox("两次输入密码不相同");
						$("#reInfoPassword").focus();
						return;
					}
					
					$.ajax({
						url : "${basePath }/user/updatePassword.action",
						type : "post",
						data : "curpassword=" + curInfoPassword + "&password=" + infoPassword,
						success:function(result){
							if(result == 0){
								ShowBox("当前密码输入不正确");
							}else if(result == 1){
								ShowBox("修改成功");
								close();
							}else
								ShowBox("修改失败");
						}
					});
				}else{
					var vInfoCode = $("#vInfoCode").val();
					var newMobilePhone = $("#newMobilePhone").val();
					
					if(!newMobilePhone && !mobilePhone){
						ShowBox("请填写新的手机号码");
						$("#newMobilePhone").focus();
						return;
					}
					if(!vInfoCode){
						ShowBox("请输入验证码");
						$("#vInfoCode").focus();
						return;
					}
					
					$.ajax({
						url : "${basePath }/user/updateMobilePhone.action",
						type : "post",
						data : "mobilePhone=" + mobilePhone + "&code=" + $("#vInfoCode").val(),
						success:function(result){
							if(result == 1){
								ShowBox("修改成功");
								$("#spanMobilePhone").html(mobilePhone);
								close();
							}else if(result == 2)
								ShowBox("验证码已过期,请重新发送");
							else if(result == 3)
								ShowBox("验证码错误");
							else
								ShowBox("修改失败");
						}
					});
				}
			}
			
			var num = 60;
			var mobilePhone = "";
			function sendCode(){
				var newMobilePhone = $("#newMobilePhone").val();
				if(!newMobilePhone){
					ShowBox("请填写新的手机号码");
					$("#newMobilePhone").focus();
					return;
				}
				if(!/^1[3|5|7|8|][0-9]{9}/.test(newMobilePhone)){
					ShowBox("请填写正确的手机号码");
					$("#newMobilePhone").focus();
					return;
				}
				
				time();
				mobilePhone = newMobilePhone;
				$.ajax({
					url:"${basePath }/user/sendValidateMsg.action",
					data:"mobilePhone=" + $("#newMobilePhone").val(),
					dataType:"text",
					success:function(res){
						if(res == "1"){
							ShowBox("发送短信失败!");
							num = 0;
							return;
						}else if(res == "0"){
							ShowBox("该手机号码已被注册,请更换新号码!");
							num = 0;
							return;
						}else{
							ShowBox("验证码已发送至手机,请注意查收!");
						}
					}
				});
			}
			
			function time(){
				num--;
				$('#sendMessage').attr("disabled", "disabled");
				$('#sendMessage').css("cursor", "default");
				$("#sendMessage").val("发送(" + num + "s)");
				if(num > 0)
					setTimeout("time()",1000);
				else{
					num = 60;
					$('#sendMessage').removeAttr("disabled");
					$('#sendMessage').css("cursor", "pointer");
					$("#sendMessage").val("发送验证码");
				}
			}
    	</script>
	</head>
	<body>
		<%@ include file="/common/head.jsp"%>
		<form id="editForm" name="editForm" method="post" action="" style="margin-left:35px;">
			<input type="hidden" id="type" name="type" value="" />
			<div class="content">
				<div class="center">
					<div class="ucenter">
						<div class="title">
							个人资料
						</div>
						<%@ include file="/usercenter/menu.jsp"%>
						<div class="uc_r">
							<div class="info">
								<div class="tit">
									个人资料
								</div>
								<div class="icon" style="float: left;width:150px;">
									<input type="hidden" id="headImage" name="headImage" value="${user.imagePath }">
									<img id="imgImagePath" src="${user.headImage }" />
								</div>
							
								<div class="upload_div" style="float: left;padding-top: 15px;width: 400px;">
									<input type="hidden" name="upload_path" id="upload_path" value=""/>
									<input name="upload_btn" id="upload_btn" type="file" value="上传头像" style="display: none;"/> 
									<span style="color: red;" id="uploadMsg">请上传头像图片(jpg,jpeg,png)</span>
								</div>
								
								<div class="field">
									<p>
										手机号码：
									</p>
									<div>
										<span id="spanMobilePhone">${user.mobilePhone }</span>&nbsp;&nbsp;
										<a href="javascript:edit(0);" class="link">修改手机号码</a>
										<a href="javascript:edit(1);" class="link">修改密码</a>
									</div>
								</div>
								<div class="field">
									<p>
										昵称：
									</p>
									<div>
										<input id="nickname" name="nickname" placeholder="请输入您的昵称" value="${user.nickname }" style="width: 290px;" />
									</div>
								</div>
								<div class="field">
									<p>
										姓名：
									</p>
									<div>
										<input id="name" name="name" placeholder="请输入您的姓名" value="${user.name }" style="width: 290px;" />
									</div>
								</div>
								<div class="field">
									<p>
										邮箱：
									</p>
									<div>
										<input id="email" name="email" placeholder="请输入您的邮箱" value="${user.email }" style="width: 290px;" />
									</div>
								</div>
								<div class="field">
									<p>
										二维码：
									</p>
									<div>
										<input id="weixinImage" name="weixinImage" readonly="readonly"  placeholder="微信二维码路径" value="${user.weixinImage }" style="width: 290px;float: left;'" />
										<input name="upload_weixin" id="upload_weixin" type="file" value="微信二维码" style="display: none;"/> 
									</div>
								</div>
								<div class="field">
									<p>
										&nbsp;
									</p>
									<div>
										<button type="button" class="save" style="cursor: pointer;" onclick="save()">
											保存
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 弹出 -->
			<div id="divShade" class="mask" style="display: none;"></div>
			<div id="divEdit" class="popbox reg" style="width: 706px; height: 225px; margin-left: -353px; display: none;">
				<div class="close">
					<a href="javascript:close();"></a>
				</div>
				<div class="form">
					<div id="divMobilePhone" class="field" style="display: none;">
						<p>
							手机号
						</p>
						<div>
							<input type="text" placeholder="请输入您的手机号" id="newMobilePhone" name="newMobilePhone" class="allInput" />
						</div>
					</div>
					<div id="divVCode" class="field" style="display: none;">
						<p>
							验证码
						</p>
						<div>
							<input type="text" placeholder="请输入您刚刚收到短信验证码" id="vInfoCode" name="vInfoCode" class="allInput" />
						</div>
					</div>
					<div id="divCurPassword" class="field" style="display: none;">
						<p>
							当前密码
						</p>
						<div>
							<input type="password" placeholder="至少6个字符" id="curInfoPassword" name="curInfoPassword" class="allInput" />
						</div>
					</div>
					<div id="divPassword" class="field" style="display: none;">
						<p>
							新&nbsp;&nbsp;密&nbsp;&nbsp;码
						</p>
						<div>
							<input type="password" placeholder="至少6个字符" id="infoPassword" name="infoPassword" class="allInput" />
						</div>
					</div>
					<div id="divRepassword" class="field" style="display: none;">
						<p>
							重复密码
						</p>
						<div>
							<input type="password" placeholder="至少6个字符" id="reInfoPassword" name="reInfoPassword" class="allInput" />
						</div>
					</div>
					<div class="field">
						<p>
							&nbsp;
						</p>
						<div>
							<button type="button" class="btn2" style="width: 173px;" onclick="submitData()">
								提交
							</button>
						</div>
					</div>
				</div>
				<div id="divSendCode" style="float: left; padding-top: 17px;">
					<input type="button" class="btn2" style="width: 84px; font-size: 12px;" name="sendMessage" id="sendMessage" value="发送验证码" onclick="sendCode()" />
				</div>
			</div>
		</form>
		<%@ include file="/common/foot.jsp"%>
	</body>
</html>