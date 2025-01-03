package login_page;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class UserDatabase {
	private HashMap<String, String> userDatas = new HashMap<>();
	private final String filePath = "./UserDataBase";
	private final String fileName = "userDatas.txt";
	
	public UserDatabase() {
		loadUser2();
	}
	
	//write file section
	private void saveUser2(User user) {
		
		File dir = new File(filePath);
		if(!dir.exists()) {
			dir.mkdirs();
			//System.out.println("USER DATABASE DIRECTORY IS MADE");
		}
		String id = user.getId();
		String pw = user.getPw();
		
		try {
			FileOutputStream fout = new FileOutputStream(filePath + "/" + fileName, true);//https://9d4u.tistory.com/322
			OutputStreamWriter osw = new OutputStreamWriter(fout, "UTF-8");
			String text = id + "," + pw + "\n";
			osw.write(text);
			
			osw.close();
			fout.close();
		}
		
		catch(IOException e) {
			System.out.println("USER WRITE ERROR");
		}
	}
	
	//read file section
	private void loadUser2() {
		
		File userDataFile = new File(filePath + "/" + fileName);
		if(!userDataFile.exists()) {
			//System.out.println("user data is empty");
			return;
		}
		
		try {
			FileInputStream fis = new FileInputStream(userDataFile);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");//https://velog.io/@yu0782/BufferedReader%EC%99%80-readLine
			BufferedReader bReader = new BufferedReader(isr);
			
			String line;
			while((line = bReader.readLine()) != null) 
			{
				String idAndPw[] = line.split(",");
				userDatas.put(idAndPw[0], idAndPw[1]);
			}
			
			bReader.close();
			isr.close();
			fis.close();
		}
		
		catch(IOException e) {
			System.out.println("User Read Error");
		}
	}
	
	
	public void addUser(User user) {	
		//add in HashMap;
		userDatas.put(user.getId(), user.getPw());
	
		//add in File
		saveUser2(user);
	}
	
	public boolean isUser(String id, String pw) {
		return userDatas.containsKey(id) && userDatas.get(id).equals(pw);
	}//check whether user is valid
	
	public boolean noId(String id) {
		return !userDatas.containsKey(id);
	}//when this function is true is time to register;
	
}


