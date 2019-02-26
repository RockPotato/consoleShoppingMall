package dao;


import java.util.ArrayList;

import vo.CartVO;
import vo.DealHistoryVO;

public interface CartDao {
	CartVO pick(int i) ;
	ArrayList<CartVO> selectCartList();
	void insertUser(CartVO cart);
	void insertPayment(DealHistoryVO dhvo);
	DealHistoryVO resultPayment(int sum);
	void forRefund(int num);
	CartVO refundPick(int i); //환불창에서 카트안에 상품을 고르기 위해
	void deletePick(int i); 
	ArrayList<CartVO> stackDealhistoty();
	void deleteCart();
	ArrayList<CartVO> selectDealhistotyList();
	ArrayList<CartVO> selectDealhistoty2(int num);
	void afterRefund(ArrayList<CartVO> cvos);
}
