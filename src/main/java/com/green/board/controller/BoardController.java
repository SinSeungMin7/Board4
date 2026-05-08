package com.green.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.board.dto.BoardDto;
import com.green.board.mapper.BoardMapper;
import com.green.menus.dto.MenuDTO;
import com.green.menus.mapper.MenuMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/Board")
public class BoardController {
	
	@Autowired
	private  MenuMapper   menuMapper;
	
	@Autowired
	private  BoardMapper  boardMapper;
	
	// /Board/List?menu_id=MENU01
	@RequestMapping("/List")
	public  ModelAndView   list( MenuDTO  menuDto  ) {
		
		// 메뉴전체목록 조회 : menus.jsp가 사용할 부분
		List<MenuDTO>  menuList  =  menuMapper.getMenuList();
		log.info("menuList:" + menuList);
				
		// 게시물 목록 조회 : list.jsp가 사용할 부분 (menu_id=MENU01)
		List<BoardDto>  boardList  =  boardMapper.getBoardList( menuDto );		
		log.error("boardList:" + boardList);
		
		// 넘어온 menu_id 이며 동시에 넘겨줄 menu_id이다
		String menu_id = menuDto.getMenu_id(); // 넘어온 menu_id를 담고있는 변수
		MenuDTO menu   = menuMapper.getMenu(menuDto);
		
		ModelAndView  mv  =  new  ModelAndView();
		mv.setViewName("board/list"); // /WEB-INF/views/board/list.jsp
		mv.addObject("menuList",  menuList);
		mv.addObject("bList", boardList);
		mv.addObject("menu_id", menu_id); // 현재 메뉴정보
		mv.addObject("menu", menu);
		return  mv;
	}
	// /Board/View?idx=7&menu_id=MENU01
	@RequestMapping("/View")
	public ModelAndView view( BoardDto boardDto ) {
		
		// 전체 메뉴 목록 조회
		List<MenuDTO> menuList = menuMapper.getMenuList();
		
		// idx 글의 조회수를 1 증가해야한다
		boardMapper.incHit( boardDto );  
		
		// idx 로 조회한 게시글
		BoardDto board = boardMapper.getBoard(boardDto);
		System.out.println("board:" + board);
		// board:BoardDto [idx=1, menu_id=MENU01, title=JAVA Hello, writer=java, regdate=2026-05-04 15:17:00, hit=0]
		
		// content 안에 있는 엔터 \n 를 <br>로 변경해주는 작업 -> content
		board.setContent(board.getContent().replace("\n","<br>" ) );
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/view");
		mv.addObject("menuList", menuList );
		mv.addObject("board", board);
		mv.addObject("menu_id", board.getMenu_id());
		 
		return mv;
	}
	
	///Board/WriteForm?menu_id=MENU01
	@RequestMapping("/WriteForm")
	public ModelAndView writeForm(BoardDto boardDto) {
		
		// 메뉴목록
		List<MenuDTO> menuList = menuMapper.getMenuList();
		
		System.out.println("/Board/WriteForm boardDto:" + boardDto);
		
		String menu_id = boardDto.getMenu_id();
		String menu_name = menuMapper.getMenuName( menu_id );
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/write");
		mv.addObject("menu_id", menu_id);
		mv.addObject("menu_name", menu_name);
		mv.addObject("menuList", menuList);
		return mv;
	}
	
	// /Board/Write?menu_id=MENU01&title=a&content=a&writer=a
	@RequestMapping("/Write")
	public ModelAndView write(BoardDto boardDto) {
		System.out.println("write boardDto: " + boardDto);
		// write boardDto: BoardDto(idx=0, menu_id=MENU01, title=aaa, content=aa, writer=aaa, regdate=null, hit=0)

		// db에 저장
		boardMapper.insertBoard(boardDto);
		
		
		String menu_id  = boardDto.getMenu_id(); 
		
		// 페이지 이동
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + menu_id);
		
		return mv;
	}
	
	///Board/Delete?idx=6&menu_id=MENU01
	@RequestMapping("/Delete")
	public ModelAndView delete(BoardDto boardDto) {
		
		System.out.println("delete boardDto: " + boardDto);
		
		boardMapper.deleteBoard(boardDto);
		
		String menu_id = boardDto.getMenu_id();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + menu_id);
		return mv;
		
	}
	
	// /Board/UpdateForm?idx=5&menu_id=MENU01
	@RequestMapping("/UpdateForm")
	public ModelAndView updateForm(BoardDto boardDto) {
		
		// 메뉴 목록
		List<MenuDTO> menuList = menuMapper.getMenuList();
		// 넘어온 정보
		System.out.println("updateForm boardDto: " + boardDto);
		
		// 수정하기위해 조회한 정보
		BoardDto board = boardMapper.getBoard(boardDto);
		System.out.println("조회된 boardDto: " + board);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/update");
		mv.addObject("board", board);
		mv.addObject("menuList", menuList);
		
		return mv;
	}
	
	@RequestMapping("/Update")
	public ModelAndView update(BoardDto boardDto) {
		boardMapper.updateBoard(boardDto);
		
		String menu_id = boardDto.getMenu_id();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + menu_id);

		return mv; 
	}
	
}











