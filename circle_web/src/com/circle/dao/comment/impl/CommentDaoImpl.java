package com.circle.dao.comment.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.circle.constant.CircleTable;
import com.circle.dao.comment.ICommentDao;
import com.circle.pojo.comment.Comment;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;

@Repository
public class CommentDaoImpl implements ICommentDao
{

	public static final String SAVE_COMMENT_SQL="insert into "+CircleTable.GOOD_COMMENT.getTableName()+" (order_id,user_id,circle_id,order_detail_id,good_id,comment_text,server_comment_text,comment_time,describe_score,server_score,ship_score,user_name)"
								+" values (:orderId,:userId,:circleId,:orderDetailId,:goodId,:commentText,:serverCommentText,now(),:describeScore,:serverScore,:shipScore,:userName)";
	
	@Resource
	ICommonDao commonDao;
	
	/* 
	 * 保存评论
	 * @see com.circle.dao.comment.ICommentDao#saveComment(com.circle.pojo.comment.Comment)
	 */
	@Override
	public boolean saveComment(Comment comment) throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("orderId", comment.getOrderId());
		paramMap.put("userId", comment.getUserId());
		paramMap.put("circleId", comment.getCircleId());
		paramMap.put("orderDetailId", comment.getOrderDetailId());
		paramMap.put("commentText", comment.getCommentText());
		paramMap.put("serverCommentText", comment.getServerCommentText());
		paramMap.put("describeScore", comment.getDescribeScore());
		paramMap.put("serverScore", comment.getServerScore());
		paramMap.put("shipScore", comment.getShipScore());
		paramMap.put("userName", comment.getUserName());
		paramMap.put("goodId", comment.getGoodId());
		return commonDao.update(SAVE_COMMENT_SQL, paramMap)>0?true:false;
	}

}
