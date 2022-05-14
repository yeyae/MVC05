<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>
</head>
<body>
	<form action="login" method="post">
	<p>아이디 : <input type="text" name="userid"></p>
	<p>비밀번호 : <input type="password" name="userpw"></p>
	<input type="submit" value="로그인">
	<input type="button" value="회원가입" onclick="location.href='joinForm'">
	</form>
</body>
</html>