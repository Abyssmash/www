package www.silver.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import www.silver.vo.BoardVO;
import www.silver.vo.PageVO;

@Repository
public class BoardDaoImpl implements IF_BoardDao {
	@Inject
	SqlSession sqlsession;
	final String mapperQuery = "www.silver.dao.IF_BoardDao";
	@Override
	public void insertBoard(BoardVO boardvo) throws Exception {
		// TODO Auto-generated method stub		
		// sqlsession을 통해서 mapper와 매핑해야하기에 정보를 넘겨준다.
		//System.out.println("dao");
		//System.out.println(boardvo.toString());
		sqlsession.insert(mapperQuery+".inin", boardvo);
	}
	@Override
	public List<BoardVO> selectAll(PageVO pagevo) throws Exception{
		return sqlsession.selectList(mapperQuery+".selectall",pagevo);
	}
	@Override
	public void deleteBoard(String delno) throws Exception{
		sqlsession.delete(mapperQuery+".delone",delno);
		}
	@Override
	public BoardVO selectOne(String title) throws Exception {
		// TODO Auto-generated method stub
//		HashMap aa = new HashMap<String, String>();
//		aa.put("tt", title);
//		aa.put("dd", date);
		return sqlsession.selectOne(mapperQuery+".selectTitle", title);
	}
	@Override
	public void updateBoard(BoardVO boardvo) throws Exception {
		// TODO Auto-generated method stub
		sqlsession.update(mapperQuery+".update", boardvo);
	}
	@Override
	public int cntBoard() throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectOne(mapperQuery+".allcnt" );
	}
}
