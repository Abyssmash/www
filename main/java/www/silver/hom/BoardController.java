package www.silver.hom;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import www.silver.service.IF_BoardService;
import www.silver.util.FileDataUtil;
import www.silver.vo.BoardVO;
import www.silver.vo.PageVO;

@Controller
public class BoardController {
	
	@Inject	// 주입 받고 사용
	IF_BoardService boardservice;
	
	@Inject
	FileDataUtil filedatautil;
	
	@GetMapping(value = "board")
	public String board(Model model,
			@ModelAttribute PageVO pagevo) throws Exception{
		// Controller > service > dao > mapper
		// 전체 게시글을 가져오는 작업이 필요
		//System.out.println("확인");
		if(pagevo.getPage()==null) {
			pagevo.setPage(1);
		}
		// 3가지 정보만 있으면 페이지 계산이 가능
		// 1. 현재 페이지 
		// 2. 페이지당 게시물 수
		// 3. 전체 페이지 수
		pagevo.setTotalCount(boardservice.totalCountBoard());
		
		// 확인용
//		System.out.println(pagevo.getStartNo()+"시작 글번호");
//		System.out.println(pagevo.getEndNo()+"마지막 글번호");
//		System.out.println(pagevo.getStartPage()+"그룹시작 번호");
//		System.out.println(pagevo.getEndPage()+"그룹 마지막 번호");
		
		
		// 서비스 Layer에 전체글 서비스를 요청하고 결과를 리턴
		List<BoardVO> list = boardservice.boardList(pagevo);
		// 단위테스트
		// System.out.println(list.size()+"건 가져옴");
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
	@PostMapping(value="mod")
	public String modsave(@ModelAttribute BoardVO boardvo)throws Exception{
		// 단위 테스트
		//System.out.println(bvo.getTitle());
		boardservice.modBoard(boardvo);
		return "redirect:board";
	}
	@GetMapping(value="mod")
	public String mod(@RequestParam("modno") String modno, Model model)throws Exception {
		BoardVO bvo = boardservice.modBoard(modno);
		//System.out.println(bvo.getTitle());
		// sysout은 서버 입장에서 부하가 걸리는 작업이다.
		// 그래서 테스트 했다면 삭제하거나 주석을 해야한다.
		// 실제로 sysout은 잘사용하지 않는다.
		// 테스트 하기 위해서는 junit test라는 도구를 사용해야한다.
		// 또 기록을 남기기위해선 로그라는 기능을 사용해야한다.
		// 로그는 홈 컨트롤러에 가면 볼 수 있다.
		model.addAttribute("boardvo", bvo);
		return "board/modform";
		// 뷰 지정은 String
	}
	@GetMapping(value="del")
	public String del(@RequestParam("delno") String delno)throws Exception {
		boardservice.deleteBoard(delno);
		return "redirect:board";
	}
	@PostMapping(value = "bwrdo")
	public String bwrdo(@ModelAttribute BoardVO boardvo, MultipartFile[] file) throws Exception{
		//System.out.println(boardvo.toString());
		//업로드 되는지 확인하는 중간 코드
//		System.out.println(file.length);
//		for(int i = 0; i<file.length; i++) {
//			System.out.println(file[i].getOriginalFilename());
//		}
		boardservice.addBoard(boardvo);	//서비스보드에 있는 addboard를 보드브이오에
		
		String[] newFileName=filedatautil.fileUpload(file);
		//System.out.println(newFileName);
		boardvo.setFilename(newFileName);		boardservice.addBoard(boardvo);
		// 랜덤으로 나온 문자는 DB에 저장해야하기 때문에 글자수를 세어보자
		return "redirect:board";
	}
	@GetMapping(value="view")
	public String boardView(@RequestParam("no") String no,
			Model model) throws Exception {
		BoardVO boardvo = boardservice.getBoard(no);
		// attach 가져오기
		List<String> attachList = boardservice.getAttach(no);
		// view에게 전송할 값들: 게시글과 첨부파일
		model.addAttribute("boardvo",boardvo);
		model.addAttribute("attachList",attachList);
		return "board/dview";
	}
}
