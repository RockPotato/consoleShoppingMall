package vo;

public class UserVO {
	private String id;
	private String password;
	private String name;
	private String phone;
	private String userClass = "C";
	private boolean gender;
	private int money = 30000;
	private int idxnumber; //키값 (해시맵과 같은 개념)
	private int total_pay;	//결제 총액 // 등급매길때 사용
	
	public int getIdxnumber() {
		return idxnumber;
	}
	public void setIdxnumber(int idxnumber) {
		this.idxnumber = idxnumber;
	}
	public void setMoney(int money)
	{
		this.money += money;
	}
	public int getMoney() {
		return money;
	}
	@Override
	public String toString() {
		return "UserVO [id=" + id + ", password=" + password + ", name=" + name
				+ ", phone=" + phone + ", gender=" + gender + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserClass() {
		return userClass;
	}
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}
	public int getTotal_pay() {
		return total_pay;
	}
	public void setTotal_pay(int total_pay) {
		this.total_pay += total_pay;
	}
}
