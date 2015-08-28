//
function firstButtonClick(msgId)
{
	$.ajax({
		url:basePath+'/msg/firstbuttonclick.action?msgId='+msgId,
		dataType:'json',
		success:function(result){
			if(result)
			{
				window.location.reload();
			}else{
				ShowBox.Show("操作失败，请稍后在试！",3);
			}
		}
	});
}

//
function secondButtonClick(msgId)
{
	$.ajax({
		url:basePath+'/msg/secondbuttonclick.action?msgId='+msgId,
		dataType:'json',
		success:function(result){
			if(result)
			{
				window.location.reload();
			}else{
				ShowBox.Show("操作失败，请稍后在试！",3);
			}
		}
	});
}

function delMsg(msgId)
{
	$.ajax({
		url:basePath+'/msg/delmsg.action?msgId='+msgId,
		dataType:'json',
		success:function(result){
			if(result)
			{
				window.location.reload();
			}else{
				ShowBox.Show("操作失败，请稍后在试！",3);
			}
		}
	});
}

