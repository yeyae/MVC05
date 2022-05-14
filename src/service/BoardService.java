package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BoardDao;
import model.Board;

public class BoardService {
	
	private BoardDao dao;
	private static final int NUM_OF_BOARD_PER_PAGE = 10; //���������� ǥ���� ���� ����
	private static final int NUM_OF_NAVI_PAGE = 10; //������ ��ư ����
	// 1 2 3 4 5 6 7 8 9 10 
	
	public BoardService() {
		dao = new BoardDao();
	}
	
	public List<Board> getAllBoards() {
		return dao.selectAll();
	}
	
	public Board getBoard(int id) {
		return dao.selectOne(id);
	}
	
	public boolean writeBoard(Board board) {
		
		int result = dao.insertBoard(board);
		if(result > 0) {
			// ���� ����
			return true;
		}
		return false;
	}

	public boolean deleteBoard(int id, String name) {
		// id : ������ board�� �̸� , name : �ۼ��� �̸�
		int result = dao.deleteBoard(id, name);
		
		if(result > 0) {
			// ���� ����
			return true;
		}
		return false;
	}
	
	//������ ���
	
	//�� �������� ���
	// �Ķ���� totalCount : ��ü board ������ ����.
	private int calPageTotalCount(int totalCount) {
		int pageTotalCount = 0; // �� ������ �� ��� ���
		if(totalCount != 0) {
			pageTotalCount = (int)Math.ceil((double)totalCount/NUM_OF_BOARD_PER_PAGE);
			// ������ ��� ��� ==> �� ���� ���� / ���������� ������ ���� ���� �ø�ó��
		}
		return pageTotalCount;
	}
	// ���� ������ ��ȣ�� 6 ==> 1 ~ 10
	// ���� ������ ��ȣ�� 16 ==> 11 ~ 20
	
	
	// �����ֱ� ������ ������ ��ȣ
	// pageNum : ���� ������ ��ȣ
	private int getStartPage(int pageNum) {
		int startPage = ((pageNum-1)/NUM_OF_NAVI_PAGE)*NUM_OF_NAVI_PAGE + 1;
		//���� ������ ��ȣ�� 20���̴�
		// 19 / 10 + 1 2
		return startPage;
	}
	
	// ���������� ������ ������ ��ȣ
	// pageNum : ���� ������ ��ȣ
	private int getEndPage(int pageNum) {
		int endPage = (((pageNum-1)/NUM_OF_NAVI_PAGE)+1) * NUM_OF_NAVI_PAGE;
		// ���� ������ ��ȣ�� 6���̴�
		// 5 / 10 + 1 = 1
		return endPage;
	}
	
	//����� �������� ��ŭ ������ �������� (���� ������ ��ȣ�� �´� �����͵� ��������)
	public Map<String, Object> getBoardList(int pageNumber) {
		// pageNumber => ���� ������ ��ȣ
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		// ���⿡ �츮�� �ʿ��� �����͸� ��� �������� ���̴�.
		
		int firstRow = (pageNumber-1)*NUM_OF_BOARD_PER_PAGE + 1;
		// �������� ������ ������ �� ��ȣ
		// ���� ������ ��ȣ�� 6�� => 1 2 3 4 5 (6) 51������ �����´�.
		// (6 - 1)*10 + 1 = 51
		
		int endRow = pageNumber*NUM_OF_BOARD_PER_PAGE;
		// ���������� ������ �� ��ȣ
		// ���� ������ ��ȣ�� 6�� => 60������ �����´�.
		
		// ���� ������ ��ȣ�� �´� board�� ���
		List<Board> boardList = dao.selectList(firstRow, endRow);
		
		viewData.put("currenPage", pageNumber); // ���� ������ ��ȣ
		viewData.put("boardList", boardList); // ���� ������ ��ȣ�� �´� board��
		viewData.put("pageTotalCount", calPageTotalCount(dao.selectCount())); // �� ������ ����
		viewData.put("startPage", getStartPage(pageNumber)); // ���� ������ ��ȣ
		viewData.put("endPage", getEndPage(pageNumber)); // ������ ������ ��ȣ
		
		return viewData;
	}
	
}
