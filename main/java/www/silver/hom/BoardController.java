package www.silver.hom;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import www.silver.service.IF_BoardService;
import www.silver.vo.BoardVO;

@Controller
public class BoardController {
	
	@Inject	// 주입 받고 사용
	IF_BoardService boardservice;
	
	@GetMapping(value = "board")
	public String board(Model model) throws Exception{
		// Controller > service > dao > mapper
		// 전체 게시글을 가져오는 작업이 필요
		//System.out.println("확인");
		
		// 서비스 Layer에 전체글 서비스를 요청하고 결과를 리턴
		List<BoardVO> list = boardservice.boardList();
		// 단위테스트
		System.out.println(list.size()+"건 가져옴");
		// 리턴받은 list 변수의 값을 모델 객체로 뷰에게 전송하는 코드 
		model.addAttribute("list",list);
		// 뷰를 지정
		return "board/bbs";
	}
	@GetMapping(value = "bwr")
	public String bwr() throws Exception{
		// Controller > service > dao > mapper
		// 전체 게시글을 가져오는 작업이 필요
		return "board/bbswr";
	}
	@GetMapping(value="del")
	public String del(@RequestParam("delno") String delno)throws Exception {
		boardservice.deleteBoard(delno);
		return "redirect:board";
	}
	@PostMapping(value = "bwrdo")
	public String bwrdo(@ModelAttribute BoardVO boardvo) throws Exception{
		//System.out.println(boardvo.toString());
		boardservice.addBoard(boardvo);	//서비스보드에 있는 addboard를 보드브이오에
		return "redirect:board";
	}
}
