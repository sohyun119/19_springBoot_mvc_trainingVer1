package com.application.trainingVer1.board.service;

import java.util.List;

import com.application.trainingVer1.board.dto.BoardDTO;

public interface BoardService {

	public void addBoard(BoardDTO boardDTO);
	public List<BoardDTO> getBoardList();
	public BoardDTO getBoardDetail(long boardId , boolean isUpdateReadCnt);
	public boolean checkAuthorizedUser(BoardDTO boardDTO);
	public void modifyBoard(BoardDTO boardDTO);
	public void removeBoard(long boardId);
	
}
