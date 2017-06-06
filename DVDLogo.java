import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class DVDLogo extends JFrame {
	public static void main(String args[]) {
		DVDLogo window = new DVDLogo();
		Screen screen = new Screen();
		screen.setPreferredSize(new Dimension(1920,1080));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(screen);
		window.setUndecorated(true);
		window.pack();
		window.setVisible(true);
		screen.timer.start();
	}
}

class Screen extends JPanel implements ActionListener {
	Logo logo = new Logo();
	Timer timer = new Timer(1, this);
	Random random = new Random();
	Color boxColor[] = new Color[3];
	int currentColor = 0;
	
	public Screen() {
		boxColor[0] = Color.RED;
		boxColor[1] = Color.GREEN;
		boxColor[2] = Color.BLUE;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1920,1080);
		g.setColor(boxColor[currentColor]);
		g.fillRect(logo.x,logo.y,logo.width,logo.height);
		//DRAW DVD VIDEO RELATIVE TO LOGO
		g.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 80));
		g.setColor(Color.BLACK);
		g.drawString("D", logo.x + 30, logo.y + 110);
		g.drawString("V", logo.x + 80, logo.y + 110);
		g.drawString("D", logo.x + 130, logo.y + 110);
		g.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 53));
		g.setColor(Color.WHITE);
		g.drawString("video", logo.x + 30, logo.y + 180);
	}
	
	public void actionPerformed(ActionEvent e) {
		//add variance to velocity
		int newX,newY;
		if(random.nextInt(2) == 0) {
			newX = 2;
			newY = 1;
		} else {
			newY = 2;
			newX = 1;
		}
		//move the logo
		if(logo.x < 0 || logo.x > 1680) { //550 = 600 - 50  (550 = window size - rectangle size)
			logo.invert(true, false);
			currentColor++;
			if(currentColor > 2) {
				currentColor = 0;
			}
			
			if(logo.xVel < 0) {
				logo.xVel = -newX;
			} else {
				logo.xVel = newX;
			}
			if(logo.yVel < 0) {
				logo.yVel = -newY;
			} else {
				logo.yVel = newY;
			}
			
		}
		if(logo.y < 0 || logo.y > 840) { //370 = 400 - 30 (370 = window size - rectangle size)
			logo.invert(false, true);
			currentColor++;
			if(currentColor > 2) {
				currentColor = 0;
			}
		
			if(logo.xVel < 0) {
				logo.xVel = -newX;
			} else {
				logo.xVel = newX;
			}
			if(logo.yVel < 0) {
				logo.yVel = -newY;
			} else {
				logo.yVel = newY;
			}
			
		}
		if(logo.x < 0) logo.x = 0;
		if(logo.y < 0) logo.y = 0;
		//prevents getting stuck in "wall"
		if(logo.x == -2 && logo.xVel == 1) logo.x = 0;
		if(logo.x == 1682 && logo.xVel == -1) logo.x = 1680;
		if(logo.y == -2 && logo.yVel == 1) logo.y = 0;
		if(logo.y == 842 && logo.yVel == -1) logo.y = 840;
		System.out.print(logo.x + "   "  + logo.y);
		logo.x += logo.xVel;
		logo.y += logo.yVel;
		System.out.println("   :   " + logo.x + "   " + logo.y);
		repaint();
	}
}

class Logo {
	int x = 480, y = 0;
	int xVel = 2, yVel = 1;
	int width = 240;
	int height = 240;
	
	public void invert(boolean x, boolean y) {
		if(x) {
			xVel *= -1;
		}
		if(y) {
			yVel *= -1;
		}
	}
}