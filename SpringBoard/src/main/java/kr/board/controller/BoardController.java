package kr.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller
public class BoardController {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	//@ResponseBody -> jackson-dtabind(객체를 -> JSON 데이터 포멧으로 변환)  
	
	@ResponseBody
	@RequestMapping("/boardList.do")
	public List<Board> boardList(){	//@ResponseBody: JSON 사용시 사용
		List<Board> list = boardMapper.getLists();
		return list;	//JSON 데이터 형식으로 변환(API)해서 리턴(응답)하겠다.
	}
	
	@ResponseBody
	@RequestMapping("/boardInsert.do")
	public void boardInsert(Board vo) {
		boardMapper.boardInsert(vo);	//등록 성공
	}
	
	@ResponseBody
	@RequestMapping("/boardDelete.do")
	public void boardDelete(@RequestParam("idx") int idx) {
		boardMapper.boardDelete(idx);	//삭제 성공
	}
	
	@ResponseBody
	@RequestMapping("/boardUpdate.do")
	public void boardUpdate(Board vo) {
		boardMapper.boardUpdate(vo);
	}
	
	@ResponseBody
	@RequestMapping("/boardContent.do")
	public Board boardContent(int idx) {
		Board vo = boardMapper.boardContent(idx);
			return vo; //vo ->Json
		}
	@ResponseBody
	@RequestMapping("/boardCount.do")
	public Board boardCount(int idx) {
		boardMapper.boardCount(idx);
		Board vo = boardMapper.boardContent(idx);
			return vo; //vo ->Json
		}
	
	
	
									//동기 방식(_delete_)
		/*
		 * @Controller public class BoardController { // boardList.do
		 * 
		 * @Autowired private BoardMapper mapper; // HandlerMapping
		 * 
		 * @RequestMapping("/boardList.do") public String boardList(Model model) {
		 * 
		 * List<Board> list = mapper.getLists(); model.addAttribute("list", list);
		 * return "boardList"; // /WEB-INF/views/boardList.jsp -> forward --View //
		 * Resolver(servlet-contenxt.xml)설정이 되어있음 }
		 * 
		 * @GetMapping("/boardForm.do") public String boardForm() { return "boardForm";
		 * }
		 * 
		 * @PostMapping("boardInsert.do") public String boardInsert(Board vo) { //title,
		 * content, writer => 파라메터 수집(Board) mapper.boardInsert(vo); //등록 return
		 * "redirect:/boardList.do"; //redirect }
		 * 
		 * @GetMapping("/boardContent.do") public String
		 * boardContent(@RequestParam("idx") int idx, Model model) { //model=객체바인딩,
		 * /idx로 넘어오므로 @RequestParam("idx") 생략 가능 Board vo =mapper.boardContent(idx);
		 * //조회수 증가 mapper.boardCount(idx); model.addAttribute("vo", vo); // ${vo.idx}
		 * 
		 * return "boardContent"; }
		 * 
		 * @GetMapping("boardDelete.do/{idx}") public String
		 * boardDelete(@PathVariable("idx") int idx) { mapper.boardDelete(idx); //삭제
		 * return "redirect:/boardList.do"; }
		 * 
		 * @GetMapping("boardUpdateForm.do/{idx}") public String
		 * boardUpdateForm(@PathVariable("idx") int idx, Model model) { Board vo
		 * =mapper.boardContent(idx); model.addAttribute("vo", vo); return
		 * "boardUpdate"; //boardUpdate.jsp }
		 * 
		 * @PostMapping("/boardUpdate.do") public String boardUpdate(Board vo) {
		 * mapper.boardUpdate(vo); //수정 return "redirect:/boardList.do"; } }
		 */
	
	
}