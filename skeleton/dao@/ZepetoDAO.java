package com.planner.godsaeng.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class ZepetoDAO {
	final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	final String JDBC_URL = "jdbc:mariadb://localhost/godsaeng";
	
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(JDBC_URL,"root","root");
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(conn);
		return conn;
	}
	
	public List<DataDTO>getAll()throws Exception{
		Connection conn = open();
		List<DataDTO>dataList = new ArrayList<>();
		
		String sql = "select did,uid,challenge,DATE_FORMAT(date,'yyyy-MM-dd hh:mm:ss')as cdate from godsaeng";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs){
			while(rs.next()) {
				DataDTO d = new DataDTO();
				d.setDid(rs.getInt("did"));
				d.setUid(rs.getString("uid"));
				d.setChallenge(rs.getString("challenge"));
				d.setDate(rs.getString("cdate"));
				dataList.add(d);
			}
			return dataList;
		}
		
		
		
	}
	public void addData(DataDTO d) throws Exception{
		Connection conn = open();
		String sql = "insert into godsaeng(uid,challenge,date) values(?,?,CURRENT_TIMESTAMP())";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		
		try(conn;pstmt){
			pstmt.setString(1, d.getUid());
			pstmt.setString(2, d.getChallenge());
			pstmt.executeUpdate();
		}
	}
	public void delData(int did) throws SQLException{
		Connection conn = open();
		String sql = "delete from godsaeng where did=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn; pstmt){
			pstmt.setInt(1, did);
			if(pstmt.executeUpdate() == 0) {
				throw new SQLException("DB에러입니다.");
			}
		}
	}
	
	
	

}
