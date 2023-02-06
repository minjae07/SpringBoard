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
		boardMapper.boardDelete(idx);	//등록 성공
	}
	
	
}