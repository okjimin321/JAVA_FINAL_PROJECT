package login_page;

public class LoginManager {
	private UserDatabase uds = new UserDatabase();
	
	public void register(String id, String pw) {
		User newUser = new User(id, pw);
		uds.addUser(newUser);
	}//check by login(...), when that returns 1
	 //and it operates
	
	int login(String id, String pw) {
		if(uds.noId(id)) {
			return 1;
		}//no ID: need to register;
		
		else if(uds.isUser(id, pw)) {
			return 2;
		}//success
		
		else 
			return -1;
		//the case when wrong password;
	}
}	
