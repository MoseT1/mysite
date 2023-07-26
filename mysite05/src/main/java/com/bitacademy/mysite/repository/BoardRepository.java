package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.exception.BoardRepositoryException;
import com.bitacademy.mysite.vo.BoardVo;


@Repository
public class BoardRepository {
	
	
	@Autowired
	private SqlSession sqlSession;
	
	
	
	
	public void insert(BoardVo vo) throws BoardRepositoryException {
		
		sqlSession.insert("board.insert", vo);
		
	}

	
	
	public BoardVo findByNO(Long no) throws BoardRepositoryException {
		
		return sqlSession.selectOne("board.findByNo", no);
		
	
	}
	
	public void updateTitleAndContentsByNO(Long no, String title, String contents) throws BoardRepositoryException {
		
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("title", title);
		map.put("contents", contents);
		
		sqlSession.update("board.updateTitleAndContentsByNO", map);
	}


	public List<BoardVo> findAll() throws BoardRepositoryException {
		//user이랑 join해서 이름까지 넘겨야함.
		List<BoardVo> result = sqlSession.selectList("board.findAll");

		return result;
		
	}
	
	public void insertToReply(BoardVo vo) throws BoardRepositoryException {
		
		sqlSession.insert("board.insertToReply", vo);
		sqlSession.update("board.updateToReply", vo);
		
	}
	
	public void deleteByNo(Long no) {
		sqlSession.delete("board.deleteByNo", no);
		
	}	
	
	
}
