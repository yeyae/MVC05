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
	
	// DB�� Board ��ü ����
	public int insertBoard(Board board) {
		String sql = "insert into board values(board_seq.nextval, ?, ?, ? , sysdate)";
		int result = 0; // ���԰���� ��Ÿ��
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// ���� Provider���� ��������
			conn = ConnectionProvider.getConnection();
			// pstmt ��ü�� ���ؼ� sql�� ����, �װ���� result�� ����
			pstmt = conn.prepareStatement(sql);
			// ����ǥ �ڸ� ä���ֱ�
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
	
	// �Խñ� ���� (�ۼ����� �̸��� ���� �α����� ������� �̸��� ������ ����)
	// ������ ���� �ʿ��� ���� ?? �ۼ��� �̸�, �Խñ� ���̵�
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
				if(conn!=null) // conn ��ü�� null �� ���¶��
					conn.close();  // close() �޼ҵ带 ȣ���Ҷ� nullPointerException
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
				if(conn!=null) // conn ��ü�� null �� ���¶��
					conn.close();  // close() �޼ҵ带 ȣ���Ҷ� nullPointerException
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
	
	// board�� ��� �������� �޼ҵ�
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
				Board board = new Board(); // ����Ʈ�� ���� board ��ü
				board.setId(rs.getInt("id"));
				board.setName(rs.getString("name"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setCreatedTime(rs.getDate("createdTime"));
				// ����Ʈ�� ����ֱ�
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
				if(conn!=null) // conn ��ü�� null �� ���¶��
					conn.close();  // close() �޼ҵ带 ȣ���Ҷ� nullPointerException
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
	
	// ��ü �Խñ� ���� ���ϴ� �޼ҵ�
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
				result = rs.getInt(1); // ����� ù��° ���� �����´�.
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
	
	// ������ ����� ���� �޼ҵ�
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
				Board board = new Board(); // ����Ʈ�� ���� board ��ü
				board.setId(rs.getInt("id"));
				board.setName(rs.getString("name"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setCreatedTime(rs.getDate("createdTime"));
				// ����Ʈ�� ����ֱ�
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
				if(conn!=null) // conn ��ü�� null �� ���¶��
					conn.close();  // close() �޼ҵ带 ȣ���Ҷ� nullPointerException
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
