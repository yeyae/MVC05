<%@page import="model.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록 페이지</title>
</head>
<body>
<% 
	List<Member> memberList = (List<Member>)request.getAttribute("memberList");
%>
<table border="1">
	<tr>
		<th>아이디</th>
		<th>이름</th>
		<th>이메일</th>
		<th>가입일</th>
		<th></th>
	</tr>
<% 
	for (int i =0; i<memberList.size(); i++) {
%>
	<tr>
		<td><%=memberList.get(i).getId() %></td>
		<td><%=memberList.get(i).getName() %></td>
		<td><%=memberList.get(i).getEmail() %></td>
		<td><%=memberList.get(i).getRegDate() %></td>
		<td><button>X</button></td>
	</tr>
<%
	}
%>
</table>
<a href="main">메인화면으로</a>
</body>
</html>