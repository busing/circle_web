<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="http://www.xwtech.com/widget" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户中心-消息中心</title>
    <link href="${basePath }/css/all.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/css/base.css" rel="stylesheet" />
    <script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
    <script type="text/javascript" src="${basePath}/js/msg.js" ></script>
    <script type="text/javascript">
    	var basePath="${basePath}";
    </script>
</head>
<body>
    <%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="ucenter">
                <div class="title">
                    消息中心
                </div>
                <%@ include file="/usercenter/menu.jsp" %> 
                <div class="uc_r">
                    <div class="msg">
                        <ul class="nava">
                        	<c:if test="${page.searchParam.status==0}">
                        		<li class="selected" ship_status="0"><a href="${basePath}/msg_0.html">未处理</a></li>
                            	<li ship_status="1"><a href="${basePath}/msg_1.html">已处理</a></li>
                        	</c:if>
                        	<c:if test="${page.searchParam.status==1}">
                        		 <li ship_status="0"><a href="${basePath}/msg_0.html">未处理</a></li>
                            	<li class="selected" ship_status="1"><a href="${basePath}/msg_1.html">已处理</a></li>
                        	</c:if>
                        </ul>
                        <c:if test="${fn:length(messageList)==0}">
                        	<div class="msgitem">
                       		<div class="l"><p>当前没有相应的短消息</p></div>
                       		</div>
                        </c:if>
                        <c:if test="${fn:length(messageList)>0}">
                        <c:forEach items="${messageList}" var="m">
                       <div class="msgitem">
                           <div class="l">
                               <img src="${basePath}${m.head_image}" />
                               <p>${m.user_name }</p>
                               <p>${m.msg_content}</p>
                           </div>
                           <div class="r">
                               <div class="op">
                               		<c:choose>
                               			<c:when test="${m.status==0}">
                               				<c:choose>
                               					<c:when test="${m.type_id==1}">
                               						<a href="javascript:firstButtonClick(${m.mid});" class="gray">已读</a>&nbsp;&nbsp;<a href="javascript:delMsg(${m.mid});" class="gray">删除</a>
                               					</c:when>
                               					<c:otherwise>
                               						<a href="javascript:secondButtonClick(${m.mid});" class="btngray">拒绝</a><a href="javascript:firstButtonClick(${m.mid});" class="btnred">通过</a>
                               					</c:otherwise>
                               				</c:choose>
                               			</c:when>
                               			<c:otherwise>
                               				<a href="javascript:delMsg(${m.mid});" class="gray">删除</a>
                               			</c:otherwise>
                               		</c:choose>
                               </div>
                               <div class="time">
                                   ${m.send_time}
                               </div>
                           </div>
                       </div>
                       </c:forEach>
                       </c:if>
                        <!-- 分页控件 -->
                        <form id="shopCartForm" action="${basePath}/usercenter/messageCenter.action" method="post" >
                        <div class="pager">
						    <w:turnPage page="${page}"/>
						</div>
						</form>
						<!-- 分页控件 -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
</body>
</html>
