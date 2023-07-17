package com.bitacademy.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bitacademy.mysite.vo.BoardVo;

public class BoardDao {
	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into values (null, ?, ?, 0, now(), ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getRegDate());
			pstmt.setLong(4, vo.getgNo());
			pstmt.setLong(5, vo.getoNo());
			pstmt.setLong(6, vo.getDepth());
			pstmt.setLong(7, vo.getUserNo());
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
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
	
	public void deleteByNOandUserNO() {
		
	}
	
	
	
	
	
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.100.55:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
	
	
}
