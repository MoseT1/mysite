package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.GuestbookRepository;
import com.bitacademy.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	
	public List<GuestbookVo> getMessageList() {
		List<GuestbookVo> list = guestbookRepository.findAll();
		
		return list;
	}
	
	public void deleteMessage(Long no, String password) {
		guestbookRepository.deleteByNoAndPassword(no, password);
	}
	
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}
	
	
	
}
