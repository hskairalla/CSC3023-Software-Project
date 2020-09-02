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
   //instantiate the canvas object for the levels as well as flowpan
   PPCanvas canvas = new PPCanvas();
   FlowPane fp = new FlowPane();
   
   //set up buttons
   Button b1 = new Button("Start");
   Button b2 = new Button("Load");
   
   
   //menu flag
   int flag = 0;
   
   
   
   public void start(Stage stage)
   {
      //set up root
      Group root = new Group();
      
      Scene scene = new Scene( root);
      stage.setScene( scene );
      stage.setTitle( "Puzzle Project" );
     
      
      //set up flow pane for menu
      
      fp.setBackground(new Background( new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
      
      //add stuff to root group
      root.getChildren().add(canvas);
      root.getChildren().add(fp);
  
      // create buttons
      b1.setPrefSize(150, 75);
      b2.setPrefSize(150, 75);
      
      fp.getChildren().add(b1);
      fp.getChildren().add(b2);
      
      
      b1.setFocusTraversable(false);
      b1.setOnAction( new ButtonHandler() );
      
      b2.setFocusTraversable(false);
      b2.setOnAction( new ButtonHandler() );
     
      
            

      //Show scene
      stage.show();
   
      //canvas.requestFocus();

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
                  
                  draw(gc);
               }
            }
         }.start();

         
         
         
         //draw(gc);
      }
     
      public void draw(GraphicsContext gc)//method to draw the level
      {    
           
         //import images

         //Image background = new Image("background.jpg");
         Image whiteTile = new Image("White_Tile.png");//03
         Image blackTile = new Image("Black_Tile.png");//04
         Image metalTile = new Image("Metal_Tile.png");//05
         Image greyTile = new Image("Grey_Tile.png");//06
         Image greyTile4 = new Image("Grey_Tile4.png");//07
         Image springTile = new Image("springTile.png");//10
         Image uparrow = new Image("uparrow.png");//21          
         Image brick = new Image("1Brick.png");//20 
         //23
         Image button1 = new Image("button.jpg");//24
         Image purpleBU = new Image("Purple_BU.png");//30
         Image orangeBU = new Image("Orange_BU.png");//31
         Image yellowBU = new Image("Yellow_BU.png");//32
         Image blueBU = new Image("Blue_BU.png");//33
         Image purpleSpike = new Image("purpleSpike.png");//40
         Image orangeSpike = new Image("orangeSpike.png");//41
         Image yellowSpike = new Image("yellowSpike.png");//42
         Image blueSpike = new Image("blueSpike.png");//43
         Image blueSpikeR = new Image("blueSpikeR.png");//44
         Image blueHoleR = new Image("blueHoleR.png");//45
         Image blueHole = new Image("blueHole.png");//46
         Image yellowHole = new Image("yellowHole.png");//47
          
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
               if(level.getData(i,j)==3)//if value is 03 draw white tile at i,j
               {
                  gc.drawImage(whiteTile,j*20,i*20,80,80);               
               }
               if(level.getData(i,j)==4)//if value is 04 draw black tile at i,j
               {
                  gc.drawImage(blackTile,j*20,i*20,80,80);               
               }
               if(level.getData(i,j)==5)//if value is 05 draw metal tile at i,j
               {
                  gc.drawImage(metalTile,j*20,i*20,80,80);               
               }
               if(level.getData(i,j)==6)//if value is 06 draw grey tile at i,j
               {
                  gc.drawImage(greyTile,j*20,i*20,80,80);               
               }
               if(level.getData(i,j)==7)//if value is 07 draw 4 grey tile at i,j
               {
                  gc.drawImage(greyTile4,j*20,i*20,160,160);               
               }
               if(level.getData(i,j)==8)//if value is 08 draw half metal tile at i,j
               {
                  gc.drawImage(metalTile,j*20,i*20,80,40);               
               }
               if(level.getData(i,j)==9)//if value is 09 draw half grey tile at i,j
               {
                  gc.drawImage(greyTile,j*20,i*20,40,80);
               }
               if(level.getData(i,j)==10)//if value is 10 draw spring tile at i,j
               {
                  gc.drawImage(springTile,j*20,i*20,80,80);
               }
               if(level.getData(i,j)==12)//if value is 12 draw light grey square at i,j
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
                  gc.drawImage(uparrow,j*20,i*20,40,60);
                  
               }  
               }
               if(level.getData(i,j)==24)//if value is 24 draw button at i,j
               {
                  gc.drawImage(button1,j*20,i*20,80,80);
                  
               }
               if(level.getData(i,j)==30)//if value is 30 draw unpressed purple button at i,j
               {
                  gc.drawImage(purpleBU,j*20,i*20,80,80);
                  
               }
               if(level.getData(i,j)==31)//if value is 31 draw unpressed orange button at i,j
               {
                  gc.drawImage(orangeBU,j*20,i*20,80,80);
                  
               }
               if(level.getData(i,j)==32)//if value is 32 draw unpressed yellow button at i,j
               {
                  gc.drawImage(yellowBU,j*20,i*20,80,80);
                  
               }
               if(level.getData(i,j)==33)//if value is 33 draw unpressed blue button at i,j
               {
                  gc.drawImage(blueBU,j*20,i*20,80,80);  
               }
               if(level.getData(i,j)==34)//if value is 34 draw stetched unpressed purple button at i,j
               {
                  gc.drawImage(purpleBU,j*20,i*20,80,120);   
               }
               if(level.getData(i,j)==35)//if value is 35 draw stetched unpressed orange button at i,j
               {
                  gc.drawImage(orangeBU,j*20,i*20,80,120); 
               }
               if(level.getData(i,j)==36)//if value is 36 draw stetched unpressed yellow button at i,j
               {
                  gc.drawImage(yellowBU,j*20,i*20,80,120);   
               }
               if(level.getData(i,j)==37)//if value is 37 draw stetched unpressed blue button at i,j
               {
                  gc.drawImage(blueBU,j*20,i*20,80,120); 
               }   
               if(level.getData(i,j)==40)//if value is 40 draw purple spikes at i,j
               {
                  gc.drawImage(purpleSpike,j*20,i*20,80,20); 
               }
               if(level.getData(i,j)==41)//if value is 41 draw orange spikes at i,j
               {
                  gc.drawImage(orangeSpike,j*20,i*20,80,20); 
               }
               if(level.getData(i,j)==42)//if value is 42 draw yellow spikes at i,j
               {
                  gc.drawImage(yellowSpike,j*20,i*20,80,20); 
               }
               if(level.getData(i,j)==43)//if value is 43 draw blue spikes at i,j
               {
                  gc.drawImage(blueSpike,j*20,i*20,80,20); 
               }
               if(level.getData(i,j)==44)//if value is 44 draw blue spike rotated at i,j
               {
                  gc.drawImage(blueSpikeR,j*20,i*20,20,80); 
               }
               if(level.getData(i,j)==45)//if value is 45 draw blue hole rotated at i,j
               {
                  gc.drawImage(blueHoleR,j*20,i*20,20,80); 
               }
               if(level.getData(i,j)==46)//if value is 46 draw blue hole at i,j
               {
                  gc.drawImage(blueHole,j*20,i*20,80,20); 
               }
               if(level.getData(i,j)==47)//if value is 47 yellow blue hole at i,j
               {
                  gc.drawImage(yellowHole,j*20,i*20,80,20); 
               }
               
               gc.setFill(Color.YELLOW);
               gc.fillRect(x, y, 20, 20);
               
               //if button not pressed cover everything
               if( flag == 0)
               {
                  
                  gc.setFill(Color.BLACK);
                  gc.fillRect( 0, 0, 800, 800);
                  
                  gc.setFill(Color.WHITE);
                  gc.fillText( "Contraption Zack!!!!", 100, 100);
                  gc.fillText( "BY Harry, Hudson, and Jake", 500, 500);
                  //gc.drawImage(background, 0,0,800,800);
                   
               }
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
   
   
   //button handler class
   public class ButtonHandler implements EventHandler<ActionEvent>
   {
      public void handle( ActionEvent e)
      {                           
         //change flag to 1
         flag = 1;
         
         //clears everything, including buttons
         fp.getChildren().clear();

         
         //if button is pressed
         //if (e.getSource() == b1)
            //System.out.println("start");
         //else if(e.getSource() == b2)
            //System.out.println("load");
        
      }
   }
}
