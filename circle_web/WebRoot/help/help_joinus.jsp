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
                        <li class="selected" >
                            <a href="${basePath}/help/help_joinus.jsp"><i class="i_question"></i>加入我们</a>
                        </li>
                        <li >
                            <a href="${basePath}/help/help_contactus.jsp"><i class="i_contactus"></i>联系我们</a>
                        </li>
                        <li>
                            <a href="${basePath}/help/help_question.jsp"><i class="i_joinus"></i>常见问题</a>
                        </li>
                    </ul>
                </div>
                <div class="uc_r ">
                    <div class="helpcont">
                        <p class="red">欢迎加入团农网团队</p>
                        <p class="b"></p>
                        <p>如果你“吃得苦，能扛事，求成长”，欢迎加入我们的e娃团队！</p>
                      
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
    </div>
</body>
</html>
