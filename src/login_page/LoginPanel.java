package login_page;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import diary.ImgLabel;
import diary.MainFrame;

class LoginPanel extends JPanel {
    private JLabel logo;
    private JTextField idField;
    private JTextField pwField; 
    private JButton loginButton;
    private JButton registerButton;
    private LoginManager loginManager = new LoginManager();
    private JFrame parFrame;
    
    static int num = 0;
    
    public LoginPanel(JFrame frame) {
        
    	this.parFrame = frame;
    	
    	setLayout(null);
        setSize(500, 500);
        
        //setting Logo section
        logo = new JLabel("mini diary");
        logo.setFont(new Font("SanSerif", Font.BOLD, 24));
        logo.setSize(200,50);
        logo.setLocation(200 - 10,50 + 30);
        //add(logo);//temporary terminated
        
        //setting id Label section
        JLabel idMsg = new JLabel("ID");
        idMsg.setSize(30,30);
        idMsg.setLocation(135, 180);
        add(idMsg);
        
        //setting pw Label section
        JLabel pwMsg = new JLabel("PW");
        pwMsg.setSize(30,30);
        pwMsg.setLocation(120 + 5, 210);
        add(pwMsg);
        
        //setting id textField section
        idField = new JTextField();
        idField.setSize(200,30);
        idField.setLocation(150,150 + 30);
        add(idField);

        //setting pw textField section
        pwField = new JTextField();
        pwField.setSize(200,30);
        pwField.setLocation(150,180 + 30);
        add(pwField);

        //setting login button section
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 260, 100, 30);
        loginButton.addActionListener(new LoginBtnActionListener());
        add(loginButton);
        
        //setting register button section
        registerButton = new JButton("register");
        registerButton.setBounds(250, 260, 100, 30);
        registerButton.addActionListener(new RegisterBtnActionListener());
        add(registerButton);
        
        addCatImg();
    }
    
    private void addCatImg() {
		ImgLabel catLabel =  new ImgLabel("cat1");
		//ImageIcon catImg = new ImageIcon(("./cats/cat1.png"));
		ImageIcon catImg = new ImageIcon(getClass().getClassLoader().getResource("cats/cat1.png"));
		catLabel.setIcon(catImg);
		catLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				num = num % 10 + 1;
				String path = "cats/cat" + num + ".png";
				ImageIcon changedIcon = new ImageIcon(getClass().getClassLoader().getResource(path));
				catLabel.setIcon(changedIcon);
				catLabel.repaint();
				catLabel.revalidate();
			}
		});
		
		catLabel.setSize(150,150);
		catLabel.setLocation(170,40);
		add(catLabel);
	}
    
    private void disposeLoginFrame() {
    	parFrame.dispose();
    }
    
    private class RegisterDialog extends JDialog {
        private JTextField idField;
        private JTextField pwField;
        private JButton registerDialogButton;
        private JButton cancelButton;

        public RegisterDialog() {
        	super(parFrame, "Register",true);
        	
        	this.setLocationRelativeTo(parFrame);
            setLayout(null);
            setSize(400, 300);
           
            JLabel idLabel = new JLabel("ID:");
            idLabel.setBounds(50, 50, 100, 30);
            add(idLabel);

            idField = new JTextField();
            idField.setBounds(150, 50, 200, 30);
            add(idField);

            JLabel pwLabel = new JLabel("Password:");
            pwLabel.setBounds(50, 100, 100, 30);
            add(pwLabel);

            pwField = new JTextField();
            pwField.setBounds(150, 100, 200, 30);
            add(pwField);

            registerDialogButton = new JButton("Register");
            registerDialogButton.setBounds(100, 200, 100, 30);
            registerDialogButton.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		String id = idField.getText().trim();
            		String pw = pwField.getText().trim();
            		
            		idField.setText("");
            		pwField.setText("");
            		
            		if(!id.equals("") && !pw.equals("")){
            			int loginState = loginManager.login(id,pw);
            			
            			if(loginState == 2 || loginState == -1) {
            				JOptionPane.showMessageDialog(LoginPanel.this,"ID ALREADY EXISTS.","REGISTER ERROR",
            						JOptionPane.ERROR_MESSAGE);
            			}
            			else {
            				//regiser success
            				JOptionPane.showMessageDialog(null, "Register Success", "Register Success",
            						JOptionPane.INFORMATION_MESSAGE);
            				loginManager.register(id, pw);
            				dispose();
            			}
            		}
            		
            		else {
            			JOptionPane.showMessageDialog(LoginPanel.this,"PLESE ENTER CORRECT ID.","EMPTY ID ERROR",
        						JOptionPane.ERROR_MESSAGE);
            		}
            	}
            });
            
            add(registerDialogButton);

            cancelButton = new JButton("Cancel");
            cancelButton.setBounds(210, 200, 100, 30);
            cancelButton.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		dispose();
            	}
            });
            add(cancelButton);
            

            setVisible(true);
        }
    }
    
    private class RegisterBtnActionListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		new RegisterDialog();
    	}
    }
    
    private class LoginBtnActionListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		String id = idField.getText();
    		String pw = pwField.getText();
    		idField.setText("");
    		pwField.setText("");
    		
    		if(!id.equals("") && !pw.equals("")) {
    			
    			int loginState = loginManager.login(id, pw);
    			
    			switch(loginState) {
    			
    			case 1:
    				//System.out.println("need to register");
    				JOptionPane.showMessageDialog(LoginPanel.this,"Need Register","login error",
    						JOptionPane.ERROR_MESSAGE);
    				//need to register
    				break;
    			case 2:
    				//System.out.println("login Success");
    				disposeLoginFrame();
    				new MainFrame(id);
    				break;
    				//login Success
    			case -1:
    				//System.out.println("wrong password!!");
    				JOptionPane.showMessageDialog(LoginPanel.this,"Wrong Password!!","login error",
    						JOptionPane.ERROR_MESSAGE);
    				break;
    			}
    			
    			
    			
    			
    		}
    	}
    }
}
