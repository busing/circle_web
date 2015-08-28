/*
    通用表单验证方法
    Validform version 5.3.2
	By sean during April 7, 2010 - March 26, 2013
	For more information, please visit http://validform.rjboy.cn
	Validform is available under the terms of the MIT license.
	
	Demo:
	$(".demoform").Validform({//$(".demoform")指明是哪一表单需要验证,名称需加在form表单上;
		btnSubmit:"#btn_sub", //#btn_sub是该表单下要绑定点击提交表单事件的按钮;如果form内含有submit按钮该参数可省略;
		btnReset:".btn_reset",//可选项 .btn_reset是该表单下要绑定点击重置表单事件的按钮;
		tiptype:1, //可选项 1=>pop box,2=>side tip(parent.next.find; with default pop),3=>side tip(siblings; with default pop),4=>side tip(siblings; none pop)，默认为1，也可以传入一个function函数，自定义提示信息的显示方式（可以实现你想要的任何效果，具体参见demo页）;
		ignoreHidden:false,//可选项 true | false 默认为false，当为true时对:hidden的表单元素将不做验证;
		dragonfly:false,//可选项 true | false 默认false，当为true时，值为空时不做验证；
		tipSweep:true,//可选项 true | false 默认为false，只在表单提交时触发检测，blur事件将不会触发检测（实时验证会在后台进行，不会显示检测结果）;
		label:".label",//可选项 选择符，在没有绑定nullmsg时查找要显示的提示文字，默认查找".Validform_label"下的文字;
		showAllError:false,//可选项 true | false，true：提交表单时所有错误提示信息都会显示，false：一碰到验证不通过的就停止检测后面的元素，只显示该元素的错误信息;
		postonce:true, //可选项 表单是否只能提交一次，true开启，不填则默认关闭;
		ajaxPost:true, //使用ajax方式提交表单数据，默认false，提交地址就是action指定地址;
		datatype:{//传入自定义datatype类型，可以是正则，也可以是函数（函数内会传入一个参数）;
			"*6-20": /^[^\s]{6,20}$/,
			"z2-4" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,4}$/,
			"username":function(gets,obj,curform,regxp){
				//参数gets是获取到的表单元素值，obj为当前表单元素，curform为当前验证的表单，regxp为内置的一些正则表达式的引用;
				var reg1=/^[\w\.]{4,16}$/,
					reg2=/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,8}$/;
				
				if(reg1.test(gets)){return true;}
				if(reg2.test(gets)){return true;}
				return false;
				
				//注意return可以返回true 或 false 或 字符串文字，true表示验证通过，返回字符串表示验证失败，字符串作为错误提示显示，返回false则用errmsg或默认的错误提示;
			},
			"phone":function(){
				// 5.0 版本之后，要实现二选一的验证效果，datatype 的名称 不 需要以 "option_" 开头;	
			}
		},
		usePlugin:{
			swfupload:{},
			datepicker:{},
			passwordstrength:{},
			jqtransform:{
				selector:"select,input"
			}
		},
		beforeCheck:function(curform){
			//在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
			//这里明确return false的话将不会继续执行验证操作;	
		},
		beforeSubmit:function(curform){
			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
			//这里明确return false的话表单将不会提交;	
		},
		callback:function(data){
			//返回数据data是json格式，{"info":"demo info","status":"y"}
			//info: 输出提示信息;
			//status: 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
			//你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
			//ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**, readyState:**, responseText:** }；
			
			//这里执行回调操作;
			//注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return false，则表单不会提交，如果return true或没有return，则会提交表单。
		}
	});
	
	Validform对象的方法和属性：
	tipmsg：自定义提示信息，通过修改Validform对象的这个属性值来让同一个页面的不同表单使用不同的提示文字；
	dataType：获取内置的一些正则；
	eq(n)：获取Validform对象的第n个元素;
	ajaxPost(flag,sync,url)：以ajax方式提交表单。flag为true时，跳过验证直接提交，sync为true时将以同步的方式进行ajax提交，传入了url地址时，表单会提交到这个地址；
	abort()：终止ajax的提交；
	submitForm(flag,url)：以参数里设置的方式提交表单，flag为true时，跳过验证直接提交，传入了url地址时，表单会提交到这个地址；
	resetForm()：重置表单；
	resetStatus()：重置表单的提交状态。传入了postonce参数的话，表单成功提交后状态会设置为"posted"，重置提交状态可以让表单继续可以提交；
	getStatus()：获取表单的提交状态，normal：未提交，posting：正在提交，posted：已成功提交过；
	setStatus(status)：设置表单的提交状态，可以设置normal，posting，posted三种状态，不传参则设置状态为posting，这个状态表单可以验证，但不能提交；
	ignore(selector)：忽略对所选择对象的验证；
	unignore(selector)：将ignore方法所忽略验证的对象重新获取验证效果；
	addRule(rule)：可以通过Validform对象的这个方法来给表单元素绑定验证规则；
	check(bool,selector):对指定对象进行验证(默认验证当前整个表单)，通过返回true，否则返回false（绑定实时验证的对象，格式符合要求时返回true，而不会等ajax的返回结果），bool为true时则只验证不显示提示信息；
	config(setup):可以通过这个方法来修改初始化参数，指定表单的提交地址，给表单ajax和实时验证的ajax里设置参数；
	
	凡要验证格式的元素均需添加datatype属性，datatype可选值内置有10类，用来指定不同的验证格式【如果还不能满足您的验证需求，可以传入自定义datatype，自定义datatype是一个非常强大的功能，有了这个基本可以实现你需要的任何验证需求，具体请参考demo页】。datatype：* | *6-16 | n | n6-16 | s | s6-18 | p | m | e | url
	*：检测是否有输入，可以输入任何字符，不留空即可通过验证；
	*6-16：检测是否为6到16位任意字符；
	n：数字类型；
	n6-16：6到16位数字；
	s：字符串类型；
	s6-18：6到18位字符串；
	p：验证是否为邮政编码；
	m：手机号码格式；
	e：email格式；
	url：验证字符串是否为网址。
	注意radio，checkbox，select这三类datatype从5.0版本开始删除，可以给这三类表单元素绑定其他任何内置或自定义的datatype。radio和checkbox元素只需给该组的第一个元素绑定datatype属性即可。
	
	其他的附加属性：
	nullmsg：是指定没有填入内容时出现的提示信息，不指定默认是“请填入信息！”，另外当datatype为radio、checkbox或select时，因为这三种类型只要为空值就表示出错，提示errormsg所指定信息，所以这三类不需要绑定该属性；
	errormsg：是指定验证格式不符时出现的提示信息，不指定默认是“请输入正确信息！”；
	ignore：绑定ignore=”ignore”的表单元素，当有输入时会验证所填数据是否符合datatype所指定数据类型（格式不对不能通过验证），当没有输入数据时也可以通过验证；
	recheck：是用来指定两个表单元素值一致性检测的另外一个对象，赋给它另外一个对象的name属性值即可；
	tip：是指定表单元素的提示信息;指定后该元素会有focus时提示信息消去，没有输入内容blur时出现提示信息的效果，请参看demo页的“备注”效果；
	altercss：是指定有tip属性的元素默认提示文字显示时的样式，当该元素focus时程序会把这个样式去掉，blur时如果值为空或者跟提示文字一样则再加上该样式；
	ajaxurl：指定ajax实时验证的后台文件路径，给需要后台数据库验证信息的对象绑定该属性。注意该文件输出的内容就是前台显示的验证出错的反馈信息，如果验证通过请输出小写字母”y”。后台页面如valid.php文件中可以用 $_POST["param"] 接收到值，Ajax中会POST过来变量param；
*/

/**
 * 定义提示信息显示方式 弹框
 * @param msg obj 检验返回对象
 */
function showMsg (msg,obj){
	if(obj.type != 2){
		ShowBox.Show(msg) ;
	}
}

/**
 * 
 * @param tiptype 提示信息显示方式 1 弹框显示  2 为在当前行的下一个单元格显示无单元格刚不显示 3、4为在当前单元格后面追加
 * @param datatype 自定义的数据校验类 为对象传入
 * @param params 为json对象形式传入  key为validform 初始化时你要更改的参数
 * 		    btnSubmit:"#btn_sub", //#btn_sub是该表单下要绑定点击提交表单事件的按钮;如果form内含有submit按钮该参数可省略;
			btnReset:".btn_reset",//可选项 .btn_reset是该表单下要绑定点击重置表单事件的按钮;
			tiptype:1, //可选项 1=>pop box,2=>side tip(parent.next.find; with default pop),3=>side tip(siblings; with default pop),4=>side tip(siblings; none pop)，默认为1，也可以传入一个function函数，自定义提示信息的显示方式（可以实现你想要的任何效果，具体参见demo页）;
			ignoreHidden:false,//可选项 true | false 默认为false，当为true时对:hidden的表单元素将不做验证;
			dragonfly:false,//可选项 true | false 默认false，当为true时，值为空时不做验证；
			tipSweep:true,//可选项 true | false 默认为false，只在表单提交时触发检测，blur事件将不会触发检测（实时验证会在后台进行，不会显示检测结果）;
			label:".label",//可选项 选择符，在没有绑定nullmsg时查找要显示的提示文字，默认查找".Validform_label"下的文字;
			showAllError:false,//可选项 true | false，true：提交表单时所有错误提示信息都会显示，false：一碰到验证不通过的就停止检测后面的元素，只显示该元素的错误信息;
			postonce:true, //可选项 表单是否只能提交一次，true开启，不填则默认关闭;
			ajaxPost:true, //使用ajax方式提交表单数据，默认false，提交地址就是action指定地址;
 * @returns
 */
function getValidForm(params){
	return $(function(){
		var param = {
				 formValid:'form:first',//指定要验证的form 默认为第一个
				 btnSubmit:"", //绑定提交按扭
				 btnReset:"",
				 tiptype:3,//提示信息显示方式 默认为3  当前单元格内容后面追加显示
				 ignoreHidden:true,//可选项 true | false 默认为false，当为true时对:hidden的表单元素将不做验证;
				 dragonfly:false,//可选项 true | false 默认false，当为true时，值为空时不做验证；
				 tipSweep:false,//可选项 true | false 默认为false，只在表单提交时触发检测，blur事件将不会触发检测（实时验证会在后台进行，不会显示检测结果）;
				 label:".label",//可选项 选择符，在没有绑定nullmsg时查找要显示的提示文字，默认查找".Validform_label"下的文字;
				 showAllError:false,//可选项 true | false，true：提交表单时所有错误提示信息都会显示，false：一碰到验证不通过的就停止检测后面的元素，只显示该元素的错误信息;
				 postonce:true,//表单只能提交一次
				 datatype:"",//传入自定义datatype类型，可以是正则，也可以是函数（函数内会传入一个参数）
				 
				 beforeCheck:function(curform){
					 
				 },//在表单提交执行验证之前执行的函数，curform参数是当前表单对象。这里明确return false的话将不会继续执行验证操作; 该涵数只在点击提交按扭时执行
				 beforeSubmit:function(curform){
					 
				 },//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。这里明确return false的话表单将不会提交;
				 callback:function(data){
					 
				 } //ajax请求方式提交表单后的回掉函数
			};
		$.extend(param,params) ;
		$(param.formValid).Validform(param);
	});
}

