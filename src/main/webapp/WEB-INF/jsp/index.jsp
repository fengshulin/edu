<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/js/jquery-1.11.3.min.js" ></script>
<script type="text/javascript" src="${ctx}/js/wx-share.js" ></script>
<script>
$(function(){
	weixin.share("1","2",null,null)
})
</script>
</head>
<body>
大学生领导力
</body>
</html>
