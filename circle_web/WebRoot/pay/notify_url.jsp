<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付结果</title>
<link href="${basePath}/css/base.css" rel="stylesheet" />
<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
<script>

</script>
</head>
<body>
    <%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="pay ">
                <div class="paysucc">
                    <div class="cont">
                        <i></i>
                        <p class="tit"><span class="red">${verifyMessage}</span></p>
                        <p class="intro"><a href="${basePath}/usercenter/orderList.action" style="color: red;padding-left: 30px;font-size: 16px;">查看我的订单</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
</body>
</html>
