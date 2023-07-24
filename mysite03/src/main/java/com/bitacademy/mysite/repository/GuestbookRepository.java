package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.exception.GuestbookRepositoryException;
import com.bitacademy.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {


	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() throws GuestbookRepositoryException{
		
		
		
		List<GuestbookVo> result = sqlSession.selectList("guestbook.findAll");			// resultType을 설정해놨으므로 자동으로 리턴
		return result;
	}

	public void insert(GuestbookVo vo) throws GuestbookRepositoryException{
		
		
		sqlSession.insert("guestbook.insert", vo);

	}

	public void deleteByNoAndPassword(Long no, String password) throws GuestbookRepositoryException {
		
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		
		sqlSession.delete("guestbook.deleteByNoAndPassword", map);
	}
	
//	private Connection getConnection() throws SQLException {
//		Connection conn = null;
//
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//
//			String url = "jdbc:mariadb://192.168.100.44:3306/webdb?charset=utf8";
//			conn = DriverManager.getConnection(url, "webdb", "webdb");
//		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		}
//
//		return conn;
//	}
}
