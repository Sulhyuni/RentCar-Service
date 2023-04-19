package com.ezenrent.rent.dao;

import java.util.ArrayList;
import java.util.List;

import com.ezenrent.car.vo.CarVO;
import com.ezenrent.main.Main;
import com.ezenrent.rent.vo.RentVO;
import com.ezenrent.util.db.DAO;
import com.ezenrent.util.db.DB;

public class RentDAOImpl extends DAO implements RentDAO {

	// list
	@Override
	public List<RentVO> list(RentVO vo) throws Exception {
		// System.out.println("RentDAO.list().....");

		try {
			List<RentVO> list = null;
			con = DB.getConnection();

			String condition = " ";
			if (vo.getId() != null)
				condition = " m.id = ? and";

			String sql = "select r.rentNo, c.carNo, m.id, m.name, m.tel,c.carBrand,c.carModel,c.carOption, "
					+ "  to_char(r.rentalDate, 'yyyy-mm-dd') rentalDate, "
					+ " to_char(r.returnDate, 'yyyy-mm-dd') returnDate, r.addDriver,r.place,c.carStatus "
					+ " from  rental r, car c ,member m" + " where " + condition
					+ " (c.carNo=r.carNo) and (m.id = r.id)  ";

			pstmt = con.prepareStatement(sql);

			String data = null;
			if (condition != " ") {
				if (vo.getId() != null)
					data = vo.getId();
				pstmt.setString(1, data);
			}
			// System.out.println("4.실행객체&데이터저장");

			// 5.실행
			rs = pstmt.executeQuery();
			// System.out.println("5.실행완료 ");

			// 6.표시,저장
			if (rs != null) {
				while (rs.next()) {
					if (list == null)
						list = new ArrayList<RentVO>();
					RentVO listVO = new RentVO();
					// rs->listVO
					listVO.setRentNo(rs.getLong("rentNo"));
					listVO.setCarNo(rs.getLong("carNo"));
					listVO.setId(rs.getString("id"));
					listVO.setName(rs.getString("name"));
					listVO.setTel(rs.getString("tel"));
					listVO.setCarBrand(rs.getString("carBrand"));
					listVO.setCarModel(rs.getString("carModel"));
					listVO.setCarOption(rs.getString("carOption"));
					listVO.setRentalDate(rs.getString("rentalDate"));
					listVO.setReturnDate(rs.getString("returnDate"));
					listVO.setAddDriver(rs.getString("addDriver"));
					listVO.setPlace(rs.getString("place"));
					listVO.setCarStatus(rs.getString("carStatus"));

					list.add(listVO);
				}
				// System.out.println("6.표시,저장완료");
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("리스트 DB처리 중 에러 발생");
		} finally {
			// 7.닫기
			DB.close(con, pstmt, rs);
			// System.out.println("7.닫기완료");
		}
	}// end of list

//예약 상세 보기 view
	@Override
	public RentVO view(long no) throws Exception {
		// System.out.println("RentDAO.view().....");

		RentVO viewVO = null;
		try {

			con = DB.getConnection();
			// System.out.println("1드라이브확인2. 연결확인");
			// 3.
			String sql = " select r.rentNo, c.carNo, m.id, m.name, m.tel, c.carBrand, c.carModel, c.carType, c.carOption, c.carfuel, "
					+ " r.totalFee, r.insurance, to_char(r.rentalDate, 'yyyy-mm-dd') rentalDate, "
					+ " to_char(r.returnDate, 'yyyy-mm-dd') returnDate, r.addDriver, r.place, c.carStatus "
					+ " from rental r, car c, member m "
					+ " where r.rentNo = ? and  m.id = r.id  and c.carNo=r.carNo  ";

			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			// System.out.println("4.실행객체,데이터세팅");

			// 5.실행
			rs = pstmt.executeQuery();
			// System.out.println("5.실행 ");

			// 6.저장
			if (rs != null && rs.next()) {
				viewVO = new RentVO();
				// rs->vo
				viewVO.setRentNo(rs.getLong("rentNo"));
				viewVO.setCarNo(rs.getLong("carNo"));
				viewVO.setId(rs.getString("id"));
				viewVO.setName(rs.getString("Name"));
				viewVO.setTel(rs.getString("tel"));
				viewVO.setCarBrand(rs.getString("carBrand"));
				viewVO.setCarModel(rs.getString("carModel"));
				viewVO.setCarType(rs.getString("carType"));
				viewVO.setCarOption(rs.getString("carOption"));
				viewVO.setCarFuel(rs.getString("carFuel"));
				viewVO.setTotalFee(rs.getLong("totalFee"));
				viewVO.setInsurance(rs.getString("insurance"));
				viewVO.setRentalDate(rs.getString("rentalDate"));
				viewVO.setReturnDate(rs.getString("returnDate"));
				viewVO.setAddDriver(rs.getString("addDriver"));
				viewVO.setPlace(rs.getString("place"));
				viewVO.setCarStatus(rs.getString("carStatus"));
			} // end if
				// System.out.println("6.데이터저장");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("보기 DB처리 중 에러 발생");
		} finally {
			// 7.닫기
			DB.close(con, pstmt, rs);
			// System.out.println("7.닫기완료");
		}
		return viewVO;
	}

	// 나의 대여내역 보기 view
	@Override
	public RentVO myView(String id) throws Exception {
		// System.out.println("RentDAO.view().....");

		RentVO viewVO = null;
		try {
			// 본인 예약건만 볼 수 있다

			con = DB.getConnection();
			// System.out.println("1드라이브확인2. 연결확인");
			// 3.
			String sql = " select r.rentNo, c.carNo, m.id, m.name, m.tel, c.carBrand, c.carModel, c.carType, c.carOption, c.carfuel, "
					+ " r.totalFee, r.insurance, to_char(r.rentalDate, 'yyyy-mm-dd') rentalDate, to_char(r.returnDate, 'yyyy-mm-dd') returnDate, r.addDriver, r.place "
					+ " from rental r, car c, member m " + " where m.id = ? and c.carNo=r.carNo and m.id = r.id ";
			// System.out.println("3.sql- "+sql);

			// 4.실행객체 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// System.out.println("4.실행객체,데이터세팅");

			// 5.실행
			rs = pstmt.executeQuery();
			// System.out.println("5.실행 ");

			// 6.저장
			if (rs != null && rs.next()) {
				viewVO = new RentVO();
				// rs->vo
				viewVO.setRentNo(rs.getLong("rentNo"));
				viewVO.setCarNo(rs.getLong("carNo"));
				viewVO.setId(rs.getString("id"));
				viewVO.setName(rs.getString("Name"));
				viewVO.setTel(rs.getString("tel"));
				viewVO.setCarBrand(rs.getString("carBrand"));
				viewVO.setCarModel(rs.getString("carModel"));
				viewVO.setCarType(rs.getString("carType"));
				viewVO.setCarOption(rs.getString("carOption"));
				viewVO.setCarFuel(rs.getString("carFuel"));
				viewVO.setTotalFee(rs.getLong("totalFee"));
				viewVO.setInsurance(rs.getString("insurance"));
				viewVO.setRentalDate(rs.getString("rentalDate"));
				viewVO.setReturnDate(rs.getString("returnDate"));
				viewVO.setAddDriver(rs.getString("addDriver"));
				viewVO.setPlace(rs.getString("place"));
			} // end if
			// System.out.println("6.데이터저장");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("보기 DB처리 중 에러 발생");
		} finally {
			// 7.닫기
			DB.close(con, pstmt, rs);
			// System.out.println("7.닫기완료");
		}
		return viewVO;
	}

	// 예약하기 write
	@Override
	public Integer write(RentVO vo) throws Exception {
		// System.out.println("RentDAO.write().....");
		Integer result = null;

		try {
			con = DB.getConnection();
			// System.out.println("1드라이브확인2. 연결확인");

			String sql = " insert into rental (rentNo, carNo, id,  rentalDate, "
					+ " returnDate,addDriver,insurance,place, totalFee )  "
					+ " values(rental_seq.nextval, ?, ?, ?, ? , ?, ?, ?, ?) ";
			// System.out.println("3.sql-" +sql);

			// 4.실행객체 &데이터저장
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, vo.getCarNo());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getRentalDate());
			pstmt.setString(4, vo.getReturnDate());
			pstmt.setString(5, vo.getAddDriver());
			pstmt.setString(6, vo.getInsurance());
			pstmt.setString(7, vo.getPlace());
			pstmt.setLong(8, vo.getTotalFee());
			// System.out.println("4.실행개체+데이터생성" );

			// 5.
			result = pstmt.executeUpdate();
			// System.out.println("5.실행 " + result);

			// 6.저장,표시
//			if(result ==1)
//				System.out.println("6.예약 됨.");
//			else
//				System.out.println("6.예약실패");
		} catch (Exception e) {
			// e.printStackTrace();
			throw new Exception("등록 DB처리 중 에러 발생");
		} finally {
			DB.close(con, pstmt);
			// System.out.println("7.닫기성공");
		}
		return result;
	}

	// update
	@Override
	public Integer update(RentVO vo) throws Exception {
		// System.out.println("RentDAO.update().....");

		Integer result = null;
		try {
			con = DB.getConnection();
			// System.out.println("1드라이브확인2. 연결확인");

			// 3.sql
			String sql = " update rental set  rentalDate= ?,   returnDate =?, addDriver=?, "
					+ " addBirth =? , addLicense= ? where rentNo = ?  ";
			// System.out.println("3.sql-" +sql);

			// 4.실행객체 + 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getRentalDate());
			pstmt.setString(2, vo.getReturnDate());
			pstmt.setString(3, vo.getAddDriver());
			pstmt.setString(4, vo.getAddBirth());
			pstmt.setString(5, vo.getAddLicense());
			pstmt.setLong(6, vo.getRentNo());
			// System.out.println("4.실행객체 + 데이터 세팅");

			// 5.
			result = pstmt.executeUpdate();
			// System.out.println("5.실행" );

			// 6.
//			if(result == 1)
//				System.out.println("6.수정완료");
//			else
//				System.out.println("6.수정실패");
//				
//			

		} catch (Exception e) {
			// e.printStackTrace();
			throw new Exception("수정 DB처리 중 에러 발생");
		} finally {

			// 7.닫기
			DB.close(con, pstmt, rs);
			// System.out.println("7.닫기완료");
		}
		return result;
	}

	// 삭제
	@Override
	public Integer delete(RentVO vo) throws Exception {
		// System.out.println("RentDAO.delete().....");

		Integer result = null;
		try {
			con = DB.getConnection();
			// System.out.println("1.드라이브확인+2.연결확인");
			// sql
			String sql = " delete from rental " + " where rentNo = ? ";

			// System.out.println("3.sql - " + sql);

			// 4.실행객체,저장
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, vo.getRentNo());
			// System.out.println("4.실행객체,저장");

			// 5.
			result = pstmt.executeUpdate();
			// System.out.println("5.실행 ");

//			//6.
//			if(result ==1)
//				System.out.println("6.삭제되었습니다.");
//			else
//				System.out.println("6.삭제 실패!!!");
//			

		} catch (Exception e) {
			// e.printStackTrace();
			throw new Exception("삭제 DB처리 중 에러 발생");
		} finally {
			// 7.닫기
			DB.close(con, pstmt, rs);
			// System.out.println("7.닫기완료");

		}
		return result;
	}

	// 차량 상태변경 
	public Integer updateStatus(RentVO vo) throws Exception {
		Integer result = null;
		try {

			con = DB.getConnection();
			String sql = "update car set carStatus = ? where carNo = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getCarStatus());
			pstmt.setLong(2, vo.getCarNo());

			result = pstmt.executeUpdate();

		} catch (Exception e) {

			//e.printStackTrace();
			//System.out.println(e.getMessage());
			throw new Exception("차량 수정 DB 처리 중 오류가 발생했습니다.");
		} finally {

			DB.close(con, pstmt);
		}
		return result;
	} // end of update

}
