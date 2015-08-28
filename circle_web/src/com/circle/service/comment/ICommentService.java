package com.circle.service.comment;

import com.circle.pojo.comment.Comment;
import com.xwtec.xwserver.exception.SPTException;

public interface ICommentService
{
	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	public boolean addComment(Comment comment)  throws SPTException;
}
