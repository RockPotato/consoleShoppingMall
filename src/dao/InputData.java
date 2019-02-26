
package dao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vo.CartVO;
import vo.Database;
import vo.NoticeVO;
import vo.ProdVO;
import vo.UserVO;

public class InputData {
	
	public void FirstInputData()
	{
		Date today=new Date();
		SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-");
		ArrayList<UserVO> users = new ArrayList<UserVO>();
		ArrayList<NoticeVO> notices = new ArrayList<NoticeVO>();
		ArrayList<CartVO> cvos=new ArrayList<CartVO>();
		
		for(int i=0;i<6;i++)
		{
			users.add(new UserVO());
			notices.add(new NoticeVO());
			cvos.add(new CartVO());
		}
		String[] names = {"master","junho","jungkwan","gyounghoon","hyungi","asd"};
		String[] ids={"master","lee04","park01","park02","kim01","asd"};
		String[] pws={"asd","wnsgh","wjdrnjs","rudgns","gusrl","asd"};
		String[] texts={"부족하지만 성심성의껏 만들었습니다.",
				"이준호, 박정권, 박경훈, 김현기와 함께 만든 최고의 쇼핑몰 ㅋ"
				,"품질 장담 !","여러모로 세일을 하고 있으니 세일정보 확인해 주세요!"
				,"구매하신뒤에 리뷰 작성 부탁드립니다."};
		
		for(int i=0;i<users.size();i++)
		{
			users.get(i).setName(names[i]);
			users.get(i).setId(ids[i]);
			users.get(i).setPassword(pws[i]);
			users.get(i).getMoney();
			users.get(i).setIdxnumber(i);
			Database.tb_user.add(users.get(i));
		}
		for(int i=0;i<notices.size()-1;i++)
		{
			notices.get(i).setNoticeBoard(texts[i]);
			Database.tb_notice.add(notices.get(i));
		}
		ArrayList<ProdVO> items =new ArrayList<ProdVO>();
		int cnt=0;
		String[] drinks = { "코카콜라", "실론티", "레쓰비", "환타오렌지", "아침햇살", "파워에이드",
				"포카리", "게토레이", "델몬트", "솔의눈"
		};
		String[] meats = { "삼겹살", "목살", "항정살", "살치살", "채끝살", "닭가슴살",
				"닭다리살", "꽃등심", "갈매기살", "뒷목살"
		};
		String[] fishs = { "고등어", "장어", "갈치", "오징어", "문어", "낙지", "농어", "연어",
				"참치", "광어"
		};
		int[] drinksPrice = { 1400, 1200, 800, 900, 900, 1200, 1300, 1400, 1200, 1100 };
		int[] meatsPrice = { 14700, 12100, 10400, 26900, 34100, 11000, 11500, 42500, 11300, 11700 };
		int[] fishsPrice = { 7100, 19800, 12100, 6800, 7400, 7100, 14000, 19500, 18800, 16000 };
		for(int i=0;i<30;i++)
		{
			items.add(new ProdVO()) ;
		}
		for (int i = 0; i < 10; i++) {
			items.get(cnt).setCategory("meat");
			items.get(cnt).setPrice(meatsPrice[i]);
			items.get(cnt).setProdInfo(meats[i]);
			items.get(cnt).setCategoryNumber(1);
			items.get(cnt).setProdNum(cnt);
			cnt++;
		}
		for (int i = 0 ;i < 10; i++) {
			items.get(cnt).setCategory("drink");
			items.get(cnt).setPrice(drinksPrice[i]);
			items.get(cnt).setProdInfo(drinks[i]);
			items.get(cnt).setCategoryNumber(2);
			items.get(cnt).setProdNum(cnt);
			cnt++;
		}
		for (int i = 0 ;i < 10; i++) {
			items.get(cnt).setCategory("fish");
			items.get(cnt).setPrice(fishsPrice[i]);
			items.get(cnt).setProdInfo(fishs[i]);
			items.get(cnt).setCategoryNumber(3);
			items.get(cnt).setProdNum(cnt);
			cnt++;
		}
		for(int i=0;i<cvos.size();i++)
		{
			int ran=(int)(Math.random()*5)+5;
			int ran2=(int)(Math.random()*10);
			int ranUser=(int)(Math.random()*6);
			String ranDay=todayFormat.format(today);
			ranDay+=Integer.toString(ran);
			cvos.get(i).setDate(ranDay);
			cvos.get(i).setItem(fishs[ran2]);
			cvos.get(i).setPrice(fishsPrice[ran2]);
			cvos.get(i).setGuestNum(ranUser);
			for(int j=0;j<Database.tb_user.size();j++)
			{
				if(Database.tb_user.get(j).getIdxnumber()==ranUser)
				{
					UserDao userDao = new UserDaoImpl();
					userDao.total_pay(j, fishsPrice[ran2]);
				}
			}
			Database.tb_deal.add(cvos.get(i));
		}
		
		for(int i=0;i<items.size();i++)
		{
			Database.tb_prod.add(items.get(i));
		}
	}
}

//items.get(cnt).setPrice((int)(Math.random()*5+20)*100);
//items.get(cnt).setPrice((int)(Math.random()*5+5)*100);
//items.get(cnt).setPrice((int)(Math.random()*5+15)*100);