<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>用户中心-我的农场</title>
		<link href="${basePath}/css/base.css" rel="stylesheet" />
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${basePath}/js/circle.js"></script>
		
		<script type="text/javascript">
			var msg = "${msg }";
			
			function showOperation(idx){
				var id = "divOperation" + idx;
				$(".icons").each(function(){
					$(this).css("display", "none");
					if($(this).attr("id") == id)
						$("#divOperation" + idx).css("display", "block");
				});
			}
			
			var num=0;
			function hideOperation(e){
				console.log("tagName" + num++ + ":" + e.tagName);
				//if()
				$(".icons").each(function(){
					$(this).css("display", "none");
				});
			}
			
			function viewCircle(cid){
				$.ajax({
					url: "${basePath }/usercenter/queryCircleAndUserByCircleId.action",
					type: "post",
					dataType: "json",
					data: "circleId=" + cid,
					success: function(res){
						try{
							var circle = res.circle;
							var list = res.list;
							$("#pinfo").html("农场主信息：" + circle.createUser + "(联系电话：" + circle.mobilePhone + ")");
							$("#pnotice").html("农场公告：" + circle.notice);
							$("#pdescription").html("农场简介：" + circle.description);
							
							var htmlStr = "";
							for(var i = 0; list && i < list.length; i++){
								htmlStr += "<li title='手机号码：" + list[i].mobilePhone + "'><img src='" + list[i].imagePath + "' /><p>" + list[i].name + "</p></li>";
							}
							$("#ulUserView").html(htmlStr);
						}catch(ex){
							ShowBox("获取数据失败,请检查网络");
						}
					}
				});
				$("#divMask").css("display", "block");
				$("#divCircleView").css("display", "block");
			}
			
			function editCircle(cid){
				$.ajax({
					url: "${basePath }/usercenter/queryCircleAndUserByCircleId.action",
					type: "post",
					dataType: "json",
					data: "circleId=" + cid,
					success: function(res){
						try{
							var circle = res.circle;
							var list = res.list;
							$("#id").val(circle.id);
							$("#info").html("农场主信息：" + circle.createUser + "(联系电话：" + circle.mobilePhone + ")");
							$("#notice").val(circle.notice);
							$("#description").val(circle.description);
							
							var htmlStr = "";
							for(var i = 0; list && i < list.length; i++){
								htmlStr += "<li title='手机号码：" + list[i].mobilePhone + "'><div class='item'><img src='" + list[i].imagePath + "' /><p>" + list[i].name + "</p><i class='close'></i></div></li>";
							}
							$("#ulUserEdit").html(htmlStr);
						}catch(ex){
							ShowBox("获取数据失败,请检查网络");
						}
					}
				});
				$("#divMask").css("display", "block");
				$("#divCircleEdit").css("display", "block");
			}
			
			function exitCircle(cid){
				if(confirm("确定退出圈子?")){
					circleForm.action = "${basePath }/usercenter/memberExitCircle.action?circleId=" + cid;
					circleForm.submit();
				}
			}
			
			function saveCircleInfo(){
				$.ajax({
					url: "${basePath }/usercenter/saveCircleInfo.action",
					type: "post",
					dataType: "text",
					data: $("#circleEditFrm").serialize(),
					success: function(res){
						if(res && res == 1){
							ShowBox("保存成功");
							closeDiv("divCircleEdit");
						}else
							ShowBox("保存失败,请检查");
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
							ShowBox("发布成功");
							closeDiv("divCirclePublish");
						}else
							ShowBox("发布失败,请检查");
					}
				});
			}
		</script>
	</head>
	<body>
		<%@ include file="/common/head.jsp"%>
		<form id="circleForm" name="circleForm" method="post" action="">
			<div class="content">
				<div class="center">
					<div class="ucenter">
						<div class="title">
							我的农场
						</div>
						<%@ include file="/usercenter/menu.jsp"%>
						<!-- 个人中心我加入的圈子 -->
						<div class="uc_r">
							<div class="cart">
								<div class="myframe">
									<div class="tit">
										已加入农场
									</div>
									<div class="frames">
										<ul>
											<c:forEach items="${circles }" var="circle" varStatus="idx">
												<li class="selected">
													<div class="img" <%-- onmouseover="showOperation(${idx.index })" --%>>
														<a href="${basePath}/circle/circleIndex.action?circleId=${circle.id}" title="农场名称：${circle.name },电话：${circle.mobilePhone }">
															<img src="${circle.imagePath }" class="f" />
														</a>
														<a href="${basePath}/circle/circleIndex.action?circleId=${circle.id}" title="农场名称：${circle.name },电话：${circle.mobilePhone }">
															<span>
																${circle.name }
															</span>
															<i>
																<c:if test="${circle.isSelfCreate == 1 }">
																	<img src="${basePath }/images/heart.png" />
																</c:if>
															</i>
														</a>
													</div>
													<div id="divOperation${idx.index }" class="links" style="display: block;">
														<c:if test="${circle.isSelfCreate == 1 }">
															<a href="javascript:editCircle(${circle.id });" class="link">编辑农场</a>
														</c:if>
														<c:if test="${circle.isSelfCreate != 1 }">
															<a href="javascript:viewCircle(${circle.id });" class="link">农场资料</a>
														</c:if>
														<a href="javaScript:void(0);" class="line">|</a>
														<c:if test="${circle.isSelfCreate == 1 }">
															<a href="javascript:publishCircle(${circle.id },'${circle.issueTime }','${circle.issueAddress }');" class="link">发布</a>
														</c:if>
														<c:if test="${circle.isSelfCreate != 1 }">
															<a href="javascript:exitCircle(${circle.id });" class="link">退出</a>
														</c:if>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
									<div style="clear: both"></div>
									<div class="create">
										<a href="${basePath }/usercenter/toCreateCircle.action">创建农场<i></i>
										</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<%@ include file="/common/foot.jsp"%>
		<div id="divMask" class="mask" style="display: none;"></div>
		<!--农场主信息-->
		<div id="divCircleView" class="popbox frameinfo" style="width: 654px; height: 300px; margin-left: -327px; margin-top: 130px; display: none;">
			<p id="pinfo" class="sp"></p>
			<div class="users">
				<div class="users_l">
					群成员:
				</div>
				<div class="users_r">
					<ul id="ulUserView">
						
					</ul>
				</div>
			</div>
			<p id="pnotice" style="width: 600px;"></p>
			<p id="pdescription" style="width: 600px;"></p>
			<div class="close">
				<a href="javascript:closeDiv('divCircleView');"></a>
			</div>
		</div>
		<form id="circleEditFrm" name="circleEditFrm" method="post" action="">
			<input type="hidden" id="id" name="id">
			<!--编辑农场主-->
			<div id="divCircleEdit" class="popbox frameinfo" style="width: 654px; height: 340px; margin-left: -327px; margin-top: 130px; display: none;">
				<p id="info" class="sp"></p>
				<div class="users">
					<div class="users_l">
						群成员:
					</div>
					<div class="users_r">
						<ul id="ulUserEdit">
						</ul>
					</div>
				</div>
				<p>
					农场公告：
					<input type="text" id="notice" name="notice" class="edit" style="width: 573px;" />
				</p>
				<p>
					农场简介：
					<input type="text" id="description" name="description" class="edit" style="width: 573px;" />
				</p>
				<div class="btns">
					<input type="button" value="保存" class="btnred" onclick="saveCircleInfo()" />
				</div>
				<div class="close">
					<a href="javascript:closeDiv('divCircleEdit');"></a>
				</div>
			</div>
			<!--发布时间地点-->
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
		
    <!--提示框
    <div class="popbox alert" style="width: 475px; height: 195px; margin-left: -232px; margin-top: 330px;">
        <div class="title">恭喜！农场创建成功咯~</div>
        <div class="info">
      		      邀请您的亲朋好友赶紧加入您的农场吧，一起享受美味可口的新鲜蔬果哦~
        </div>
        <div class="close"><a href="#"></a></div>
    </div>-->
	</body>
	<script type="text/javascript">
		if(msg)
			ShowBox(msg);
	</script>
</html>