package com.bitacademy.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitacademy.mysite.service.GuestbookService;
import com.bitacademy.mysite.vo.GuestbookVo;

@RequestMapping("/guestbook")
@Controller
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	

	@RequestMapping("")
	public String index(Model model) {
		
		List <GuestbookVo> list = guestbookService.getMessageList();
		
		model.addAttribute("list", list);
		
		return "/guestbook/list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addMessage(vo);
		return "redirect:/guestbook";
	}
	
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@RequestParam(value="n", required=true, defaultValue="0") Long no, Model model) {
		
		model.addAttribute("no", no);
		
		return "/guestbook/delete";
		
	}
	
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="n", required=true, defaultValue="0") Long no, String password) {  //변수명이 다르니까 작동 못함
		
		guestbookService.deleteMessage(no, password);
		return "redirect:/guestbook";
		
	}
}
