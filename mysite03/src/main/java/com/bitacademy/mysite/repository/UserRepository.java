package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.exception.UserRepositoryException;
import com.bitacademy.mysite.vo.UserVo;


@Repository
public class UserRepository {

	@Autowired
	private DataSource dataSource;   //이거 없어도 될거같은데?
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(UserVo vo)  throws UserRepositoryException{

		return 1 == sqlSession.insert("user.insert", vo);
		
	}

	public UserVo findByEmailAndPassword(UserVo vo) throws UserRepositoryException {		//오버로딩
		return findByEmailAndPassword(vo.getEmail(), vo.getPassword());
	}
	
	
	public UserVo findByEmailAndPassword(String email, String password) throws UserRepositoryException {
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}

	public UserVo findByNo(Long no)  throws UserRepositoryException{
		

		
		return sqlSession.selectOne("user.findByNo", no);
	}
	
	public void update(UserVo vo)  throws UserRepositoryException {

		sqlSession.update("user.update", vo);
		
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	//  applicationContext에 설정
	
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
