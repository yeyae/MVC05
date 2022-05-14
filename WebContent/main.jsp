<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 화면</title>
</head>
<body>
	<h1>메인 화면 입니다!!!!</h1>
	<h2><%=session.getAttribute("userid")%>
		님 반갑습니다.!
	</h2>
	<button onclick="location.href='memberList'">회원목록보기</button>
	<%-- Servlet한테 요청보내기 (memberList) --%>
	<button onclick="location.href='modifyForm'">내 정보 수정</button>
	<%-- Servlet한테 요청보내기 (modifyForm) --%>
	<button onclick="location.href='logout'">로그 아웃</button>
	<%-- Servlet한테 요청보내기 (logout) --%>
	<button onclick="location.href='${pageContext.request.contextPath}/board/boardList'">게시글 목록</button>
	<%-- 게시글 목록 페이지로 이동하는 버튼 --%>
</body>
</html>