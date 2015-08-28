var commonImageUrl = "http://localhost:8080/circle_web/";
//var service_url = 'http://192.168.1.240/animal_online_service_agent/';
var service_url = 'http://localhost:8080/circle_web/';
//var qurl = 'http://www.njpostjjt.com/post_service/service.do?key=';
//var wurl = 'http://www.njpostjjt.com/post_service/';
//var page_url = "http://192.168.1.240/mall/";
var page_url = "http://localhost:8080/circle_web/";

/**
 * 方法描述:文件上传组件初始化
 * 参数：
 *    id：页面中上传组件ID，<input type='file' id='***'/> [必须]
 *    basePath：工程访问路径 [必须]
 *    param：上传组件所需参数，{key:value,key:value}形式。[类型:js对象]
 *    		key值具体含义：
 *          auto : 是否选中文件后自动上传，true：自动上传，false：选中后出现待上传文件列表，需手动上传，[默认：true] [可选]
 *          buttonText : 上传文件的按钮文字 [默认：'上传附件'] [可选]
 *          fileObjName : 上传的文件后台接受时的变量名 [必须]
 *          fileSizeLimit : 上传的文件大小限制。可以用数字来限制，如果用数字，默认单位为KB，如果用字符串，提供的单位有：B、KB、MB、GB，将此属性值设为0为不限制大小 [类型:int或string] [可选]
 *          fileTypeDesc : 此参数与fileTypeExts配合使用，文件类型过滤器的文字描述，将会出现在弹出的文件选择对话框中文件类型选择框中。 [默认：'All Files'] [可选]
 *          fileTypeExts : 文件类型过滤器，在弹出文件选择对话框时限制可以选择的文件类型，格式示例：'*.gif; *.jpg; *.png' [默认：*.*] [可选]
 *          height : 上传文件的按钮高度 [类型:int] [默认：20] [可选]
 *          multi : 是否支持多文件上传，true:在弹出的文件选择框中可以一次选择多个文件（多个文件要在同一个同一级目录下），false：一次只能选择一个文件 [默认：true] [可选]
 *          onUploadSuccess : 上传成功回调函数，本回调函数有三个参数，按顺序：file, data, response [必须]
 *                            file : 上传文件信息，包括：文件名、文件大小、文件类型等
 *                            data : 服务端的响应值
 *                            response : 响应状态，在成功回调函数里一般为true
 *          queueID : 待上传文件队列(有上传进度条，并且上传成功后显示1-2秒就会消失)需要自定义显示在指定容器下的容器ID [默认：false] [可选]
 *                    如：<div id="***"></div>，如果想让文件队列显示在这个层里，本参数就是这个层的ID，这个层放在页面上的位置就是文件队列显示的位置
 *                    如果设置此参数为false，文件队列显示在页面文件组件所在位置的下方
 *          swf : 文件上传组件核心swf文件路径 [不可传值：改变此路径会导致文件上传组件失效]
 *          uploader : 处理上传的文件的后台请求URL [必须]
 *          width : 上传文件的按钮宽度 [类型:int] [默认：80] [可选]
 *          
 *          
 * date:2013-11-15
 * add by: liu_tao@xwtec.cn
 */
function initUploadFileCom(id,basePath,param) {
	var uploadifyParam = {
		'height' : 20,
		'width' : 80,
	    'swf' : basePath + '/js/common/component/uploadify/uploadify.swf',
	    buttonText : '上传附件'
	};
	$.extend(uploadifyParam,param);
	$('#' + id).uploadify(uploadifyParam);
}

/**
 * 方法描述:ajax提交共通方法
 * 参数：
 *    actionUrl：请求路径 [必须]
 *    successFun：成功回调函数 [必须]
 *    paramData：请求参数，{key:value,key:value}形式。[类型:js对象] [可选]
 *    async : 是否为同步请求[true：同步，不传参为异步] [默认：异步] [类型：boolean] [可选]
 *    errorFun：出错回调函数 [可选]
 *    newTimeout：请求超时时间 [默认：20000] [可选]
 *    
 * date:2013-11-15
 * add by: liu_tao@xwtec.cn
 */
function ajax(actionUrl, successFun, paramData,isSyn,errorFun,newTimeout) {
	var mytimeout = 20000;
	//打开蒙板
	// cover();
	if (typeof successFun != "function" || actionUrl == undefined) {
		return;
	};
	if (newTimeout != undefined && newTimeout != null) {
		mytimeout = newTimeout;
	};
	ajaxPoint  = $.ajax({
		type : "POST",
		timeout : mytimeout,
		dataType : "json",
		url : actionUrl,
		global : false,
		data : paramData,
		async : isSyn ? false : true,
		success : function(data) {
			ajaxPoint = undefined;
			successFun(data);
			// closeCover();
		},
		error : function(request) {
			ajaxPoint = undefined;
			try {
				if(request.status == 404) {
					ShowBox.Show("请求的服务不存在！",3);
				} else if(request.status == 500) {
					ShowBox.Show("系统内部错误，请将下面详细信息截图，联系并发送系统管理员!" + "\r\n" + request.responseText,3);
				} else if(request.status == 0) {
					ShowBox.Show('请求超时，服务端长时间没有响应！',3);
				} else {
					// Session超时
					if(request.responseText) {
						window.location = request.responseText + '/logout/logout.action?flag=1';
					} else {
						// alert('系统发生未知错误！');
					}
				}
				if(typeof errorFun == "function") {
					errorFun();
				}
			} catch (e) {
				// alert('系统发生未知错误！');
			} finally {
				//关闭蒙板
				// closeCover();
			}
		}
	});
};

/**
 * 方法描述:checkbox全选方法
 * 参数：
 *    checkId：全选checkbox的ID [类型:string] [必须]
 *    checkAll：可以标识列表上全部checkbox的jquery可以使用的名称，如：".class"、"name" [类型:string] [必须]
 *    
 * date:2013-11-26
 * add by: liu_tao@xwtec.cn
 */
function check(checkId,checkAll) {
	$("#" + checkId).bind("click",function() {
		if($("#" + checkId).is(":checked")) {
			$(checkAll).attr("checked", true);
		} else {
			$(checkAll).attr("checked", false);
		}
	});
	$(checkAll).bind("click",function() {
		$(checkAll).each(function() {
			if(!($(this).is(":checked"))) {
				$("#" + checkId).attr("checked", false);
				return false;
			}
			$("#" + checkId).attr("checked", true);
		});
	});
}


/**
 * 初始化列表页面地市下拉框
 * @param selectId 下拉框ID, areaCodes 管理员所属地市, sAreaCode 需要选中的地市编码
 */
function initSelect(selectId,areaCodes,sAreaCode,flag){
	var areaCode = areaCodes.split("|");
	var select = '<option value="">全部</option>';
	if(flag==0){
		select = '';
	}
	for(var i = 0; i<areaCode.length; i++){
		if(sAreaCode == areaCode[i]){
			select += '<option selected value="'+areaCode[i]+'">'+cityName[areaCode[i]]+'</option>';
		}else{
			select += '<option value="'+areaCode[i]+'">'+cityName[areaCode[i]]+'</option>';
		}
	}
	$("#"+selectId).html(select);
}

/**
 * 方法描述:iframe高度自适应
 * 参数：
 *    iframeId：需要自适应的iframe的dom节点ID [必须]
 *    height: 自适应时需要增加的高度 [默认]
 * date:2013-12-06
 * add by: liu_tao@xwtec.cn
 */
function adaptive(iframeId,height) {
	var ifm= document.getElementById(iframeId);
	var subWeb = document.frames ? document.frames[iframeId].document : ifm.contentDocument; 
	if(ifm != null && subWeb != null) {
		var heightTemp = 20;
		if(height) {
			heightTemp = height;
		}
		ifm.height = subWeb.body.scrollHeight + heightTemp;
	}
}

/**
 * 根据管理员权限初始化页面城市
 */
function initCity(checkId,areaCodes){
	var areaCode = areaCodes.split("|");
	var checkCity = "";
	for(var i=0;i<areaCode.length;i++){
		if(areaCode[i]!='')
			checkCity += '<input type="checkbox" name="areaCode" class ="check_city" value="'+areaCode[i]+'"/>&nbsp;&nbsp;'+cityName[areaCode[i]];
	}
	$("#"+checkId).html(checkCity);
}

var cityName = {"320100":"南京",
		"320200":"无锡",	
		"320300":"徐州",
		"320400":"常州",
		"320500":"苏州",
		"320600":"南通",
		"320700":"连云港",
		"320800":"淮安",
		"320900":"盐城",
		"321000":"扬州",
		"321100":"镇江",
		"321200":"泰州 ",
		"321300":"宿迁"};


/***********蒙板效果弹出框*****************
//添加弹窗口
function addDiv() {
  var as = "<div id='ajax_fgc' style='dipslay:none'></div>"+
  " <div class='whh_tcbox xgtx' id='loading_div' style='box-shadow: 0px 0px 0px #ccc; left:49%; *left:49%; _left:49%; top:50%;width:36px;padding:1px;margin:0px; display: none ;position: fixed ;index:100;height:40px;"
  +"background: url(http://pic.jswxcs.cn/attms/image/usercenter/load5.gif) left center no-repeat;text-align:center'></div>";
	$("body").append(as);
}

var isIe=(document.all)?true:false;
//覆盖页面   
function cover() {
	 addDiv();
	 //closeWindow();   
	 var bWidth=parseInt(document.documentElement.scrollWidth);   
	 var bHeight=parseInt(document.documentElement.scrollHeight);   
	 var back=document.createElement("div");   
	// back.id="back";   
	 var styleStr="z-index:4;top:0px;left:0px;position:absolute;background:#666;width:"+bWidth+"px;height:"+bHeight+"px;";   
	 styleStr+=(isIe)?"filter:alpha(opacity=44);":"opacity:0.44;";   
	 back.style.cssText=styleStr; 
	 jQuery("#ajax_fgc").html(back); 
	 jQuery("#ajax_fgc").css("display","block");
	 jQuery("#loading_div").css("display","block");
}

//移除弹窗口
function closeCover() {
	if($("#ajax_fgc") && $("#loading_div")) {
		$("#loading_div").css("display","none");
		$("#ajax_fgc").css("display","none");
		//$('#loading_div').remove();
		//$('#ajax_fgc').remove();
	} 
} */

/**
 * 禁止使用右键菜单
 * add by houx 
 */
function disableRightClick(e){
   var message = "";
   if(!document.rightClickDisabled){ // initialize
   
	  if(document.layers){
	    document.captureEvents(Event.MOUSEDOWN);
	    document.onmousedown = disableRightClick;
	  }
	  else {
	  	document.oncontextmenu = disableRightClick;
	  }
	  return document.rightClickDisabled = true;
   }
   if(document.layers || (document.getElementById && !document.all)){
	  if (e.which==2||e.which==3){
	    //alert(message);
	    return false;
	  }
   }else{
	  //alert(message);
	  return false;
   }
 }
 //禁用右键
 //disableRightClick();

/**
 * 方法描述：初始化Grid组件
 * 参数：
 *    id : Grid所在容器的id,如：<div id="***"></div> [类型:string] [必须]
 *    columns : 列头信息 [类型:js数组] [必须]
 *    data : 行数据 [类型:js数组] [必须]
 */
function initGrid(id,columns,data,param) {
	var gridParam = {
		columns : columns,
		data : {Rows:data},
		usePager:false,
		rownumbers : true
	};
	$.extend(gridParam,param);
	$("#" + id).ligerGrid(gridParam);
}

/**
 * 插码应用类别(第一位)
 */
var sortVal = "ABCDEFGHI".split("");

var sortName = {"A":"医疗",
		"B":"旅游",
		"C":"交通",
		"D":"政务",
		"E":"生活",
		"F":"购物",
		"G":"求职",
		"H":"教育",
		"I":"金融"};

/**
 * 方法描述:quicksearch快速搜索
 * 参数：
 *    arrList：下拉框的列表 [类型:Array] [必须]
 *    inputer：快速搜索文本框的id[类型:string] [必须]
 *    searchResult：快速搜索显示div的id[类型:string] [必须]
 *    checkboxId：下拉框的id[类型:string] [必须]
 * date:2014-6-3
 * add by: zhangyu@xwtec.cn
 */
function quicksearch(arrList,inputer,searchResultId,checkboxId){
	var $searchResult=$("#"+searchResultId);
	var $inputer=$("#"+inputer);
	arrList.sort(function(a,b){
	 if(a.length > b.length)
	 	return 1;
	 else if(a.length==b.length)
	 	return a.localeCompare(b);
	 else 
	 	return -1;
	 });
	var objouter = $("#__smanDisp"); //显示的DIV对象
	var selectedIndex = -1;
	var intTmp; //循环用的
	
	//文本框失去焦点
	$inputer.bind("blur",function(){
		$searchResult.hide();
	});
	//文本框按键抬起
	$inputer.bind("keyup",function(){
		checkKeyCode();
	});
	//objInput.onkeyup = checkKeyCode;
	
	//文本框得到焦点
	$inputer.bind("focus",function(){
		checkAndShow();
	});
	//objInput.onfocus = checkAndShow;
	
	function checkKeyCode(){
		var ie = (document.all)? true:false
		if (ie){
			var keyCode = event.keyCode
			if (keyCode==40||keyCode==38){ //下上
				var isUp = false;
				if(keyCode == 40){
					isUp = true ;
				}
				chageSelection(isUp)
			}else if (keyCode==13){//回车
				outSelection(selectedIndex);
			}else{
				checkAndShow();
			}
		}else{
			checkAndShow();
			
		}
		//divPosition();
	}

	function checkAndShow(){
		var strInput = $inputer.val();
		if (strInput!=""){
			//divPosition();
			selectedIndex = -1;
			objouter.innerHTML = "";
			for (intTmp = 0;intTmp < arrList.length; intTmp++){
				for(i = 0;i < arrList[intTmp].length; i++){
					if (arrList[intTmp].substr(i, strInput.length).toUpperCase() == strInput.toUpperCase()){
						var arrlist=arrList[intTmp].split(",");
						addOption(arrlist[0],arrlist[1], strInput);
						break;
					}
				}
			}
			$searchResult.html(objouter.innerHTML);
			$searchResult.show();
		}else{
			$searchResult.hide();
		}
		function addOption(value1,value2,keyw){
			var showHtml = "";
			var v = value1.replace(keyw,"<b><font color=red>"+keyw+"</font></b>");
			objouter.innerHTML += "<div style=\"cursor:pointer\" onmouseover=\"this.className='sman_selectedStyle'\" onmouseout=\"this.className=''\" onmousedown=\"selectInput('"+value1+"','"+value2+"','"+inputer+"','"+checkboxId+"')\">" + v + "</div>"
		}
	}
	
	
	function chageSelection(isUp){
		if ($searchResult.attr("display")=="none"){
			$searchResult.show();
		}else{
			if (isUp)
				selectedIndex++
			else
				selectedIndex--
		}
		var maxIndex = objouter.children.length-1;
		if (selectedIndex<0){
			selectedIndex=0;
		}
		if (selectedIndex>maxIndex) {
			selectedIndex=maxIndex;
		}
		for (intTmp = 0; intTmp <= maxIndex; intTmp++){
			if (intTmp==selectedIndex){
				objouter.children[intTmp].className = "sman_selectedStyle";
			}else{
				objouter.children[intTmp].className = "";
			}
		}
	}
	function outSelection(Index){
		$inputer.val(objouter.children[Index].innerText);
		$searchResult.show();
	}
}
/**
 * 方法描述:selectInput文本框和下拉框赋值
 * 参数：
 *    value1: 快速搜索文本框显示的值[类型:string] [必须]
 *    value2: 下拉框的value[类型:string] [必须]
 *    inputer：快速搜索文本框的id[类型:string] [必须]
 *    checkboxId：下拉框的id[类型:string] [必须]
 * date:2014-6-3
 * add by: zhangyu@xwtec.cn
 */
function selectInput(value1,value2,inputerId,checkboxId){
	$("#"+inputerId).val(value1);
	$("#"+checkboxId).find("option[value="+value2+"]").attr("selected",true);
	
}

/**
 * 导出后台
 */
function exportCode(path, formId,rpath){
	var form = $('form[id="' + formId + '"]');
	var basePath = $("#basePath").val();
	form.attr("action", path);
	form.submit(); 
	form.attr("action",rpath);
}

/**
 * 计算天数差的函数
 * @param {Object} sDate1 开始时间
 * @param {Object} sDate2 结束时间
 * add by ganwenxiu@xwtec.cn
 */
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
   var  aDate,  oDate1,  oDate2,  iDays;
   aDate  =  sDate1.split("-");
   oDate1 = new Date(aDate[0] , aDate[1] ,aDate[2]); //转换为12-18-2006格式 
   aDate = sDate2.split("-"); 
   oDate2 = new Date(aDate[0] , aDate[1] , aDate[2]);
   iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
   
   return iDays;
}

/**
 * 时间校验应用与查询条件只有时间空间的报表
 * @param {Object} isEmpty 是否允许时间查询条件为空
 * @param {Object} startTimeId 开始时间id
 * @param {Object} endTimeId 结束时间id
 * @param {Object} formId 表单id
 * @param {Object} actionInfo action跳转地址
 * 
 * add ganwenxxiu@xwtec.cn
 */
function checkTime(isEmpty,startTimeId,endTimeId,formId,actionInfo){
	var startTime = $("#"+startTimeId).val();
	var endTime = $("#"+endTimeId).val();
	
	if(!isEmpty){
		if(startTime == "" || endTime == ""){
			ShowBox.Show("开始时间和结束时间不能为空！");
			return;
		}
	}
	if(startTime>endTime){
		ShowBox.Show("开始时间不能大于结束时间");
   	    return;
    }else {
	    var dt = DateDiff(startTime,endTime);
	    if(dt>31){
	    	ShowBox.Show("时间区间不能间隔31天!");
   	    	return;
	    }else{
	    	 $("#"+formId).attr("action",actionInfo);
   	 		 $("#"+formId).submit();
	    }
    }
}

//获取URL参数
function requestParams(){
	var query = location.search.substring(1);
	var result = new Array();
	if(query){
		var arQuery = query.split("&");
		for(var i=0;i<arQuery.length;i++){
			var pos = arQuery[i].indexOf("=");
			var qName = arQuery[i].substring(0,pos);
			var qValue = arQuery[i].substring(pos+1);
			result[qName] = qValue;
		}
	}
if (typeof (result) == "undefined") {
      return "";
  } else {
      return result;
  }
}
