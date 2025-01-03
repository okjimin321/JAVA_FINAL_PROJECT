package diary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;


public class MainMenuBar extends JMenuBar{
	private JButton nextMonthButton, prevMonthButton;
	private JButton nextYearButton, prevYearButton;
	private int currentMonth;
	private int currentYear;
	private JMenu yearMenu, monthMenu;
	private JFrame parentFrame;
	
	MainMenuBar(JFrame parent){
		LocalDate currentDate = LocalDate.now();
		
		//setting MenuBar
		this.parentFrame = parent;
		this.currentYear = currentDate.getYear();
		this.currentMonth = currentDate.getMonthValue();
		this.yearMenu = new JMenu(currentYear + "");
		this.monthMenu = new JMenu(currentMonth + "");
		
		//setting Buttons
		setButtons();
		addBtnsActionListener();
		
		//add buttons to menu
		yearMenu.add(nextYearButton);	yearMenu.add(prevYearButton);
		monthMenu.add(nextMonthButton);	monthMenu.add(prevMonthButton);
		
		//add menu to menuBar
		add(yearMenu);	add(monthMenu);	
	}
	
	private void addBtnsActionListener() {
		//add yearBtns
		prevYearButton.addActionListener(new YearChangeActionListener());
		nextYearButton.addActionListener(new YearChangeActionListener());
		
		//add monthBtns
		nextMonthButton.addActionListener(new MonthChangeActionListener());
		prevMonthButton.addActionListener(new MonthChangeActionListener());
	}
	
	private void setButtons() {
		//setting text to buttons
		nextYearButton = new JButton("next");
		prevYearButton = new JButton("prev");
		nextMonthButton = new JButton("next");
		prevMonthButton = new JButton("prev");
	}
	
	private void updateMonthMenuText(int x) {
		int month = getMonth();
		month += x;
		
		if(month == 0) {
			month = 12;
			updateYearMenuText(-1);
		}
		else if(month == 13) {
			month = 1;
			updateYearMenuText(1);
		}
		monthMenu.setText(month + "");
	}
	
	private void updateYearMenuText(int x) {
		int year = getYear();
		year += x;
		
		if(year < 0) {
			//System.out.println("Year can not be negative");
			return;
		}
		
		else {
			yearMenu.setText(year + "");
		}
	}
	
	private class YearChangeActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton tmp = (JButton)e.getSource();
			String msg = tmp.getText();
			
			if(msg.equals("next")) {
				updateYearMenuText(1);
			}
			else if(msg.equals("prev")) {
				updateYearMenuText(-1);
			}
			
			if(parentFrame instanceof MainFrame)
				((MainFrame) parentFrame).updateMonthPanel();

			

		}
	}
	
	private class MonthChangeActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton tmp = (JButton)e.getSource();
			String msg = tmp.getText();
			
			if(msg.equals("next")) {
				updateMonthMenuText(1);
			}
			
			else if(msg.equals("prev")) {
				updateMonthMenuText(-1);
			}
			
			if(parentFrame instanceof MainFrame)
				((MainFrame) parentFrame).updateMonthPanel();
		}
	}
	
	public int getMonth() {
		return Integer.parseInt(monthMenu.getText());
	}
	
	public int getYear() {
		return Integer.parseInt(yearMenu.getText());
	}
	
}
