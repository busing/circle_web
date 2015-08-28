$(document).ready(function(){
	//增加数量
	$(".top").live("click",function(){
		var num=$(this).parent().prev().val();
		if(num < 99){
			num++;
			$(this).parent().prev().val(num);
			var cartId=$(this).parent().attr("cartId");
			var $t=$(this).parent().parent().next().eq(0);
			updateCartNum(cartId,num,$t);
		}
	});
	
	//减少购物车数量
	$(".down").live("click",function(){
		var num=$(this).parent().prev().val();
		if(num>1)
		{
			num--;
			$(this).parent().prev().val(num);
		}
		var cartId=$(this).parent().attr("cartId");
		var $t=$(this).parent().parent().next().eq(0);
		updateCartNum(cartId,num,$t);
		
	});
	
	//购物数量数字变化事件
	$("input[name='goodNum']").live("change",function(){
		if(!validateGoodNum($(this).val()))
		{
			$(this).val(1);
		}
		var num=$(this).val();
		var cartId=$(this).attr("cartId");
		var $t=$(this).parent().next().eq(0);
		updateCartNum(cartId,num,$t);
	});
	
	//勾选购物车商品事件
	$(".chkbox[name='cartCheck']").toggle(
	  function () 
	  {
		  $(this).addClass("selected");
		  //已经全部勾选
		  if($(".chkbox[name='cartCheck']").length==$(".chkbox[name='cartCheck']").filter(".selected").length)
		  {
			  $(".chkbox[name='allCheck']").addClass("selected");
		  }
		  calTotal();
	  },
	  function () 
	  {
		  $(this).removeClass("selected");
		  //没有全选
		  if($(".chkbox[name='cartCheck']").length!=$(".chkbox[name='cartCheck']").filter(".selected").length)
		  {
			  $(".chkbox[name='allCheck']").removeClass("selected");
		  }
		  calTotal();
	  }
	);
	
	//全选购物车商品事件
	$(".chkbox[name='allCheck']").toggle(
	  function () 
	  {
		  $(".chkbox[name='allCheck']").addClass("selected");
		  $(".chkbox[name='cartCheck']").addClass("selected");
		  calTotal();
	  },
	  function () 
	  {
		  $(".chkbox[name='allCheck']").removeClass("selected");
		  $(".chkbox[name='cartCheck']").removeClass("selected");
		  calTotal();
	  }
	);
	
	//购物车删除事件
	$(".op .gray").live("click",function(){
		var cartId=$(this).attr("cartId");
		//请求服务器删除购物车商品
		$.ajax({
			url:basePath+'/shopcart/deleteShopCart.action?cartId='+cartId,
			dataType:'json',
			success:function(result){
				//删除购物车成功
				if(result)
				{
					getShopCartCount();
					$(".tablist[cartId='"+cartId+"']").remove();
					//没有全选
					if($(".chkbox[name='cartCheck']").length!=$(".chkbox[name='cartCheck']").filter(".selected").length || $(".chkbox[name='cartCheck']").length==0)
					{
						$(".chkbox[name='allCheck']").removeClass("selected");
					}
					calTotal();
				}
			}
		});
		//end 请求服务器删除购物车商品
	});
	
});

//验证购买数字是否正确
function validateGoodNum(goodNum)
{
	var reg =  /^\d+$/;
	var flag= goodNum.match(reg);
	return flag;
}

//更新购物车数量
function updateCartNum(cartId,buyNum,t)
{
	$.ajax({
		url:basePath+'/shopcart/updateCartNum.action?buyNum='+buyNum+"&cartId="+cartId,
		dataType:'json',
		success:function(result){
			//更新数量成功
			if(result.flag)
			{
				var singleTotal=result.singleTotal;
				var $t=$(t);
				$t.html(singleTotal+" 元");
				calTotal();
			}
		}
	});
}

//计算购物车勾选的总价
function calTotal()
{
	var cartId="";
	var selectNum=0;
	$(".chkbox[name='cartCheck']").filter(".selected").each(function(i){
		cartId+=$(this).attr("cartId")+",";
		selectNum++;
	});
	if(cartId!="")
	{
		cartId=cartId.substring(0,cartId.length-1);
	}
	//勾选总数
	$(".total .red").eq(0).html(selectNum);
	
	if(selectNum<=0)
	{
		$(".total .red").eq(1).html(0);
		return ;
	}
	
	//请求计算总金额
	$.ajax({
		url:basePath+'/shopcart/calTotal.action?cartId='+cartId,
		dataType:'json',
		success:function(result){
			$(".total .red").eq(1).html("￥"+result);
		}
	});
}

//结算购物车事件
function calcart()
{
	var calCartId="";
	$(".chkbox[name='cartCheck']").filter(".selected").each(function(){
		calCartId+=$(this).attr("cartId")+",";
	});
	if(calCartId!="")
	{
		calCartId=calCartId.substring(0,calCartId.length-1);
		$("input[name='calCartId']").val(calCartId);
		return true;
	}
	else
	{
		ShowBox.Show("请至少选择一个商品");
		return false;
	}
		
}
