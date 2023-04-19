package com.ezenrent.notice.dao;

import java.util.ArrayList;
import java.util.List;

import com.ezenrent.notice.vo.NoticeVO;
import com.ezenrent.util.db.DAO;
import com.ezenrent.util.db.DB;
import com.ezenrent.util.io.Out;

public class NoticeDAOImpl extends DAO implements NoticeDAO {
	
	// 전체보기 리스트
	public List<NoticeVO> list() throws Exception {
//		System.out.println("NoticeDAOImpl.list()-------");
		List<NoticeVO> list = null;

		try {
			// 1 드라이버 확인 2 접속
			con = DB.getConnection();
	//		System.out.println("1. 드라이버 확인 2. 접속");
			// 3 sql
			String sql = "select no,title,to_char(startDate,'yyyy-mm-dd')startDate,"
					+ " to_char(endDate,'yyyy-mm-dd')endDate," + " to_char(updateDate,'yyyy-mm-dd')updateDate, hit "
					+ " from notice where 1=1  "
					+ " order by updateDate desc";
	//		System.out.println("3. sql :" + sql);
			// 4 실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
	//		System.out.println("4. 실행객체생성");
			// 5 실행
			rs = pstmt.executeQuery();
	//		System.out.println("5. 실행완료");
			// 6 표시 및 저장
			if (rs != null) {
				while (rs.next()) {
					if (list == null)
						list = new ArrayList<NoticeVO>();
					NoticeVO vo = new NoticeVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setEndDate(rs.getString("endDate"));
					vo.setUpdateDate(rs.getString("updateDate"));
					vo.setHit(rs.getLong("hit"));

					list.add(vo);
					}
				return list;
				}		// end of if	
	//		System.out.println("6.객체 담기 성공 ");
		} catch (Exception e) {
	//		e.printStackTrace();
	//		throw new Exception("공지사항 리스트 보기 중 오류가 발생되었습니다. 다시 시도해주세요.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7.객체 닫기 성공");

		}
		return list;

	}// end of list()
	
	// 현재 공지 리스트 
	public List<NoticeVO> currentList() throws Exception {
//		System.out.println("NoticeDAOImpl.currentList()-------");
		List<NoticeVO> list = null;
		
		try {
			// 1 드라이버 확인 2 접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3 sql
			String sql = "select no,title,to_char(startDate,'yyyy-mm-dd')startDate,"
					+ " to_char(endDate,'yyyy-mm-dd')endDate," + " to_char(updateDate,'yyyy-mm-dd')updateDate, hit "
					+ " from notice where startDate <= sysdate and trunc(sysdate) <= enddate  "
					+ " order by updateDate desc";
//			System.out.println("3. sql :" + sql);
			// 4 실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
//			System.out.println("4. 실행객체생성");
			// 5 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행완료");
			// 6 표시 및 저장
			if (rs != null) {
				while (rs.next()) {
					if (list == null)
						list = new ArrayList<NoticeVO>();
					NoticeVO vo = new NoticeVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setEndDate(rs.getString("endDate"));
					vo.setUpdateDate(rs.getString("updateDate"));
					vo.setHit(rs.getLong("hit"));
					
					list.add(vo);
				}
				return list;
			}		// end of if	
//			System.out.println("6.객체 담기 성공 ");
		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("현재공지 리스트 보기 중 오류가 발생되었습니다. 다시 시도해주세요.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7.객체 닫기 성공");
			
		}
		return list;
		
	}// end of currentList()
	
	// 예약공지리스트 
	public List<NoticeVO> reservationList() throws Exception {
//		System.out.println("NoticeDAOImpl.reservationList()-------");
		List<NoticeVO> list = null;
		
		try {
			// 1 드라이버 확인 2 접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3 sql
			String sql = "select no,title,to_char(startDate,'yyyy-mm-dd')startDate,"
					+ " to_char(endDate,'yyyy-mm-dd')endDate," + " to_char(updateDate,'yyyy-mm-dd')updateDate, hit "
					+ " from notice where startDate > sysDate  "
					+ " order by updateDate desc";
//			System.out.println("3. sql :" + sql);
			// 4 실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
//			System.out.println("4. 실행객체생성");
			// 5 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행완료");
			// 6 표시 및 저장
			if (rs != null) {
				while (rs.next()) {
					if (list == null)
						list = new ArrayList<NoticeVO>();
					NoticeVO vo = new NoticeVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setEndDate(rs.getString("endDate"));
					vo.setUpdateDate(rs.getString("updateDate"));
					vo.setHit(rs.getLong("hit"));
					
					list.add(vo);
				}
				return list;
			}		// end of if	
//			System.out.println("6.객체 담기 성공 ");
		} catch (Exception e) {
//			e.printStackTrace();
//		throw new Exception("예약공지를 볼수없습니다. 다시 시도해주세요.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7.객체 닫기 성공");
			
		}
		return list;
		
	}// end of currentList()

	// 지난공지리스트
	public List<NoticeVO> lastList() throws Exception {
//		System.out.println("NoticeDAOImpl.lastList()-------");
		List<NoticeVO> list = null;
		
		try {
			// 1 드라이버 확인 2 접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3 sql
			String sql = "select no,title,to_char(startDate,'yyyy-mm-dd')startDate, "
					+ " to_char(endDate,'yyyy-mm-dd')endDate,to_char(updateDate,'yyyy-mm-dd')updateDate,hit  "
					+ " from notice "
					+ " where endDate +1 < sysDate "
					+ " order by updateDate desc";
//			System.out.println("3. sql :" + sql);
			// 4 실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
//			System.out.println("4. 실행객체생성");
			// 5 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행완료");
			// 6 표시 및 저장
			if (rs != null) {
				while (rs.next()) {
					if (list == null)
						list = new ArrayList<NoticeVO>();
					NoticeVO vo = new NoticeVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setEndDate(rs.getString("endDate"));
					vo.setUpdateDate(rs.getString("updateDate"));
					vo.setHit(rs.getLong("hit"));
					
					list.add(vo);
				}
				return list;
			}		// end of if	
//			System.out.println("6.객체 담기 성공 ");
		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("지난공지를 볼수가 없습니다. 다시 실행해주세요.");
			
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7.객체 닫기 성공");
			
		}
		return list;
		
	}// end of lastList()

    //검색하는 리스트 
	public List<NoticeVO> searchNotice(String title) throws Exception{
//		System.out.println("searchNotice.service()----------");
		List<NoticeVO>list = new ArrayList<NoticeVO>();
		
		try {// 1 드라이버 확인 2 접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3 sql
			String sql = "select no,title,to_char(startDate,'yyyy-mm-dd')startDate, "
					+ " to_char(endDate,'yyyy-mm-dd')endDate,hit "
					+ " from notice where 1=1";
					
					if (title != null) {
						sql += " and title like ? ";
					}
					
//			System.out.println("3. sql :" + sql);
			// 4 실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
//			System.out.println("4. 실행객체생성");
			
			int idx = 0;
			if (title != null)
				pstmt.setString(++idx, "%" + title + "%");
			
			
			// 5 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행완료");
			// 6 표시 및 저장
			if (rs != null) {
				while (rs.next()) {
				
					NoticeVO vo = new NoticeVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setEndDate(rs.getString("endDate"));
					
					vo.setHit(rs.getLong("hit"));
					
					list.add(vo);
				}
				return list;
			}		// end of if	
//			System.out.println("6.객체 담기 성공 ");
		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("검색한 단어에 해당되는 제목은 없습니다.");
			
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7.객체 닫기 성공");
			
		}
		return list;
		
			
		
	}// end of searchNotice

	//관리자 전체 글 보기 
	public NoticeVO view(long no) throws Exception {
//		System.out.println("NoticeDAOImpl.view()-------");
		NoticeVO noticeVO = null;
		try {
			// 1.드라이버확인 2.접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3.sql
			String sql = "select no,title,content,to_char(startDate,'yyyy-mm-dd')startDate, "
					+ " to_char(endDate,'yyyy-mm-dd')endDate, to_char(updateDate,'yyyy-mm-dd')updateDate, hit "
					+ "  from notice where no=?";
//			System.out.println("3. sql :" + sql);
			// 4.실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
//			System.out.println("4. 실행객체생성");

			// 5 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행완료");

			// 6. 표시 및 저장

			if (rs != null && rs.next()) {

				noticeVO = new NoticeVO();

				noticeVO.setNo(rs.getLong("no"));
				noticeVO.setTitle(rs.getString("title"));
				noticeVO.setContent(rs.getString("content"));
				noticeVO.setStartDate(rs.getString("startDate"));
				noticeVO.setEndDate(rs.getString("endDate"));
				noticeVO.setUpdateDate(rs.getString("updateDate"));
				noticeVO.setHit(rs.getLong("hit"));
			}

//			System.out.println("6.저장 - 글보기 완료 ");
		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("전체글 보기를 볼수가 없습니다. 다시 실행해주세요.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//		System.out.println("글보기 닫기 성공");
		}
		return noticeVO;
	}// end of view 
	
	//회원이 현재 글 보기 
	public NoticeVO preView(long no) throws Exception {
//		System.out.println("NoticeDAOImpl.preView()-------");
		NoticeVO noticeVO = null;
		try {
			// 1.드라이버확인 2.접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3.sql
			String sql = "select no,title,content,to_char(startDate,'yyyy-mm-dd')startDate, "
					+ " to_char(endDate,'yyyy-mm-dd')endDate, to_char(updateDate,'yyyy-mm-dd')updateDate, hit "
					+ "  from notice where (no=?) and (startDate <= sysdate and trunc(sysdate) <= enddate)";
//			System.out.println("3. sql :" + sql);
			// 4.실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
//			System.out.println("4. 실행객체생성");

			// 5 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행완료");

			// 6. 표시 및 저장

			if (rs != null && rs.next()) {

				noticeVO = new NoticeVO();

				noticeVO.setNo(rs.getLong("no"));
				noticeVO.setTitle(rs.getString("title"));
				noticeVO.setContent(rs.getString("content"));
				noticeVO.setStartDate(rs.getString("startDate"));
				noticeVO.setEndDate(rs.getString("endDate"));
				noticeVO.setUpdateDate(rs.getString("updateDate"));
				noticeVO.setHit(rs.getLong("hit"));
			}

//			System.out.println("6.저장 - 글보기 완료 ");
		} catch (Exception e) {
	//		e.printStackTrace();
//			throw new Exception("관련된 글이 없습니다. 다시 시도해주세요.	");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("글보기 닫기 성공");
		}
		return noticeVO;
	}// end of preView 
	
	
	// 조회수 증가 
	public Integer increase(long no) throws Exception {
//		System.out.println("NoticeDAOImpl.increase()-------");
		int result = 0;
		try {
			// 1.드라이버확인 2.접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3.sql
			String sql = "update notice set hit = hit+1  where no =?";
//			System.out.println("3.sql:" + sql);
			// 4.실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);

			pstmt.setLong(1, no);
//			System.out.println("4. 실행객체 생성 및 데이터 세팅 완료");
			// 5. 실행
			result = pstmt.executeUpdate();
//			System.out.println("조회수 1 증가 완료 ");
			// 6. 표시 및 저장
//			if (result == 1)
//			System.out.println("6. 데이터 저장 or 표시- 순서번호 1증가");
//			else
//			System.out.println("6 표시 - 증가할 데이터가 없습니다.");

		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("공자사항 조회수증가 DB 처리 중 오류가 발생되었습니다");

		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
//			System.out.println("7. 객체닫기성공");
		}
		return result;

	}// end of increase();
	
	//공지사항 등록
	public Integer write(NoticeVO vo) throws Exception {
//		System.out.println("NoticeDAOImpl.write()-------");
		int result = 0;
		try {
			// 1.드라이버확인 2.접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3.sql
			String sql = "insert into notice(no,title,content,startDate,endDate)  "
					+ " values(notice_seq.nextval,?,?,?,?)";
//			System.out.println("3.sql:" + sql);
			// 4.실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getStartDate());
			pstmt.setString(4, vo.getEndDate());
			
//			System.out.println("4. 실행객체 생성 및 데이터 세팅 완료");
			// 5. 실행
			result = pstmt.executeUpdate();
//			System.out.println("조회수 1 증가 완료 ");
			// 6. 표시 및 저장
//			if (result == 1)
//				System.out.println("6. 데이터 저장 or 표시- 공지사항 등록");
//			else
//				System.out.println("6 표시 - 증가할 데이터가 없습니다.");

		} catch (Exception e) {
//			e.printStackTrace();
//			Out.line("-", 40);
//			System.out.println("공지사항 등록이 정상적으로 되지 않았습니다. 다시 시도해주세요.");
//			Out.line("-", 40);
		//	throw new Exception("공지사항 등록이 정상적으로 되지 않았습니다. 다시 시도해주세요.");

		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
//			System.out.println("7. 객체닫기성공");
		}
		return result;


	}// end of write();
	
	// 공지사항 수정하기 
	public Integer update(NoticeVO vo) throws Exception {
//		System.out.println("NoticeDAOImpl.update()-------");
		int result = 0;
		try {
			// 1.드라이버확인 2.접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3.sql
			String sql = "update notice set title =?, content=?, startDate=?,endDate=? "
					+ " where no =?";
//			System.out.println("3.sql:" + sql);
			// 4.실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getStartDate());
			pstmt.setString(4, vo.getEndDate());
			pstmt.setLong(5, vo.getNo());
			
//			System.out.println("4. 실행객체 생성 및 데이터 세팅 완료");
			// 5. 실행
			result = pstmt.executeUpdate();
//			System.out.println("5. 공지사항이 수정되었습니다");
			// 6. 표시 및 저장
//			if (result == 1)
//				System.out.println("6. 데이터 저장 or 표시- 공지사항 수정  ");
//			else
//				System.out.println("6 표시 -수정할 데이터가 없습니다.");
//			
		} catch (Exception e) {
//			e.printStackTrace();
			Out.line("-", 40);
			System.out.println("공지 수정이 되지않았습니다. 다시 시도해주세요.");
			Out.line("-", 40);
			//	throw new Exception("공지 수정이 되지않았습니다. 다시 시도해주세요.");
		
//			System.out.println("수정된 정보가 없습니다.");
		
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
	//		System.out.println("7. 객체닫기성공");
		}
		return result;
		
	}// end of update()
	
	
	// 공지 사항 삭제하기 
	public Integer delete(NoticeVO vo) throws Exception {
//		System.out.println("NoticeDAOImpl.delete()-------");
		int result = 0;
		try {
			// 1.드라이버확인 2.접속
			con = DB.getConnection();
//			System.out.println("1. 드라이버 확인 2. 접속");
			// 3.sql
			String sql = "delete from notice where no =?";
//			System.out.println("3.sql:" + sql);
			// 4.실행객체생성 및 데이터 세팅
			pstmt = con.prepareStatement(sql);
		
			pstmt.setLong(1, vo.getNo());
			
//			System.out.println("4. 실행객체 생성 및 데이터 세팅 완료");
			// 5. 실행
			result = pstmt.executeUpdate();
//			System.out.println("5. 공지사항이 삭제되었습니다");
			// 6. 표시 및 저장
//			if (result == 1)
//				System.out.println("6. 데이터 저장 or 표시- 공지사항 삭제  ");
//			else
//				System.out.println("6 표시 -삭제할 데이터가 없습니다.");
//			
		} catch (Exception e) {
//			e.printStackTrace();
			Out.line("-", 40);
			System.out.println("공지삭제가 되지않았습니다. 다시 시도해주세요.");
		//	throw new Exception("공지삭제가 되지않았습니다. 다시 시도해주세요.");
			Out.line("-", 40);
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
//			System.out.println("7. 객체닫기성공");
		}
		return result;
		
	}// end of delete()



}
// end of DAOImpl()
