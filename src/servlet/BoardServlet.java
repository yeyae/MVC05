package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Board;
import service.BoardService;

@WebServlet("/board/*")
public class BoardServlet extends HttpServlet {
	
	BoardService service;
	
	public BoardServlet() {
		service = new BoardService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	
	protected void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 여기서 모두 처리
		// 인코딩 처리
		req.setCharacterEncoding("utf-8");
		
		// 페이징 처리
		/*
		 * 처리해야할 요청
			 /write : 메세지 작성
			 /messageList : 메시지 목록 화면 요청
			 /pwCheck : 비밀번호를 확인해주는 요청
		 * 
		 */
		
		String contextPath = req.getContextPath() + "/board"; // 이 애플리케이션의 기본주소
		String requestURI = req.getRequestURI(); // 요청을 받은 주소 
		
		// 기본 주소 + 요청 = 요청을 받은 주소
		if(requestURI.equals(contextPath + "/write")) {
			// 메세지 작성 요청
			// form 에서 입력한 값 파라미터로 추출
			String name = req.getParameter("name");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			Board mes = new Board();
			mes.setName(name);
			mes.setTitle(title);
			mes.setContent(content);
			
			// 서비스의 메시지 삽입 기능 실행
			boolean result = service.writeBoard(mes);
			
			if(result == true) {
				req.setAttribute("msg", "정상 등록되었습니다.");
			} else {
				req.setAttribute("msg", "등록 실패하였습니다.");
			}
			
			req.setAttribute("url", "boardList");
			req.getRequestDispatcher("/result.jsp").forward(req, resp);
		} else if (requestURI.equals(contextPath + "/boardList")) {
			
			// 페이지 처리 시작 
			int pageNumber = 1;
			String strPageNumber = req.getParameter("page");
			// 우리가 전에 저장한 페이지 정보가 있는지 확인
			
			if(strPageNumber != null) {
				// 우리가 전에 저장한 페이지 정보가 있다.
				pageNumber = Integer.parseInt(strPageNumber);
			} // 페이지 정보가 없다면 기본값인 1페이지가 현재 페이지가 될것입니다.
			
			// 페이지 출력을 위해 우리가 몽땅 집어넣었던 Map을 가져온다.
			Map<String, Object> viewData = service.getBoardList(pageNumber);
			
			req.setAttribute("viewData", viewData);
			req.getRequestDispatcher("/boardList.jsp").forward(req, resp);
			
		}  else if (requestURI.equals(contextPath + "/boardDelete")) {
			// 게시글 삭제 => 게시글 id와 사용자 name 같은거 찾아서 삭제
			
		}
		
	}
	
	
}
