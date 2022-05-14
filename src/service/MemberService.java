package service;

import java.util.List;

import dao.MemberDao;
import model.Member;

public class MemberService {
	
	// ������
	// ���񽺿��� ��Ÿ� ����ؼ� ����� �����ϳ���???
	// MemberDao �� ���� DB�� �����ؾ��Ѵ�.
	// DB�� ����ϴ� ������ Dao �� ����
	// ������ ������ service�� ����
	// servlet�� ������ó���� ����
	MemberDao dao;
	
	public MemberService() {
		dao = new MemberDao();
	}
	
	// �α��α��
	public boolean login(String id, String pw) {
		//1. id�� ���ؼ� db���� ȸ�������� �����´�.
		//2. ������ ȸ�������� null �� �ƴϸ� ���������� �ִ� ���̹Ƿ� ��й�ȣ�� ��
		//   ������ ȸ�������� null�̸� ���������� ���� => �α��� ����
		//3. ��й�ȣ�� ���ؼ� ��й�ȣ�� db�� ��й�ȣ�� ���ٸ� => �α��μ���
		//   ��й�ȣ�� �ٸ��ٸ� => �α��� ����
		Member member = dao.selectOne(id);
		
		if(member != null) {
			
			if(member.getPw().equals(pw)) {
				return true; // �α��� ����
			}
		}
		
		return false; // �ٸ� ��� ��� �α��� ����
	}
	
	// ȸ������ ���
	public boolean join(Member member) {
		int result = dao.insertMember(member);
		// ȸ������ ��� �����ϰ� �� ����� �����´�.
		
		if(result == 1) {
			// ���������� �����Ͱ� ���ԵǾ���.
			return true;
		}
		
		// result�� 1�� �ƴ� ���� ���� ����
		return false;
	}
	
	// ȸ������ ����
	public boolean modify(Member member) {
		int result = dao.updateMember(member);
		
		if(result > 0 ) { // 1�̶� ���ص� ����� ����.
			// �׷���... ���߿� �������� �������� ���� update �� ����
			// ������� ����� 1���� �ƴϰ���?? �׶��� 0���� Ŭ ��찡 �����Դϴ�.
			return true;
		}
		
		return false;
	}
	
	// ȸ������ �ϳ� ��������
	public Member getMember(String id) {
		return dao.selectOne(id);
	}
	
	// ȸ������ ��� ��������
	public List<Member> getAllMembers() {
		return dao.selectAll();
	}
	
}
