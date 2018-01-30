document
		.write('<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>');
weixin = {
	currentOpenId : "",// 当前用户openid
	currentPage : "",// 当前页名称
	title : "",// 分享标题
	desc : "",// 分享描述
	wxLink : "",// 微信分享链接
	imgUrl : "",// 分享图片地址
	appId : "",// 公众号appid
	nonceStr : "",
	timestamp : "",
	signature : "",
	url : "",// 当前请求路径
	appMessage : "appMessage",// 分享渠道：微信好友
	timeline : "timeline",// 分享渠道：朋友圈
	qq : "qq",// 分享渠道：QQ
	qzone : "qzone",// 分享渠道：QQ空间
	weibo : "weibo",// 分享渠道：腾讯微博
	rootpath : "",// 当前根路径
	share : function(requesttitle, requestdesc, requestwxlink,requestimgUrl) {
		url = window.location.href;
			title = "领无止境 创无边界";
			desc = "大学生领导力测验设计大赛";
			wxLink = "http://www.lifengedu.com/wx/test";
			imgUrl = "https://www.baidu.com/img/bd_logo1.png"
		
		this.loadWxjssdkConfig()
	},
	loadWxjssdkConfig : function() {
		$.ajax({
			type : "get",
			url : weixin.rootpath + "/wx/wxJssdkConfig",
			dataType : "json",
			async : false,
			data : {
				"currentUrl" : url
			},
			success : function(data) {
				appId = data.appId;
				nonceStr = data.nonceStr;
				timestamp = data.timestamp;
				signature = data.signature
			}
		});
		this.wxconfig();
	},
	wxconfig : function() {
		wx.config({
			debug : false,
			appId : appId,
			timestamp : timestamp,
			nonceStr : nonceStr,
			signature : signature,
			jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
					'onMenuShareAppMessage', 'onMenuShareQQ',
					'onMenuShareWeibo', 'hideMenuItems', 'showMenuItems',
					'onMenuShareQZone', 'hideAllNonBaseMenuItem',
					'showAllNonBaseMenuItem', 'translateVoice', 'startRecord',
					'stopRecord', 'onRecordEnd', 'playVoice', 'pauseVoice',
					'stopVoice', 'uploadVoice', 'downloadVoice', 'chooseImage',
					'previewImage', 'uploadImage', 'downloadImage',
					'getNetworkType', 'openLocation', 'getLocation',
					'hideOptionMenu', 'showOptionMenu', 'closeWindow',
					'scanQRCode', 'chooseWXPay', 'openProductSpecificView',
					'addCard', 'chooseCard', 'openCard' ]
		});
		wx.ready(function() {
			wx.getNetworkType({
				success : function(res) {
				},
				fail : function(res) {
				}
			});
			wx.onMenuShareAppMessage({
				title : title,
				desc : desc,
				link : wxLink,
				imgUrl : imgUrl,
				type : '',
				dataUrl : '',
				success : function() {
				},
				cancel : function() {
				}
			});
			wx.onMenuShareTimeline({
				title : title,
				desc : desc,
				link : wxLink,
				imgUrl : imgUrl,
				success : function(res) {
				},
				cancel : function(res) {
				}
			});
		});
		wx.error(function(res) {
		})
	},
	getRootPath : function() {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		var localhostPaht = curWwwPath.substring(0, pos);
		var projectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 1);
		weixin.rootpath = localhostPaht + projectName
	}
}