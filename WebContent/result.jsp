<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 결과를 경고창으로 보여주기, 다음 화면으로 이동시키기 --%>
<script type="text/javascript">
	alert("<%=request.getAttribute("msg") %>");
	location.href="<%=request.getAttribute("url")%>";
</script>