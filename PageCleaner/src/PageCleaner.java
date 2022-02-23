import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.MouseInfo;
import java.awt.Point;

import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.sun.glass.ui.Screen;

public class PageCleaner  implements MouseListener{

	static JFrame window;
	static PageCleaner pg;
	static JLabel label_X = new JLabel ("100");
	static JLabel label_Y = new JLabel ("100");
    
	  public void mouseClicked(MouseEvent ev) {
      	Point p = MouseInfo.getPointerInfo().getLocation();
      	System.out.println("mouseClicked  "+ p.x + " " + p.y);
      	
		label_X.setText(""+p.x);
		label_Y.setText(""+p.y);
	}
	
	public static void click(int x, int y) throws AWTException{
		try {
			Robot bot = new Robot();
		    bot.mouseMove(x, y);    
		    Thread.sleep(100);
		    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    Thread.sleep(100);
		    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		   // Thread.sleep(5000);
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
		
    	 window = new JFrame("showMessageDialog");
    	 
    	
    	 
    	 window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 
    	 String text = "Bonjour\n";
    	 text += "Cette petite applicaton permet de supprimer les pages facebook indésirable\n";
    	 text += "Pour ce faire, il faut :\n";
    	 text += "  1- Ouvrir un navigateur web\n";
    	 text += "  2- Se connecter au compte facebook\n";
    	 text += "  3- Aller à la page suivante: \n";
    	 text += "      --------------\n";
         text += "      -->  https://www.facebook.com/1457534434/allactivity/?activity_history=false&category_key=LIKEDINTERESTS\n";
         text += "      --------------\n";
         
         text += "  4- cliquer sur le bout ... sur la dernière page likée (ça permet de sauvgarder les coodonnées X,Y";
         
         window.setSize(800, 400);

         JTextArea message = new JTextArea (text);
         message.setEditable(false);


         JButton configure = new JButton("select ... button");
         JButton valid     = new JButton("validate XY"); 
         JButton start     = new JButton("start clean");
         
         
         window.add(message);
         window.add(label_X);
         window.add(label_Y);
         window.add(configure);
         window.add(valid);
         window.add(start);
         
         window.setLayout(new FlowLayout());
         window.setVisible(true);
         


         configure.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
     	    	if (pg == null)
     	    	{
     	    		System.out.println("add listener");
					pg =  new PageCleaner();
					window.addMouseListener(pg);
					Point p = MouseInfo.getPointerInfo().getLocation();

     	    	}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
     	    	
     	});
         
         valid.addActionListener(new ActionListener() {
      	    public void actionPerformed(ActionEvent arg0) {
      	    	if (pg == null)
      	    	{
      	    		System.out.println("add listener");
 					pg =  new PageCleaner();
 					window.addMouseListener(pg);
 					Point p = MouseInfo.getPointerInfo().getLocation();
 					label_X.setText(""+p.x);
 					label_Y.setText(""+p.y);
      	    	}
      	    }
      	    	
      	});
         
     		
         start.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("click button");
			        
			        for (int i = 0;  i < 200 ; i++)
			        {
			        	try {
				        	Point p = MouseInfo.getPointerInfo().getLocation();
				        	System.out.println(i+"  "+ p.x + " " + p.y);
				        	click (Integer.parseInt(label_X.getText()),Integer.parseInt(label_X.getText()));
				        	click (Integer.parseInt(label_X.getText())-167,Integer.parseInt(label_X.getText()));
				        	//click (1622,377);
				        	//refresh();	
							
						} catch (Exception e) {
							// TODO: handle exception
						}
			        }
				}
        	});
        		
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
