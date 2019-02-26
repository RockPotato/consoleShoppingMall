package service;


public interface AdminService {
	void adminUI(); //관리자 UI
	void adminLogin();//관리자 로그인
	void updateNotice();
	
	//1. 상품관리
	void prodUI();
	void prodUpdate(); //물품 등록
	void prodChange(); //물품 정보 수정
	void prodDelete(); //물품 삭제
//2. 세일상품관리
	void saleUI();
	void saleUpdate(); //세일물품 등록
	void saleCancel(); //세일 취소
//3. 거래내역조회
	void dealUI(); // 전체 거래목록을 조회. 
	void userManagement();
	
	
}
