<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="http://www.xwtech.com/widget" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${basePath }/css/all.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/base.css" rel="stylesheet" />
<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">
</script>
<title>商品评价信息</title>
</head>
<body style="background-color: #fff;">
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
         </ul>
     </div>
 </div> -->
 <c:choose>
 <c:when test="${commentList ne null and fn:length(commentList) gt 0 }">
 <form id="getSmsTaskListForm" action="${basePath}/good/commentList.action?searchParam[good_id]=${page.searchParam.good_id}" method="post" >
 <div class="detail">
 <div class="comments">
 <ul class="commlist">
 	<c:forEach var="comment" items="${commentList}">
	     <li>
	         <div class="l">
	             <p>${comment.comment_text }</p>
	             <p class="gray">${comment.comment_time }</p>
	         </div>
	         <div class="r">
	             <i></i>来自  ${comment.username}
	         </div>
	     </li>
     </c:forEach>
 </ul>
 <div class="pager">
    <w:turnPage page="${page}"/>
 </div>
 </div>
 </div>
 </form>
 </c:when>
 
 <c:otherwise>
 	<div style="font-weight: bolder;color: gray;vertical-align: middle;font-size: 17px;padding-top: 50px;padding-left: 399px;">暂无评论</div>
 </c:otherwise>
  </c:choose>
</body>
</html>