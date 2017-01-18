<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String url = request.getRequestURL().toString();
	String servletPath = request.getServletPath();
	String path = url.substring(0, url.indexOf(servletPath));
	request.setAttribute("WEBROOT", path);
%>