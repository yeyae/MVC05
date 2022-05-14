package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.ConnectionProvider;
import model.Board;
import oracle.sql.ARRAY;

public class BoardDao {
	
	// DB에 Board 객체 삽입
	public int insertBoard(Board board) {
		String sql = "insert into board values(board_seq.nextval, ?, ?, ? , sysdate)";
		int result = 0; // 삽입결과를 나타냄
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 연결 Provider에서 가져오고
			conn = ConnectionProvider.getConnection();
			// pstmt 객체를 통해서 sql문 실행, 그결과를 result에 저장
			pstmt = conn.prepareStatement(sql);
			// 물음표 자리 채워주기
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null)
					conn.close();
				if(pstmt!=null)
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 게시글 삭제 (작성자의 이름이 현재 로그인한 사용자의 이름과 같을때 삭제)
	// 삭제를 위해 필요한 정보 ?? 작성자 이름, 게시글 아이디
	// delete from board where id = ? and name = ?
	public int deleteBoard(int id, String name) {
		String sql = "delete from board where id = ? and name = ?";
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) // conn 객체가 null 인 상태라면
					conn.close();  // close() 메소드를 호출할때 nullPointerException
				if(pstmt!=null)
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Board selectOne(int id) {
		String sql = "select * from board where id = ?";
		Board result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = new Board();
				result.setId(rs.getInt("id"));
				result.setName(rs.getString("name"));
				result.setTitle(rs.getString("title"));
				result.setContent(rs.getString("content"));
				result.setCreatedTime(rs.getDate("createdTime"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) // conn 객체가 null 인 상태라면
					conn.close();  // close() 메소드를 호출할때 nullPointerException
				if(pstmt!=null)
					pstmt.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// board를 모두 가져오는 메소드
	public List<Board> selectAll() {
		String sql = "select * from board";
		List<Board> result = new ArrayList<Board>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Board board = new Board(); // 리스트에 담을 board 객체
				board.setId(rs.getInt("id"));
				board.setName(rs.getString("name"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setCreatedTime(rs.getDate("createdTime"));
				// 리스트에 담아주기
				result.add(board);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) // conn 객체가 null 인 상태라면
					conn.close();  // close() 메소드를 호출할때 nullPointerException
				if(pstmt!=null)
					pstmt.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 전체 게시글 갯수 구하는 메소드
	public int selectCount() {
		int result = 0;
		String sql = "select count(*) from board";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1); // 결과의 첫번째 열을 가져온다.
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null)
					conn.close();
				if(pstmt!=null)
					pstmt.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 페이지 기능을 위한 메소드
	public List<Board> selectList(int firstRow, int endRow) {
		String sql = "select *\r\n" + 
				"from(select rownum as run , b.id, b.name, b.title, b.content, b.createdtime\r\n" + 
				"    from (select * from board order by id desc) b )\r\n" + 
				"where rnum between ? and ? \r\n";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> result = new ArrayList<>();
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, firstRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Board board = new Board(); // 리스트에 담을 board 객체
				board.setId(rs.getInt("id"));
				board.setName(rs.getString("name"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setCreatedTime(rs.getDate("createdTime"));
				// 리스트에 담아주기
				result.add(board);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) // conn 객체가 null 인 상태라면
					conn.close();  // close() 메소드를 호출할때 nullPointerException
				if(pstmt!=null)
					pstmt.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}
}
