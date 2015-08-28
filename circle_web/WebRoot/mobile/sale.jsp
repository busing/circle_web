<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-title" content="团农">
		<link rel="apple-touch-icon" href="${basePath }/images/mobile/icon.jpg" />
		<title>团弄特卖</title>
		<link href="${basePath }/Content/bootstrap.min.css" rel="stylesheet" />
		<link href="${basePath }/css/mobile.css" rel="stylesheet" />
		<script type="text/javascript" src="${basePath }/js/common/jquery-1.7.2.min.js"></script>
		<script src="${basePath }/js/common/common.js"></script>
		<script src="${basePath }/js/mobile/head.js"></script>
		<script type="text/javascript">
	    	$(document).ready(function(){
        		$.ajax({
	        		url:"${basePath }/batchsell/queryBatchsell.action",
        			dataType:"json",
	        		success:function(data){
	        			var good="",html="";
	            		for(var i=0;i<data.length;i++){
	            			good = data[i];
	            			html += '<div class="row item">';
	            			html += '<div class="col-xs-12 col-sm-5">';
	            			html += '<div class="left_img">';
	            			html += '<a href="#">';
	            			html += '<img src="${basePath }' + good.image + '" width="60" height="60" />';
	            			html += '</a>';
	            			html += '</div>';
	            			html += '</div>';
	            			html += '<div class="col-xs-12 col-sm-7">';
	            			html += '<div class="right_info">';
	            			html += '<p class="p1">';
	            			html += '<a href="#">' + good.good_name + '</a>';
	            			html += '</p>';
	            			html += '<p>';
	            			html += '<span class="money">￥' + good.sell_price + '</span>';
	            			html += '<del>';
	            			html += '￥' + good.original_price + '';
	            			html += '</del>';
	            			html += '<span class="nor">/' + good.unit_str + '</span>';
	            			html += '</p>';
	            			html += '</div>';
	            			html += '</div>';
	            			html += '</div>';
	            		}
	            		$(".produts").html(html);
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
						<li class="col-xs-12">
							<div>
								<a href="${basePath }/mobile/myfarm.jsp">我的农场</a>
							</div>
						</li>
						<li class="col-xs-12 selected">
							<div>
								团农特卖
							</div>
						</li>
					</ul>
				</div>
				<div class="col-xs-9 col-xs-offset-3 right">
					<div class="right_content">
						<div class="produts">
							
						</div>
					</div>
					<div class="bottom"></div>
				</div>
			</div>
		</div>
	</body>
</html>
