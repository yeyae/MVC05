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

	// �����ͺ��̽��� Member������ ����
	public int insertMember(Member member) {
		// ������ sql ��
		String sql = "insert into member values(?,?,?,?, sysdate)";
		// insert into (�÷� �̸�1, �÷��̸�2, �÷��̸�3)
		// values (��1, ��2, ��3)

		// DB�� ���� ��ü
		Connection conn = null;
		// ������ sql���� �����ؼ� �� ����� �������ִ� ģ��
		PreparedStatement pstmt = null;
		// ���� ��� (���� or ����) ���� 0�̸� ����, 0�̾ƴϸ� ����
		int result = 0;

		try {
			// �츮�� ó���� �������� ���� �����⿡�� ���� ��ü �޾ƿ���
			conn = ConnectionProvider.getConnection();
			// ���� ��ü�κ��� pstmt ���� (�Ķ���ͷ� ������ sql�� ��ߵȴ�.)
			pstmt = conn.prepareStatement(sql);
			// sql���� �ִ� ? �� ���� ������ ä���ֱ�
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPw());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			// ����ǥ�� ä��� ���� �ƴ϶� �������� �����ؾ� �Ѵ�.
			result = pstmt.executeUpdate();
			// executeUpdate() ==> ��� ó���� �ʿ� ������
			// ���� Ÿ�� : int ( ����ǰ� ������ ���� �� ����)
			// insert => �� 1�� ���ԵǾ���.
			// update => �� 1�� �����Ǿ���.
			// delete => �� 1�� �����Ǿ���.

			// executeQuery() ==> ��� ó���� �ʿ��Ҷ� (select �� �����)
			// ���� Ÿ�� : ResultSet ( �÷� - �� ���� )

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
		// �������� ���� ��� ��� ���� ���� �޾Ҵ��� �����ؼ�
		// �������� �������� �˼� �ֵ��� ���ش�.
		return result;
	}
	
	// ȸ������ ���� �޼ҵ�
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
		return result; // ��� ���� ���� �޾Ҵ��� ����
	}
	
	// �ش� id�� ���� member ���� 1�� ��������
	public Member selectOne(String id) {
		String sql = "select * from member where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // sql �������� ��� �ִ� ��ü
		Member member = null; // ���� ����� ó���ؼ� �츮�� �������� member ����
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// ���� ����� ó���ϱ� ���� ResultSet�� ��´�.
			rs = pstmt.executeQuery();
			
			// ����� �������� �� 1���̹Ƿ� rs���� ����� �ѹ��� �������� �ȴ�.
			if(rs.next()) {
				// rs.next() ==> ���� ���(��)�� �������� �޼ҵ�
				member = new Member();
				// rs.getString() => ���ڿ� Ÿ�� ������ ��������
				// �Ķ���ʹ� ������ �÷��� �̸�
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
				if (rs != null) // select ���� ������ ��� �߰�
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return member; // �츮�� rs���� ������ data���� ��Ҵ� member ��ü ����
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
				Member member = new Member(); // ����Ʈ�� ���� member ��ü ����
				member.setId(rs.getString("id"));
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setRegDate(rs.getDate("reg_date"));
				// ��� ����Ʈ�� member ��ü �߰�
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
				if (rs != null) // select ���� ������ ��� �߰�
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result; // member ��ü���� ��� ����Ʈ�� ��ȯ
	}
	
}
