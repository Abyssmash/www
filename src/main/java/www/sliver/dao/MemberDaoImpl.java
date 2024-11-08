package www.sliver.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import www.silver.vo.MemberVO;
@Repository
public class MemberDaoImpl implements IF_MemberDao{
	
	// sqlSession이 필요합니다.	Mybatis에서 제공하는 객체
	// 주소가 올바르게 주입되려면 root-context.xml에서 설정이 잘 되어 있어야한다.
	@Inject
	SqlSession sqlSession;
	private static String mapperQuery = "www.silver.dao.IF_MemberDao";
	// www.silver.dao.IF_MemberDao.insertOne	<< 매핑 정보
	@Override
	public void insertOne(MemberVO membervo) throws Exception {
		// TODO Auto-generated method stub
		// sqlSessionTemplate 의 객체의 메서드를 call하면 된다.
		//sqlSession.insert("mapper와 매핑정보","파라미터값");
		sqlSession.insert(mapperQuery+".insertOne",membervo);
	}

}
