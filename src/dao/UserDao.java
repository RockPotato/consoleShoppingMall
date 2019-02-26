package dao;

import java.util.ArrayList;

import vo.UserVO;

public interface UserDao {

	UserVO selectUser(String id, String pw);
	UserVO selectUser2(int num); // 키값으로 유저를 가져옴
	void insertUser(UserVO user);
	ArrayList<UserVO> selectUserList();
	void removeUser(UserVO uvo);	
	void updateUser(int i);
	void showMoney(UserVO user);
	int selectUsertbSize();
	int selectIndex(UserVO uvo);
	void total_pay(int num, int pay);
	void set_class(int num);				//유저등급부여
	String show_class(int num);				//유저등급추출
	int payment_sale(String userclass);
	void charge_Money(int num, int money);
}
