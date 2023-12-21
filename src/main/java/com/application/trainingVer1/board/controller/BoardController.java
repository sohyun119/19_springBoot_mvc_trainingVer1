package com.application.trainingVer1.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.application.trainingVer1.board.dto.BoardDTO;
import com.application.trainingVer1.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/addBoard")
	public ModelAndView addBoard() {
		return new ModelAndView("board/addBoard");
	}
	
	@PostMapping("/addBoard")
	@ResponseBody
	public String addBoard(@ModelAttribute BoardDTO boardDTO) {
		
		// 단위 테스트
		//System.out.println(boardDTO);
		boardService.addBoard(boardDTO);
		
		String jsScript = "<script>";
			   jsScript += "alert('Post Added');";
			   jsScript += "location.href='boardList';";
			   jsScript += "</script>";
		
		return jsScript;
		
	}
	
	
	@GetMapping("/boardList")
	public ModelAndView boardList() {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("board/boardList"); 						// view
		mv.addObject("boardList", boardService.getBoardList()); //model
		
		return mv;
		
	}
	
	
	@GetMapping("/boardDetail")
	public ModelAndView boardDetail(@RequestParam("boardId") long boardId) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("board/boardDetail"); 							  		 // view
		mv.addObject("boardDTO" , boardService.getBoardDetail(boardId , true));  // model
		
		return mv;
		
	}
	
	
	@GetMapping("/authentication")
	public ModelAndView authentication(@RequestParam("boardId") long boardId , @RequestParam("menu") String menu) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("board/authentication");  								 // view
		mv.addObject("boardDTO" , boardService.getBoardDetail(boardId , false)); // model
		mv.addObject("menu", menu);
		
		return mv;
	
	}
	
	
	@PostMapping("/authentication")
	@ResponseBody
	public String authentication(@ModelAttribute BoardDTO boardDTO , @RequestParam("menu") String menu) {
		
		String jsScript = "";
		if (boardService.checkAuthorizedUser(boardDTO)) {
			
			if (menu.equals("update")) {
				jsScript = "<script>";
				jsScript += "location.href='modifyBoard?boardId=" + boardDTO.getBoardId() + "';";
				jsScript += "</script>";
			}
			else if (menu.equals("delete")) {
				jsScript = "<script>";
				jsScript += "location.href='removeBoard?boardId=" + boardDTO.getBoardId() + "';";
				jsScript += "</script>";
			}
			
		}
		else {
			jsScript = "<script>";
			jsScript += "alert('Check your Id or Password');";
			jsScript += "history.go(-1);";
			jsScript += "</script>";
		}
		
		return jsScript;
	
	}
	
	
	@GetMapping("/modifyBoard")
	public ModelAndView modifyBoard(@RequestParam("boardId") long boardId) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("board/modifyBoard");
		mv.addObject("boardDTO", boardService.getBoardDetail(boardId , false));
		
		return mv;
	
	}
	
	
	@PostMapping("/modifyBoard")
	@ResponseBody
	public String modifyBoard(@ModelAttribute BoardDTO boardDTO) {
		
		boardService.modifyBoard(boardDTO);
		
		String jsScript = "<script>";
			   jsScript += "alert('It is changed.');";
			   jsScript += "location.href='boardList';";
			   jsScript += "</script>";
		
		return jsScript;	   
			   
	}
	
	
	@GetMapping("/removeBoard")
	public ModelAndView removeBoard(@RequestParam("boardId") long boardId) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/removeBoard");
		mv.addObject("boardId", boardId);
		
		return mv;
		
	}

	
	@PostMapping("/removeBoard")
	@ResponseBody
	public String postRemoveBoard(@RequestParam("boardId") long boardId) {
		
		boardService.removeBoard(boardId);
		
		String jsScript = "<script>";
		   	   jsScript += "alert('It has been deleted.');";
		   	   jsScript += "location.href='boardList';";
		   	   jsScript += "</script>";
	
		return jsScript;	
		
	}
	
	
	

}


