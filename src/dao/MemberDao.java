package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.ConnectionProvider;
import model.Member;

public class MemberDao {

	// insert, update, delete, selectOne, selectAll

	// 데이터베이스에 Member정보를 삽입
	public int insertMember(Member member) {
		// 실행할 sql 문
		String sql = "insert into member values(?,?,?,?, sysdate)";
		// insert into (컬럼 이름1, 컬럼이름2, 컬럼이름3)
		// values (값1, 값2, 값3)

		// DB와 연결 객체
		Connection conn = null;
		// 실제로 sql문을 실행해서 그 결과를 가져다주는 친구
		PreparedStatement pstmt = null;
		// 삽입 결과 (성공 or 실패) 값이 0이면 실패, 0이아니면 성공
		int result = 0;

		try {
			// 우리가 처음에 만들어줬던 연결 생성기에서 연결 객체 받아오기
			conn = ConnectionProvider.getConnection();
			// 연결 객체로부터 pstmt 생성 (파라미터로 실행할 sql문 줘야된다.)
			pstmt = conn.prepareStatement(sql);
			// sql문에 있는 ? 를 실제 값으로 채워주기
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPw());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			// 물음표만 채우고 끝이 아니라 쿼리문을 실행해야 한다.
			result = pstmt.executeUpdate();
			// executeUpdate() ==> 결과 처리가 필요 없을떄
			// 리턴 타입 : int ( 실행되고 영향을 받은 행 갯수)
			// insert => 행 1개 삽입되었다.
			// update => 행 1개 수정되었다.
			// delete => 행 1개 삭제되었다.

			// executeQuery() ==> 결과 처리가 필요할때 (select 문 실행시)
			// 리턴 타입 : ResultSet ( 컬럼 - 값 매핑 )

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 쿼리문의 실행 결과 몇개의 행이 영향 받았는지 리턴해서
		// 성공인지 실패인지 알수 있도록 해준다.
		return result;
	}
	
	// 회원정보 수정 메소드
	public int updateMember(Member member) {
		String sql = "update member set pw = ? , name = ? , email = ? where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPw());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getId());
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result; // 몇개의 행이 영향 받았는지 리턴
	}
	
	// 해당 id를 가진 member 정보 1개 가져오기
	public Member selectOne(String id) {
		String sql = "select * from member where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // sql 실행결과를 담고 있는 객체
		Member member = null; // 실행 결과를 처리해서 우리가 리턴해줄 member 정보
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 실행 결과를 처리하기 위해 ResultSet에 담는다.
			rs = pstmt.executeQuery();
			
			// 여기는 실행결과가 단 1줄이므로 rs안의 결과를 한번만 가져오면 된다.
			if(rs.next()) {
				// rs.next() ==> 다음 결과(행)을 가져오는 메소드
				member = new Member();
				// rs.getString() => 문자열 타입 데이터 가져오기
				// 파라미터는 가져올 컬럼의 이름
				member.setId(rs.getString("id"));
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setRegDate(rs.getDate("reg_date"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null) // select 문을 실행한 경우 추가
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return member; // 우리가 rs에서 꺼내온 data들을 담았던 member 객체 리턴
	}
	
	public List<Member> selectAll() {
		String sql = "select * from member";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> result = new ArrayList<Member>();
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Member member = new Member(); // 리스트에 담을 member 객체 생성
				member.setId(rs.getString("id"));
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setRegDate(rs.getDate("reg_date"));
				// 결과 리스트에 member 객체 추가
				result.add(member);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null) // select 문을 실행한 경우 추가
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result; // member 객체들이 담긴 리스트를 반환
	}
	
}
