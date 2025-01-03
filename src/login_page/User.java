package login_page;

class User{
	private String id;
	private String pw;
	
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}//set user infor;
	
	public String getId() {
		return id;
	}
	
	public String getPw() {
		return pw;
	}
}