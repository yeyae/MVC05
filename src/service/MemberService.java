package service;

import java.util.List;

import dao.MemberDao;
import model.Member;

public class MemberService {
	
	// 생성자
	// 서비스에서 어떤거를 사용해서 기능을 구현하나요???
	// MemberDao 를 통해 DB와 연동해야한다.
	// DB와 통신하는 역할은 Dao 가 전담
	// 나머지 로직은 service가 전담
	// servlet은 페이지처리를 전담
	MemberDao dao;
	
	public MemberService() {
		dao = new MemberDao();
	}
	
	// 로그인기능
	public boolean login(String id, String pw) {
		//1. id를 통해서 db에서 회원정보를 가져온다.
		//2. 가져온 회원정보가 null 이 아니면 가입정보가 있는 것이므로 비밀번호를 비교
		//   가져온 회원정보가 null이면 가입정보가 없음 => 로그인 실패
		//3. 비밀번호를 비교해서 비밀번호가 db의 비밀번호와 같다면 => 로그인성공
		//   비밀번호가 다르다면 => 로그인 실패
		Member member = dao.selectOne(id);
		
		if(member != null) {
			
			if(member.getPw().equals(pw)) {
				return true; // 로그인 성공
			}
		}
		
		return false; // 다른 경우 모두 로그인 실패
	}
	
	// 회원가입 기능
	public boolean join(Member member) {
		int result = dao.insertMember(member);
		// 회원가입 기능 실행하고 그 결과를 가져온다.
		
		if(result == 1) {
			// 정상적으로 데이터가 삽입되었다.
			return true;
		}
		
		// result가 1이 아닌 경우는 삽입 실패
		return false;
	}
	
	// 회원정보 수정
	public boolean modify(Member member) {
		int result = dao.updateMember(member);
		
		if(result > 0 ) { // 1이랑 비교해도 상관은 없다.
			// 그러나... 나중에 여러분이 여러개의 행을 update 한 경우는
			// 영향받은 행수가 1개가 아니겠죠?? 그때는 0보다 클 경우가 성공입니다.
			return true;
		}
		
		return false;
	}
	
	// 회원정보 하나 가져오기
	public Member getMember(String id) {
		return dao.selectOne(id);
	}
	
	// 회원정보 모두 가져오기
	public List<Member> getAllMembers() {
		return dao.selectAll();
	}
	
}
