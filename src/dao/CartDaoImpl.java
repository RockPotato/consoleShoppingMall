package dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vo.CartVO;
import vo.Database;
import vo.ProdVO;
import vo.DealHistoryVO;

public class CartDaoImpl implements CartDao {

	@Override
	public CartVO pick(int i) {// 고른 상품을 반환하는 메서드
		
		ProdVO pvo = Database.tb_prod.get(i);
		CartVO cvo = new CartVO();
		cvo.setItem(pvo.getProdInfo());
		if(pvo.getSalePrice()==0)
		{
			cvo.setPrice(pvo.getPrice());
		}
		else
		{
			cvo.setPrice(pvo.getSalePrice());
		}
		return cvo;
	}

	@Override
	public ArrayList<CartVO> selectCartList() { // 장바구니 테이블을 반환하는 메서드
		return Database.tb_cart;
	}

	@Override
	public void insertUser(CartVO cart) { // 장바구니 테이블에 상품 입력
		Database.tb_cart.add(cart);
	}

	@Override
	public void insertPayment(DealHistoryVO dhvo) {
		Database.tb_dealHistory.add(dhvo);
	}

	@Override
	public DealHistoryVO resultPayment(int sum) {
		DealHistoryVO dhvo = new DealHistoryVO();
		CartDao cd = new CartDaoImpl();
		Date today=new Date();
		ArrayList<CartVO> alcvo = cd.selectCartList();
		ArrayList<String> items= new ArrayList<String>();
		for(int i=0;i<alcvo.size();i++)
		{
			items.add(alcvo.get(i).getItem());
			alcvo.remove(i);
		}
		SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dhvo.setMoneyIn(sum);
		dhvo.setDate(todayFormat.format(today));
		dhvo.setItems(items);
		return dhvo;
	}
	
	@Override	//정권
	public CartVO refundPick(int i) {
		CartVO cvo = Database.tb_deal.get(i);
		cvo.getItem();
		cvo.getPrice();
		return cvo;
	}

	@Override	//정권
	public void deletePick(int i) {
		Database.tb_deal.remove(i);
	}

	
	@Override	//정권
	public ArrayList<CartVO> stackDealhistoty() {
		for(int i=0; i < Database.tb_cart.size(); i++){
			Database.tb_deal.add(Database.tb_cart.get(i));
		}
		return Database.tb_deal;
	}

	@Override
	public void deleteCart() {
		for(int i=0; i<Database.tb_cart.size();){	
			Database.tb_cart.remove(i);			
		}
	}

	@Override
	public ArrayList<CartVO> selectDealhistotyList() {
		return Database.tb_deal;
	}

	@Override
	public ArrayList<CartVO> selectDealhistoty2(int num) {
		ArrayList<CartVO> cvos = new ArrayList<CartVO>();
		for(int i=0;i<Database.tb_deal.size();i++)
		{
			if(Database.tb_deal.get(i).getGuestNum()==num)
			{
				cvos.add(Database.tb_deal.get(i));
			}
		}
		return cvos;
	}

	@Override
	public void forRefund(int num) {
		for(int i=0;i<Database.tb_deal.size();i++)
		{
			if(Database.tb_deal.get(i).getGuestNum()==num)
			{
				Database.tb_deal.remove(i);
				i--;
			}
		}
	}

	@Override
	public void afterRefund(ArrayList<CartVO> cvos) {
		for(int i=0;i<cvos.size();i++)
		{
			Database.tb_deal.add(cvos.get(i));
		}
		
	}
	
}
