package dao;


import java.util.ArrayList;

import vo.Database;
import vo.ProdVO;

public class ProdDaoImpl implements ProdDao {
	
	@Override
	public ProdVO selectProd(String key, Object value) { //상품 하나를 골라서 가져오는 메서드.
		for (int i = 0; i < Database.tb_prod.size(); i++) {
			ProdVO prod = Database.tb_prod.get(i);

			if (key.equals("Choose")) {
				if (value.equals(i)){
					return prod;
				}
			}
		}
		return null;
	}
	
	@Override
	public ArrayList<ProdVO> selectProdList() { // 전체상품을 가져오는 메서드
		return Database.tb_prod;
	}
	
	@Override
	public ArrayList<ProdVO> selectCategoryList() { //카테고리 목록을 가져오는 메서드.
		return null;
	}	
	
	@Override
	public ArrayList<ProdVO> selectCategoryProdList(int num) { // 카테고리와 일치하는 상품들만 가져오는 메서드 
		ArrayList<ProdVO> alpvo = new ArrayList<ProdVO>();
		for(int i=0;i<Database.tb_prod.size();i++)
		{
			if(Database.tb_prod.get(i).getCategoryNumber()==num)
			{
				alpvo.add(Database.tb_prod.get(i));
			}
		}
		return alpvo;
	}
	
	@Override
	public ArrayList<ProdVO> selectSaleList() { //세일중인 상품리스트를 가져오는 메서드
		ArrayList<ProdVO> saleList = new ArrayList<ProdVO>();
		for(int i = 0; i < Database.tb_prod.size(); i++){
			if(Database.tb_prod.get(i).isSale()){
				saleList.add(Database.tb_prod.get(i));
			}
		}
		return saleList;
	}	
	
	@Override
	public void insertProd(ProdVO prod) { //상품 테이블에 물품을 추가하는 메서드
		Database.tb_prod.add(prod);
	}

	@Override
	public void changeProd(int i, ProdVO tmpProd) { //물품정보를 수정하는 메서드
		Database.tb_prod.get(i).setProdInfo(tmpProd.getProdInfo());
		Database.tb_prod.get(i).setPrice(tmpProd.getPrice());
	}

	@Override
	public void changeProdSale(int i, ProdVO tmpProd) {
		Database.tb_prod.get(i).setSalePrice(tmpProd.getSalePrice());
		Database.tb_prod.get(i).setSale(tmpProd.isSale());
		Database.tb_prod.get(i).setSaleRate(tmpProd.getSaleRate());

	}

	@Override
	public void deleteProd(int i) {
		Database.tb_prod.remove(i);
	}
	
}
