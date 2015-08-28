<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE html>
<head>
	<title>创建农场</title>
</head>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title></title>
		<link href="${basePath}/css/all.css" rel="stylesheet" type="text/css" />
		<link href="${basePath}/css/base.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${basePath}/js/common/component/uploadify/uploadify.css" />
		<style>
			.field p{width:150px !important;}
			.field div{width:470px !important;}
			.field div input{width:290px !important;}
		</style>
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/component/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/common.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/datepicker/WdatePicker.js"></script>
		<script type="text/javascript">
	    	function save(){
	    		var name=$("input[attr='circle_name']").val();
	    		var address=$("input[name='address']").val();
	    		if(name=="")
    			{
	    			ShowBox("请填写农场名称");
	    			return;
    			}
	    		if(address=="")
    			{
	    			ShowBox("请填写农场地址");
	    			return;
    			}
	    		editForm.action = "${basePath }/usercenter/createCircle.action";
	    		editForm.submit();
	    	}
	    	
	    	
	    	function getNextPostTime()
	    	{
	    		var week=$("select[name='postWeek']").val();
	    		var amPm=$("select[name='postAmPm']").val();
	    		$.ajax({
	    			url:basePath + "/circle/getNextPostDate.action",
	    			type:'post',
	    			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	    			data:{'week':week,'amPm':amPm},
	    			success:function(result){
	    				$("#nextPostSpan").html(decodeURI(result));
	    			}
	    		});
	    		
	    	}
	    	
	    	$(document).ready(function(){
				//初始化图片上传控件
				initUploadFileCom('upload_btn','${basePath}',{
					'fileObjName':'file',
					'fileTypeExts':'*.jpg',
					'fileSizeLimit':'250',
					'multi':false,
					'uploader':'${basePath}/usercenter/uploadFile.action;jsessionid=${pageContext.session.id}',
					'onUploadSuccess':function(file, obj){
						var data = eval('(' + obj + ')'); // string 转为json对象
						$("#headPath").val(data.imgPath);
						$("#imgImagePath").attr("src", data.path);
					}
				});
				
				getNextPostTime();
			});
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
							我的农场
						</div>
						<%@ include file="/usercenter/menu.jsp"%>
						<div class="uc_r">
							<div class="info creframe" style="margin-left: 25px;">
								<div class="tit">
									创建农场
								</div>
								<div class="icon" style="float: left;width:150px;margin-left: 70px;margin-bottom: 18px;">
									<input type="hidden" id="headPath" name="headPath" value="${circle.headPath }"/>
									<img id="imgImagePath" src="${circle.imagePath }" />
								</div>
								<div class="upload_div" style="float: left;padding-top: 15px;width: 400px;">
									<input type="hidden" name="upload_path" id="upload_path" value=""/>
									<input name="upload_btn" id="upload_btn" type="file" value="上传图片" style="display: none;"/> 
									<span style="color: red;" id="uploadMsg">请上传您的头像，以便其他用户可以识别您的农场(jpg,jpeg,png)</span>
								</div>
								<div class="warning">
                        		  	  奖励：审核通过之后您可享受一次不限金额不限人数的免运费试买服务，当您组织达5人及以上的团购活动，我们即为您免费送上一斤新鲜可口的苹果。
                      			</div>
								<div class="field">
									<p>
										农场名称：
									</p>
									<div>
										<input id="name" attr="circle_name" name="name" value="${circle.name }" placeholder="给您的农场起个名字吧" style="width: 290px;" />
									</div>
								</div>
								<div class="field" style="display: none;">
									<p>
										农场简介：
									</p>
									<div>
										<input  id="description" name="description" style="width: 290px;" placeholder="一句话描述下您的农场"  value="${circle.description }"/>
									</div>
								</div>
								
								<div class="field" style="display: none;">
									<p>
										农场公告：
									</p>
									<div>
										<input id="notice" name="notice" placeholder="农场公告" value="${circle.notice }" style="width: 290px;"/>
									</div>
								</div>
								<div class="field">
									<p>
										农场所在地点：
									</p>
									<div>
										<input id="address" name="address" placeholder="区、街道名称" value="${circle.address }" style="width: 290px;" />
									</div>
								</div>
								<div class="field" style="display: none;">
									<p>
										每周投递时间：
									</p>
									<div>
										每
										<select id="postWeek" name="postWeek" style="width: 93px;" onchange="getNextPostTime();">
											<option value="1">周一</option>
											<option value="2">周二</option>
											<option value="3">周三</option>
											<option value="4">周四</option>
											<option value="5">周五</option>
											<option value="6">周六</option>
											<option value="7">周日</option>
										</select>
										的
										<select id="postAmPm" name="postAmPm" style="width: 93px" onchange="getNextPostTime();">
											<option value="1">上午</option>
											<option value="2">下午</option>
										</select>
										<span style="font-size:15px;" id="nextPostSpan"></span>
									</div>
								</div>
								<div class="field">
									<p>
										成员加入方式：
									</p>
									<div>
										<select id="joinType" name="joinType" >
											<option value="8001" <c:if test="${circle.joinType == 8001 }">selected</c:if>>直接加入</option>
											<option value="8002" <c:if test="${circle.joinType == 8002 }">selected</c:if>>需要审核</option>
										</select>
									</div>
								</div>
								<div class="field" style="display: none;">
									<p>
										本次发放货物时间：
									</p>
									<div>
										<input id="issueTime" name="issueTime" class="Wdate" placeholder="本次发放货物时间" readonly="readonly" onclick="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m'})" value="${circle.issueTime }" style="width: 155px;" />
									</div>
								</div>
								<div class="field" style="display: none;">
									<p>
										本次发放货物地点：
									</p>
									<div>
										<input id="issueAddress" name="issueAddress" placeholder="本次发放货物地点"  value="${circle.issueAddress }" style="width: 155px;" />
									</div>
								</div>
								
								<div class="field" style="display: none;">
									<p>
										农场所在省：
									</p>
									<div>
										<input type="hidden" id="province" name="province" value="320000" style="width: 290px;" />
									</div>
								</div>
								<div class="field" style="display: none;">
									<p>
										农场所在市：
									</p>
									<div>
										<input type="hidden" id="city" name="city" value="320100" style="width: 290px;" />
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
		</form>
		
    

		<%@ include file="/common/foot.jsp"%>
	</body>
</html>