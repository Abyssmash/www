package www.silver.util;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileDataUtil {
	
	private ArrayList<String> extNameArray = new ArrayList<String>() // 허용하는 확장자 정의를 한 것.
	{
		{
			add("gif");
			add("jpg");
			add("png");
		}
	};     //<-- 현재 코드는 활용하지는 않는다.. 얘는 선언이지 기능이 동작하지는 않는다. 절대 미리 예측 금지..
	
	//첨부파일 업로드 경로 변수값으로 가져옴 servlet-context.xml
	// Resource = 컨테이너에서 주입 : 이름으로 주입받겠다. 
	@Resource(name="uploadPath")
	// uploadpath의 의미: 첨부파일의 위치=여기에 저장하겠다.
	private String uploadPath;
	
	// 위에 방법 혹은 이 방법도 사용 가능
	//private String uploadPath="/tmp";
	
	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	/**
	 * 게시물 상세보기에서 첨부파일 다운로드 메서드 구현(공통)
	 */
	/**
	 responseBody 파일명
	 : 주입받기 위한 목적과 이 클래스 안에서 처리해주기 위한
	 */
	@RequestMapping(value="/download", method=RequestMethod.GET)
	@ResponseBody   // 어떤 데이터를 포함하여 전송.. 어노테이션.. view지정하지 않고 바로 클라이언트 요청으로 응답.
	public FileSystemResource fileDownload(@RequestParam("filename") String fileName, HttpServletResponse response) {
		File file = new File(uploadPath + "/" + fileName);
		response.setContentType("application/download; utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		return new FileSystemResource(file);
	}
	
	/**
	 * 파일 업로드 메서드(공통)
	 * @throws IOException 
	 */
	public String[] fileUpload(MultipartFile[] file) throws IOException {
		String[] files = new String[file.length];
		for(int i=0; i < file.length; i++) {
			if(file[i].getOriginalFilename()!="") {  // 실제 file객체가 존재한다면 = 파일 이름이 빈공간이 아니라면
				String originalName = file[i].getOriginalFilename();//확장자가져오기 위해서 전체파일명을 가져옴.
				UUID uid = UUID.randomUUID();//랜덤문자 구하기 맘에안든다. 
				String saveName = uid.toString() + "." + originalName.split("\\.")[1];//한글 파일명 처리 때문에...
				// 새로운 파일명을 만들겠다. 랜덤수 + 확장자		오류가 있음. 확인요망
//			String[] files = new String[] {saveName}; //형변환  files[0] 파일명이 들어 간다..
				// 올린 파일을 바이트로 바꿔줌
				byte[] fileData = file[i].getBytes();
				
				// uploadPath의 위치에 있는걸 저장된 파일명으로 저장
				File target = new File(uploadPath, saveName);
				FileCopyUtils.copy(fileData, target);
				// 변경된 파일명을 여기에 저장
				files[i]=saveName;
			}			
		}		
		return files;
	}

	public ArrayList<String> getExtNameArray() {
		return extNameArray;
	}

	public void setExtNameArray(ArrayList<String> extNameArray) {
		this.extNameArray = extNameArray;
	}
}

