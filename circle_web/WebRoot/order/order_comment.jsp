<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>评价订单</title>
    <link href="${basePath}/css/base.css" rel="stylesheet" />
    <script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
    <style> 
		ul{list-style-type:none;}
	</style>
	<script type="text/javascript"> 
	var basePath="${basePath}";
	$(document).ready(function(){
		$(".btnred").click(function(){
			var orderId="${od.order_id}";
			var circleId="${od.circle_id}";
			var orderDetailId="${od.id}";
			var goodId="${od.good_id}";
			var commentText=$("textarea[name='commentText']").val();
			if(commentText=="")
			{
				ShowBox("请填写评价内容");
				return;
			}
			var serverCommentText=$("textarea[name='commentText']").val();
			var describeScore=$("#star li").filter(".on").length;
			if(describeScore=="")
			{
				ShowBox("请您打个分");
				return;
			}
			var noName=$("input[name='noname']").attr("checked")=="checked"?1:0;;
			$.ajax({
				url:basePath+'/comment/addComment.action',
				data:{orderId:orderId,circleId:circleId,orderDetailId:orderDetailId,commentText:commentText,serverCommentText:serverCommentText,describeScore:describeScore,noName:noName,goodId:goodId},
				dataType:'json',
				success:function(result){
					if(result==true)
					{
						ShowBox("评价成功!",1);
						window.location.href="${basePath}/usercenter/orderList.action";
					}
					else
					{
						ShowBox("评价失败!");
					}
				}
			});
		});
		
		
	});
	
	window.onload = function ()
	{
		var oStar = document.getElementById("star");
		var aLi = oStar.getElementsByTagName("li");
		var oUl = oStar.getElementsByTagName("ul")[0];
		var oSpan = oStar.getElementsByTagName("span")[1];
		var oP = oStar.getElementsByTagName("p")[0];
		var i = iScore = iStar = 0;
		var aMsg = [
					"很不满意|差得太离谱，买的不愉快，非常不满",
					"不满意|部分有破损，不满意",
					"一般，没有想象的那么好",
					"满意|不错，和描述的一样，还是挺满意的",
					"非常满意|非常好，和描述的完全一致，非常满意"
					]
		
		for (i = 1; i <= aLi.length; i++)
		{
			aLi[i - 1].index = i;
			//鼠标移过显示分数
			aLi[i - 1].onmouseover = function ()
			{
				fnPoint(this.index);
				//浮动层显示
				oP.style.display = "block";
				//计算浮动层位置
				oP.style.left = oUl.offsetLeft + this.index * this.offsetWidth - 104 + "px";
				//匹配浮动层文字内容
				oP.innerHTML = "<em><b>" + this.index + "</b> 分 " + aMsg[this.index - 1].match(/(.+)\|/)[1] + "</em>" + aMsg[this.index - 1].match(/\|(.+)/)[1]
			};
			//鼠标离开后恢复上次评分
			aLi[i - 1].onmouseout = function ()
			{
				fnPoint();
				//关闭浮动层
				oP.style.display = "none"
			};
			//点击后进行评分处理
			aLi[i - 1].onclick = function ()
			{
				iStar = this.index;
				oP.style.display = "none";
				oSpan.innerHTML = "<strong>" + (this.index) + " 分</strong> (" + aMsg[this.index - 1].match(/\|(.+)/)[1] + ")"
			}
		}
		//评分处理
		function fnPoint(iArg)
		{
			//分数赋值
			iScore = iArg || iStar;
			for (i = 0; i < aLi.length; i++) aLi[i].className = i < iScore ? "on" : "";	
		}
	};
</script>
</head>
<body>
    <%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="prod">
                <div class="prol">
                    <div class="proimg">
                        <img src="${od.image }" />
                        <div class="open"><i></i></div>
                    </div>
                </div>
                <div class="pror">
                    <div class="title fb">${od.good_title}</div>
                    <div class="item">价格&nbsp;&nbsp;<span class="rmb">￥</span><span class="price">${od.unit_price}</span></div>
                    <div class="item">单位&nbsp;&nbsp;${od.good_unit_str}</div>
                    <div class="item">销量&nbsp;&nbsp;${od.buy_num}</div>
                    <div class="item">
                    	<!-- 累计评价&nbsp;<span class="red">985</span> --></div>
                    <div class="prolook">
                        <i></i>您正在查看于${od.order_time }购买商品的信息
                    </div>
                </div>
                <div class="detail">
                   <!--  <div class="nav">
                        <ul>
                            <li><a href="#">累计评价<span class="green">985</span></a></li>
                        </ul>
                    </div> -->
                    <div class="prodetail" style="display: none;">
                        <img src="../images/temp_6.png" />
                    </div>
                    <div class="comments">
                        <!-- <div class="score">
                            <div class="sl">
                                <p>与描述相符</p>
                                <p><span class="red">4.9</span>分</p>
                            </div>
                            <div class="sr">
                                <div class="title">大家都在说~</div>
                                <ul>
                                    <li class="nor"><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                    <li><a href="#">很便宜&nbsp;490</a></li>
                                </ul>
                            </div>
                        </div> -->
                        <div class="feedback">
                            <div class="title">你的建议，对其他买家很重要哦~</div>
                            <div class="post">
                               <div class="row">
                                   <div class="l" style="height:92px;">评价商品</div>
                                   <div class="r" style="height:81px;">
                                       <textarea name="commentText"></textarea>
                                   </div>
                               </div>
                               <div class="row">
                                   <div class="l" style="height:40px;">评价服务</div>
                                   <div class="r" style="height:29px;">
                                       <textarea name="serverCommentText"></textarea>
                                   </div>
                               </div>
                               <!-- 星级评价 -->
			                   	<div id="star" style="float: left;">
								    <span>点击星星就能打分</span>
								    <ul name="describeScore">
								        <li><a href="javascript:;">1</a></li>
								        <li><a href="javascript:;">2</a></li>
								        <li><a href="javascript:;">3</a></li>
								        <li><a href="javascript:;">4</a></li>
								        <li><a href="javascript:;">5</a></li>
								    </ul>
								    <span></span>
								    <p></p>
								</div>
				                   <!-- end of 星级评价 -->
                               <!-- <div class="row img">
                                   <div class="l" style="height:68px;">商品晒图</div>
                                   <div class="r" style="height:68px;">
                                       <ul>
                                           <li><img src="images/temp_5.png" /></li>
                                           <li><img src="images/temp_5.png" /></li>
                                           <li><img src="images/temp_5.png" /></li>
                                           <li><img src="images/temp_5.png" /></li>
                                       </ul>
                                       <div class="imgnum">1/5</div>
                                   </div>
                               </div> -->
                            </div>
                            <div class="btns">
                                <input type="checkbox" name="noname" id="nm" checked="checked"/><label for="nm">匿名评价</label>
                                <input class="btnred" type="button" value="提交评价"/> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>
   
</body>
</html>
