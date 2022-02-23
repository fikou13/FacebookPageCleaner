import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;

public class PageCleaner  implements MouseListener{

	  public void mouseClicked(MouseEvent ev) {
	   System.out.println(ev.getX() +":"+ev.getY());
	}
	
	public static void click(int x, int y) throws AWTException{
		try {
			Robot bot = new Robot();
		    bot.mouseMove(x, y);    
		    Thread.sleep(100);
		    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    Thread.sleep(100);
		    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void refresh () throws AWTException{
	    Robot bot = new Robot();
	    try {
	    	bot.keyPress(KeyEvent.VK_F5);
			Thread.sleep(100);
			bot.keyRelease(KeyEvent.VK_F5);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
    public static void main(String[] args) throws IOException, Exception 
    {
        // Prints "Hello, World" to the terminal window.
        Thread.sleep(10*1000);
      
        		
        
        for (int i = 0;  i < 200 ; i++)
        {
        	Point p = MouseInfo.getPointerInfo().getLocation();
        	System.out.println(i+"  "+ p.x + " " + p.y);
        	click (1789,377);
        	click (1622,377);
        	refresh();	
        }
   }

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
