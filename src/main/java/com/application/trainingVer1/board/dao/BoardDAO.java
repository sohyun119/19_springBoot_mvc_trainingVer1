package com.application.trainingVer1.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.trainingVer1.board.dto.BoardDTO;

@Mapper // mapper파일과 매핑
public interface BoardDAO {

	public void insertBoard(BoardDTO boardDTO);
	public List<BoardDTO> selectListBoard();
	public BoardDTO selectOneBoard(long boardId);
	public String selectOnePasswd(long boardId);
	public void updateBoard(BoardDTO boardDTO);
	public void deleteBoard(long boardId);
	public void updateReadCnt(long boardId);
	
}



