package kr.board.mapper;

import java.util.List; 

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import kr.board.entity.Board;

@Mapper //-Mybatis API
public interface BoardMapper {
	public List<Board> getLists(); //전체 리스트, SQL 파일 연결 
	public void boardInsert(Board vo);
	public Board boardContent(int idx);
	public void boardDelete(int idx);
	public void boardUpdate(Board vo);
	
	
	@Update("update board set count=count+1 where idx=#{idx}")	//조회수
	public void boardCount(int idx);
}

