var lon = "";
var lat =  "";
var level = 14;

$(document).ready(function(){
	getValidForm({
		formValid:'#regForm',
		formValid:'#updatePassForm',
	}) ;
	
	if(lat =="" || lon=="")
	{
		lon = 118.790722;
		lat = 32.04781;
	}
	
	var map = new BMap.Map("mapContainer",{minZoom:12,maxZoom:20}); // 创建地图实例
	window.map = map;//将map变量存储在全局
	//创建和初始化地图函数：
	locateMap(lon,lat,level);//创建地图
	setMapEvent();//设置地图事件
	addMapControl();//向地图添加控件	
	loadInitFarm();
});

//首页默认加载农场
function loadInitFarm()
{
	$.ajax({
		url:basePath + "/circle/getInitMapCircle.action",
		dataType:'json',
		success:function(data){
		if(data != null && data !="") 
		{
			locateMap(lon,lat,13);
			for(var i = 0; i < data.length; i++)
			{
				var message ="<div class=\"mapWindow\">" 
					+"<div class=\"box\">" 
					+"<div style=\"float: left;\" class=\"title\">本次发放时间：</div>"
					+"<div style=\"float: left;\" class=\"txt\">"+(data[i].issueTime==""?"暂未发布":data[i].issueTime)+"</div>" 
					+"<div style=\"float: left;clear: both;\" class=\"title\" >本次发放地点：</div>"
					+"<div style=\"float: left;overflow: hidden;width: 142px;text-overflow: ellipsis;white-space: nowrap;\" class=\"txt\" title=\""+(data[i].issueAddress==""?"暂未发布":data[i].issueAddress)+"\">"+(data[i].issueAddress==""?"暂未发布":data[i].issueAddress)+"</div>" 
					+"<div style=\"float: left;clear: both;\" class=\"title\">农场主：</div>"
					+"<div style=\"float: left;\" class=\"txt\">"+data[i].createUser+"</div>" 
					+"<div style=\"float: left;\" class=\"txt\"><a href=\"javascript:goFarm('"+data[i].id+"')\" title=\"去购物\" style=\"color:#924a48;font-size: 16px;padding-left: 53px;\">去购物</a></div>" 
					+"<div class=\"framer\">"
					+"<img  src='"+data[i].imagePath+"'/>"
					+"<a href=\"javascript:goFarm('"+data[i].id+"')\" title=\"点击查看农场\" class=\"name\">"+data[i].name+"</a>"
					+"<a class=\"joinFramer\" href=\"javascript:joinFarm('"+data[i].id+"')\"  farmId=\""+data[i].id+"\">加入农场</a>"
					+"</div></div></div>";
				addIcon(data[i].longitude,data[i].latitude,message);//添加定位图标
			}
		}
	}});
}


//创建地图函数：
function locateMap(x, y,level) {
	var point = new BMap.Point(x, y);//定义一个中心点坐标
	map.centerAndZoom(point, level);//设定地图的中心点和坐标并将地图显示在地图容器中
	window.map = map;//将map变量存储在全局
}

//地图事件设置函数：
function setMapEvent() {
	map.enableDoubleClickZoom(true);
	map.enableInertialDragging(true);
	map.enableScrollWheelZoom(true);
	map.enableContinuousZoom(true);
	map.enableAutoResize(true);
	map.enableDragging(true);//启用地图拖拽事件，默认启用(可不写)
   	map.enableKeyboard(true);//启用键盘上下左右键移动地图
}

//地图控件添加函数：
function addMapControl() {
	//向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
   //向地图中添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	map.addControl(ctrl_ove);
   //向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);
	//鹰眼
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	map.addControl(ctrl_ove);
}

//添加图标
function addIcon(x,y,message,autoOpen){
	var icon = new BMap.Icon(basePath+'/images/pin.png', new BMap.Size(26, 43), {
		 anchor: new BMap.Size(13, 40),
		 infoWindowAnchor: new BMap.Size(10, 0)
	});
	
	var infoWindowTxt="<div style=\"width:100%;height:100%;\">"+message+"</div>";
	var infoWindow = new BMap.InfoWindow(infoWindowTxt,{enableMessage:false});

	var mkr = new BMap.Marker(new BMap.Point(x,y), {
		icon: icon,
		enableDragging: false,
		raiseOnDrag: false
	});

	map.addOverlay(mkr);
	
	mkr.addEventListener('click', function(){  
    	this.openInfoWindow(infoWindow);  
   		 //图片加载完毕重绘infowindow  
   		// document.getElementById('imgDemo').onload = function (){  
  		 infoWindow.redraw();  
 	   //}  
  });
	if(autoOpen)
	{
		mkr.openInfoWindow(infoWindow,new BMap.Point(x, y));
	}
	
}

function  saveMapPoint(){
	if(confirm("是否确认保存位置?")){
		window.close();
	}
}


//地图搜索
function searchMap() {
	map.clearOverlays();
    var area = "南京市"+$("#searchTxt").attr("value"); //得到地区
    var ls = new BMap.LocalSearch(map);
    ls.setSearchCompleteCallback(function(rs) {
        if (ls.getStatus() == BMAP_STATUS_SUCCESS) {
            var poi = rs.getPoi(0);
            if (poi) {
        	$.ajax({
        		url:basePath + "/circle/getCircleByPoint.action?x="+poi.point.lng+"&y="+poi.point.lat,
        		dataType:'json',
        		success:function(data){
            		viewFarms(data);
            		locateMap(poi.point.lng, poi.point.lat,14);
					if(data != null && data !="") 
					{
						for(var i = 0; i < data.length; i++)
						{
							var message ="<div class=\"mapWindow\">" 
								+"<div class=\"box\">" 
								+"<div style=\"float: left;\" class=\"title\">本次发放时间：</div>"
								+"<div style=\"float: left;\" class=\"txt\">"+data[i].issueTime+"</div>" 
								+"<div style=\"float: left;\" class=\"title\" >本次发放地点：</div>"
								+"<div style=\"float: left;overflow: hidden;width: 142px;text-overflow: ellipsis;white-space: nowrap;\" class=\"txt\" title=\""+data[i].issueAddress+"\">"+data[i].issueAddress+"</div>" 
								+"<div style=\"float: left;clear: both;\" class=\"title\">农场主：</div>"
								+"<div style=\"float: left;\" class=\"txt\">"+data[i].createUser+"</div>" 
								+"<div style=\"float: left;\" class=\"txt\"><a href=\"javascript:goFarm('"+data[i].id+"')\" title=\"去购物\" style=\"color:#924a48;font-size: 16px;padding-left: 53px;\">去购物</a></div>"
								+"<div class=\"framer\">"
								+"<img  src='"+data[i].headPath+"'/>"
								+"<a href=\"javascript:goFarm('"+data[i].id+"')\" title=\"点击查看农场\" class=\"name\">"+data[i].name+"</a>"
								+"<a class=\"joinFramer\" href=\"javascript:joinFarm('"+data[i].id+"')\"  farmId=\""+data[i].id+"\">加入农场</a>"
								+"</div></div></div>";
							addIcon(data[i].longitude,data[i].latitude,message);//添加定位图标
						}
					}else{
						var message ="<p id=\"nofarm\" style=\"color:#924a48; font-size:18px; line-height:30px;\">该地区没有团购农场，您可以创建一个</p><a style=\"width:93px; height:30px; background-color:#ff5a5a;color:#ffeded;font-size:16px;text-align:center; line-height:30px;display:block;\" href=\"javascript:goUserCenter()\">创建</a>";
						var center = map.getCenter();
						addIcon(center.lng,center.lat,message,true);//添加定位图标
					}
				}});
            }
        }
    });
    ls.search(area);
}

function goFarm(farmId){
	location.href=basePath+"/circle/circleIndex.action?circleId="+farmId;
}

function joinFarm(farmId)
{
	$.ajax({
		url:basePath+'/circle/joinCircle.action?circleId='+farmId,
		dataType:'json',
		success:function(result){
			//0加入失败    1加入成功	2未登陆
			if(result.msg == "")
			{
				$("#viewId").val("2");
				$(".mask").show();
				$(".login").show();
				ShowBox.Show("请登录系统", 2);
			}else if(result.msg=="success"){
				getMyCircle();
				ShowBox.Show("加入成功", 1);
			}else{
				ShowBox.Show(result.msg, 3);
			}
		}
	});
}

//地图搜索2
function searchMap2() {
	document.getElementById("searchTxt").value = $("#searchTxt2").attr("value"); //得到地区
	document.getElementById("searchTxt2").value="";
	searchMap();
}


//搜索附近的圈子
function search()
{
	if(event.keyCode ==13)
	{
		searchMap();
	}
}

//显示地图下方 “附近的农场”
function viewFarms(data)
{
	if(data && data!= ""){
		var html=new Array();
		if(data.length>0)
		{
			$(".farms").show();
			html.push("<div class=\"farmers\">");
			html.push("<ul>");
		}
		else
		{
			$(".farms").hide();
			$(".n_right").hide();
			$(".farmers").remove();
		}
		//更多农场按钮
		if(data.length>9)
		{
			$(".n_right").show();
		}
		else
		{
			$(".n_right").hide();
		}
		for(var i=0;i<data.length;i++)
		{
			html.push("<li>");
			html.push("<a class=\"joinFarm\" href=\"javascript:joinFarm('"+data[i].id+"')\"  farmId=\""+data[i].id+"\">");
			html.push("<div class=\"tx\">");
			html.push("<img style=\"width: 85px;height: 85px;\" src=\""+data[i].headPath+"\" /><div class=\"mask\" >加入农场</div>");
			html.push("</div>");
			html.push("</a>");
			html.push("<a href=\"javaScript:goFarm("+data[i].id+");\">");
			html.push(data[i].name);
			html.push("</a>");
			html.push("</li>");
		}
		//显示附近的农场
		if(data.length>0)
		{
			$(".farms").show();
			html.push("</ul>");
			html.push("</div>");
		}
		$(".farmers").remove();
		$(".farms").after(html.join(""));
	}
}


function goUserCenter(){
	$.ajax({
		url:basePath+'/login/checkUserIsLogin.action',
		dataType:'text',
		success:function(result){
			//0未登录    1已登录
			if(result == "0")
			{
				$("#viewId").val("1");
				$(".mask").show();
				$(".login").show();
			}else{
				url =basePath+"/usercenter/toCreateCircle.action";
				window.location.href = url;
			}
		}
	});
}

//我要创建圈子
$(".joins a").live("click",function(){
	goUserCenter();
});



