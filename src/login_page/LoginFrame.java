package login_page;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.io.File;
import diary.ImgLabel;

public class LoginFrame extends JFrame{
	
	private Container c = getContentPane();
	private LoginPanel lgp = new LoginPanel(this);
	
	public LoginFrame() {
		super("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.add(lgp, BorderLayout.CENTER);
		
		setSize(500,500);
		setResizable(false);//https://blog.naver.com/start150408/220362388236
		setLocationRelativeTo(null);//https://selfinvestfriends.tistory.com/15
		setVisible(true);
		
	}
	
	
	
	public static void main(String args[]) {
		new LoginFrame();
	}
}

