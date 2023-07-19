package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.exception.GuestbookRepositoryException;
import com.bitacademy.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {

	public List<GuestbookVo> findAll() throws GuestbookRepositoryException{

		List<GuestbookVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from guestbook";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1); // 1번째 column가져옴
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String reg_date = rs.getString(5);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setMessage(message);
				vo.setReg_date(reg_date);
				result.add(vo);
			}

		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.toString());
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public void insert(GuestbookVo vo) throws GuestbookRepositoryException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "insert into guestbook values(null, ?, ?, ?, curdate())";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			

			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.toString());
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void deleteByPassword(Long no, String password) throws GuestbookRepositoryException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "delete from guestbook where no=? and password=?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.toString());
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.100.56:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
}
