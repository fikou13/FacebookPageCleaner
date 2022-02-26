package PageCleaner;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;



class Browser extends Region {

	private HBox toolBar;
	final static  WebView browser = new WebView();
	public static final WebEngine webEngine = browser.getEngine();


	
	
    CookieManager cookieManager = new CookieManager();
   
	URI uri = URI.create("https://www.facebook.com/");
	
	final Button showPrevDoc 		= new Button("Toggle Previous Docs");
	final Button  goToCleanupPage 	= new Button("Page de Clean");
	final Label cords 				= new Label("Coordinates XY");
	final TextField c_X				= new TextField("X");
	final TextField c_Y				= new TextField("Y");
	final Button  configure			= new Button("configure");
	final Button  start 			= new Button("start");
	final Button  stop 				= new Button("stop");
	final Button  help 				= new Button("help");
	
	int c_X_int=-1;
	int c_Y_int=-1;
	
	final TextField time_refresh = new TextField("3");
	
	public boolean start_clean=false;
	public boolean start_configure=false;

	void refresh() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				webEngine.reload();
			}
		});
	}

	public  void click(int x, int y){
		try {
			Robot bot = new Robot();
		    bot.mouseMove(x, y);    
		    Thread.sleep(100);
		    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    Thread.sleep(100);
		    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		    int time_wait = 3*1000;
		    try {
		    	time_wait = Integer.parseInt(time_refresh.getText()) * 1000;
		    }catch (final NumberFormatException e) {}
		    
		    Thread.sleep(time_wait);
		    
		} catch (InterruptedException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void loadComplete()
	{
		Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
		try {
			java.net.CookieHandler.getDefault().get(uri,headers) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 for (String value : headers.keySet()) {
		        System.out.println(value);
		    }
		
		
		if (start_clean) {
			  Thread cleanThread = new Thread(new Runnable() {
	  	         public void run() {
	  	        	 if (c_X_int>-1 & c_Y_int>-1) {
	  				 click(c_X_int, c_Y_int);
	  				 click(c_X_int - 165 , c_Y_int);
	  				 refresh();
	  	        	 }
	  		     }
		   	    });  
			 cleanThread.start();
		}
	}


	public Browser() {

		//apply the styles
		getStyleClass().add("browser");


		// create the toolbar
		toolBar = new HBox();
		toolBar.setAlignment(Pos.CENTER);
		toolBar.getStyleClass().add("browser-toolbar");
		
		c_X.setMaxWidth(60);
		c_Y.setMaxWidth(60);
		
		toolBar.getChildren().add(help);
		toolBar.getChildren().add(goToCleanupPage);
		toolBar.getChildren().add(cords);
		toolBar.getChildren().add(c_X);
		toolBar.getChildren().add(c_Y);
		toolBar.getChildren().add(configure);
		toolBar.getChildren().add(start);
		
		toolBar.getChildren().add(stop);
		toolBar.getChildren().add(time_refresh);

		
		
		Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
		headers.put("Set-Cookie", Arrays.asList("name=value"));
		
		try {
			java.net.CookieHandler.getDefault().put(uri, headers);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail appname/appversion
		webEngine.setUserAgent("Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion appname/appversion");
		webEngine.load("https://www.facebook.com/");
		
		
		getChildren().add(toolBar);
		getChildren().add(browser);

		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				// new page has loaded, process:
				loadComplete();
			}
		});


		browser.addEventHandler(MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                    	if (start_configure) {
                    		c_X_int = (int) e.getScreenX();
                    		c_Y_int = (int) e.getScreenY();
                    		c_X.setText(""+c_X_int);
                    		c_Y.setText(""+c_Y_int);
                    	}
                    }
                });
		
		goToCleanupPage.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				webEngine.load("https://www.facebook.com/1457534434/allactivity/?activity_history=false&category_key=LIKEDINTERESTS&manage_mode=false&should_load_landing_page=false");
			}
		});
		
		configure.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				start_configure = true;

			}
		});
		
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				start_clean=false;
			}
		});  
		
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				start_configure = false;
				start_clean=true;
				refresh();
			}
		});
	
		help.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
		        // Add scene to stage
				Group root = new Group(); 
				Stage helpStage= new Stage();
				Scene helpView = new Scene(root, 840, 600);
				
			    Image image = new Image(getClass().getResourceAsStream("Help.gif"));
		        
		        // simple displays ImageView the image as is
		        ImageView gif = new ImageView();
		        gif.setImage(image);
		        
		         
		        helpView.setFill(Color.BLACK);
		        HBox box = new HBox();
		        box.getChildren().add(gif);
   	            root.getChildren().add(box);
		        
		        
		        helpStage.setScene(helpView);
		        helpStage.setTitle("Help");
		        // Show Stage
		        helpStage.show();
			}
		});
	}
	
		
    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        double tbHeight = toolBar.prefHeight(w);
        layoutInArea(browser,0,0,w,h-tbHeight,0,HPos.CENTER,VPos.CENTER);
        layoutInArea(toolBar,0,h-tbHeight,w,tbHeight,0,HPos.CENTER,VPos.CENTER);
    }
	
}
