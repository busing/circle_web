/////
////ShowBox.Show("nihao",1,false);
////params:內容、1succ 2warning 3faild  默認2、是否自動消失
////
var ShowBox = {
		_mask:'<div class="maskalert"></div>',
		_template:
			'<div class="popbox alter" style="position: fixed;">'+
	        '	<div class="title">'+
        	'	    <i class=""></i>'+
        	'		<p class="msg"></p>'+
	        '    </div>'+
	        '    <div class="clo"><a href="javaScript:void(0);"></a></div>'+
	        '</div>',
	    _config:{
			boxWidth:490,
			labelWidth:22,
			labelHeight:30,
			labelBoxWidth:350,
			speed:200,
			autoHide:3000
		},
		ele:undefined,
		Show:function(msg, type, isAutoHide) {
			var that = this;
			if(this.ele){
				$(this.ele).remove();
			}
			if($(".maskalert").length==0){
				$("body").append(this._mask);
			}
			var labelWidth = msg.length*this._config.labelWidth;
			var height = (parseInt(labelWidth/this._config.labelBoxWidth) + (labelWidth%this._config.labelBoxWidth>0?1:0))*this._config.labelHeight+60;
			this.ele = $(this._template).width(this._config.boxWidth).height(height).css("margin-left",(0-this._config.boxWidth/2)+"px").hide();
			$(this.ele).find(".msg").text(msg);
			$(this.ele).find(".clo").click(function(){
				that.Hide();
			});
			var cls = "warning";
			switch (type) {
			case 1:
				cls = "succ";
				break;
			case 2:
				cls = "warning";
				break;
			case 3:
				cls = "faild";
				break;
			}
			$(this.ele).find("i").addClass(cls);
			$("body").append(this.ele);
			$(".maskalert").show();
			$(this.ele).fadeIn(this._config.speed);
			if(isAutoHide){
				window.setTimeout(function() {
					that.Hide();
				},this._config.autoHide);
			}
		},
		Hide:function(){
			$(this.ele).fadeOut(this._config.speed);
			$(".maskalert").fadeOut(this._config.speed);
		}
};