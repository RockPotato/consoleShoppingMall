package dao;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vo.Database;
import vo.UserVO;

public class UserDaoImpl implements UserDao {

	@Override
	public UserVO selectUser(String id, String pw) { // id 중복 검사하는 메서드
		for (int i = 0; i < Database.tb_user.size(); i++) {
			UserVO user = Database.tb_user.get(i);
			if (user.getId().equals(id) && user.getPassword().equals(pw)) {
				return user;
			} else if (user.getId().equals(id)
					&& !(user.getPassword().equals(pw))) {
				user.setPassword(" ");
				return user;
			}
		}
		return null;
	}

	@Override
	public void insertUser(UserVO user) { // 회원 테이블에 유저를 받아 저장하는 메서드
		user.getMoney();
		user.setIdxnumber(selectUsertbSize());
		Database.tb_user.add(user);
	}

	@Override
	public ArrayList<UserVO> selectUserList() { // 회원테이블을 반환하는 메서드
		return Database.tb_user;
	}

	@Override
	public void removeUser(UserVO uvo) {
		Database.tb_user.remove(uvo);

	}

	@Override
	public void updateUser(int i) {
		UserVO tmp = Database.tb_user.get(i);
		Scanner s = new Scanner(System.in);
		System.out.println("1.PASSWORD 변경");
		System.out.println("2.이름 변경");
		System.out.println("3.회원 탈퇴");
		System.out.print("입력 :");
		int num = s.nextInt();
		String str;
		switch (num) {
		case 1:
			System.out.print("변경할 비밀번호 : ");
			s.skip("[\\r\\n]+");
			str = s.nextLine();
			
			boolean check = PatternChecker(Database.tb_user.get(i).getId(), str);
			if(check == true){
			tmp.setPassword(str);
			System.out.println("변경 완료");
			}else{
				System.out.println("비밀번호는 숫자 대소문자 특수기호를 사용하여 8-20자");
			}
			
			break;
		case 2:
			System.out.print("변경할 이름 : ");
			s.skip("[\\r\\n]+");
			str = s.nextLine();
			tmp.setName(str);
			System.out.println("변경 완료");
			break;
		case 3:
			Database.tb_user.remove(tmp);
			System.out.println("=====================");
			System.out.println("       탈퇴 완료");
			System.out.println("=====================");
			break;
		default:
			break;

		}

	}

	@Override
	public void showMoney(UserVO user) {
		user.getMoney();
	}

	@Override
	public int selectUsertbSize() {
		return Database.tb_user.size();
	}

	@Override
	public int selectIndex(UserVO uvo) {
		int num=0;
		for(int i=0;i<Database.tb_user.size();i++)
		{
			UserVO uvo1 =Database.tb_user.get(i);
			if(uvo.getIdxnumber()==uvo1.getIdxnumber())
			{
				return i;
			}
		}
		return -1;
	}

	@Override
	public UserVO selectUser2(int num) {
		return Database.tb_user.get(num);
	}
	
	   @Override
	   public void set_class(int num) {
	      UserVO user = new UserVO();
	      user=selectUser2(num);
	      int total_pay = user.getTotal_pay();
	      if(total_pay >= 30000 && total_pay < 80000){
	         user.setUserClass("B");
	         System.out.println("B 등급 회원이 되었습니다");
	      }else if(total_pay >= 80000 && total_pay < 100000){
	         user.setUserClass("A");
	         System.out.println("A 등급 회원이 되었습니다");
	      }else if(total_pay >= 100000){
	         user.setUserClass("VIP");
	         System.out.println("VIP 등급 회원이 되었습니다");
	      }
	      
	   }
	@Override
	public String show_class(int num) {
		UserVO user = new UserVO();
		user = selectUser2(num);
		
		return user.getUserClass();
	}

	@Override
	public int payment_sale(String userclass) {
		if(userclass.equals("B")){
			return 10;
		}else if(userclass.equals("A")){
			return 20;
		}else if(userclass.equals("VIP")){
			return 50;
		}
		return 1;
	}

	@Override
	public void charge_Money(int num, int money) {
		Database.tb_user.get(num).setMoney(-money);
		
	}
	@Override
	public void total_pay(int num, int pay) {
		for(int i=0;i<Database.tb_user.size();i++)
		{
			if(Database.tb_user.get(i).getIdxnumber()==num)
			{
				Database.tb_user.get(i).setTotal_pay(pay);
			}
		}
		
	}

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
	

}
