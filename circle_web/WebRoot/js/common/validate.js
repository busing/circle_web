/**
 * 以下是validate自带的验证方法
 * required() //必填验证元素
 * remote(url) //请求远程校验。url通常是一个远程调用方法  
 * remote: {//可以ajax校验  远程地址只能输出 "true" 或 "false"，不能有其它输出
 *   url: "check-email.action",     //后台处理程序
 *   type: "post",               //数据发送方式
 *   dataType: "json",           //接受数据格式  
 *   data: {                     //要传递的数据
 *       username: function() {
 *           return $("#username").val();
 *       }
 *   }
 *}
 * minlength(length)//设置最小长度
 * maxlength(length)//设置最大长度
 * rangelength(range)//	设置一个长度范围[min,max]
 * min(value)//设置最小值
 * max(value)//设置最大值
 * email()//验证电子邮箱格式
 * range(range)//设置值的范围
 * url()//验证URL格式
 * date()//验证日期格式(类似30/30/2008的格式,不验证日期准确性只验证格式)
 * dateISO()//验证ISO类型的日期格式
 * dateDE()//验证德式的日期格式（29.04.1994 or1.1.2006）
 * number()//验证十进制数字（包括小数的）
 * digits()//	验证整数
 * creditcard()//验证信用卡号
 * accept(extension)//验证相同后缀名的字符串
 * equalTo(other)//验证两个输入框的内容是否相同
 * phoneUS()//验证美式的电话号码
 */
/*jQuery.extend(jQuery.validator.messages, {
      required: "必选字段",
		remote: "请修正该字段",
		email: "请输入正确格式的电子邮件",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.format("请输入一个长度最多是 {0} 的字符串"),
		minlength: jQuery.format("请输入一个长度最少是 {0} 的字符串"),
		rangelength: jQuery.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
		range: jQuery.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max: jQuery.format("请输	入一个最大为 {0} 的值"),
		min: jQuery.format("请输入一个最小为 {0} 的值")
});*/

/**
 * 自定义验证方法格式
 * 参数method是一个函数,接收三个参数(value,element,param)
 * value是元素的值,element是元素本身
 * param是参数,我们可以用addMethod来添加除built-in Validation
 * methods之外的验证方法 比如有一个字段,只能输一个字母,范围是a-f,写法如下:
 */
//addMethod的第一个参数,就是添加的验证方法的名子,这时是af
//addMethod的第二个参数,是一个函数,这个比较重要,决定了用这个验证方法时的写法
//addMethod的第三个参数,就是自定义的错误提示,这里的提示为:"必须是一个字母,且a-f"
//如果只有一个参数,直接写,如果af:"a",那么a就是这个唯一的参数,如果多个参数,用在[]里,用逗号分开
//$.validator.addMethod("af",function(value,element,params){ 
//if(value.length>1){
//   return false;

//   }
//   if(value>=params[0]&& value<=params[1]){

//    return true;

//   }else{

//    return false;

//   }

//},"必须是一个字母,且a-f");


//用的时候,比如有个表单字段的id="username",则在rules中写
//username:{
//  af:["a","f"]
//}

//输入数值不能超过100
//$.validator.addMethod("max100",function(value,element,params){ 
//	  	   if(value>params){
//	  	   	return false;
//	  	   }else{
//	  	   	return true;
//	  	   }
//			},"不能大于100");
//用的时候,比如有个表单字段的id="username",则在rules中写
//username:{
//max100:100
//}

//输入值不能超过100  动态改变错误信息提示
//$.validator.addMethod("max100",function(value,element,params){ 
//	  	   
//	  	   if(value>params[0]){
//	  	   	return false;
//	  	   }else{
//	  	   	return true;
//	  	   }
//			},jQuery.format("{1}"));
//用的时候,比如有个表单字段的id="username",则在rules中写
//username:{
//max100:[100,"输入值不能超过100"]//第二个值用来作为错误提示信息
//}

//输入值不能超过100，可以通过true 执行验证
//$.validator.addMethod("max100",function(value,element,params){ 
//	  	   
//	  	   if(value>100){
//	  	   	return false;
//	  	   }else{
//	  	   	return true;
//	  	   }
//			},"输入值不能超过100");
//用的时候,比如有个表单字段的id="username",则在rules中写
//username:{
//max100:true
//}


//后台验证用户名是否是张三
//$.validator.addMethod("checkusername",function(value,element,params){ 
//	  	   var flag=true;
//	  	   var basePath='${basePath}'	
//			 $.ajax({ 
//			 	async: false,
//				url : basePath+'/validatedemo/ajaxCheck.action', 
//				type : 'post', 
//				data: {userName:value}, 
//				success : function(data) { 		
//					flag=true;
//				},
//				error: function(data){
//				  flag=false;
//				}	 
//	  		 });
//	  		return flag;
//			},"不能为张三");

//用的时候,比如有个表单字段的id="username",则在rules中写
//username:{
//checkusername:true
//}




/**
 * 表单验证
 * @param id   from表单的id
 * @param params  json对象形式传入  key为validate 初始化时你要更改的参数
 * 				debug:true  //true 只验证不提交
 * 				submitHandlerL:function(form){$(form).ajaxSubmit();} //通过验证后运行的函数,里面要加上表单提交函数,否则表单不会提交
 * 				ignore:".ignore"  //对某些元素不进行验证（class为ignore）  
 * 				rules:{name:{required:true},email:{required:true}}//自定义规则,key:value的形式,key是要验证的元素,value可以是字符串或对象
 * 				messages:{name:{required:"不能为空"},email:{required:"不能为空"}} //自定义的提示信息key:value的形式key是要验证的元素,值是字符串或函数;内部也是key value形式 key是元素，value错误信息
 * 				groups:{username:"fname lame"}  //对一组元素的验证,用一个错误提示,用error Placement控制把出错信息放在哪里
 * 				Onubmit:true //Onubmit Boolean 默认:true是否提交时验证
 * 				onfocusout:true//Boolean 默认:true 是否在获取焦点时验证
 * 				onkeyup:true//onkeyup Boolean 默认:true 是否在敲击键盘时验证
 * 				onclick:true//onclick Boolean 默认:true是否在鼠标点击时验证（一般验证checkbox,radiobox）
 * 				focusInvalid:true//focusInvalid Boolean 默认:true提交表单后,未通过验证的表单(第一个或提交之前获得焦点的未通过验证的表单)会获得焦点
 *				focusCleanup:false//focusCleanup Boolean 默认:false当未通过验证的元素获得焦点时,并移除错误提示（避免和 focusInvalid.一起使用）
 *				errorClass:"error" //  errorClass String 默认:"error"指定错误提示的css类名,可以自定义错误提示的样式
 *				errorElement:"label" //errorElement String 默认:"label"使用什么标签标记错误
 *				wrapper:"li"//使用什么标签再把上边的errorELement包起来
 *				errorLabelContainer:"#messageBox" //把错误信息统一放在一个容器里面
 *				showErrors:function(errorMap,errorList){};//跟一个函数,可以显示总共有多少个未通过验证的元素
 *				errorPlacement:function(error,element){};//跟一个函数,可以自定义错误放到哪里
 *				success:"valid"  //要验证的元素通过验证后的动作,如果跟一个字符串,会当做一个css类,也可跟一个函数
 *				highlight:  //可以给未通过验证的元素加效果,闪烁等
 * @return
 */
function formValidate(id,params){
		var param={
				rules:{},//验证内容
				messages:{},//自定义提示信息
				showErrors:showErrors//错误信息显示样式		
		}
		$.extend(param,params) ;

		$("#"+id).validate(param);
	
}


/**
 * 重写错误信息显示样式
 * @return
 */
function showErrors(){   

    var t = this;   

    for ( var i = 0; this.errorList[i]; i++ ) {   
        var error = this.errorList[i];   
        this.settings.highlight && this.settings.highlight.call( this, error.element, this.settings.errorClass, this.settings.validClass );   
        var elename = this.idOrName(error.element);   

        // 错误信息div   

        var errdiv = $('div[for='+ elename + ']'); 
        var errimg = $('img[for='+ elename + ']');   

        if(errdiv.length == 0){ // 没有div则创建  
        // div错误信息显示
          errdiv = $('<div class="validatebox-tip">'
          +   '<span class="validatebox-tip-pointer"> </span>'
          +   '<span style="position:absolute;display:inline-block;top:0px;left:10px;padding:1px;border:1px solid #CC9933;background:#FFFFCC;z-index:9900001;font-size:12px;" class="errmsg"> </span>'   
          +   '</div> '); 
      
            errdiv.attr({"for":  this.idOrName(error.element), generated: true}).addClass(this.settings.errorClass);   
            errdiv.appendTo($('body'));   
            var tt=$(error.element);//当前错误元素
            if(this.idOrName(error.element)=='radio'||this.idOrName(error.element)=='checkbox'){
            	var td=$("input=[name="+this.idOrName(error.element)+"]").parent();//控件父元素
            	tt=td.children("input:last-child");//最后一个子元素
            }
        	var w=tt.width();//元素宽度
        	var offset=tt.offset();//元素坐标
        	var left1=offset.left +w + 2;
        	var top1=offset.top;
            
            $('div[for="'+ this.idOrName(error.element) + '"]').css({position: 'absolute',left : (left1) + 'px',top : (top1) + 'px'}); // 信息显示在错误元素后面   
        	//$('div[for="'+ this.idOrName(error.element) + '"]').fadeIn(100);
        	$('div[for="'+ this.idOrName(error.element) + '"]').show();
        }else{
        	var tt=$(error.element);
            if(this.idOrName(error.element)=='radio'||this.idOrName(error.element)=='checkbox'){
            	var td=$("input=[name="+this.idOrName(error.element)+"]").parent();
            	tt=td.children("input:last-child");
            }
        	var w=tt.width();//控件宽度
        	var offset=tt.offset();
        	var left1=offset.left +w + 2;
        	var top1=offset.top;
            
            $('div[for="'+ this.idOrName(error.element) + '"]').css({position: 'absolute',left : (left1) + 'px',top : (top1) + 'px'}); // 显示在空间后面   
        	//$('div[for="'+ this.idOrName(error.element) + '"]').fadeIn(100);
            $('div[for="'+ this.idOrName(error.element) + '"]').show(); 
        }

//        if(errimg.length == 0){ // 没有img则创建   
//			
//            errimg = $('<img  src="../css/images/error.png">')   
//
//            errimg.attr({"for":  this.idOrName(error.element), generated: true});   
//			var name=this.idOrName(error.element);
//			
//			if (name=='radio' || name=='checkbox') { //如果是radio或checkbox 
//				
//				errimg.appendTo($("input=[name="+name+"]").parent()); //将错误信息添加当前元素的父结点后面 
//			} else { 
//				errimg.insertAfter(error.element); 
//			} 			 				
//        }   
        
        errimg.show();   

        errdiv.find(".errmsg").html("<font style='color:red;'>*</font>"+error.message+"&nbsp&nbsp" || ""); 
        errimg.attr({"title":  error.message}); 
//        errdiv.hide();  

        // 鼠标放到图片显示错误   

        $(errimg).hover(function(e){               				
           $('div[for="'+ $(this).attr('for') + '"]').css({position: 'absolute',left : (e.pageX+5) + 'px',top : (e.pageY+5) + 'px'}); // 显示在鼠标位置偏移20的位置   
             $('div[for="'+ $(this).attr('for') + '"]').fadeIn(100);
        },   
        function(){   
           $('div[for="'+ $(this).attr('for') + '"]').fadeOut(100); 
        });   

        
     // 空间获取焦点显示错误信息   
        $(error.element).focus(function(){   	
        	var tt=$(this);//当前错误元素
        	if(t.idOrName(this)=='radio'||t.idOrName(this)=='checkbox'){
            	var td1=$("input=[name="+t.idOrName(this)+"]").parent();//父元素
            	tt=td1.children("input:last-child");//最后一个子元素
            }
        	var w=tt.width();//控件宽度
        	var offset=tt.offset();
        	var left1=offset.left +w + 2;
        	var top1=offset.top;
        	$('div[for="'+ t.idOrName(this) + '"]').css({position: 'absolute',left : (left1) + 'px',top : (top1) + 'px'}); // 显示在空间后面   
        	//$('div[for="'+ t.idOrName(this) + '"]').fadeIn(100); 
        	  $('div[for="'+ t.idOrName(this) + '"]').show(); 
        }); 
        $(error.element).blur(function(){  	
    		//$('div[for="'+ t.idOrName(this) + '"]').fadeOut(100);
        	  $('div[for="'+ t.idOrName(this) + '"]').hide();
    	});
       
        	// 鼠标放到控件上显示错误
            $(error.element).hover(
            	function(){   	
            		var tt=$(this);
            		if(t.idOrName(this)=='radio'||t.idOrName(this)=='checkbox'){
                    	var td1=$("input=[name="+t.idOrName(this)+"]").parent();
                    	tt=td1.children("input:last-child");
                    }
            		var w=tt.width();
            		var offset=tt.offset();
            		var left1=offset.left +w + 2;
            		var top1=offset.top;
            		$('div[for="'+ t.idOrName(this) + '"]').css({position: 'absolute',left : (left1) + 'px',top : (top1) + 'px'}); // 显示在空间后面   
            		if(this.id!=document.activeElement.id){
            			//$('div[for="'+ t.idOrName(this) + '"]').fadeIn(100); 
            			  $('div[for="'+ t.idOrName(this) + '"]').show(); 
            		}
            		
            	},
            	function(){  
            		if(this.id!=document.activeElement.id){
            			//$('div[for="'+ t.idOrName(this) + '"]').fadeOut(100);
            			  $('div[for="'+ t.idOrName(this) + '"]').hide();
            		}
            	}	
            	
           ); 
            
            
        }
     // 鼠标放到控件上显示错误   
//        $(error.element).hover(function(e){
//        	$('div[for="'+ t.idOrName(this) + '"]').css({color:'red',position: 'absolute',left : (e.pageX+5) + 'px',top : (e.pageY+5) + 'px'}); // 显示在鼠标位置偏移20的位置   
//        	$('div[for="'+ t.idOrName(this) + '"]').fadeIn(100); 
//        },
//        function(){   
//           $('div[for="'+ t.idOrName(this) + '"]').fadeOut(100);   
//        });
 //   }   


    // 校验成功的去掉错误提示   

    for ( var i = 0; this.successList[i]; i++ ) {   
            $('div[for="'+ this.idOrName(this.successList[i]) + '"]').remove();   
            $('img[for='+ this.idOrName(this.successList[i]) + ']').hide();   
    }         
    // 自定义高亮   

    if (this.settings.unhighlight) {        		
        for ( var i = 0, elements = this.validElements(); elements[i]; i++ ) {   
            this.settings.unhighlight.call( this, elements[i], this.settings.errorClass, this.settings.validClass );   
        }   
    }   
}   