package vo;

import java.util.ArrayList;

public class DealHistoryVO {
	private int moneyIn;
	private ArrayList<String> items;
	private String date;
	public int getMoneyIn() {
		return moneyIn;
	}
	public void setMoneyIn(int moneyIn) {
		this.moneyIn = moneyIn;
	}
	public ArrayList<String> getItems() {
		return items;
	}
	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String string) {
		this.date = string;
	}
}
