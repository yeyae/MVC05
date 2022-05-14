package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BoardDao;
import model.Board;

public class BoardService {
	
	private BoardDao dao;
	private static final int NUM_OF_BOARD_PER_PAGE = 10; //한페이지에 표시할 보드 갯수
	private static final int NUM_OF_NAVI_PAGE = 10; //페이지 버튼 갯수
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
			// 삽입 성공
			return true;
		}
		return false;
	}

	public boolean deleteBoard(int id, String name) {
		// id : 삭제할 board의 이름 , name : 작성자 이름
		int result = dao.deleteBoard(id, name);
		
		if(result > 0) {
			// 삭제 성공
			return true;
		}
		return false;
	}
	
	//페이지 계산
	
	//총 페이지수 계산
	// 파라미터 totalCount : 전체 board 개수를 뜻함.
	private int calPageTotalCount(int totalCount) {
		int pageTotalCount = 0; // 총 페이지 수 계산 결과
		if(totalCount != 0) {
			pageTotalCount = (int)Math.ceil((double)totalCount/NUM_OF_BOARD_PER_PAGE);
			// 페이지 계산 방법 ==> 총 보드 개수 / 한페이지당 보여줄 보드 갯수 올림처리
		}
		return pageTotalCount;
	}
	// 현재 페이지 번호가 6 ==> 1 ~ 10
	// 현재 페이지 번호가 16 ==> 11 ~ 20
	
	
	// 보여주기 시작할 페이지 번호
	// pageNum : 현재 페이지 번호
	private int getStartPage(int pageNum) {
		int startPage = ((pageNum-1)/NUM_OF_NAVI_PAGE)*NUM_OF_NAVI_PAGE + 1;
		//현재 페이지 번호가 20번이다
		// 19 / 10 + 1 2
		return startPage;
	}
	
	// 마지막으로 보여줄 페이지 번호
	// pageNum : 현재 페이지 번호
	private int getEndPage(int pageNum) {
		int endPage = (((pageNum-1)/NUM_OF_NAVI_PAGE)+1) * NUM_OF_NAVI_PAGE;
		// 현재 페이지 번호가 6번이다
		// 5 / 10 + 1 = 1
		return endPage;
	}
	
	//계산한 페이지수 만큼 데이터 가져오기 (현재 페이지 번호에 맞는 데이터들 가져오기)
	public Map<String, Object> getBoardList(int pageNumber) {
		// pageNumber => 현재 페이지 번호
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		// 여기에 우리가 필요한 데이터를 모두 때려박을 것이다.
		
		int firstRow = (pageNumber-1)*NUM_OF_BOARD_PER_PAGE + 1;
		// 가져오기 시작할 보드의 행 번호
		// 지금 페이지 번호가 6번 => 1 2 3 4 5 (6) 51번부터 가져온다.
		// (6 - 1)*10 + 1 = 51
		
		int endRow = pageNumber*NUM_OF_BOARD_PER_PAGE;
		// 마지막으로 가져올 행 번호
		// 지금 페이지 번호가 6번 => 60번까지 가져온다.
		
		// 현재 페이지 번호에 맞는 board들 담기
		List<Board> boardList = dao.selectList(firstRow, endRow);
		
		viewData.put("currenPage", pageNumber); // 현재 페이지 번호
		viewData.put("boardList", boardList); // 현재 페이지 번호에 맞는 board들
		viewData.put("pageTotalCount", calPageTotalCount(dao.selectCount())); // 총 페이지 개수
		viewData.put("startPage", getStartPage(pageNumber)); // 시작 페이지 번호
		viewData.put("endPage", getEndPage(pageNumber)); // 마지막 페이지 번호
		
		return viewData;
	}
	
}
