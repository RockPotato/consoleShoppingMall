package service;

import java.util.ArrayList;
import java.util.Scanner;

import vo.AdminVO;
import vo.NoticeVO;
import vo.ProdVO;
import vo.UserVO;
import dao.AdminDao;
import dao.AdminDaoImpl;
import dao.NoticeDao;
import dao.NoticeDaoImpl;
import dao.ProdDao;
import dao.ProdDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

public class AdminServiceImpl implements AdminService {
	
	NoticeDao noticeDao = new NoticeDaoImpl();
	AdminDao adminDao = new AdminDaoImpl(); 
	UserDao userDao = new UserDaoImpl();
	ProdDao prodDao = new ProdDaoImpl();
	@Override
	public void saleUI() {
		boolean beforeMenu=true;
		
		while(beforeMenu)
		{
			System.out.println();
			Scanner s =new Scanner(System.in);
			System.out.println("======DDIT-MART======");
			System.out.println("    1. 세일물품 등록");
			System.out.println("    2. 세일 취소하기");
			System.out.println("=====================");
			System.out.println(" 그외 > 이전메뉴");
			System.out.print(" 메뉴에 해당하는 번호 입력> ");	
			int ns = s.nextInt();
			switch (ns) {
			case 1:
				saleUpdate();
				break;
				
			case 2:
				saleCancel();
				break;
				
			default:
				beforeMenu=false;
				break;
			}
		}		
				
	}

	
	@Override
	public void adminLogin() {
		Scanner s =new Scanner(System.in);
		System.out.print("Key : ");
		String str = s.nextLine();
		AdminVO adminCheck = adminDao.SelectAdmin(str);
		if(adminCheck!=null)
		{
			adminUI();
		}
		else
		{
			System.out.println("키 값이 다릅니다.");
		}
	}

	@Override
	public void updateNotice() {
		clearScreen();
		ArrayList<NoticeVO> nvos=noticeDao.selectNotices();
		for(int i=0;i<nvos.size();i++)
		{
			System.out.println((i+1)+" 번째 공지사항 : "+nvos.get(i).getNoticeBoard());
		}
		System.out.print("몇번째 공지사항을 바꾸시겠습니까? ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt() - 1;
		if(nvos.size()<=num||num<0)
		{
			System.out.println("잘못 입력하셨습니다.");
		}
		else
		{
			clearScreen();
			System.out.print("공지사항 입력(50자 이내) : ");
			Scanner sc2 =new Scanner(System.in);
			String str =sc2.nextLine();
			if(str.length()>50)
			{
				System.out.println("50자가 넘습니다.");
			}
			else
			{	
				clearScreen();
				nvos.get(num).setNoticeBoard(str);
				noticeDao.selectNotices().set(num, nvos.get(num));
				System.out.println("변경 완료");
			}
		}
	}
	


	
	@Override
	public void prodUI() {
		boolean beforeMenu=true;
		
		while(beforeMenu)
		{
			System.out.println();
			Scanner s =new Scanner(System.in);
			System.out.println("======DDIT-MART======");
			System.out.println("    1. 물품 등록");
			System.out.println("    2. 물품 정보 수정");
			System.out.println("    3. 물품 삭제");
			System.out.println("=====================");
			System.out.println(" 그외 > 이전메뉴");
			System.out.print(" 메뉴에 해당하는 번호 입력> ");	
			int ns = s.nextInt();
			switch (ns) {
			case 1:
				prodUpdate();
				break;
				
			case 2:
				prodChange();
				break;
				
			case 3:
				prodDelete();
				break;
				
			default:
				beforeMenu=false;
				break;
			}
		}		
		
	}
	
	@Override
	public void prodUpdate() { // 물품 등록
		
		Scanner s = new Scanner(System.in);
		ProdVO tmpProd = new ProdVO();
		
		System.out.println();
		System.out.println("상품을 등록합니다. 물품의 정보를 입력해주세요.");
//		System.out.print("분류 : "); // 분류를 불러와서 번호 내에서 정할 수 있도록 구현하기.
//		prod.setCategory(s.nextLine());
		System.out.print("이름 : ");
		tmpProd.setProdInfo(s.nextLine());
		
		// 입력받은 가격은 먼저 input(String)에 저장된다. 입력받은 문자열이 전부 숫자로
		// 이루어져 있는지 확인하고 intInput(int)에 저장된다.
		String input = "";
		int intInput = 0;
		
		boolean flag = true;
		while(flag){ // 입력받은 문자열이 전부 숫자로 이루어져 있으면 나감.
			System.out.print("가격 : ");
			input = s.nextLine();
			for(int i = 0; i < input.length(); i++){
				if((int)input.charAt(i) - 48 < 0 || (int)input.charAt(i) - 48 > 9){
					System.out.println("가격에는 숫자만 입력해주세요.");
					break;
				}else if(i == input.length() - 1){ 
					flag = false;
				}
			}
			if(!flag){ 
				break;
			}
		}
		intInput = Integer.valueOf(input);
		tmpProd.setPrice(intInput);
		
		prodDao.insertProd(tmpProd);
		System.out.println("\n등록이 완료되었습니다.");
		int prodNum = prodDao.selectProdList().size();
		System.out.println("현재 등록된 물품의 수는 총 " + prodNum + "개입니다.\n");
		
	}

	@Override
	public void prodChange() { // 물품 정보 수정
		Scanner s = new Scanner(System.in);
		System.out.println();
		// 우선 전체 물품리스트부터. 카테고리부터 나오도록 구현하기.
//		prodDao.selectCategoryList();
		
		// 리스트 출력 줄 맞추는 포맷. 나중에 좀더 정확하게 구현하기
		// ex) String str = String.format("%10s", "Hello"); 
		
		String str = "";
		ArrayList<ProdVO> prodList = prodDao.selectProdList();
		ProdVO selectProd = new ProdVO();		
		
		System.out.println("\t\t\t\t<전체 물품 리스트>");
		for(int i = 0; i < prodList.size(); i++){
			int price = prodList.get(i).getPrice();
			String stringPrice = String.valueOf(price);
			String commaPrice = ""; // int형인 price를 String으로 바꾸고 콤마를 찍은 뒤
									// commaPrice에 저장.
			for(int j = 0; j < stringPrice.length(); j++){
				if(j % 3 == 0 && j != 0){
					commaPrice = "," + commaPrice;				
				}
				commaPrice = stringPrice.charAt(stringPrice.length() - 1 - j) + commaPrice;
			}
		
			if((i) % 3 == 0){ // 품목이 한 줄에 3개씩 나오도록 함.
				System.out.println();
			}
			str = (i+1) + ". " + prodList.get(i).getProdInfo() + "  "
					+ commaPrice + "원";
			str = String.format("%-31s", str);
			
			System.out.print(str);
		}
		
		System.out.print("\n물품 리스트 중에서 수정하려는 물품의 번호를 입력해주세요. : ");
		
		String input = "";
		int intInput = 0;
		
		boolean flag = true;
		while(flag){ // '숫자만 입력' && '목록에 있는 숫자'면 나감.
			boolean flag2 = true;
			while(flag2){ // 입력받은 문자열이 전부 숫자로 이루어져 있으면 나감.
				input = s.nextLine();
				for(int i = 0; i < input.length(); i++){
					if((int)input.charAt(i) - 48 < 0 || (int)input.charAt(i) - 48 > 9){
						System.out.print("\n숫자만 입력해주세요. > ");
						break;
					}else if(i == input.length() - 1){
						flag2 = false;
					}
				}
				if(!flag2){
					break;
				}
			}	
			
			intInput = Integer.valueOf(input);
			if(intInput < 1 || intInput > prodList.size()){
				System.out.print("목록에 있는 숫자를 입력해주세요. > ");
				flag2 = true;
				continue;
			}else {
				selectProd = prodList.get(intInput - 1);
				flag = false;
				break;
			}
		}
		
		int price = prodList.get(intInput - 1).getPrice();
		String stringPrice = String.valueOf(price);
		String commaPrice = ""; 
		for(int j = 0; j < stringPrice.length(); j++){
			if(j % 3 == 0 && j != 0){
				commaPrice = "," + commaPrice;				
			}
			commaPrice = stringPrice.charAt(stringPrice.length() - 1 - j) + commaPrice;
		}		

		System.out.println("\n물품의 현재 정보입니다.");
		System.out.println((intInput) + ". " + selectProd.getProdInfo() + "  " + commaPrice + "원");
		
		System.out.println("\n바꿀 정보를 입력해주세요.");
		System.out.print("이름 : ");
		selectProd.setProdInfo(s.nextLine());		
		
		String inputPrice = "";
		int intPrice = 0;
		
		boolean flag3 = true;
		while(flag3){ // 숫자만 입력받았으면 나감.
			System.out.print("가격 : ");
			inputPrice = s.nextLine();
			for(int i = 0; i < inputPrice.length(); i++){
				if(((int)inputPrice.charAt(i) - 48) < 0 || ((int)inputPrice.charAt(i) - 48) > 9){
					System.out.println("\n가격에는 숫자만 입력해주세요.");
					break;
				}else if(i == inputPrice.length() - 1){
					flag3 = false;
					break;
				}
			}
		}
		
		intPrice = Integer.valueOf(inputPrice);
		selectProd.setPrice(intPrice);			
		prodDao.changeProd(intInput - 1, selectProd);
		
		stringPrice = inputPrice;
		commaPrice = ""; 
		for(int j = 0; j < stringPrice.length(); j++){
			if(j % 3 == 0 && j != 0){
				commaPrice = "," + commaPrice;				
			}
			commaPrice = stringPrice.charAt(stringPrice.length() - 1 - j) + commaPrice;
		}		
		
		System.out.println("\n다음과 같이 수정이 완료되었습니다.");
		System.out.println((intInput) + ". " + selectProd.getProdInfo() + "  " + commaPrice + "원");			
	}

	@Override
	public void prodDelete() { // 물품 삭제
		Scanner s = new Scanner(System.in);
		System.out.println();
		
		// 카테고리 먼저 선택하게 해서 수정할 물품을 고를 수 있도록 구현하기.
		// prodDao.selectCategoryList();
		// 우선 전체 물품리스트부터.

		ArrayList<ProdVO> prodList = prodDao.selectProdList();
		ProdVO selectProd = new ProdVO();

		boolean flag = true;
		while (flag) { // 최종 확인에서 y를 입력받으면 나감.
			boolean flag2 = true;
			boolean flag3 = true;
			boolean flag4 = true;

			String str = "";
			System.out.println("\t\t\t\t<전체 물품 리스트>");
			for(int i = 0; i < prodList.size(); i++){
				int price = prodList.get(i).getPrice();
				String stringPrice = String.valueOf(price);
				String commaPrice = ""; 
				for(int j = 0; j < stringPrice.length(); j++){
					if(j % 3 == 0 && j != 0){
						commaPrice = "," + commaPrice;				
					}
					commaPrice = stringPrice.charAt(stringPrice.length() - 1 - j) + commaPrice;
				}
			
				if((i) % 3 == 0){ // 품목이 한 줄에 3개씩 나오도록 함.
					System.out.println();
				}
				str = (i+1) + ". " + prodList.get(i).getProdInfo() + "  "
						+ commaPrice + "원";
				str = String.format("%-31s", str);
				System.out.print(str);
			}
				
			String input = "";
			int intInput = 0;
			while(flag2){ // '숫자만 입력' && '목록에 있는 숫자'면 나감.				
				while(flag3){ // 숫자만 입력받으면 나감.
					System.out.print("\n물품 리스트 중에서 삭제하려는 물품의 번호를 입력해주세요. : ");
					input = s.nextLine();
					for (int i = 0; i < input.length(); i++) {
						if ((int) input.charAt(i) - 48 < 0 || (int) input.charAt(i) - 48 > 9) {
							System.out.println("\n숫자만 입력해주세요.");
							break;
						}else if(i == input.length() - 1){
							flag3 = false;
							break;
						}
					}
				}
				intInput = Integer.parseInt(input);
	
				if (intInput < 1 || intInput > prodList.size()) {
					System.out.println("\n목록에 있는 숫자를 입력해주세요.");
					flag3 = true;
					continue;
				}	
				flag2 = false;
				selectProd = prodList.get(intInput - 1);
				break;
			}		
			
			int price = prodList.get(intInput - 1).getPrice();
			String stringPrice = String.valueOf(price);
			String commaPrice = ""; 
			for(int j = 0; j < stringPrice.length(); j++){
				if(j % 3 == 0 && j != 0){
					commaPrice = "," + commaPrice;				
				}
				commaPrice = stringPrice.charAt(stringPrice.length() - 1 - j) + commaPrice;
			}				
						
			System.out.println("\n물품의 현재 정보입니다.");
			System.out.println((intInput) + ". " + selectProd.getProdInfo() + "  " + commaPrice + "원");
			
			while (flag4) { // y나 n 중 하나를 입력하면 나감.
				System.out.println("\n이 물품을 삭제하시겠습니까?");
				System.out.print("y : 예    /    n : 다른 물품 선택 > ");

				input = "";
				input = s.nextLine();
				if (input.equals("y")) {
					prodDao.deleteProd(intInput - 1);
					System.out.println("\n선택한 물품이 삭제되었습니다.");
					flag = false;
					flag4 = false;
				} else if (input.equals("n")) { // 물품 삭제의 처음으로 돌아감.
					flag4 = false;
				} else {
					System.out.print("\ny와 n 중 하나를 입력해주세요.");
				}
				System.out.println();
			}
		}
		
		prodList = prodDao.selectProdList();
		System.out.println("현재 등록된 물품의 수는 총 " + prodList.size() + "개입니다.\n");
	}
	

 @Override
   public void adminUI() {   //관리자 UI
      boolean beforeMenu=true;
      
      while(beforeMenu)
      {
         System.out.println();
         Scanner s =new Scanner(System.in);
         System.out.println("======DDIT-MART======");
         System.out.println("    1. 상품관리");
         System.out.println("    2. 세일상품관리");
         System.out.println("    3. 공지사항 관리");
         System.out.println("    4. 회원 관리");
         System.out.println("=====================");
         System.out.println(" 그외 > 이전메뉴");
         System.out.print(" 메뉴에 해당하는 번호 입력> ");         
         int ns = s.nextInt();
         switch (ns) {
         case 1:
            prodUI();
            break;
            
         case 2:
            saleUI();
            break;
            
         case 3:
            updateNotice();
            break;
            
         case 4:
            userManagement();
            break;
            
         default:
            beforeMenu=false;
            break;
         }
      }      
   }

	@Override
	public void saleUpdate() { //세일물품 등록
		Scanner s = new Scanner(System.in);
		ProdVO tmpProd = new ProdVO(); // 확인해보고 지우기.
		
		ArrayList<ProdVO> saleList = prodDao.selectSaleList();
		if(saleList.size() == 0){
			System.out.println("\n현재 세일중인 상품이 없습니다.\n");
		}else{
			System.out.println("\n\t<세일 물품 리스트>");
			for(int i = 0; i < saleList.size(); i++){
				System.out.println((i+1) + ". " + saleList.get(i).getProdInfo() + "  " 
						+ saleList.get(i).getPrice() + " -> " + saleList.get(i).getSalePrice()
						+ " (" + saleList.get(i).getSaleRate() + "% 할인중)");
			}
			System.out.println();
		}
		
		ArrayList<ProdVO> prodList = prodDao.selectProdList();
		ProdVO selectProd = new ProdVO();
		
		String inputCheck = "";
		int inputNum = 0;

		boolean flag = true;
		while(flag){
			System.out.println("\t\t\t\t<전체 물품 리스트>");
			
			String str = ""; // 물품리스트에서 열을 맞추기 위해 만듦.
			//String strTmp = "";
			for(int i = 0; i < prodList.size(); i++){
				if((i) % 3 == 0){
					System.out.println();
				}
				str = (i+1) + ". " + prodList.get(i).getProdInfo() + " "
						+ prodList.get(i).getPrice() + "원";
				str = String.format("%-31s", str);
				
				System.out.print(str);
			}
			
			System.out.print("\n세일 등록할 물품의 번호를 입력해주세요. : ");
			//선택한 물품이 세일중일 경우 '해당 물품은 현재 세일중입니다.' 출력되도록 구현하기.
			inputCheck = s.nextLine();
		
			//숫자 이외의 문자를 입력하면 이전메뉴로 돌아가게 함.
			//while을 이용해서 이전메뉴로 돌아가지 않고 다시 가격 입력을 받도록 구현하기.
			for(int i = 0; i < inputCheck.length(); i++){
				if(((int)inputCheck.charAt(i) - 48) < 0 || ((int)inputCheck.charAt(i) - 48) > 9){
					System.out.println("숫자만 입력해주세요.");
					return;
				}
			}		
			inputNum = Integer.parseInt(inputCheck); //inputNum - 1은 인덱스. 
			selectProd = prodDao.selectProd("Choose", inputNum - 1);
			
			if(selectProd == null){
				System.out.println("\n목록에 있는 숫자를 입력해주세요.");
				continue;
			}
			flag = false;
		}
		
		System.out.println("\n물품의 현재 정보입니다.");
		System.out.println((inputNum) + ". " + selectProd.getProdInfo() + "  "
				           + selectProd.getPrice());
		
		
		System.out.println("\n세일해서 판매할 가격을 입력해주세요.");
		//현재가격보다 낮은가격을 입력하도록 수정. while로 구현하기.
		//(예- 물건가격 9000 -> 세일가격 100 ~ 8900 -> 100 ~ (price - 100))
		
		System.out.print("가격 : ");
		inputCheck = s.nextLine();
		int inputPrice = 0;
		
		//마찬가지로 while 추가하기.
		//가격에 숫자 이외의 문자를 입력하면 이전메뉴로 돌아가게 함.
		//먼저 String으로 입력을 받고 숫자로만 이루어져있는지 확인. 그 다음에 parseInt로 숫자로 변경.
		for(int i = 0; i < inputCheck.length(); i++){
			if(((int)inputCheck.charAt(i) - 48) < 0 || ((int)inputCheck.charAt(i) - 48) > 9){
				System.out.println("가격에는 숫자만 입력해주세요.");
				return;
			}
		}
		inputPrice = Integer.parseInt(inputCheck);
		tmpProd.setSalePrice(inputPrice);	
		tmpProd.setSale(true);

			//물품 가격과 세일가격 모두 int형이기 때문에 나눗셈을 할때 0.0...이 0으로 되므로 float를 붙임.
		int inputSaleRate = (int)(((selectProd.getPrice()-inputPrice)/(float)selectProd.getPrice()) * 100);	
		
		tmpProd.setSaleRate(inputSaleRate);
		
		
	
		prodDao.changeProdSale(inputNum - 1, tmpProd);
		System.out.println("\n세일상품으로 등록이 되었습니다.");
		saleList = prodDao.selectSaleList();
		
		for(int i = 0; i < saleList.size(); i++){
			System.out.println((i+1) + ". " + saleList.get(i).getProdInfo() + "  "
					+ saleList.get(i).getPrice() + " -> " + saleList.get(i).getSalePrice()
					+ " (" + saleList.get(i).getSaleRate() + "% 할인중)");
		
		}
		
	}

	@Override
	public void saleCancel() {
		
		Scanner s = new Scanner(System.in);

		ArrayList<ProdVO> saleList = prodDao.selectSaleList();
		if(saleList.size() == 0){
			System.out.print("\n현재 세일중인 상품이 없습니다.");
			System.out.println(" 이전 메뉴로 돌아갑니다.");
			
			return;
		}	
		
		boolean flag = true;
		int inputNum = 0;

		while (flag) { 	
		
			System.out.println("\n\t<세일 물품 리스트>");
			for(int i = 0; i < saleList.size(); i++){
				System.out.println((i+1) + ". " + saleList.get(i).getProdInfo() + "  "
						+ saleList.get(i).getPrice() + " -> " + saleList.get(i).getSalePrice()
						+ " (" + saleList.get(i).getSaleRate() + "% 할인중)");
			}
			System.out.println();		
		
			System.out.print("\n리스트에서 세일을 취소할 물품의 번호를 입력해주세요. : ");

			String inputCheck = s.nextLine();

			// 숫자 이외의 문자를 입력하면 이전메뉴로 돌아가게 함.
			for (int i = 0; i < inputCheck.length(); i++) {
				if (((int) inputCheck.charAt(i) - 48) < 0
						|| ((int) inputCheck.charAt(i) - 48) > 9) {
					System.out.println("숫자만 입력해주세요.");
					return;
				}
			}

			inputNum = Integer.parseInt(inputCheck);

			if (!(inputNum > 0 && inputNum < saleList.size() + 1)) {
				System.out.println("\n목록에 있는 숫자를 입력해주세요.");
				continue;
			} else {

				System.out.println("\n물품의 현재 정보입니다.");
				System.out.println(inputNum + ". " + saleList.get(inputNum - 1).getProdInfo() + "  "
						+ saleList.get(inputNum - 1).getPrice() + " -> " + saleList.get(inputNum - 1).getSalePrice()
						+ " (" + saleList.get(inputNum - 1).getSaleRate() + "% 할인중)");
			}

			boolean flag2 = true;
			while (flag2) {
				System.out.println("\n이 물품의 세일을 취소합니다.");
				System.out.print("y : 예    /    n : 다른 물품 선택 > ");

				String input = s.nextLine();
				if (input.equals("y")) {
					flag = false;
					flag2 = false;
				} else if (input.equals("n")) {
					flag2 = false;
				} else {
					System.out.print("\n\ny와 n 중 하나를 입력해주세요.");
				}
				System.out.println();
			}

		}
		
		ProdVO notSale = new ProdVO();
		notSale.setSale(false);
		notSale.setSaleRate(0);
		notSale.setSalePrice(0);
		
		//saleList에서 지우기 전에 먼저 해당 상품의 세일 정보를 지워준다.
		prodDao.changeProdSale(inputNum - 1, notSale);
		
		saleList.remove(inputNum - 1);
		System.out.println("세일 등록이 취소되었습니다.");
		System.out.println("현재 세일중인 물품의 수는 총 " + saleList.size() + "개입니다.\n");
		
	}

	public void dealUI() { // 전체 거래내역 조회 (전체 거래횟수, 총 매출액 구현하기) 
		
		// 전체 거래내역 리스트 띄우기
		System.out.println("\n\t<전체 거래내역 리스트>");
		// 1. (유저ID) (prodInfo) (price)
		// 2.      :         :        :
		System.out.println("ex) 1. asd123    삼겹살    3000원");
		
		// 마지막에 전체 거래횟수, 총 매출액 띄우기
		System.out.println("\n현재까지 전체 거래 건수는 " + "00" + "회, 총 매출액은 " 
							+ "00000" + "원 입니다.");
		
	}
	
	@Override
	public void userManagement() {
		ArrayList<UserVO> uvos = userDao.selectUserList();
		for(int i=0;i<uvos.size();i++)
		{
			System.out.println(i+"번째 회원 이름 : "+uvos.get(i).getName()+"ID : "+uvos.get(i).getId()+
					"비밀번호 : "+uvos.get(i).getPassword());
		}
		System.out.println("몇번째 회원의 정보를 바꾸시겠습니까? ");
		Scanner sc = new Scanner(System.in);
		int num=sc.nextInt();
		System.out.println("1.이름 2.ID 3.비밀번호");
		int num1=sc.nextInt();
		System.out.println("변경할 값 입력 :");
		Scanner sc1 = new Scanner(System.in);
		String str = sc1.nextLine();
		switch(num1)
		{
			case 1:
				userDao.selectUser2(num).setName(str);
				break;
			case 2:
				userDao.selectUser2(num).setId(str);
				break;
			case 3:
				userDao.selectUser2(num).setPassword(str);
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");
				break;
		}
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

}
