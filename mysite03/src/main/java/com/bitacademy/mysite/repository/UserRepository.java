package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.UserVo;


@Repository
public class UserRepository {

	public Boolean insert(UserVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = " insert" + "   into user" + " values (null, ?, ?, password(?), ?, now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			int count = pstmt.executeUpdate();

			// 5. 결과 처리
			result = count == 1;

		} catch (SQLException e) {
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

		return result;
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select no, name" + "  from user" + " where email = ?" + "   and password = password(?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);

				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
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

	public UserVo findByNo(Long no) {
		UserVo result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select * from user where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Long num = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(5);
				
				result = new UserVo();
				result.setNo(num);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
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
	
	public void update(UserVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			
			if("".equals(vo.getPassword())) {
				String sql = "update user set name=?, gender=? where no=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setLong(3, vo.getNo());
			} else {
				String sql = "update user set name=?, gender=?, password=password(?) where no=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setString(3, vo.getPassword());
				pstmt.setLong(4, vo.getNo());
			}

			rs = pstmt.executeQuery();
			

		} catch (SQLException e) {
			System.out.println("Error:" + e);
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
