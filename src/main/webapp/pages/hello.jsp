<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>Hello World</title>  
</head>  
<body> 
${message}  xx
<h3> ===============这是第二个关于SpringMVC的程序。================</h3>
   		  <h2> ${prm}</h2>
		<c:forEach items="${prm}" var="map">
			<h3>${map.key}===${map.value}</h3>
		</c:forEach>
		
</body>  
</html>  