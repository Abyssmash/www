package www.sliver.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import www.silver.vo.MemberVO;
import www.sliver.dao.IF_MemberDao;

@Service	//해달 클래스를 객체로 만들어라
public class MemberServiceImpl implements IF_MemberService{
	@Inject
	IF_MemberDao memberdao;
	@Override
	public void join(MemberVO membervo) throws Exception {
		System.out.println("join 서비스");
		// dao에게 작업 지시해야한다..
		// 실제 데이터를 저장하도록 지시한다.
		memberdao.insertOne(membervo);
	}
	
}
