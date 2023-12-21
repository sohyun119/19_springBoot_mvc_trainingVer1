package com.application.trainingVer1.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.trainingVer1.board.dao.BoardDAO;
import com.application.trainingVer1.board.dto.BoardDTO;

/*
 * 
	# 패스워드 암호화
	
	1) build.gradle 파일에 dependency를 추가한다.
	
		// 패스워드 인코더
		implementation 'org.springframework.boot:spring-boot-starter-security' 
	
	2) SecurityConfiguration 클래스를 생성한다.
	
		@Configuration
		@EnableWebSecurity
		public class SecurityConfiguration{
		
		    @Bean
		    public PasswordEncoder passwordEncoder(){
		        return new BCryptPasswordEncoder();
		    }
		
		    @Bean
			public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
				http.cors().disable()			//cors 방지
					.csrf().disable()			//csrf 방지
					.formLogin().disable()		//기본 로그인페이지 없애기
					.headers().frameOptions().disable();
		 
				return http.build();
			}
		    
		}
	
	
	3) 서비스 로직에서 BCryptPasswordEncoder 객체를 생성한다.
	
		@Autowired
		private PasswordEncoder passwordEncoder;
	
	4) 사용법
	
		- passwordEncoder.encode(암호화하고 싶은 패스워드)   				// encode(평문)메서드를 통하여 패스워드를 암호화한다.
		- passwordEncoder.matches(입력받은 패스워드, 암호화된 패스워드) 	// matches(평문,암호문) 메서드를 통하여 입력받은 패스워드와 암호화된 패스워드를 비교한다.
		- 암호화된 패스워드를 복호화하는 메서드는 존재하지 않는다.		

*/


@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public void addBoard(BoardDTO boardDTO) {
		boardDTO.setPasswd(passwordEncoder.encode(boardDTO.getPasswd()));
		boardDAO.insertBoard(boardDTO);
	}

	@Override
	public List<BoardDTO> getBoardList() {
		return boardDAO.selectListBoard();
	}

	
	@Override
	public BoardDTO getBoardDetail(long boardId , boolean isUpdateReadCnt) {
		
		if (isUpdateReadCnt) {
			boardDAO.updateReadCnt(boardId);
		}
		
		return boardDAO.selectOneBoard(boardId);
	
	}

	
	@Override
	public boolean checkAuthorizedUser(BoardDTO boardDTO) {

		boolean isAuthorizedUser = false;
		
		String encodedPassword = boardDAO.selectOnePasswd(boardDTO.getBoardId());
		boolean isMatched = passwordEncoder.matches(boardDTO.getPasswd() , encodedPassword);
		
		//if (bCryptPasswordEncoder.matches(boardDTO.getPasswd(), boardDAO.selectOnePasswd(boardDTO.getBoardId()))) {}
		
		if (isMatched) {
			isAuthorizedUser = true;
		}
		
		return isAuthorizedUser;
		
	}

	@Override
	public void modifyBoard(BoardDTO boardDTO) {
		boardDAO.updateBoard(boardDTO);
	}

	@Override
	public void removeBoard(long boardId) {
		boardDAO.deleteBoard(boardId);
	}
	
	
}
