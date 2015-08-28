<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/common.jsp"%> 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link href="${basePath}/css/base.css" rel="stylesheet" />
    <script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
</head>
<body>
    <%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
            <div class="ucenter">
                <div class="title">
                    关于团农网
                </div>
                <div class="uc_l">
                    <ul>
                        <li >
                            <a href="${basePath}/help/help_about.jsp"><i class="i_about"></i>关于我们</a>
                        </li>
                        <li>
                            <a href="${basePath}/help/help_joinus.jsp"><i class="i_question"></i>加入我们</a>
                        </li>
                        <li class="selected">
                            <a href="${basePath}/help/help_contactus.jsp"><i class="i_contactus"></i>联系我们</a>
                        </li>
                        <li>
                            <a href="${basePath}/help/help_question.jsp"><i class="i_joinus"></i>常见问题</a>
                        </li>
                    </ul>
                </div>
                <div class="uc_r ">
                    <div class="helpcont">
                        <p class="red">团农网的联系方式</p>
                        <p class="b">商务合作|市场推广</p>
                        <p>联系电话：15051885903</p>
                        <p>电子邮件：1725064314@qq.com</p>
                        <p class="b">客户服务</p>
                        <p>客服热线：15051885903</p>
                        <p>客服邮箱：comefarm_service@163.com</p>
                        <p class="b">联系地址</p>
                        <p>地址：南京市鼓楼区江东北路305号滨江广场02栋24楼 </p>
                        <p>邮编：210036</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
</body>
</html>
