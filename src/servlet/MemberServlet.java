package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Member;
import service.MemberService;

@WebServlet("/member/*")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MemberService service;

	public MemberServlet() {
		service = new MemberService();
		// 서비스를 통해 db에 간접적으로 접근
		// 서비스는 기능의 단위를 분리
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doProc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		System.out.println("URI : " + request.getRequestURI());
		System.out.println("Context : " + request.getContextPath());

		// 요청을 구분하는 방법
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath() + "/member";
		// 원래는 우리가 기본 경로 "/" 에서 시작했지만
		// 현재 이 서블릿은 "/member/*" 경로를 처리하기 때문에
		// /member 를 뒤에 붙여준다.

		if (uri.equals(contextPath + "/join")) {
			// 회원가입 요청이다.
			Member member = new Member();
			// 데이터베이스에 삽입할 Member 만들기
			// 요청 파라미터에서 입력에 필요한 값들 가져오기
			member.setId(request.getParameter("userid"));
			member.setPw(request.getParameter("userpw"));
			member.setName(request.getParameter("name"));
			member.setEmail(request.getParameter("email"));

			boolean result = service.join(member);
			// 회원가입 성공하면 loginForm 요청
			// 회원가입 실패하면 joinForm 요청
			if (result) {
				response.sendRedirect("loginForm");
			} else {
				response.sendRedirect("joinForm");
			}

		} else if (uri.equals(contextPath + "/loginForm")) {
			// /loginForm
			request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
		} else if (uri.equals(contextPath + "/joinForm")) {
			// /joinForm
			request.getRequestDispatcher("/joinForm.jsp").forward(request, response);
		} else if (uri.equals(contextPath + "/login")) {
			// 서비스 클래스의 login 메소드를 이용해서 처리
			String id = request.getParameter("userid");
			String pw = request.getParameter("userpw");
			
			// 로그인 결과
			boolean result = service.login(id, pw);
			
			String url = null; // 로그인 결과에 따라 보내줄 페이지 주소
			String msg = null; // 로그인 결과에 따라 사용자에게 보여줄 메시지
			if(result) {
				// 로그인 성공
				// 메인화면 보여주기 : main 요청보내기
				msg = "로그인 성공!";
				url = "main";
				
				// 세션 가져와서 session 에 userid 속성
				request.getSession().setAttribute("userid", id);
				
				// 세션에 사용자 이름 저장
				Member m = service.getMember(id);
				request.getSession().setAttribute("username", m.getName());
			} else {
				// 로그인 실패
				// 로그인화면 보여주기 : loginForm 요청보내기
				msg = "로그인 실패";
				url = "loginForm";
			}
			// 목적 페이지로 바로 보내지않고 결과출력페이지 먼저 보여주기
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		} else if (uri.equals(contextPath + "/main")) {
			// main 요청 처리
			request.getRequestDispatcher("/main.jsp").forward(request, response);
			
		} else if (uri.equals(contextPath + "/memberList")) {
			
			// memberList 요청 처리
			List<Member> memberList = service.getAllMembers();
			// 가져온 멤버 목록을 memberList.jsp에 전달
			request.setAttribute("memberList", memberList );
			request.getRequestDispatcher("/memberList.jsp").forward(request, response);
			
		} else if (uri.equals(contextPath + "/modifyForm")) {
			// modifyForm 요청 처리
			// 회원정보 수정 페이지를 보여준다.
			// 기존의 회원 데이터가 전달되어야 한다.
			
			// 로그인할때 세션에 저장했던 사용자 정보를 꺼내온다.
			String userid = (String)request.getSession().getAttribute("userid");
			// 세션에 저장되어있던 사용자 id를 통해서
			// service의 getMember() 메소드를 사용
			Member member = service.getMember(userid);
			
			// 가져온 정보를 request에 담아주고
			request.setAttribute("member", member);
			// 그 request를 유지한 상태로 페이지를 이동
			request.getRequestDispatcher("/modifyForm.jsp").forward(request, response);
		} else if (uri.equals(contextPath + "/logout")) {
			// 로그아웃 요청 처리
			// session에서 사용자 정보 삭제하고 loginForm으로 돌아가기
			// 세션의 attribute (속성 제거)
			request.getSession().removeAttribute("userid");
			response.sendRedirect("loginForm");
		} else if (uri.equals(contextPath + "/modify")) {
			// 요청에 담겨있는 파라미터를 가져와서 해당 정보를 가지고
			// 데이터베이스의 사용자 정보를 업데이트
			String userid = request.getParameter("userid");
			String userpw = request.getParameter("userpw");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			Member member = new Member();
			member.setId(userid);
			member.setPw(userpw);
			member.setName(name);
			member.setEmail(email);
			
			boolean result = service.modify(member);
			// result 안에는 업데이트의 결과
			// 성공시 true, 실패시 false
			
			String msg = "";
			String url = "main"; // 성공하던 실패하던 똑같이 main으로 보내기
			if(result) {
				// 성공
				msg = "변경 성공하였습니다.";
			} else {
				// 실패
				msg = "변경 실패하였습니다.";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			
			// result.jsp 로 페이지 이동
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		}
	}

}
