package com.ezenrent.rent.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RentCalculation {
	
	public void rentalCalculation()  throws Exception {
		// 렌탈 비용 메서드 호출한다.
		// 전달 데이터 -> 예약날짜, 반납날짜, 차량가격, 보험료
		// get 메서드로 전달 할 예정. 지금은 임의 데이터 넣기.
		Long rentalPrice = rental("2022-12-01", "2022-12-03", 30000, 10000);
		// 렌탈비용 출력
		System.out.println("총 렌탈 비용 : " + rentalPrice + "원");
	}

	// 렌탈 비용 계산하는 메서드
	// 예약날짜, 반납날짜, 차량가격, 보험료 데이터를 전달 받는다.
	public static long rental(String start, String end, long carPrice, long insurance) throws Exception {
		// 연산을 위해 String 타입을 날짜형 타입으로 변환 해준다
		Date startDate = new SimpleDateFormat("yyyy-mm-dd").parse(start);
		Date endDate = new SimpleDateFormat("yyyy-mm-dd").parse(end);
		// 날짜형 타입으로 변환된 변수들을 밀리세컨드(1000분의1초)단위로 바꿔 준 후 -> getTime()
		// 반납날짜에서 예약날짜를 빼주고 그 값에 나누기 1000을 해서 초 단위로 변경해준다.
		long diffSec = (endDate.getTime() - startDate.getTime()) / 1000;
		// 다시 일 단위로 변경 해 준다. 60초=1분, 60분=1시간, 24시간=1일
		long diffDays = diffSec / (24 * 60 * 60);
		// 총 렌탈료 = (하루 렌탈료 * 예약 일수) + 보험료
		long rentalPrice = (carPrice * diffDays) + insurance;
        // 확인용 데이터
		System.out.println("예약 일 수 : " + diffDays +"일");
		System.out.println("하루 렌탈료 : " + carPrice + "원");
		System.out.println("보험료 : " + insurance + "원");
		return rentalPrice;
	}

}
