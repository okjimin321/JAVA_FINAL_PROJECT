package diary;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImgLabel extends JLabel{
	public ImgLabel(String text){
		super(text);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		if(!(this.getIcon() instanceof ImageIcon))
			return;
		else {
			ImageIcon icon = (ImageIcon)this.getIcon();
			Image img = icon.getImage();
			g.drawImage(img, 0,0,getWidth(), getHeight(), this);
		}
	}
}//define ImgLabel to case when the window size changes , the image size also changes
