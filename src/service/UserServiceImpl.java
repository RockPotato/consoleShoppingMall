package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vo.CartVO;
import vo.ProdVO;
import vo.UserVO;
import dao.CartDao;
import dao.CartDaoImpl;
import dao.NoticeDao;
import dao.NoticeDaoImpl;
import dao.ProdDao;
import dao.ProdDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

public class UserServiceImpl implements UserService {

	UserDao userDao = new UserDaoImpl();
	CartDao cartDao = new CartDaoImpl();
	ProdDao prodDao = new ProdDaoImpl();
	NoticeDao noticeDao = new NoticeDaoImpl();

	@Override
	public void join() {	//회원가입
		Scanner s = new Scanner(System.in);
		UserVO user = new UserVO(); // 임시 회원정보 담을 변수
		System.out.println("ID는 숫자와 영어만 사용가능하고 길이는 5-15자");
		System.out.println("비밀번호는 숫자 대소문자 특수기호를 사용하여 8-20자");
		System.out.print("ID : ");
		String inputId = s.nextLine();

		System.out.print("PW : ");
		String inputPw = s.nextLine();
		
		boolean check = PatternChecker(inputId, inputPw);
		if(check == false){
			System.out.println("아이디와 패스워드를 정확하게 입력해주세요.");
			return;
		}
		else{
			
			user.setId(inputId);
			user.setPassword(inputPw);
			user.setIdxnumber(userDao.selectUserList().size());
		}

		
		System.out.print("NAME : ");
		user.setName(s.nextLine());

		UserVO userCheck = userDao.selectUser(user.getId(), user.getPassword()); // 중복

		if (userCheck == null) {
			// 회원가입 진행
			userDao.insertUser(user); // 회원 테이블에 입력
			clearScreen();
			addLine();
			System.out.println("        회원가입 완료");
			addLine();
			addLine2();
		} else {
			// 아이디 중복
			clearScreen();
			addLine();
			System.out.println("    아이디가 중복되었습니다");
			addLine();
			addLine2();
		}
	}

	@Override	 
	public void userList() {	//유저리스트 
		ArrayList<UserVO> userList = userDao.selectUserList();

		for (UserVO user : userList) {
			System.out.println("ID : " + user.getId());
			System.out.println("NAME : " + user.getName());
			System.out.println("CLASS : "+ user.getUserClass());
			System.out.println();
		}
	}
	


	private int to_int(String substring) {
		return Integer.parseInt(substring);
	}

	@Override
	public String[] UserLogin() {
		String[] idpw = new String[3];
		Scanner s = new Scanner(System.in);
		UserVO user = new UserVO();
		System.out.print("ID : ");
		user.setId(s.nextLine());
		System.out.print("PW : ");
		user.setPassword(s.nextLine());
		clearScreen();
		idpw[0] = user.getId();
		idpw[1] = user.getPassword();
		
		UserVO userCheck = userDao.selectUser(user.getId(), user.getPassword());
		if (userCheck == null) {
			addLine();
			System.out.println("     아이디가 틀렸습니다");
			addLine();
			addLine2();
			idpw = null;
			return idpw;
		} else if (!(userCheck.getPassword().equals(user.getPassword()))) {
			addLine();
			System.out.println("    비밀번호가 틀렸습니다");
			addLine();
			addLine2();
			idpw = null;
			return idpw;
		} else if (userCheck.getId().equals("master")
				&& userCheck.getPassword().equals(user.getPassword())) {
			addLine();
			System.out.println("        관리자 로그인");
			addLine();
			addLine2();
			idpw[0] = user.getId();
			idpw[1] = user.getPassword();
			idpw[2] = Integer.toString(userCheck.getIdxnumber()); // 키값 저장
			return idpw;
		} else if (userCheck.getId().equals(user.getId())
				&& userCheck.getPassword().equals(user.getPassword())) {
			addLine();
			System.out.println("         회원 로그인");
			addLine();
			addLine2();
			idpw[0] = user.getId();
			idpw[1] = user.getPassword();
			idpw[2] = Integer.toString(userCheck.getIdxnumber()); // 키값 저장
			return idpw;
		}
		return null;
	}

	@Override
	public int userUI() {
		Scanner s = new Scanner(System.in);
		addLine2();
		System.out.println("1. 장보기 ");
		System.out.println("2. 결제/환불");
		System.out.println("3. My Page");
		System.out.println("4. 공지사항");
		System.out.println("0. 로그아웃 ");
		System.out.println();
		System.out.print("번호 입력 > ");
		int ns = s.nextInt();
		return ns;
	}
	
	@Override
	public void payment(int num) {// 키값으로 유저를 받아오기 위해 매개변수 num 삽입
		Date today=new Date();
		SimpleDateFormat todayFormat = new SimpleDateFormat("  yyyy-MM-dd");
		clearScreen();
		ArrayList<CartVO> cartlist = cartDao.selectCartList();
		int cart_size = cartDao.selectCartList().size();
		if (cart_size > 0) {
			int sum = 0;
			for (CartVO cart : cartlist) {
				sum += cart.getPrice();
			}
			int money = userDao.selectUser2(num).getMoney(); // 유저 돈
			cartList();
			System.out.println("총 " + sum + "원 입니다");
			System.out.println("잔액 : " + money);

			String sc = userDao.show_class(num); // 추가
			System.out.println("회원님의 등급은 " + sc + " 입니다"); // 추가

			System.out.println("결제 (Y | y)");
			Scanner s = new Scanner(System.in);
			String y = s.nextLine();

			if (y.equals("y") || y.equals("Y")) {
				
				clearScreen();
				
				System.out.println("=======영수증=======");
				ArrayList<CartVO> cartList = cartDao.selectCartList();
				for (CartVO cart : cartList) {
					System.out.println(cart.getItem() + " : " + cart.getPrice());
					cart.setGuestNum(num);
					cart.setDate(todayFormat.format(today));
				}
				System.out.println("===================");
				if(money>=sum)
				{
					cartDao.stackDealhistoty();
					cartDao.deleteCart(); // 장바구니 비우기
					if (userDao.payment_sale(sc) == 1) {
						userDao.charge_Money(num, sum);
						userDao.total_pay(num,sum);
						
						System.out.println("총 " + (sum) + "원 입니다" + "(등급할인 "
								+ (userDao.payment_sale(sc) - 1) + "% 적용)");
					} else {
						userDao.charge_Money(num, sum - (sum / userDao.payment_sale(sc)));
						userDao.total_pay(num, sum - (sum / userDao.payment_sale(sc)));
						System.out.println("총 " + (sum - (sum / userDao.payment_sale(sc)))
								+ "원 입니다" + "(등급할인 " + userDao.payment_sale(sc)
								+ "% 적용)");
					}

				}
				else
				{
					System.out.println("잔액이 부족합니다.");
				}
				
				System.out.println("잔액 : " + userDao.selectUser2(num).getMoney());
				System.out.println("이용해주셔서 감사합니다\n\n");
			}

			userDao.set_class(num); // 등급 자동 저장
		} else {
			addLine();
			System.out.println("상품이 없습니다");
			addLine();
			addLine2();
		}
	}
	@Override
	public void refund(int num) { //키값으로 유저를 받아오기 위해 매개변수 num 삽입
		ArrayList<CartVO> cvos=cartDao.selectDealhistoty2(num);
		cartDao.forRefund(num);
		if(cvos.size() != 0){
		
		System.out.println("==========거래내역=============");
		for(int i=0; i<cvos.size(); i++){
			System.out.println((i+1) + ". " + cvos.get(i).getItem()
					+ " : " + cvos.get(i).getPrice()+" "+cvos.get(i).getDate());
		}
		System.out.println("=============================");
		System.out.println("잔액 : " + userDao.selectUser2(num).getMoney());
		System.out.print("환불 품목 번호: ");
		
		Scanner s = new Scanner(System.in);
		int j = s.nextInt();
		clearScreen();
		Date today=new Date();
		SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(cvos.get(j-1).getDate().substring(2,6).equals(todayFormat.format(today).substring(0,4))
				&&cvos.get(j-1).getDate().substring(7,9).equals(todayFormat.format(today).substring(5,7))
				&&to_int(todayFormat.format(today).substring(8,10))-to_int(cvos.get(j-1).getDate().substring(10,12))<=7)
		{
			System.out.println("\n" + "[" + cvos.get(j-1).getItem() + "]" + "환불 되었습니다\n" );
			userDao.charge_Money(num, -(cvos.get(j-1).getPrice()));
			userDao.total_pay(num, -(cvos.get(j-1).getPrice()));
			cvos.remove(j-1);	
			cartDao.afterRefund(cvos);
			System.out.println("잔액 : " + userDao.selectUser2(num).getMoney());
			cvos.clear();
		}
		else
		{
			System.out.println("7일이 지났습니다. 환불이 불가능합니다.");
			cartDao.afterRefund(cvos);
		}
		}else if(cartDao.selectDealhistoty2(num).size() == 0){
			addLine();
			System.out.println("구매내역이 없습니다.");
			addLine();
			addLine2();
		}
	}
	@Override
	public void cartList() {
		ArrayList<CartVO> cartList = cartDao.selectCartList(); // 장바구니 테이블을 받아오는
																// 메서드

		System.out.println("==========장바구니=========");
		
		for (CartVO cart : cartList) {
			System.out.println(cart.getItem() + " : " + cart.getPrice());
		}
		System.out.println("=========================");
	}

	@Override
	public void Shopping(int num) {//키값으로 유저를 받아오기 위해 매개변수 num 삽입
		while(true)
		{
			String str=""; //이전 메뉴로 돌아가는 것, 물품 이름을 물어보기 위한 변수
			addLine2();
			System.out.println("1. 고기");
			System.out.println("2. 음료");
			System.out.println("3. 생선");
			System.out.println("0. 뒤로가기");
			System.out.println();
			System.out.print("번호 입력 > ");
			Scanner s = new Scanner(System.in);
			int ns = s.nextInt();
			ArrayList<ProdVO> uv = prodDao.selectCategoryProdList(ns);  //카테고리와 일치하는 상품들만 가져오는 메서드 
			if(ns !=1 && ns !=2 && ns!=3 ){
				break;
			}
			clearScreen();
			for (int i = 0; i < uv.size(); i++) {				// 상품출력
				if(uv.get(i).getSalePrice()>0)
				{
					System.out.println((uv.get(i).getProdNum()+1) +". "+uv.get(i).getProdInfo() + " : "
						+ uv.get(i).getPrice()+" 세일 가격 : "+uv.get(i).getSalePrice()+" (세일 가격으로 장바구니 추가)");
				}
				else
				{
					System.out.println((uv.get(i).getProdNum()+1) +". "+uv.get(i).getProdInfo() + " : "
							+ uv.get(i).getPrice());
				}
			}
			System.out.println("0. 뒤로가기");
			System.out.println();
			System.out.print("물품 입력 >");
			Scanner sc2 = new Scanner(System.in);
			int prodNum = sc2.nextInt()-1; // sc2
			ArrayList<ProdVO> pl = prodDao.selectProdList(); // 상품 전체를 받아올 배열리스트
			for (int i = 0; i < pl.size(); i++) {
				if (pl.get(i).getProdNum()==prodNum) {
					cartDao.insertUser(cartDao.pick(i)); // 상품을 장바구니 테이블에 추가
					clearScreen();
					System.out.println("추가 완료");
					cartList(); // 장바구니 테이블을 보여주는 메서드
					/*break;*/
				}
			}			
		}//while문 종료
	}

	@Override
	public int Shopping_Basket(int num) {//키값으로 유저를 받아오기 위해 매개변수 num 삽입
		Scanner s = new Scanner(System.in);
		clearScreen();
		System.out.println("1. 장바구니");
		System.out.println("2. 결제하기");
		System.out.println("3. 환불하기");
		System.out.println("4. 머니충전");
		System.out.println("0. 뒤로가기");
		System.out.println();
		System.out.print("번호 입력 : ");
		int ns = s.nextInt();
		return ns;
	}

	@Override
	public int My_Page(int num) {//키값으로 유저를 받아오기 위해 매개변수 num 삽입
		int num1=num;
		clearScreen();
		Scanner s = new Scanner(System.in);
		System.out.println("1. 회원등급");
		System.out.println("2. 거래내역");
		System.out.println("3. 정보수정");
		System.out.println("0. 뒤로가기");
		System.out.println();
		System.out.print("번호 입력 : ");
		int ns = s.nextInt();
		return ns;
	}

	@Override
	public int UserUpdate() {
		clearScreen();
		Scanner s = new Scanner(System.in);
		System.out.println("본인 확인");
		System.out.print("ID : ");
		String id = s.nextLine();
		System.out.println("PW : ");
		String pw = s.nextLine();
		ArrayList<UserVO> uvo = userDao.selectUserList();
		for (int i = 0; i < uvo.size(); i++) {
			UserVO user = uvo.get(i);
			if (user.getPassword().equals(pw) && user.getId().equals(id)) {
				System.out.println("=====================");
				System.out.println("   확인이 완료 되었습니다");
				System.out.println("=====================");
				return i;
			}
		}
		return -1;
	}

	@Override
	public void dealHistory(int num) {	// 거래 내역
		clearScreen();
		ArrayList<CartVO> cvos=cartDao.selectDealhistoty2(num);
		System.out.println("\n\n========거래내역==========");	
		for(int i=0; i<cvos.size(); i++){
			System.out.println(cvos.get(i).getItem()+" : "+cvos.get(i).getPrice()+" "+cvos.get(i).getDate());
		}
		System.out.println("========================\n\n");
		System.out.println("총 구매금액 : " + userDao.selectUser2(num).getTotal_pay());
	
	}

	@Override
	public void showClass(int num) {
		clearScreen();
		String sc = userDao.show_class(num);
		System.out.println("회원님의 등급은 " + sc + " 등급 입니다");
		if (userDao.payment_sale(sc) == 1) {
			System.out.println("[" + sc + "등급 회원 혜택 " + (userDao.payment_sale(sc)-1)
					+ "% 할인]");
		} else {
			System.out.println("[" + sc + "등급 회원 혜택 " + userDao.payment_sale(sc)
					+ "% 할인]");
		}
		addLine2();
	}
	
	@Override
	public void chargeMoney(int num) {
		clearScreen();
		System.out.println("현재 잔액 : " + userDao.selectUser2(num).getMoney() + " 원");
		Scanner s = new Scanner(System.in);
		System.out.print("충전 금액 : ");
		int m = s.nextInt();
		userDao.charge_Money(num, -m);
		clearScreen();
		System.out.println("[" + m +" 원] 충전"   );
		System.out.println("현재 잔액 : " + userDao.selectUser2(num).getMoney() + " 원");
	}
	
	@Override
	public void exeUpdateUser(int num) {
		userDao.updateUser(num);
	}
	
	@Override
	public void exeShowNotice() {
		noticeDao.showNoticesList();
	}
	
	
	//콘솔창 메서드
	
	//라인추가
	public static void addLine(){
		System.out.println("=========================");
	}
	
	public static void addLine2(){
		System.out.println();
		System.out.println();
	}
	
	//콘솔창 치우기
	public static void clearScreen(){
		for (int i = 0; i < 30; i++)
			System.out.println("");
	}

	@Override
	public boolean PatternChecker(String inputId, String inputPw) {
		
		Pattern id = Pattern.compile("[A-Za-z0-9]{5,15}");
		Matcher a = id.matcher(inputId);
		
		Pattern ps = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=-])(?=.*[0-9]).{8,20}");
		Matcher b = ps.matcher(inputPw);
		
		if(a.matches() == true && b.matches() == true){
			return true;
		}
		return false;
	}

	@Override
	public void logout() {
		cartDao.deleteCart();
	}

	
}

class date
{
	int to_int(String str)
	{
		return Integer.parseInt(str);
	}
}

