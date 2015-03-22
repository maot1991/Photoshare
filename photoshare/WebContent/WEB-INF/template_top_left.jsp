<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<title>Favorite Website</title>
	<style>
		.menu-head {font-size: 10pt; font-weight: bold; color: black; }
		.menu-item {font-size: 10pt;  color: black }
    </style>
</head>
<%@ page import="Beans.UserBean" %>

<body>


<table cellpadding="4" cellspacing="0">
    <tr>
	    <!-- Banner row across the top -->
        <td width="130" bgcolor="#FFFFFF">
            <img border="0" src="1.jpg" height="75">
        
        </td>
        <td bgcolor="#DDDDDD">&nbsp;  </td>
        <td width="500" bgcolor="#DDDDDD">
            <p align="center">
            
            
		        <font size="7">Favorite Sharing Website</font>


			</p>
		</td>
    </tr>
	
	<!-- Spacer row -->
	<tr>
		<td bgcolor="#DDDDDD" style="font-size:5px">&nbsp;</td>
		<td colspan="2" style="font-size:5px">&nbsp;</td>
	</tr>
	
	<tr>
		<td bgcolor="DDDDDD" valign="top" height="500">
			<!-- Navigation bar is one table cell down the left side -->
            <p align="left">
            
           <c:choose>
			<c:when test="${ (empty user) }">
				<span class="menu-item"><a href="login.do">Login</a></span><br/>
				<span class="menu-item"><a href="register.do">Register</a></span><br/>
			</c:when>
			<c:otherwise>
				<span class="menu-head">${ user.firstName} ${user.lastName}</span><br/>
				<span class="menu-item"><a href="manage.do">Manage Your List</a></span><br/>
				<span class="menu-item"><a href="change-pwd.do">Change Password</a></span><br/>
				<span class="menu-item"><a href="logout.do">Logout</a></span><br/>
				<span class="menu-item">&nbsp;</span><br/>
			</c:otherwise>
		</c:choose>
            
				<span class="menu-head">Photos From:</span><br/>
				
			
			<c:forEach var="user" items="${userlist}">
				<span class="menu-item"><a href="list.do?userEmail=${ user.emailAddress }">${ user.firstName} ${ user.lastName}</a></span><br/>
			</c:forEach>
				

			</p>
		</td>
		
		<td>
			<!-- Padding (blank space) between navbar and content -->
		</td>
		<td  valign="top">