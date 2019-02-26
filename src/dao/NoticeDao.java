package dao;

import java.util.ArrayList;

import vo.NoticeVO;


public interface NoticeDao {
	public void noticeBoardWrite(String text);
	void showNoticesList();
	ArrayList<NoticeVO> selectNotices();
	NoticeVO selectNotice(int num);
}
