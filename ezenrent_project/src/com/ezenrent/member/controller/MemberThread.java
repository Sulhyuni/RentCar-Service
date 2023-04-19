package com.ezenrent.member.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.ezenrent.main.Execute;
import com.ezenrent.member.service.MemberRestServiceImpl;
import com.ezenrent.util.io.Out;

public class MemberThread implements Runnable{

	@Override
	public void run() {
		while(true) {
			try {
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime oneYearsAgo = now.minusYears(1);
				String format = oneYearsAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				Execute.run(new MemberRestServiceImpl(), format);
				Thread.sleep(1000 * 60 * 1);			
			} catch (Exception e) {
				e.printStackTrace();
				Out.error("=", 50,
						"예기치 못한 오류가 발생하였습니다. 다시 한 번 시도해 주세요. " 	+ "\n ✖ 오류가 지속될 시 admin@naver.com으로 연락 바랍니다.");
			}
		}
	}
}
