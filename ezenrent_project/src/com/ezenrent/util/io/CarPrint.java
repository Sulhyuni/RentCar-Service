package com.ezenrent.util.io;

import java.util.List;

import com.ezenrent.car.vo.CarVO;

public class CarPrint {

	// 차량 리스트
	public static void print(List<CarVO> list) {
//		System.out.println("\nCarPrint.print(list)");
		if (list == null || list.size() == 0) {
			System.out.println("\n데이터가 존재하지 않습니다.");
			return;
		}

		System.out.println();
		Out.line("-", 66);
		System.out.println("번호 | 대여 여부 | 차종 | 회사명 | 차량명 | 좌석 | 연식 | 일일 대여비");
		Out.line("-", 66);

		for (CarVO vo : list) {
			System.out.print("  " + vo.getCarNo());
			System.out.print(" | " + vo.getCarStatus());
			System.out.print(" | " + vo.getCarType());
			System.out.print(" | " + vo.getCarBrand());
			System.out.print(" | " + vo.getCarModel());
			System.out.print(" | " + vo.getCarSeat());
			System.out.print(" | " + vo.getCarYear());
			System.out.print(" | " + vo.getCarPrice() + "원");
			System.out.println();
		}
		Out.line("-", 66);

//		for (int i = 0; i < list.size(); i++) {
//			CarVO vo = list.get(i);
//			System.out.print(" " + vo.getCarNo() + "\t" + vo.getCarStatus() + "\t" + vo.getCarType() + "\t"
//					+ vo.getCarBrand() + "\t" + vo.getCarModel() + "\t" + vo.getCarSeat() + "\t" + vo.getCarYear()
//					+ "\t" + vo.getCarPrice());
//		System.out.println();
//		}

	}

	// 차량 상세 보기
	public static void print(CarVO vo) {
//		System.out.println("\nCarPrint.print(vo)");
		if (vo == null) {
			System.out.println("\n존재하지 않는 번호입니다.");
			return;
		}

		System.out.println();
		Out.line("-", 50);
		System.out.println(" - 번     호: " + vo.getCarNo());
		System.out.println(" - 대여  여부: " + vo.getCarStatus());
		System.out.println(" - 차     종: " + vo.getCarType());
		System.out.println(" - 회  사  명: " + vo.getCarBrand());
		System.out.println(" - 차  량  명: " + vo.getCarModel());
		System.out.println(" - 좌     석: " + vo.getCarSeat());
		System.out.println(" - 연     식: " + vo.getCarYear());
		System.out.println(" - 일일 대여비: " + vo.getCarPrice() + "원");
		System.out.println(" - 연     료: " + vo.getCarFuel());
		System.out.println(" - 옵     션: " + vo.getCarOption());
		Out.line("-", 50);

	}

}