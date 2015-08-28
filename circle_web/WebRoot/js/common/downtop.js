$(document).ready(function(){
	$(".top").live("click",function(){
		var num=$(this).parent().prev().val();
		if(num < 99){
			num++;
			$(this).parent().prev().val(num);
		}
	});
	
	$(".down").live("click",function(){
		var num=$(this).parent().prev().val();
		if(num>1)
		{
			num--;
			$(this).parent().prev().val(num);
		}
		
	});
	
	$("input[name='goodNum']").live("change",function(){
		if(!validateGoodNum($(this).val()))
		{
			$(this).val(1);
		}
	});
});

function validateGoodNum(goodNum)
{
	var reg =  /^\d+$/;
	var flag= goodNum.match(reg);
	return flag;
}