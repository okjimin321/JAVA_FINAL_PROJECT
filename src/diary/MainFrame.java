package diary;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
	//private Vector<MonthPanel> monthPanels;
	private MonthPanel monthPanel;
	private MainMenuBar menuBar;
	private String id;
	
	public MainFrame(String id) {
		//initialize section
		this.id = id;
		this.menuBar = new MainMenuBar(this);
		
		//setting mainFrame
		setTitle(id + "'s diary");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		
		//update MonthPanel
		updateMonthPanel();
		
		//set size and visible
		setSize(537,500);
		setVisible(true);
	}
	
	public void updateMonthPanel() {
		//get menuBar's year and month
		int month = menuBar.getMonth();
		int year = menuBar.getYear();
		
		//if monthPanel exists, remove
		if(monthPanel != null) {
			remove(monthPanel);
		}
		
		//add current monthPanel
		monthPanel = new MonthPanel(year, month, id);
		add(monthPanel);
		
		//revalidate to show current monthPanel
		revalidate();
		repaint();
	}

}
