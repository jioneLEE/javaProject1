package _03소코반2_김성윤2;

public class User {

	private int userNum=1;
	private String userId;
	private String userPwd;
	
	public User(int userNum, String userId, String userPwd) {
		super();
		this.userNum = userNum;
		this.userId = userId;
		this.userPwd = userPwd;
	}
	
	
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	
}
