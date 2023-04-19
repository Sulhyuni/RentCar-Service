package com.ezenrent.member.dao;

import java.util.ArrayList;
import java.util.List;
import com.ezenrent.member.vo.LoginVO;
import com.ezenrent.member.vo.MemberVO;
import com.ezenrent.util.db.DAO;
import com.ezenrent.util.db.DB;

public class MemberDAOImpl extends DAO implements MemberDAO {

	@Override
	// 로그인 정보 저장 메서드
	public LoginVO login(LoginVO vo) throws Exception {
		LoginVO loginVO = null;
		try {
			con = DB.getConnection();			
			String sql = " select m.id, m.name, m.email, m.gradeNo, g.gradeName, m.status, m.conDate "
					+ " from member m, grade g "
					+ " where (m.id = ? and m.pw = ?) and (m.gradeNo = g.gradeNo)";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());			
			rs = pstmt.executeQuery();			
			if(rs != null && rs.next()) {
				loginVO = new LoginVO();
				loginVO.setId(rs.getString("id"));
				loginVO.setName(rs.getString("name"));
				loginVO.setEmail(rs.getString("email"));
				loginVO.setGradeNo(rs.getInt("gradeNo"));
				loginVO.setGradeName(rs.getString("gradeName"));
				loginVO.setStatus(rs.getString("status"));
				loginVO.setConDate(rs.getString("conDate"));
			}		
			return loginVO;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt, rs);
		}
	} // end of login()
	
	@Override
	// 회원 리스트 가져오기 메서드 (검색 기능 있음)
	public List<MemberVO> list(MemberVO vo) throws Exception {
		List<MemberVO> list = null;
		try {
			con = DB.getConnection();
			String condition = " ";
			if(vo.getId() != null ) condition = "id =? and";
			else if(vo.getName() != null ) condition = "name =? and";
			else if(vo.getTel() != null ) condition = "tel LIKE ? and";
			else if(vo.getStatus() != null) condition = "status = ? and";
			String sql = " SELECT m.id, m.name, m.gender, m.tel, m.status, m.gradeNo, g.gradeName, "
					+ " NVL(to_char(m.conDate, 'yyyy-mm-dd HH24:MI:SS'),'미접속') conDate "
					+ " FROM grade g, member m "
					+ " WHERE " + condition + "(m.gradeNo = g.gradeNo) "
					+ " ORDER BY id ASC";		
			pstmt = con.prepareStatement(sql);
			String data = null;
			if(condition != " ") {
				if(vo.getId() != null) data = vo.getId();
				else if(vo.getName() != null) data = vo.getName();
				else if(vo.getTel() != null) data = "%"+vo.getTel()+"%";
				else if(vo.getStatus() != null) data = vo.getStatus();
				pstmt.setString(1, data);
			} 			
			rs = pstmt.executeQuery();		
			if(rs != null) {
				while (rs.next()) {
					if(list == null) list = new ArrayList<MemberVO>();
					MemberVO listVO = new MemberVO();	
					listVO.setId(rs.getString("id"));
					listVO.setName(rs.getString("name"));
					listVO.setGender(rs.getString("gender"));
					listVO.setTel(rs.getString("tel"));
					listVO.setStatus(rs.getString("status"));
					listVO.setGradeNo(rs.getInt("gradeNo"));
					listVO.setGradeName(rs.getString("gradeName"));
					listVO.setConDate(rs.getString("conDate"));
					list.add(listVO);
				}
			}
			return list;			
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt, rs);
		}
	} // end of list()

	@Override
	// 아이디 중복 확인 하는 메서드
	public boolean idList(String id) throws Exception {
		try {
			con = DB.getConnection();
			String sql = " SELECT id FROM member where id = ? ";	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);		
			rs = pstmt.executeQuery();		
			return rs.next();		
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt, rs);
		}
	} // end of idList();
	
	@Override
	// 회원의 모든 정보를 가져오는 메서드
	public MemberVO view(String str) throws Exception {
		MemberVO viewVO = null;
		try {
			con = DB.getConnection();			
			String sql = "SELECT m.id, m.name, m. pw, m.gender, to_char(m.birth, 'yyyy-mm-dd') birth, "
					+ " m.tel, m.email, NVL(m.licenseNo, '미입력') licenseNo, "
					+ " NVL(to_char(m.conDate, 'yyyy-mm-dd HH24:MI:SS'),'미접속') conDate, "
					+ " m.status, m.gradeNo, g.gradeName "
					+ " FROM grade g, member m "
					+ " WHERE id = ? and (m.gradeNo = g.gradeNo) ";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, str);			
			rs = pstmt.executeQuery();			
			if(rs != null && rs.next()) {
				viewVO = new MemberVO();
				viewVO.setId(rs.getString("id"));
				viewVO.setName(rs.getString("name"));
				viewVO.setPw(rs.getString("pw"));
				viewVO.setGender(rs.getString("gender"));
				viewVO.setBirth(rs.getString("birth"));
				viewVO.setTel(rs.getString("tel"));
				viewVO.setEmail(rs.getString("email"));
				viewVO.setLicenseNo(rs.getString("licenseNo"));
				viewVO.setConDate(rs.getString("conDate"));
				viewVO.setStatus(rs.getString("status"));
				viewVO.setGradeNo(rs.getInt("gradeNo"));
				viewVO.setGradeName(rs.getString("gradeName"));
			}
			return viewVO;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt, rs);
		}
	} // end of view()
	
	// 비밀번호를 가져오는 메서드
	public MemberVO viewPw(String pw) throws Exception {
		MemberVO viewPwVO = null;
		try {
			con = DB.getConnection();
			
			String sql = "SELECT pw"
					+ " FROM member "
					+ " WHERE id = ? ";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pw);	
			rs = pstmt.executeQuery();			
			if(rs != null && rs.next()) {
				viewPwVO = new MemberVO();
				viewPwVO.setPw(rs.getString("pw"));
			}
			return viewPwVO;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt, rs);
		}
	} // end of viewPw()

	
	@Override
	// 회원 가입 메서드
 	public Integer write(MemberVO vo) throws Exception {
		int result = 0;
		try {
			con = DB.getConnection();
			
			String sql = " INSERT INTO member (id, pw, name, gender, birth, tel, email, licenseNo) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getBirth());
			pstmt.setString(6, vo.getTel());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getLicenseNo());						
			result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt);
		}
	} // end of write()

	@Override
	// 정보 수정 메서드
	public Integer update(MemberVO vo) throws Exception {
		int result = 0;
		try {
			con = DB.getConnection();		
			String sql = " update member set "
					+ " tel =?, email=?, licenseNo=?, gradeNo=?, status =? "
					+ " where id =? ";	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTel());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getLicenseNo());
			pstmt.setInt(4, vo.getGradeNo());
			pstmt.setString(5, vo.getStatus());
			pstmt.setString(6, vo.getId());			
			result = pstmt.executeUpdate();
			return result;		
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt);
		}
	} // end of update()
	
	// 비밀번호 변경 메서드
	public Integer updatePw(MemberVO vo) throws Exception {
		int result = 0;
		try {
			con = DB.getConnection();			
			String sql = " update member set "
					+ " pw = ? "
					+ " where id =? ";		
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getId());			
			result = pstmt.executeUpdate();			
			return result;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt);
		}
	} // end of updatePw()
	
	// 로그인 했을 때 최근 접속일을 현재 날짜로 업데이트 해주는 메서드
	public Integer updateDate(String id) throws Exception {
		try {
			con = DB.getConnection();			
			String sql = "update member set condate = sysdate where id = ? ";
			pstmt = con.prepareStatement(sql);	
			pstmt.setString(1, id);			
			return pstmt.executeUpdate();	
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt);
		}
	}
	
	@Override
	// 정상 회원을 휴면회원으로 전환하는 메서드
	public Integer rest(String date) throws Exception {
		try {
			con = DB.getConnection();
			String sql = " update member set "
					+ " status = '휴면' "
					+ " WHERE to_char(conDate, 'yyyy-mm-dd HH24:MI:SS') < ?  AND status = '정상' ";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date);
			return pstmt.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt);
		}
	} // end of rest()
	
	// 휴면 회원을 정상 회원으로 전환하는 메서드
	public Integer wakeUp(MemberVO vo) throws Exception {
		int result = 0;
		try {
			con = DB.getConnection();		
			String sql = " update member set "
					+ " status =  '정상' "
					+ " where id = ? and name =? and pw =? ";			
			pstmt = con.prepareStatement(sql);	
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPw());		
			result = pstmt.executeUpdate();					
			return result;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt);
		}
	}
	
	@Override
	// 회원을 삭제하는 메서드
 	public Integer delete(String str) throws Exception {
		int result = 0;
		try {
			con = DB.getConnection();		
			String sql = " delete from member "
					+ " where id = ? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, str);			
			result = pstmt.executeUpdate();				
			return result;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt);
		}
	} // end of delete()

	// 아이디를 찾아주는 메서드
	public LoginVO findId(LoginVO vo) throws Exception {
		LoginVO findIdVO = null;
		try {
			con = DB.getConnection();					
			String sql = " select id from member where name =? and email = ? ";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());			
			rs = pstmt.executeQuery();			
			if(rs != null && rs.next()) {
				findIdVO = new LoginVO();
				findIdVO.setId(rs.getString("id"));
			}
			return findIdVO;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt, rs);
		}
	} // end of findId()
	
	// 비밀번호를 찾아주는 메서드
	public MemberVO findPw(MemberVO vo) throws Exception {
		MemberVO findPwVO = null;
		try {
			con = DB.getConnection();			
			String sql = " select pw from member where id=? and name =? and email = ? ";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());			
			rs = pstmt.executeQuery();			
			if(rs != null && rs.next()) {
				findPwVO = new MemberVO();
				findPwVO.setPw(rs.getString("pw"));
			}
			return findPwVO;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception();
		} finally {
			DB.close(con, pstmt, rs);
		}
	} // end of findPw()	
} // end of class
