$(document).ready(function(){
	$(".icon .flag").live("hover",function(){
		$(".flag .downbox").show();
//		$(".flag .downbox").hide("slow");
	});
	
	$(".icon .flag").live("mouseleave",function(){
		$(".flag .downbox").hide();
	});
	
	//注册登陆关闭样式事件
	$(".close").live("click",function(){
		$(this).parent().hide();
		$(".mask").hide();
	});
	//登陆框--注册按钮事件
	$("button[name='toLogin']").live("click",function(){
		$(".login").hide();
		$(".reg").show();
	});
	

	getMyCircle();
	getMsgCount();
	getShopCartCount();
});

//方法描述:用户登录(返回毁掉url，登录结果 0：登录失败 1：登录成功)
// view viewId（1：创建圈子	2：加入圈子 其他：直接登录）
function login(){
	var viewId =  $("#viewId").val();
	var mobilePhone = $("#mobilePhone").val();
	var password = $("#password").val();
	var cId =  $("#cId").val();
	
	if(!mobilePhone){
		ShowBox.Show("请输入手机号码");
		$("#mobilePhone").focus();
		return;
	}
	
	if(!password){
		ShowBox.Show("请输入密码");
		$("#password").focus();
		return;
	}
	
	$("#btnLogin").attr("disabled","disabled");
	$.ajax({
		url:basePath + "/login/login.action",
		type:"post",
		data:"circleId="+cId+"&viewId="+viewId+"&mobilePhone="+mobilePhone+"&password="+password,
		dataType:"json",
		success:function(result){
			if(result != null && result != "")
			{
				var loginFlag=result.result;
				//登录失败  提示登录错误信息
				if(loginFlag==0)
				{
					ShowBox.Show(result.msg);
					$('#btnLogin').removeAttr("disabled");
				}
				else
				{debugger
					//登录失败
					if(result.result == 0){
						ShowBox.Show(result.msg);
						return;
					}else if(result.result == 1){
						$(".login").hide();
						//登录成功  创建圈子登录
						if(result.msg != "")
							ShowBox.Show(result.msg,1);
						//window.location.href=basePath + result.url;
					}
					if(result.url=="")
					{
						window.location.href=self.location.href;
					}
					else
					{
						window.location.href=basePath + result.url;
					}
				}
			}
		}
	});
}


//发送验证码
$("#sendMsg").live("click",function(){
	
	var m = $("#regForm #mobilePhone").val();
	if(m == "" || $("#regForm #mobilePhone").hasClass("Validform_error")){
		ShowBox.Show("请输入正确的手机号码！");
		return;
	}
	
	$.ajax({
		url:basePath+'/login/sendValidateMsg.action?mobilePhone='+$("#regForm #mobilePhone").val()+"&status=1",
		dataType:'text',
		success:function(result){
			if(result == "1"){
				ShowBox.Show("发送短信失败!");
				return;
			} else if(result == "0"){
				ShowBox.Show("您已经注册,请登录!");
				return;
			}else{
				$("#pId").val(m);
				$("#verificationCode").removeAttr("readonly");
				$("font").html("短信已经发送");
				$("#sendMsg").attr("disabled","disabled");
				$("#sendMsg").attr("class","btn1");
				
				setTimeout("enableSendBtn()",60*5*1000);
			}
		}
	});
});

//发送修改密码验证码
$("#sendUpdatePassMsg").live("click",function(){
	var m = $("#updatePassForm #mobilePhone").val();
	if(m == "" || $("#updatePassForm #mobilePhone").hasClass("Validform_error")){
		ShowBox.Show("请输入正确的手机号码！");
		return;
	}
	
	$.ajax({
		url:basePath+'/login/sendValidateMsg.action?mobilePhone='+$("#updatePassForm #mobilePhone").val()+"&status=2",
		dataType:'text',
		success:function(result){
			if(result == "1"){
				ShowBox.Show("发送短信失败!");
				return;
			} else if(result == "0"){
				ShowBox.Show("用户不存在!");
				return;
			}else{
				$("#updatePassForm #verificationCode").removeAttr("readonly");
				$("#updatePassForm font").html("短信已经发送");
				$("#updatePassForm #sendUpdatePassMsg").attr("disabled","disabled");
				$("#updatePassForm #sendUpdatePassMsg").attr("class","btn1");
				
				setTimeout("enableSendBtn2()",60*5*1000);
			}
		}
	});
});


$("#updatePassButtom").live("click",function(){
	var code = $("#updatePassForm #verificationCode").val();
	var mobilePhone =$("#updatePassForm #mobilePhone").val();
	var password = $("#updatePassForm #password").val();
	var repassword = $("#updatePassForm #repassword").val();
	
	if(mobilePhone == ""){ ShowBox.Show("请输入手机号码!"); return; }
	if(code == ""){ ShowBox.Show("请输入验证码!"); return; }
	if(password ==""){ ShowBox.Show("请输入密码!");  return; }
	if(repassword ==""){ ShowBox.Show("请输入重复密码!"); return; }
	if(password != repassword){ ShowBox.Show("两次密码不匹配,请重新输入!"); return; }
	
	$.ajax({
		url:basePath+'/login/checkRegister.action?code='+code+'&mobile='+mobilePhone,
		dataType:'text',
		async:true,
		success:function(result){
			if(result == "1")
			{
				ShowBox.Show("验证码过期,请重新输入发送!",3);
				enableSendBtn2();
				enableSubmitBtn();
				return;
			}else if(result == "2"){
				ShowBox.Show("验证码错误!",3);
				enableSendBtn2();
				enableSubmitBtn();
				return;
			}else{
				disableSubmitBtn();
				$.ajax({
					url:basePath+'/user/updatePasswordByMobilePhone.action?code='+code+'&curpassword='+password+'&mobilePhone='+mobilePhone,
					dataType:'text',
					success:function(result){
						if(result == "0")
						{
							$(".updatePass").hide();
							$(".reg").hide();
							$(".login").show();
							$(".mask").show();
							ShowBox.Show("修改成功",1);
						}else{
							enableSubmitBtn();
							ShowBox.Show("修改密码失败",3);
						}
					}
				});
			}
		}
	});
	
	
});

//允许继续发送
function enableSendBtn2(){
	$("#updatePassForm #sendUpdatePassMsg").removeAttr("disabled");
	$("#updatePassForm #sendUpdatePassMsg").attr("class","btn2");
	$("#updatePassForm font").html("发送验证码");
}

//允许继续发送
function enableSendBtn(){
	$("#regButtom #sendMsg").removeAttr("disabled");
	$("#regButtom #sendMsg").attr("class","btn2");
	$("#regButtom font").html("发送验证码");
}

function enableSubmitBtn(){
	$("#regButtom").val("提交");
	$("#regButtom").removeAttr("disabled");
	$("#regButtom").attr("class","btn2");
	
	$("#updatePassButtom").val("提交");
	$("#updatePassButtom").removeAttr("disabled");
	$("#updatePassButtom").attr("class","btn2");
}

function disableSubmitBtn(){
	$("#regButtom").attr("disabled","disabled");
	$("#regButtom").attr("class","btn1");
	
	$("#updatePassButtom").attr("disabled","disabled");
	$("#updatePassButtom").attr("class","btn1");
	
}

//注册用户
$("#regButtom").live("click",function(){
	var code = $("#regForm #verificationCode").val();
	var mobilePhone =$("#regForm #mobilePhone").val();
	var uname = $("#regForm #name").val();
	var password = $("#regForm #password").val();
	var repassword = $("#regForm #repassword").val();
	var inviteCode = $("#regForm #inviteCode").val();
	
	$.ajax({
		url:basePath+'/login/checkRegister.action?code='+code+'&mobile='+mobilePhone,
		dataType:'text',
		async:true,
		success:function(result){
			if(result == "1")
			{
				ShowBox.Show("验证码过期,请重新输入发送!",3);
				enableSendBtn("#regForm");
				enableSubmitBtn();
				return;
			}else if(result == "2"){
				ShowBox.Show("验证码错误!",3);
				enableSendBtn("#regForm");
				enableSubmitBtn();
				return;
			}else{
				if(mobilePhone == ""){
					ShowBox.Show("请输入手机号码!");
					return;
				}
				if(code == ""){
					ShowBox.Show("请输入验证码!");
					return;
				}
				if(uname ==""){
					ShowBox.Show("请输入姓名!");
					return;
				}
				if(password ==""){
					ShowBox.Show("请输入密码!");
					return;
				}
				if(repassword ==""){
					ShowBox.Show("请输入重复密码!");
					return;
				}
				if(password != repassword){
					ShowBox.Show("两次密码不匹配,请重新输入!");
					return;
				}
				disableSubmitBtn();
				$.ajax({
					url:basePath+'/login/registUser.action?verificationCode='+code+'&userName='+encodeURIComponent(uname)+'&password='+password+'&mobilePhone='+mobilePhone+"&inviteCode="+inviteCode,
					dataType:'json',
					success:function(result){
						if(result != null && result != "")
						{
							var loginFlag=result.result;
							//登录失败  提示登录错误信息
							if(loginFlag==0)
							{
								ShowBox.Show(result.msg,3);
								enableSubmitBtn();
								return;
							}
							else
							{
								//登录失败
								if(result.result == 0){
									ShowBox.Show(result.msg,3);
									enableSubmitBtn();
									return;
								}else{
									$(".reg").hide();
									$(".login").hide();
									$(".mask").show();
									$("#success").show();
									
									$("#success h5 a").click(function(){//点击我知道了
										window.location.href=result.url;
								   	 });
									
								}
							}
						}
					}
				});
			}
		}
	});
});

//显示修改密码
function showUpdatePass()
{
	$(".login").hide();
	$(".mask").show();
	$(".updatePass").show();
}

//显示注册
function showReg()
{
	$(".mask").show();
	$(".reg").show();
}

//显示登录
function showLogin()
{
	$(".mask").show();
	$(".login").show();
}

