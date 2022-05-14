<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 화면</title>
</head>
<body>
<form action="join" method="post">
	<p>아이디 : <input type="text" name="userid"></p>
	<p>비밀번호 : <input type="password" name="userpw"></p>
	<p>이름 : <input type="text" name="name"></p>
	<p>이메일 : <input type="text" name="email"></p>
	<input type="submit" value="회원가입">
	<input type="reset" value="다시 작성">
</form>
</body>
</html>