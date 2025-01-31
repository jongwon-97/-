package com.kosmo.model;

//DAO (Database Access Object) : 데이터베이스에 접근하여 CRUD로직을수행하는 객체
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kosmo.util.DBUtil;

public class MemberDAO {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	// 회원가입 처리 메서드 - insert문을 수행
	public int insert(MemberDTO user) {
		try {
			// 1. DB연결
			con = DBUtil.getConnection();
			// 2. sql문 작성 -insert 문
			String sql = "insert into member(idx,name,userId,userPw,email,mstate,indate) ";
			sql += "values(member_seq.nextval, ?,?,?,?,1,sysdate)";
			// ? : in paremeter
			// 3. PreparedStatement객체 얻기 - con통해 얻는다
			ps = con.prepareStatement(sql);// sql문을 미리 dbms 포맷에 맞게 전처리함
			ps.setString(1, user.getName());// ? 값 설정
			ps.setString(2, user.getUserId());
			ps.setString(3, user.getUserPw());
			ps.setString(4, user.getEmail());
			// 4. sql문 실행시키기 => ps.executeUpdate() =>DML문장(insert,delete,update)
			// ps.executeQuery() => DQL문장 (select)
			int n = ps.executeUpdate();
			// sql문에 의해 영향받은 레코드 개수를 반환한다
			// 5. 실행결과를 return
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			// 6. DB 연결 자원 반납(rs,ps,con==> close()작업)
			DBUtil.close(con, ps);
		}
	}// ------------------------------------

	/** 전체 회원 목록 가져오기 - select문 수행 */
	public List<MemberDTO> list() {
		try {
			con = DBUtil.getConnection();
			String sql = "select * from member order by idx asc";
			ps = con.prepareStatement(sql);
			// select문 ==> ResultSet executeQuery()
			rs = ps.executeQuery();
			return makeList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.close(con, ps, rs);
		}
	}// -----------------------------------

	public List<MemberDTO>makeList(ResultSet rs)throws SQLException{
		List<MemberDTO> arrList = new ArrayList<>();
		while (rs.next()) {
			int idx = rs.getInt("idx");
			String name = rs.getString("name");
			String userId = rs.getString("userId");
			String userPw = rs.getString("userPw");
			String email = rs.getString("email");
			int mstate = rs.getInt("mstate");
			java.sql.Date indate = rs.getDate("indate");

			MemberDTO record = new MemberDTO(idx, name, userId, userPw, email, mstate, indate);
			// 1개의 행
			arrList.add(record);// 테이블 구조
		} // while-----
		return arrList;
	
	}

	public int delete(int idx) {
		try {
			// 1. DB연결
			con = DBUtil.getConnection();

			String sql = "delete from member where idx = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idx);

			int n = ps.executeUpdate();
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			DBUtil.close(con, ps);
		}
	}

	// 회원번호(pk)로 회원정보 가져오기 => select문 수행 (단일행 레코드)
	public MemberDTO getMember(int idx) {
		try {
			con = DBUtil.getConnection();
			String sql = "select * from member where idx=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, idx);
			rs = ps.executeQuery();
			
			List<MemberDTO> arrList = makeList(rs);
			if(arrList ==null||arrList.size()==0) {
				//회원정보가 없는 경우
				return null;
			}
			//회원정보가 있다면 1개 있음 인덱스 0번지정
			MemberDTO user = arrList.get(0);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.close(con, ps, rs);
		}
	}
	public int updateMember(MemberDTO user,int idx) {
		try {
			con = DBUtil.getConnection();
			String sql ="UPDATE members SET name = ?, userId = ?, email = ? WHERE idx = ?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getName());  
	        ps.setString(2, user.getUserId()); 
	        ps.setString(3, user.getEmail()); 
	        ps.setInt(4,idx);    
	        int n = ps.executeUpdate(); // 실행 후 영향받은 레코드 수 반환
	        return n;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			DBUtil.close(con, ps, rs);
		}
	}
}
