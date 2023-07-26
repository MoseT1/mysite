package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	BoardRepository boardRepository;
	
	public void addBoard(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public BoardVo getBoard(Long no) {
		return boardRepository.findByNO(no);
	}
	
	public void updateBoard(Long no, String title, String contents) {
		boardRepository.updateTitleAndContentsByNO(no, title, contents);
	}
	
	public List<BoardVo> getBoardList(){
		return boardRepository.findAll();
	}
	
	public void replyBoard(BoardVo vo) {
		boardRepository.insertToReply(vo);
	}
	
	public void deleteBoard(Long no) {
		boardRepository.deleteByNo(no);
	}
}
