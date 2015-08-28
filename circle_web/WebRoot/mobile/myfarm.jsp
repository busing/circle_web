﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-title" content="团农">
		<link rel="apple-touch-icon" href="${basePath }/images/mobile/icon.jpg" />
		<title>我的农场</title>
		<link href="${basePath }/Content/bootstrap.min.css" rel="stylesheet" />
		<link href="${basePath }/css/mobile.css" rel="stylesheet" />
		<script type="text/javascript" src="${basePath }/js/common/jquery-1.7.2.min.js"></script>
		<script src="${basePath }/js/common/common.js"></script>
		<script src="${basePath }/js/mobile/head.js"></script>
		<script type="text/javascript">
	    	$(document).ready(function(){
        		$.ajax({
	        		url:"${basePath }/circle/queryMyCircles.action",
        			dataType:"json",
	        		success:function(data){
	        			var circle="",html="";
	        			if(data.length > 0){
		            		for(var i=0;i<data.length;i++){
		            			circle = data[i];
		            			html += '<div class="row item">';
		            			html += '<div class="col-xs-4 left">';
		            			html += '<a href="${basePath }/mobile/farm_list.html?circleId='+ circle.id +'">';
		            			html += '<img src="' + circle.headPath + '" width="60" height="60" /></a>';
		            			//html += '<i class="add cli"></i>';
		            			html += '</div>';
		            			html += '<div class="col-xs-8 right">';
		            			html += '<p class="p1"><a href="${basePath }/mobile/farm_list.html?circleId='+ circle.id +'">'+ circle.name +'</a></p>';
		            			html += '<p class="p2">';
		            			html += '发放时间：' + circle.issueTime + '<br />';
		            			html += '发放地点：' + circle.issueAddress;
		            			html += '</p>';
		            			html += '</div>';
		            			html += '</div>';
		            		}
	            		}else{
	            			html += '<div class="row none">';
	            			html += '<p class="col-xs-12">附近还没有农场哦，快来创建吧~</p>';
	            			html += '<div class="col-xs-12">';
	            			html += '<a href="farm_list.html" class="create">创建农场</a>';
	            			html += '</div>';
	            			html += '</div>';
	            		}
	            		$(".frams").html(html);
					}
				});
		    });
	    </script>
	</head>
	<body>
		<div class="container-fluid cont">
			<div class="row head">
				<div class="col-xs-2 msg">
					<i></i>
				</div>
				<div class="col-xs-8 center">
					<i class="loc"></i>
					<span class="tit">烽火科技大厦</span>
					<i class="arrow"></i>
				</div>
				<div class="col-xs-2 user">
					<i></i>
				</div>
			</div>
			<div class="row content">
				<div class="col-xs-3 menu">
					<ul class="row">
						<li class="col-xs-12">
							<div>
								<a href="${basePath }/mobile/nearby.jsp">附近农场</a>
							</div>
						</li>
						<li class="col-xs-12 selected">
							<div>
								我的农场
							</div>
						</li>
						<li class="col-xs-12 ">
							<div>
								<a href="${basePath }/mobile/sale.jsp">团农特卖</a>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-xs-9 col-xs-offset-3 right">
					<div class="right_content">
						<div class="frams">
							
						</div>
					</div>
					<div class="bottom"></div>
				</div>
			</div>
		</div>
	</body>
</html>