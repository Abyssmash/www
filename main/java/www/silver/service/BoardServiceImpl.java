package www.silver.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import www.silver.dao.IF_BoardDao;
import www.silver.vo.BoardVO;
import www.silver.vo.PageVO;

@Service
public class BoardServiceImpl implements IF_BoardService{
	
	@Inject	// 주입 받고 사용하겠다.
	IF_BoardDao boarddao;
	
	@Override
	public void addBoard(BoardVO boardvo) throws Exception{
		// TODO Auto-generated method stub
		if(boardvo.getViewmember() != null) {
			if(boardvo.getViewmember().equals("1")) {
				boardvo.setViewmember("공개");
			}
		}else {
			boardvo.setViewmember("비공개");
		}
		boarddao.insertBoard(boardvo);
		// dao > mapper > DB insert
		
		// 만약 첨부파일이 있다면 첨부파일을 kboard_attach로 저장해라
		String[] fname = boardvo.getFilename();
		//System.out.println(fname.length);
		for(int i = 0; i < fname.length; i++) {
			// kboard_attach 테이블에 저장하는 코드
			if(fname[i]!=null)
				boarddao.insertAttach(fname[i]);
		}
	}

	@Override
	public List<BoardVO> boardList(PageVO pagevo) throws Exception {
		// TODO Auto-generated method stub
		// 처리하다가 DB 작업이 필요
		
		// 이 코드를 JSTL로 바꿔보자
		List<BoardVO>list = boarddao.selectAll(pagevo);
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
		return boarddao.selectOne(modno);
	}

	@Override
	public void modBoard(BoardVO boardvo) throws Exception {
		// TODO Auto-generated method stub
		if(boardvo.getViewmember() != null) {
			if(boardvo.getViewmember().equals("1")) {
				boardvo.setViewmember("공개");
			}
		}else {
			boardvo.setViewmember("비공개");
		}
		// 게시글을 kboard에 저장해라
		boarddao.updateBoard(boardvo);
	}
	@Override
	public int totalCountBoard() throws Exception {
		// TODO Auto-generated method stub
		return boarddao.cntBoard();
	}

	@Override
	public BoardVO getBoard(String no) throws Exception {
		// TODO Auto-generated method stub
		return boarddao.selectOne(no);
	}

	@Override
	public List<String> getAttach(String no) throws Exception {
		// TODO Auto-generated method stub
		return boarddao.selectAllAttach(no);
	}
	
}
