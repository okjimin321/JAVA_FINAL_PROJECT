package diary;


import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class MonthPanel extends JPanel{
	private DateButton dateBtns[];
	private String id;
	private int year;
	private int month;
	private int dates;
	private String[] days = {"SUN", "MON" ,"TUE", "WED" , "THU", "FRI", "SAT" };
	
	public MonthPanel(int year, int month, String id) {
		//initialize section
		this.year = year;
		this.month = month;
		this.id = id;

		//setting Panel option section
		setDates();
		setDateBtns();//initialize each btns;
		setLayout(new GridLayout(0,7));
		
		//add components section
		addDaysLabel();
		addStartEmptyLabels();
		addDateBtns();
		
		//set size and visible
		setSize(500,500);
		setVisible(true);
		
	}
	
//functions about setting buttons
	private void setDates() {
		LocalDate newDate = LocalDate.of(year, month, 1);
		dates = newDate.lengthOfMonth();//refer this blog https://hey79.tistory.com/159
	}
	
	private void setDateBtns() {
		dateBtns = new DateButton[dates];
		for(int i = 0; i < dates; i++) {//(i + 1) is a date;
			dateBtns[i] = new DateButton(year, month, i + 1, id);
		}
	}
	
//functions about add Components
	private void addDaysLabel() {
		for(int i = 0; i < 7; i++) {
			JLabel dayLabel = new JLabel(days[i]);
			dayLabel.setHorizontalAlignment(JLabel.CENTER);//refer this blog(https://blog.naver.com/reeeh/220436765640)
			add(dayLabel);
		}
	}
	
	private void addDateBtns() {
		for(int i= 0; i < dates; i++) {
			add(dateBtns[i]);
		}//add buttons
	}
	
	private void addStartEmptyLabels() {
		LocalDate date = LocalDate.of(year, month, 1);
		DayOfWeek day = date.getDayOfWeek();//refer this blog https://hianna.tistory.com/610
		
		for(int i = 1; i <= day.getValue() % 7; i++) {
			add(new JLabel());
			//set empty label to set start of day;
		}
	}
	
	
}
