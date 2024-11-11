package www.silver.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import www.silver.dao.IF_BoardDao;
import www.silver.vo.BoardVO;

@Service
public class BoardServiceImpl implements IF_BoardService{
	
	@Inject	// 주입 받고 사용하겠다.
	IF_BoardDao boarddao;
	
	@Override
	public void addBoard(BoardVO boardvo) throws Exception{
		// TODO Auto-generated method stub
		if(boardvo != null) {
			boardvo.setViewmember("공개");
		}else {
			boardvo.setViewmember("비공개");
		}
		boarddao.insertBoard(boardvo);
		// dao > mapper > DB insert
	}
}
