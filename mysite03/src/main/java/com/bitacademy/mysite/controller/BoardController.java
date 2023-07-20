package com.bitacademy.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/write")
	public String write() {
		
		
		return "";
	}
	
	@RequestMapping("/modify")
	public String modify() {
		
		return "";
	}
	
	
	
}
