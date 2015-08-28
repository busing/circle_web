<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/common.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>佣金-分享链接</title>
		<link href="${basePath}/css/all.css" rel="stylesheet" type="text/css" />
		<link href="${basePath}/css/base.css" rel="stylesheet" />
		<script type="text/javascript" src="${basePath}/js/common/common.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/circle.js"></script>
		
		<script type="text/javascript">
			var jiathis_config = { 
				url:"www.comefarm.com/?vcode=${user.inviteCode }", 
				title:"快来注册", 
				summary:"团农网（www.comefarm.com），一个社区团购水果的网站。注册就有水果送，同时还有外快赚哦~@团农网",
				pic:"http://www.comefarm.com/images/logo.png"
			} ;
			function copyLink(txt){
				if (window.clipboardData) {
	                window.clipboardData.clearData();
	                window.clipboardData.setData("Text", txt);
		        } else if (navigator.userAgent.indexOf("Opera") != -1) {
	                window.location = txt;
		        } else if (window.netscape) {
	                try {
                        netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
	                } catch (e) {
                        ShowBox("您的firefox安全限制限制您进行剪贴板操作，请在新窗口的地址栏里输入'about:config'然后找到'signed.applets.codebase_principal_support'设置为true'");
                        return false;
	                }
	                var clip = Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(Components.interfaces.nsIClipboard);
	                if (!clip)
                        return;
	                var trans = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);
	                if (!trans)
                        return;
	                trans.addDataFlavor('text/unicode');
	                var str = new Object();
	                var len = new Object();
	                var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
	                var copytext = txt;
	                str.data = copytext;
	                trans.setTransferData("text/unicode", str, copytext.length * 2);
	                var clipid = Components.interfaces.nsIClipboard;
	                if (!clip)
                        return false;
	                clip.setData(trans, null, clipid.kGlobalClipboard);
		        }
			}
		</script>
	</head>
	<body>
		<%@ include file="/common/head.jsp"%>
		<div class="content">
			<div class="center">
				<div class="ucenter">
					<div class="title">
						赚取佣金
					</div>
					<%@ include file="/usercenter/menu.jsp"%>
					<div class="uc_r">
						<div class="yj">
							<div class="yjlink">
								<div class="cont">
									<p class="tit">
										我的邀请码：${user.inviteCode }
									</p>
									<p class="intro">
										您可以将您的邀请码告诉您的朋友，在注册的时候填入您的邀请码，您可以获得0.5元佣金，同时您将获得注册用户在一个月内成功购买的金额百分之5的佣金。
									</p>
									<p class="intro">
										您也可以直接将下面的链接复制或者分享给您的好友
									</p>
								</div>
							</div>
							<div class="yjshare">
								<span>我的邀请链接：</span>
								<input id="inviteCode" type="text" class="link"
									value="www.comefarm.com/?vcode=${user.inviteCode }" />
								<button type="button" class="red1" onclick="copyLink('www.comefarm.com/?vcode=${user.inviteCode }')">
									复制
								</button>
							</div>
							<!-- JiaThis Button BEGIN -->
								<div class="yjshare" style="float: left;font-size: 18px;color: #565656;">分享到：</div>
								<div id="btn_share" style="display: block;float:left;padding-top: 23px;padding-left: 15px;" class="jiathis_style_32x32">
									<a class="jiathis_button_qzone"></a>
									<a class="jiathis_button_tqq"></a>
									<a class="jiathis_button_weixin"></a>
									<a class="jiathis_button_tsina"></a>
									<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
								</div>
								<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
								<!-- JiaThis Button END -->
						</div>
					</div>
				</div>

			</div>
		</div>
		<%@ include file="/common/foot.jsp"%>
	</body>
</html>
