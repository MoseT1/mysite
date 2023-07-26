package com.bitacademy.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitacademy.mysite.service.BoardService;
import com.bitacademy.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping("")
	public String list(Model model) {
		
		List<BoardVo> list = boardService.getBoardList();
		
		model.addAttribute("list", list);
		
		return "/board/list";
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(@RequestParam(value="n", required=true, defaultValue="0") Long no, Model model) {
		BoardVo boardVo = boardService.getBoard(no);
		model.addAttribute("boardVo", boardVo);
		return "/board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		
		
		return "/board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BoardVo vo) {
		
		boardService.addBoard(vo);
		
		
		return "redirect:/board";
	}
	

	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@RequestParam(value="n", required=true, defaultValue="0") Long no, Model model) {
		
		BoardVo boardVo = boardService.getBoard(no);
		model.addAttribute("boardVo", boardVo);
		
		return "/board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(BoardVo vo) {
		
		boardService.updateBoard(vo.getNo(), vo.getTitle(), vo.getContents());
		
		return "redirect:/board";
	}
	
	
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public String reply(@RequestParam(value="n", required=true, defaultValue="0") Long no, Model model) {
		
		BoardVo boardVo = boardService.getBoard(no);
		model.addAttribute("boardVo", boardVo);
		
		return "/board/reply";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(BoardVo vo) {
		
		boardService.replyBoard(vo);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@RequestParam(value="n", required=true, defaultValue="0") Long no) {
		
		boardService.deleteBoard(no);
		
		return "redirect:/board";
	}
	
}
