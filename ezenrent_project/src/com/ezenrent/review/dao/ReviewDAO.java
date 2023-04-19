package com.ezenrent.review.dao;

import java.util.List;

import com.ezenrent.review.vo.ReviewVO;

public interface ReviewDAO {
	
	// 리뷰리스트
	public List<ReviewVO> list() throws Exception;
	// 리뷰보기
	public ReviewVO view(long reNo) throws Exception;
	// 조회수 1증가
	public int increase(long reNo) throws Exception;
	// 리뷰 등록
	public Integer write(ReviewVO vo)throws Exception;
    // 리뷰 수정
	public Integer update(ReviewVO vo)throws Exception;
	// 리뷰 삭제
	public Integer delete(ReviewVO vo)throws Exception;
	// 답글 쓰기
	public Integer reply(ReviewVO vo)throws Exception;
	// 순서번호 1증가
    public Integer ordNoIncrease(ReviewVO vo) throws Exception;
    // 관련번호로 리뷰 답글 보기    
    public List<ReviewVO> refNolist(long refNo) throws Exception;
    // 관리자 삭제
    public Integer masterdelete(ReviewVO vo) throws Exception;
}
