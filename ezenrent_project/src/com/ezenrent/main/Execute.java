package com.ezenrent.main;

public class Execute {
	public static Object run(ServiceInterface service, Object obj) throws Exception {
//		System.out.println("\n** Execute.run() 실행");
//		System.out.println("** 실행되는 클래스명 : " + service.getClass().getSimpleName());
///	System.out.println("** 전달되는 데이터 : " + obj);
//		System.out.println("** 실행");
		Object result = service.service(obj);
//		System.out.println("** 실행 결과 : " + result);
		return result;
	}
}
