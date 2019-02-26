package vo;

public class ProdVO {
	private int price;
	private String prodInfo;
	private String[] review;
	private String category;
	private int categoryNumber;
	private boolean sale; //세일중인지 확인하기. true/false
	private int salePrice; //세일중인 물품의 가격
	private int saleRate; //할인율
	private int prodNum; //상품번호
	
	public int getProdNum() {
		return prodNum;
	}
	public void setProdNum(int prodNum) {
		this.prodNum = prodNum;
	}
	public boolean isSale() {
		return sale;
	}
	public void setSale(boolean sale) {
		this.sale = sale;
	}
	
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	
	public int getSaleRate() {
		return saleRate;
	}
	public void setSaleRate(int saleRate) {
		this.saleRate = saleRate;
	}
	
	public int getCategoryNumber() {
		return categoryNumber;
	}
	public void setCategoryNumber(int categoryNumber) {
		this.categoryNumber = categoryNumber;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String[] getReview() {
		
		return review;
	}
	public void setReview(String[] review) {
		for(int i=0;i<review.length;i++)
		{
			if(review[i].length()>20)
			{
				System.out.println("리뷰 길이를 벗어남");
				review[i]="";
			}
		}
		this.review = review;
	}
	public String getProdInfo() {
		return prodInfo;
	}
	public void setProdInfo(String prodInfo) {
		this.prodInfo = prodInfo;
	}
	
}
