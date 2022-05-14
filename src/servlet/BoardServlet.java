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
		// ���⼭ ��� ó��
		// ���ڵ� ó��
		req.setCharacterEncoding("utf-8");
		
		// ����¡ ó��
		/*
		 * ó���ؾ��� ��û
			 /write : �޼��� �ۼ�
			 /messageList : �޽��� ��� ȭ�� ��û
			 /pwCheck : ��й�ȣ�� Ȯ�����ִ� ��û
		 * 
		 */
		
		String contextPath = req.getContextPath() + "/board"; // �� ���ø����̼��� �⺻�ּ�
		String requestURI = req.getRequestURI(); // ��û�� ���� �ּ� 
		
		// �⺻ �ּ� + ��û = ��û�� ���� �ּ�
		if(requestURI.equals(contextPath + "/write")) {
			// �޼��� �ۼ� ��û
			// form ���� �Է��� �� �Ķ���ͷ� ����
			String name = req.getParameter("name");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			Board mes = new Board();
			mes.setName(name);
			mes.setTitle(title);
			mes.setContent(content);
			
			// ������ �޽��� ���� ��� ����
			boolean result = service.writeBoard(mes);
			
			if(result == true) {
				req.setAttribute("msg", "���� ��ϵǾ����ϴ�.");
			} else {
				req.setAttribute("msg", "��� �����Ͽ����ϴ�.");
			}
			
			req.setAttribute("url", "boardList");
			req.getRequestDispatcher("/result.jsp").forward(req, resp);
		} else if (requestURI.equals(contextPath + "/boardList")) {
			
			// ������ ó�� ���� 
			int pageNumber = 1;
			String strPageNumber = req.getParameter("page");
			// �츮�� ���� ������ ������ ������ �ִ��� Ȯ��
			
			if(strPageNumber != null) {
				// �츮�� ���� ������ ������ ������ �ִ�.
				pageNumber = Integer.parseInt(strPageNumber);
			} // ������ ������ ���ٸ� �⺻���� 1�������� ���� �������� �ɰ��Դϴ�.
			
			// ������ ����� ���� �츮�� ���� ����־��� Map�� �����´�.
			Map<String, Object> viewData = service.getBoardList(pageNumber);
			
			req.setAttribute("viewData", viewData);
			req.getRequestDispatcher("/boardList.jsp").forward(req, resp);
			
		}  else if (requestURI.equals(contextPath + "/boardDelete")) {
			// �Խñ� ���� => �Խñ� id�� ����� name ������ ã�Ƽ� ����
			
		}
		
	}
	
	
}
