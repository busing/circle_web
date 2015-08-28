package com.circle.service.comment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.circle.dao.comment.ICommentDao;
import com.circle.pojo.comment.Comment;
import com.circle.service.comment.ICommentService;
import com.xwtec.xwserver.exception.SPTException;

@Service
public class CommentServiceImpl implements ICommentService
{

	@Resource
	ICommentDao commentDao;
	
	@Override
	public boolean addComment(Comment comment)   throws SPTException
	{
		return commentDao.saveComment(comment);
	}
	
	
}
