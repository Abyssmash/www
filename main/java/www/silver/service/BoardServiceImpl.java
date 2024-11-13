package www.silver.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import www.silver.dao.IF_BoardDao;
import www.silver.vo.BoardVO;

@Service
public class BoardServiceImpl implements IF_BoardService{
	
	@Inject	// 주입 받고 사용하겠다.
	IF_BoardDao boarddao;
	
	@Override
	public void addBoard(BoardVO boardvo) throws Exception{
		// TODO Auto-generated method stub
		if(boardvo.getViewmember() != null) {
			if(boardvo.getViewmember().contentEquals("1")) {
				boardvo.setViewmember("공개");
			}
		}else {
			boardvo.setViewmember("비공개");
		}
		boarddao.insertBoard(boardvo);
		// dao > mapper > DB insert
	}

	@Override
	public List<BoardVO> boardList() throws Exception {
		// TODO Auto-generated method stub
		// 처리하다가 DB 작업이 필요
		
		// 이 코드를 JSTL로 바꿔보자
		List<BoardVO>list = boarddao.selectAll();
		for(BoardVO b : list) {
			String date = b.getIndate();
			b.setIndate(date.substring(0,10));
		}
		return list;
	}

	@Override
	public void deleteBoard (String delno) throws Exception {
		// TODO Auto-generated method stub
		boarddao.deleteBoard(delno);
	}

	@Override
	public BoardVO modBoard(String modno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
