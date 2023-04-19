package com.ezenrent.notice.dao;

import java.util.List;

import com.ezenrent.notice.vo.NoticeVO;


public interface NoticeDAO {
	public List<NoticeVO> list() throws Exception;
	public List<NoticeVO> currentList() throws Exception;
	public List<NoticeVO> reservationList() throws Exception;
	public List<NoticeVO> lastList() throws Exception;
	public List<NoticeVO> searchNotice(String title) throws Exception;
	public NoticeVO view(long no) throws Exception;
	public NoticeVO preView(long no) throws Exception;
	public Integer increase(long no) throws Exception;
	public Integer write(NoticeVO vo) throws Exception;
	public Integer update(NoticeVO vo) throws Exception;
	public Integer delete(NoticeVO vo) throws Exception;
}
