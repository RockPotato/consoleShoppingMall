package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import service.AdminService;
import service.AdminServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import dao.InputData;

public class controller {
	public static void main(String[] args) {
		AdminService as = new AdminServiceImpl();
		UserService us = new UserServiceImpl();
		boolean flag=false;
		boolean on_off = true;
		 //시간 포맷
		InputData fdb = new InputData();	//초기값 설정을 위한 인스턴스화
		fdb.FirstInputData();// 초기값 설정
		String[] idpw=null; //id,pw,키값 저장 메서드에 매개변수로 키값을 받아 데이터 베이스의 해당되는
							// 유저의 정보를 가져오기 위함
		while(on_off){
			Scanner s = new Scanner(System.in); 
			firstMenu();
			int ns = s.nextInt();
			idpw=null;
			switch (ns) {
			case 1:
				clearScreen();
				idpw=us.UserLogin();// 로그인 화면
				break;
			case 2:
				clearScreen();
				us.join(); // 회원가입 화면 
				break;
			case 3:
				clearScreen();
				addLine();
				us.userList();
				addLine();
				addLine2();
				break;
			default:
				on_off = false;
				clearScreen();
				addLine();
				System.out.println("        프로그램 종료");
				addLine();
				break;
			}
			if(idpw!=null)
			{
				flag=(!flag);
			}
			while(flag)
			{
				int temp=Integer.parseInt(idpw[2]);
				if (idpw[0].equals("master")) {
					as.adminLogin();
					flag=(!flag);
				} else {
					int menu1=us.userUI();
					switch (menu1) {
					case 1:
						us.Shopping(Integer.parseInt(idpw[2])); 
						break;
					case 2:
						int checkBasket=us.Shopping_Basket(Integer.parseInt(idpw[2]));
						switch (checkBasket) {
						case 1:
							clearScreen();
							us.cartList();
							break;
						case 2:
							clearScreen();
							us.payment(Integer.parseInt(idpw[2]));
							break;
						case 3:
							clearScreen();
							us.refund(Integer.parseInt(idpw[2]));
							break;
						case 4:
							us.chargeMoney(Integer.parseInt(idpw[2]));
							break;
						default:
							addLine();
							System.out.println("   이전 메뉴로 돌아갑니다");
							addLine();
							break;
						}
						break;
					case 3:
						clearScreen();
						temp=us.My_Page(Integer.parseInt(idpw[2]));
						switch(temp)
						{
						case 1:
							clearScreen();
							us.showClass(Integer.parseInt(idpw[2]));
							break;
						case 2:
							clearScreen();
							us.dealHistory(Integer.parseInt(idpw[2]));
							break;
						case 3:
							clearScreen();
							int checkUp=us.UserUpdate();
							us.exeUpdateUser(checkUp);
							flag=(!flag);
							break;
						default:
							addLine();
							System.out.println("   이전 메뉴로 돌아갑니다");
							addLine();
							break;
						}
						break;
					case 4:
						clearScreen();
						us.exeShowNotice();
						break;
					default:
						addLine();
						System.out.println("       로그아웃");
						addLine();
						us.logout();
						flag=(!flag);
					}
				}
			}
		}
		
	}
	
	
	public static void addLine(){
		System.out.println("=========================");
	}
	
	public static void addLine2(){
		System.out.println();
		System.out.println();
	}
	
	public static void clearScreen(){
		 for (int i = 0; i < 30; i++)
		   System.out.println("");
	}
	
	static void firstMenu()
	{
		/*System.out.println("    D마켓에 오신것을 환영합니다.");
		System.out.println("2018 쇼핑기관 인증평가 5년 인증 획득");
		System.out.println("      §§ 최우수 쇼핑기관 §§");
		System.out.println("경 쇼핑기관평가 5년 연속 A등급 수상! 축");
		System.out.println(" '고객에게 최선을 다하는 DDIT마트...'");*/
		addLine2();
		Date today=new Date();
		SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("========DDIT-MART========");
		System.out.println(todayFormat.format(today));
		System.out.println("     1. 회원 로그인");
		System.out.println("     2. 유저 회원 가입");
		System.out.println("     3. 유저리스트");
		System.out.println("     0. 종료");
		System.out.println("=========================");
		System.out.println();
		System.out.print("번호 입력 > ");
	}
}




