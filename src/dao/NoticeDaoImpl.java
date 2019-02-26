package dao;

import java.util.ArrayList;
import java.util.Scanner;

import vo.Database;
import vo.NoticeVO;

public class NoticeDaoImpl implements NoticeDao{

	@Override
	public void noticeBoardWrite(String text) {
		if(text.length()<50)
		{
			NoticeVO nvo = new NoticeVO();
			nvo.setNoticeBoard(text);
			Database.tb_notice.add(nvo);
		}
	}

	@Override
	public void showNoticesList() {
		System.out.println();
		for(int i=0;i<Database.tb_notice.size();i++)
		{
			System.out.println(Database.tb_notice.get(i).getNoticeBoard());	
		}
		System.out.println("다 읽으셨으면 키를 입력해주세요.");
		Scanner sc =new Scanner(System.in);
		sc.nextLine();		
		for(int i=0;i<30;i++)
		{
			System.out.println();
		}
		
	}

	@Override
	public ArrayList<NoticeVO> selectNotices() {
		return Database.tb_notice;
	}

	@Override
	public NoticeVO selectNotice(int num) {
		return Database.tb_notice.get(num);
	}
	

}
