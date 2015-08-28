package com.circle.dao.comment;

import com.circle.pojo.comment.Comment;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 评论DAO
 * @author Taiyuan
 *
 */
public interface ICommentDao
{
	/**
	 * 保存评论
	 * @param comment
	 * @return
	 */
	public boolean saveComment(Comment comment)  throws SPTException;
}
