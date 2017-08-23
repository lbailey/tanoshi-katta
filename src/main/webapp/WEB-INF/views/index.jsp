<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Tanoshi Katta</title>
    
    <spring:url value="/resources/scripts.js" var="scriptsJs" />
    <spring:url value="/resources/styles.css" var="stylesCss" />
    
    <meta name="description" content="" />
    <link href="${stylesCss}" rel="stylesheet">
  </head>
  <body>  
    <div id="timestamp"><span></span></div>      
    <div id="earthContainer"></div>  
  
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script src="/resources/scripts.js"></script>   
  </body>
</html>
