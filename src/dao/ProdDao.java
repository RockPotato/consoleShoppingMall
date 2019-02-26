package dao;

import java.util.ArrayList;

import vo.ProdVO;

public interface ProdDao {
	ProdVO selectProd(String key, Object value); //상품 하나를 골라서 가져오는 메서드.
	ArrayList<ProdVO> selectProdList(); // 전체상품을 가져오는 메서드
	ArrayList<ProdVO> selectCategoryList();	//카테고리 목록을 가져오는 메서드.
	ArrayList<ProdVO> selectCategoryProdList(int num); // 카테고리와 일치하는 상품들만 가져오는 메서드
	ArrayList<ProdVO> selectSaleList(); // 세일중인 상품리스트를 가져오는 메서드
	void insertProd(ProdVO prod); //상품 테이블에 물품을 추가하는 메서드
	void changeProd(int i, ProdVO tmpProd); //물품정보를 수정하는 메서드(prodInfo, price) 
	void deleteProd(int i); //선택한 물품을 삭제하는 메서드 
	void changeProdSale(int i, ProdVO tmpProd); //물품의 세일정보를 수정하는 메서드(sale, salePrice, saleRate)
	
}
