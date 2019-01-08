<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" import="java.util.*"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%request.setAttribute("ctx", request.getContextPath());%>
<% 
response.setHeader("Pragrma","no-cache"); 
response.setHeader("Cache-Control","no-store"); 
response.setDateHeader("Expires",0); 
%> 
