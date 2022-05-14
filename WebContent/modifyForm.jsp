<%@page import="model.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	Member member = (Member)request.getAttribute("member");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
</head>
<body>
	<form action="modify" method="post">
		<p>아이디 : <input readonly type="text" name="userid" value="<%=member.getId()%>"></p>
		<p>비밀번호 : <input type="password" name="userpw"></p>
		<p>이름 : <input type="text" name="name" value="<%=member.getName()%>"></p>
		<p>이메일 : <input type="text" name="email" value="<%=member.getEmail() %>"></p>
		<p>가입일 : <input readonly type="text" name="regDate" value="<%=member.getRegDate() %>"></p>
		<input type="submit" value="수정하기">
	</form>
	<a href="main">메인화면으로</a>
</body>
</html>