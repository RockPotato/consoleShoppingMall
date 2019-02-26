package service;

import vo.ProdVO;
import vo.UserVO;

public interface UserService {
	void join(); //회원가입
	int UserUpdate();// 회원정보수정 (비밀번호 받을 것)
	void refund(int num); // 환불
	String[] UserLogin(); //로그인
	int userUI();
	void userList(); //유저정보 출력
	void payment(int num); // 결제
	void cartList(); 
	void Shopping(int num);
	int Shopping_Basket(int num);
	int My_Page(int num);
	void exeUpdateUser(int num);
	void dealHistory(int num);		//거래내역
	void showClass(int num);		//거래등급 보여주기
	void chargeMoney(int num);
	void exeShowNotice();
	boolean PatternChecker(String inputId, String inputPw);	//정규식패턴채크
	void logout();
}
