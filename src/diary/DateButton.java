package diary;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class DateButton extends JButton {
	private File imgFile;
	private File diaryFile;
	private int date;
	private int month;
	private int year;
	private String id;//store its owner
	private String textFileName;
	private String textFilePath = "./Diarys";
	private String imgFileName;
	private String imgFilePath = "./Images";
	
	public DateButton(int year, int month, int date, String id) {
		//set button text
		super(date + "");
		
		//initialize section
		this.date = date;
		this.month = month;
		this.year = year;
		this.id = id;
		this.textFilePath = textFilePath + "/" + id  +"/" + year + "/" + month;//set directory (./Diary/id/year/month)
		this.textFileName = id + "_" + year + "_" + month + "_" + date + "_" +  ".txt";
		this.imgFilePath = imgFilePath + "/" + id  +"/" + year + "/" + month;
		this.imgFileName = id + "_" + year + "_" + month + "_" + date + "_" + ".jpg";
		this.imgFile = new File(imgFilePath + "/" + imgFileName);
		this.diaryFile = new File(textFilePath + "/" + textFileName);//make only button is clicked;
		
		//setting buttons Color and Image section
		setBtnDefaultColor();
		setDateBtnColor();
		setDateBtnImg();
		setBorderPainted(true);
		
		//add ActionListener section
		addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DateDialog();
			}
		});
	}
	
//Override paint to set Image size
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(!(this.getIcon() instanceof ImageIcon)) {
			return;
		}
		else {
			ImageIcon icon = (ImageIcon)this.getIcon();
			Image img = icon.getImage();
			g.drawImage(img, 0,0, getWidth(), getHeight(),this);
		}
	}
	
	private ImageIcon getDateBtnIcon() {
		return (ImageIcon)this.getIcon();
	}
	
//functions about Color and Image section
	private void setDateBtnColor() {
		if(diaryFile.exists()) {
			setBtnWrittenColor();
		}
	}
	
	private void setDateBtnImg() {
		if(imgFile.exists()) {
			setIcon(new ImageIcon(imgFile.getPath()));
		}
		else {
			setIcon(null);
		}
		repaint();
		revalidate();
	}
	
	private void setDateBtnImg(Icon icon) {
		setIcon(icon);
	}
	
	private void setBtnDefaultColor() {
		setBackground(new Color(160, 225, 220));
	}
	
	private void setBtnWrittenColor() {
		setBackground(new Color(236, 234, 228));//to represent which date diary is written;
	}												
	
	
	
//dateDialog class section
	private class DateDialog extends JDialog{
		private static int count = 0;
		private ImgLabel imgLabel;
		private String sourceImgFilePath;
		private JTextArea diaryMessageField;
		private JButton saveButton;
		private JButton deleteButton;
		
		public DateDialog(){	
			//setting dialog's components section
			setLocationRelativeTo(DateButton.this);
			setBtns();
			addTextAndImage();
			
			//setting dialog section
			setTitle(id + "'s "+ year + "/" + month + "/" + date + " diary.");
			setModal(true);
			setSize(400,600);
			setVisible(true);
		}
		
		
	//functions about setting components
		private void setBtns() {
			//set save button
			saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String text = diaryMessageField.getText();
					updateText(text);
					updateImg(sourceImgFilePath);
					//setDateBtnImg(getIcon());
					//dispose();
				}
			});
			
			//set cancel button
			deleteButton = new JButton("delete");
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(null,"Are you sure to delete?",
							"Confirm", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						if(!imgFile.exists() && !diaryFile.exists()) {
							JOptionPane.showMessageDialog(null, "NO FILE TO DELETE",
									"Delete warning", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if (imgFile.exists()) {
							imgFile.delete();
			        	}
			        	if (diaryFile.exists()) {
			            	diaryFile.delete();
			        	}
			        	
						setBtnDefaultColor();
						setDateBtnImg();
					
						dispose();
					}
					
				}
			});
			
			//add buttons to Dialog
			JPanel tmp= new JPanel();
			tmp.setLayout(new FlowLayout());
			tmp.add(saveButton);
			tmp.add(deleteButton);
			add(tmp,BorderLayout.NORTH);
		}
		
		//setting img label
		private void setImgLabel() {
			imgLabel = new ImgLabel("click to insert jpg file");
			imgLabel.setHorizontalAlignment(JLabel.CENTER);
			imgLabel.setOpaque(true);
			imgLabel.setBackground(new Color(213,255,213));
			
			imgLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
					chooser.setFileFilter(filter);
					
					int ret = chooser.showOpenDialog(null);
					if(ret != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "YOU DID NOT CHOOSE FILE",
								"FILE SELECTION WARNING", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					
					String imgFilePath = chooser.getSelectedFile().getPath();
					sourceImgFilePath = imgFilePath;//set path to save Image
					imgLabel.setIcon(new ImageIcon(sourceImgFilePath));
					
					imgLabel.repaint();
					imgLabel.revalidate();
				}
			});
		}
		
		//setting msg field
		private void setMsgField() {
			diaryMessageField = new JTextArea();
			diaryMessageField.setLineWrap(true);//refer blog (https://blog.naver.com/ksseo63/221498516605)
												//when horizen is full,it automatically changes the line
		}
		
		
	//add msgField and imgLabel
		private void addTextAndImage() {
			//set msgField
			setMsgField();
			loadText();
			
			//set image label section
			setImgLabel();
			loadImg();
			
			//add image label and msgField
			JScrollPane msgFieldScrollPane = new JScrollPane(diaryMessageField);
			JPanel tmp = new JPanel();
			tmp.setLayout(new GridLayout(0,1));
			tmp.add(imgLabel);
			tmp.add(msgFieldScrollPane);
			add(tmp);
		}
		
		
	//function about image File Stream
		private void updateImg(String path) {
			
			if(path == null) {
				return;
			}
			else {
				saveImg(path);
				setDateBtnImg(imgLabel.getIcon());//solved and checked
				
				//case image is changed
				if(!(imgFile.getPath().equals(imgFilePath + "/" + imgFileName))) {
					imgFile.renameTo(new File(imgFilePath + "/" +  imgFileName));
					imgFile = new File(imgFilePath + "/" + imgFileName);
				}
			}
		}
		
		//write image file section
		private void saveImg(String path) {
			//I try to copy new image to imgFile,
			//but DateButton's icon is not changed immediately until restart program
			//i think the problem happens because i used same path file
			//so i decided to use trick
			if(imgFile.exists()) {				
				imgFile.delete();
				count++;//to avoid case file names are same
				File tmp = new File(imgFilePath + "/" + count + "tmp.jpg");
				imgFile = tmp;
			}
			
			File dir = new File(imgFilePath);
			if(!dir.exists()) {
				dir.mkdirs();
				//System.out.println("IMG DIRECTORY IS MADE");
			}
			
			try {
				
				FileInputStream fi = new FileInputStream(path);
				FileOutputStream fo = new FileOutputStream(imgFile);
				
				byte[] b = new byte[1024*64];
				int c;
				while((c = fi.read(b)) != -1) {
					fo.write(b, 0, c);
				}
				
				fi.close();
				fo.close();
			}
			
			catch(IOException e) {
				//System.out.println("IMG FILE WRITE ERROR");
			}
		}
		
		//read image file section
		private void loadImg() {
			
			ImageIcon imgIcon = getDateBtnIcon();
			imgLabel.setIcon(imgIcon);
			imgLabel.repaint();
			imgLabel.revalidate();	
		}
		
		
	//functions about text File Stream
		private void updateText(String text) {
			if (text.equals("") && !diaryFile.exists()) {
				return;
				//when the textArea is empty and file is not existed but try to save data
			}
			else if (text.equals("") && diaryFile.exists()) {
				diaryFile.delete();
				setBtnDefaultColor();
				//when reset the textArea
			}
			
			else {
				saveText(text);
				setBtnWrittenColor();
				//write success
			}
		}
		
		//write text file section
		private void saveText(String text) {
			File dir = new File(textFilePath);
			//directory make section
			if(!dir.exists()) {
				dir.mkdirs();
				//System.out.println("DIARY DIRECTORY IS MADE");
			}
		
			try {
				FileOutputStream fout = new FileOutputStream(diaryFile);
				OutputStreamWriter osw = new OutputStreamWriter(fout, "UTF-8");
				osw.write(text);
				
				osw.close();
				fout.close();
			}
			
			catch(IOException e) {
				//System.out.println("FILE WRITE ERROR");
			}
		}
	
		//read text file section
		private void loadText() {
			if(!diaryFile.exists()) {
				return;
			}
			
			try {
				
				FileInputStream fis = new FileInputStream(diaryFile);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				
				int c;
				while((c = isr.read()) != -1) {
					diaryMessageField.append((char)c + "");
				}
				
				isr.close();
				fis.close();
				
				
			}
			catch(IOException e) {
				//System.out.println("FILE READ ERROR");
			}
		}
		
	}
	
}

