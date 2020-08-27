//Puzzle Project Application
//Written by Harrison Kairalla, Jake Bell, and Hudson Herr
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import javax.imageio.*;


public class PPApplication extends Application
{
   public void start(Stage stage)
   {
      //Create flowpane root
      FlowPane root = new FlowPane();
      
      //instantiate PPCanvas object canvas 
      PPCanvas canvas = new PPCanvas();
      
      //add canvas to root flowpane
      root.getChildren().add(canvas);

      //Set scene
      Scene scene = new Scene(root, 800, 800);//sets up window 
      stage.setScene(scene);
      stage.setTitle("Puzzle Project Application");
      stage.show();
   
      canvas.requestFocus();
   }
   
   public class PPCanvas extends Canvas
   {      
      //instantiate graphics context "gc"
      GraphicsContext gc = getGraphicsContext2D();
      
      //x and y vars for moving zack
      int x;
      int y;
      
      PPLevel level;
       
      private String fileName = "test.txt";//starting file name for program
      
      private String levelName = fileName;//instantiate level name
         
      public PPCanvas()   
      {
         level = new PPLevel(levelName);
   
         setWidth(800);//set width
         setHeight(800);//set heigth
         draw(gc);//run draw method
         
         //x and y variables for player
         x = 100;
         y = 100;
         
         /*
   
         //Make a Button
         Button b = new Button("change color");
         b.setOnAction(new ButtonHandler());
   
         //Set up event handler
         setOnKeyPressed(new KeyHandler());
         public class ButtonHandler implements EventHandler<ActionEvent>
         {
            public void handle(ActionEvent e)
            {
               
               draw(gc);            
            }
         }
         */
         
         draw(gc);
      }
     
      public void draw(GraphicsContext gc)//method to draw the level
      {  
         //jakes images 
         Image jukebox_1 = new Image("jukebox_frame_1_small.png");
         Image jukebox_2 = new Image("jukebox_frame_2_small.png");
         
         //set up animation timer
         final long startTime = System.nanoTime();
         new AnimationTimer()
         {
            public void handle( long currentTime)
            {
               double time_past = (currentTime - startTime) / 500000000; //this makes it in seconds
            //jukebox   
            for(int i=0; i<40; i++)
            {
               for(int j=0; j<40; j++)
               {
                  //Jukebox animation (Possible switch here)
                  if(level.getData(i,j) == 22)
                  {
                     if( time_past % 2 == 0 ) //checks if even or odd second, activates if even
                     {
                        gc.drawImage( jukebox_1,j*20,i*20,60,120); //place image
                     }
                     else
                     {
                        gc.drawImage( jukebox_2,j*20,i*20,60,120); //place image
                     }   
                  }
                  //treadmill
                  
                  //etc.
               }
            }
    
            }
                     
         }.start();
   
   
         //hudsons code here
         
         //import images
         Image brick = new Image("1Brick.png");//20
         Image uparrow = new Image("uparrow.png");//21 
          
         for(int i=0; i<40; i++)
         {
            for(int j=0; j<40; j++)
            {
               if(level.getData(i,j) == 0)//if value is 0 draw black square at i,j
               {
                  gc.setFill(Color.BLACK);
                  gc.fillRect(j*20.0, i*20.0, 20, 20);  
               }
               if(level.getData(i,j)==1)//if value is 1 draw white square at i,j
               {
                  gc.setFill(Color.WHITE);
                  gc.fillRect(j*20.0, i*20.0, 20, 20);  
               }
               if(level.getData(i,j)==2)//if value is 2 draw grey square at i,j
               {
                  gc.setFill(Color.GREY);
                  gc.fillRect(j*20.0, i*20.0, 20, 20);  
               }
               if(level.getData(i,j)==12)//if value is 2 draw light grey square at i,j
               {
                  gc.setFill(Color.LIGHTGREY);
                  gc.fillRect(j*20.0, i*20.0, 20, 20);  
               }
               if(level.getData(i,j)==20)//if value is 20 draw brick image at i,j
               {
                  gc.drawImage(brick,j*20,i*20,80,80);
                  
               }
               if(level.getData(i,j)==21)//if value is 21 draw arrow image at i,j
               {
                  gc.drawImage(uparrow,j*20,i*20,40,40);
                  
               }
               /*
               if(level.getData(i,j)==22)//if value is 22 draw jukebox gif at i,j
               {
                  gc.drawImage(jukebox,j*20,i*20,60,120);
                  
               }
               */
   
               gc.setFill(Color.YELLOW);
               gc.fillRect(x, y, 20, 20);
            }
         }
      }
   
      public class KeyHandler implements EventHandler<KeyEvent>
      {
         public void handle(KeyEvent event)
         {
            if(event.getCode() == KeyCode.UP)//if up key is pressed
            {
               y=y-10; //move square up by subtracting y coordinate by 3
            
            }
        
            if(event.getCode() == KeyCode.LEFT)//if left key is pressed
            {
               x=x-10; //move square left by subtracting x coordinate by 3
            
            }
         
            if(event.getCode() == KeyCode.DOWN)//if down key is pressed
            {
               y=y+10; //move square down by adding y coordinate by 3
            
            }
         
            if(event.getCode() == KeyCode.RIGHT)//if right key is pressed
            {
               x=x+10; //move square down by adding y coordinate by 3
            
            }
         draw(gc);
         }  
      }
   }
  
   public class PPLevel
   {
      private String fileName; 
      private int levelData[][] = new int[40][40];
      private String nextData[] = new String[4];
      private int x;
      private int y;
      private int data;
      private int nextLoc;
      private String next;
       
      public PPLevel(String fileName_in)//Constructor with string parameter for file name
      {
         fileName = fileName_in;
         try //Try catch loop to catch fnfe
         {
            File inputFile = new File(fileName);
            Scanner readFile = new Scanner(inputFile);//scanner to read in the file
         
            for(int i = 0; i < 40; i++)//reading values into 2D array list "levelData"
            {
               for(int j = 0; j < 40; j++)
               {
                  levelData[i][j] = readFile.nextInt();
               }
            } 
         }
          catch(FileNotFoundException fnfe)
         {
         }
   
      }
      
      public int getData(int x_in, int y_in)//accessor to get particulat x,y pair
      {
         x = x_in;
         y = y_in;
         data = levelData[x][y];
         return data;
      }
   }
}
