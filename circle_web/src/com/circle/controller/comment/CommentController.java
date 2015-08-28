package com.circle.controller.comment;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.circle.pojo.comment.Comment;
import com.circle.pojo.user.User;
import com.circle.service.comment.ICommentService;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.StringUtil;

@Controller
@RequestMapping("comment")
public class CommentController
{
	/**
	 * logger
	 */
	public static final Logger logger=Logger.getLogger(CommentController.class);
	
	@Resource
	ICommentService commentService;
	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addComment.action")
	public boolean addComment(Comment comment,int noName,HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		int userId=user.getId();
		comment.setUserId(userId);
		if(noName==0)
		{
			comment.setUserName(StringUtil.isEmpty(user.getNickname())?user.getName():user.getNickname());
		}
		boolean flag=false;
		try
		{
			flag=commentService.addComment(comment);
		}
		catch (SPTException e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return flag;
	}
}
