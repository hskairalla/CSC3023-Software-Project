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

public class PuzzleProjectNewFormat extends Application
{

   
   //canvas stuff
   PPCanvas canvas = new PPCanvas();
   FlowPane fp = new FlowPane();

   
   //set up buttons
   Button b1 = new Button("Start");
   Button b2 = new Button("Load");
   
   /////////////////////////////////////////////
   int x = 350;
   int y = 600;
   int z = 0;
   boolean a = false; 
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
   
      canvas.requestFocus();     
   }
    

   public class PPCanvas extends Canvas
   {
      //instantiate graphics context "gc"
      GraphicsContext gc = getGraphicsContext2D();
      
      //starting file name for program
      //String fileName = "test_format_2.txt";

      //levelName = "menu"; //instantiate level name
      
      //instantiate level object
      PPLevel level = new PPLevel();
      
      /////////////////////////////////////////////////////
      //x and y vars for moving character
      //int x;
      //int y;
      /////////////////////////////////////////////

      
      public PPCanvas()   
      {
         setWidth(800);//set width
         setHeight(800);//set heigth
         
         ///////////////////////////////////////////////////////// 
         //Set up event handler
         setOnKeyPressed(new KeyHandler());  
         
         //x and y vars for moving zack
         x = 200;
         y = 200;
         /////////////////////////////////////////////////////////         

         //level.loadData();
         //level.draw(gc);
         
         
         
         
         //animation timer to handle animated objects
         final long startTime = System.nanoTime();
         new AnimationTimer()
         {
            public void handle( long currentTime)
            {
               double half_second = (currentTime - startTime) / 500000000; //this makes it in half seconds
               
               
               
               
               if( half_second % 1 == 0 ) //activates every half second 
               {
                  //read text file to create 2D object array (load data)
                  level.loadData();
                  
                  //check for key press, update player movement 
                  level.draw(gc);
                  
                  //level.updateData();
                  
                  //level.writeData();
               }
               
               
               //check if character has done a switch press (update data)
               
               //call draw() on updated data (draw data)
               
               //save data back to text file -> Maybe use dictionary? <- (save data)
               level.draw(gc);
            
            }
         }.start();
      
      } 
      public PPLevel getLevel()
      {
         return level;
      }
            
   }
   
   public class PPLevel
   {
      Object[][] objectArray;
      String levelName = "menu"; //start game on menu
      
      public PPLevel()   
      {
         //set up 2d object array
         objectArray = new Object[40][40];

         //System.out.println( "Debug 1");
         
      }
      
      //load method to loadData the data from a .txt file
      public void loadData()
      {
         if( levelName != "menu")
         {
            //System.out.println( "Debug 2");
            
            try //Try catch loop to catch fnfe
            {
               File inputFile = new File(levelName);
               Scanner read = new Scanner(inputFile);//scanner to read in the file
      
               for( int i=0; i<40; i++)
               {
                  for( int j=0; j<40; j++)
                  {
                     //read next num from file
                     String value = read.next();
                     
                     
                     
                     //create Object based on the first # in list, then use value as the arguments for the Object
                     switch( value.charAt(0) ) 
                     {
                        case '1': //value is a Tile
                           objectArray[i][j] = new Tile(value);
                           break;
                        case '2': //value is RaisedTile
                           objectArray[i][j] = new RaisedTile(value);
                           break;
                        case '3': //value is Wall
                           objectArray[i][j] = new Wall(value);
                           break;
                        case '4': //value is Spike
                           objectArray[i][j] = new Spike(value);
                           break;
                        case '5': //value is PressurePlate
                           objectArray[i][j] = new PressurePlate(value);
                           break;
                        case '6': //value is Spring
                           objectArray[i][j] = new Spring(value);
                           break;
                        case '7': //value is Gate
                           objectArray[i][j] = new Gate(value);
                           break;
                        case '8': //value is Portal
                           objectArray[i][j] = new Portal(value);
                           break;
                        case '9': //value is Misc.
                           objectArray[i][j] = new Misc(value);
                           break;  
                     }
                     //System.out.print( objectArray[i][j].getValue() + " "); //for debugging
                  }
                 //System.out.println(); //for debugging
               }
               //System.out.println( "Debug 3");
            }
            catch(FileNotFoundException fnfe)
            {
               System.out.println( "File " + levelName + " not found"); 
            }
         }
         
         else
         {
            //System.out.println( "Menu active");
         }
      }

      
      
      
      //draw method to draw the level to the screen
      public void draw(GraphicsContext gc)
      {
         
         if( levelName != "menu")
         {
            //System.out.println( "in draw method");
            //read from the 2D object array and based on data draw the level
            
            for( int i=0; i<40; i++)
            {
               for( int j=0; j<40; j++)
               {
                  int height = objectArray[i][j].getHeight();
                  int width = objectArray[i][j].getWidth();
                  
                  //to cover for null space
                  if( objectArray[i][j].getValue().equals("100") )
                  {
                     //System.out.println("100 value found");
                     gc.setFill(Color.BLACK);  
                     gc.fillRect(20*j, 20*i, height, width);               
                  }
                  else
                  {
                     gc.drawImage( objectArray[i][j].getImage(), 20*j, 20*i, height, width);
                  }
               }
            }
            ///////////////////////////////////////////////
            gc.setFill(Color.WHITE);
            // gc.fillRect(x, y, 40, 40);
            if(z == 4)
            {
               if(a == true)
               {
                  Image moveRight1 = new Image("moveRight1.png");// character moving right 1
                  gc.drawImage(moveRight1, x, y, 40, 40);
               }
               else
               {
                  Image moveRight2 = new Image("moveRight2.png");// character moving right 2
                  gc.drawImage(moveRight2, x, y, 40, 40);
               }
               
            }
            if(z == 2 || z == 3)
            {
               if(a == true)
               {
                  Image moveLeft1 = new Image("moveLeft1.png");// character moving left 1
                  gc.drawImage(moveLeft1, x, y, 40, 40);
               }
               else
               {
                  Image moveLeft2 = new Image("moveLeft2.png");// character moving left 2
                  gc.drawImage(moveLeft2, x, y, 40, 40);
               }
               

            }
            if(z == 1)
            {
               if(a == true)
               {
                  Image moveUp1 = new Image("moveUp1.png");// character moving up 1
                  gc.drawImage(moveUp1, x, y, 40, 40);
               }
               else
               {
                  Image moveUp2 = new Image("moveUp2.png");// character moving up 2
                  gc.drawImage(moveUp2, x, y, 40, 40);
               }
            }
            if(z == 0)
            {
               Image moveLeft1 = new Image("moveLeft1.png");// character moving left 1
               gc.drawImage(moveLeft1, x, y, 40, 40);
            }
            ///////////////////////////////////////////////
         }
         else
         {
            //System.out.println( "no draw, menu open");
            //set up menu 
            gc.setFill(Color.BLACK);
            gc.fillRect( 0, 0, 800, 800);
            Image background = new Image("background.jpg");
            gc.drawImage(background, 100, 100, 600, 700);
            gc.setFill(Color.WHITE);
            gc.fillText( "BY Harry, Hudson, and Jake", 500, 500);
         }
      
      }
      /*
      //update method to update values
      public void updateData()
      {
         for( int i=0; i<40; i++)
         {
            for( int j=0; j<40; j++)
            {
               String value = objectArray[i][j].getValue();
               String first2Chars = ""+value.charAt(0) + value.charAt(1);
               String frameNum = ""+ value.charAt(2);
               
               if( first2Chars.equals("90") )
               {
                  //objectArray[i][j].nextFrame(); //move jukebox to next frame
                  if( frameNum.equals("1") )
                     frameNum = "2";
                  else
                     frameNum = "1";
                     
               } 
             }  
         }
         
      }
      
      //write method to write the level back to the .txt file
      public void writeData()
      {
      
      }
      */
      
      
      //accessor to get data at specific point
      public int getData(int x_in, int y_in)
      {
         //accessor to get level data at specific point
         return 1;
      }
      
      public void setLevelName( String levelName_in)
      {
         levelName = levelName_in;
         System.out.println("level changed to: " + levelName);
      }
      
   }
   
   
   //key & button handlers
   

   public class KeyHandler implements EventHandler<KeyEvent>
   {
      public void handle(KeyEvent event)
      {
         ////////////////////////////////////////////////////////////////////////
         if(event.getCode() == KeyCode.UP)//if up key is pressed
         {
            y=y-3; //move square up by subtracting y coordinate by 3
            System.out.println("up "+y);
            z=1;
         }
      
         if(event.getCode() == KeyCode.LEFT)//if left key is pressed
         {
            x=x-3; //move square left by subtracting x coordinate by 3
            System.out.println("left "+x);
            z=2;
         }
         if(event.getCode() == KeyCode.DOWN)//if down key is pressed
         {
            y=y+3; //move square down by adding y coordinate by 3 
            System.out.println("down "+y);
            z=3;
         }
      
         if(event.getCode() == KeyCode.RIGHT)//if right key is pressed
         {
            x=x+3; //move square down by adding y coordinate by 3
            System.out.println("right "+x);
            z=4;
         }
         if(a == true)
         {
          a = false;
         }
         else
         a = true;

         ////////////////////////////////////////////////////////////////////////
         
     }
         //based on arrow key press, move player
         
         //check for collision
         //checkCollision(player);
      //call draw() method?
   }
 
   public class ButtonHandler implements EventHandler<ActionEvent>
   {
      public void handle( ActionEvent e)
      {  
         //if menu button pressed, change level from menu to level 1
         System.out.println( "button press");
         
         canvas.getLevel().setLevelName( "jukebox_room.txt");
         fp.getChildren().clear();
         
      }
   }
   
   
   
      
   
   
   
   
   //Inheritance Section:
   
   public abstract class Object
   {
      String value;
      int height = 20; //protected?
      int width = 20;
      Image image;
   
      public Object()
      {
      }
      
      //abstract methods
      public abstract String getValue();
      public abstract Image getImage();
      public abstract int getHeight();
      public abstract int getWidth();
      
   }
   
   
   public class Tile extends Object
   {  
      public Tile(String value_in )
      {
         //set up string 'value'
         value = value_in;
         
         switch( value)
         {
            case "100": //empty tile
               //Image emptySquare = new Image("Empty_Square.jpg");//100
               //image = emptySquare;
               break;
            case "101": //white square
               Image whiteSquare = new Image("White_Square.jpg");//101
               image = whiteSquare;
               break;
            case "102": //black/grey square
               Image blackSquare = new Image("Black_Square.jpg");//102
               image = blackSquare;
               break;
            case "103": //white tile
               Image whiteTile = new Image("White_Tile.png");//103
               image = whiteTile;
               height = 80;
               width = 80;
               break;
            case "104": //black tile
               Image blackTile = new Image("Black_Tile.png");//104
               image = blackTile;
               height = 80;
               width = 80;
               break;
            case "105": //metal tile
               Image metalTile = new Image("Metal_Tile.png");//105
               image = metalTile;
               break;
            case "106": //grey tile
               Image greyTile = new Image("Grey_Tile.png");//106
               image = greyTile;
               break;
            case "107": //grey tile4
               Image greyTile4 = new Image("Grey_Tile4.png");//107
               image = greyTile4;
               break;
            case "110": //brick tile
               Image brick = new Image("1Brick.png");//110
               image = brick;
               break;
            case "111": //grey brick tile
               //image = greyBrick;
               break;
            /*   
            case "108": //half metal tile
               image = halfMetalTile;
               break;
            case "109": //half grey tile
               image = greyGrey4;
               break;
            */
         }
              
      }
      
      public String getValue() //returns original value #
      {
         return value;
      }
      public Image getImage() //returns corresponding image
      {
         return image;
      }
      public int getHeight() //returns height (used in draw() method)
      {
         return height;
      }
      public int getWidth() //returns width (used in draw() method)
      {
         return width;
      }
      
      
   }
   
   public class RaisedTile extends Object
   {
      
      public RaisedTile( String value_in )
      {
         value = value_in;
         
         switch( value)
         {
            case "202": //metal tile
               Image metalTile = new Image("Metal_Tile.png");//202
               image = metalTile;
               height = 80;
               width = 80;
               break;
         
         }
      }
      
      
      public String getValue()
      {
         return value;
      }
      public Image getImage()
      {
         return image;
      }
      public int getHeight()
      {
         return height;
      }
      public int getWidth()
      {
         return width;
      }
   }
   
   public class Spike extends Object
   {
      
      public Spike( String value_in )
      {
         value = value_in;
      }
      
      
      public String getValue()
      {
         return value;
      }
      public Image getImage()
      {
         return image;
      }
      public int getHeight()
      {
         return height;
      }
      public int getWidth()
      {
         return width;
      }
   }
   
   public class PressurePlate extends Object
   {
      public PressurePlate( String value_in )
      {
         value = value_in;
      }
      
      
      public String getValue()
      {
         return value;
      }
      public Image getImage()
      {
         return image;
      }
      public int getHeight()
      {
         return height;
      }
      public int getWidth()
      {
         return width;
      }
   }
   
   public class Spring extends Object
   {
      
      public Spring( String value_in )
      {
         value = value_in;
      }
      
      
      public String getValue()
      {
         return value;
      }
      public Image getImage()
      {
         return image;
      }
      public int getHeight()
      {
         return height;
      }
      public int getWidth()
      {
         return width;
      }
   }
   
   public class Wall extends Object
   {
      public Wall( String value_in )
      {
         value = value_in;
         
         switch(value)
         {
            case "202": //empty tile
               Image metalTile = new Image("Metal_Tile.png");//2020
               image = metalTile;
               height = 80;
               width = 80;
               break;
         }
      }
     
      public String getValue()
      {
         return value;
      }
      public Image getImage()
      {
         return image;
      }
      public int getHeight()
      {
         return height;
      }
      public int getWidth()
      {
         return width;
      }
   }
   
   public class Portal extends Object
   {
      String connectingRoom;
      
      public Portal( String value_in )
      {
         value = value_in;
         height = 40;
         width = 40;
         
         String first2Chars = ""+value.charAt(0) + value.charAt(1);
         
         switch( first2Chars)
         {
            case "80": //Up Portal
               Image upPortal = new Image("uparrow.png");//21
               image = upPortal;
               break;
         }
      }
      
      
      public String getValue()
      {
         return value;
      }
      public Image getImage()
      {
         return image;
      }
      public int getHeight()
      {
         return height;
      }
      public int getWidth()
      {
         return width;
      }
   }
   
   public class Gate extends Object
   {
      
      public Gate( String value_in )
      {
         value = value_in;
      }
      
      public String getValue()
      {
         return value;
      }
      public Image getImage()
      {
         return image;
      }
      public int getHeight()
      {
         return height;
      }
      public int getWidth()
      {
         return width;
      }
   }
   
   public class Misc extends Object
   {
      String frameNum;
      
      public Misc( String value_in )
      {
         value = value_in;
         
         String first2Chars = ""+value.charAt(0) + value.charAt(1); //only use 2 chars to allow variability in frame counter
         
         frameNum =  ""+value.charAt(2);
         
         switch( first2Chars )
         {
            case "90": //jukebox
               height = 80;
               width = 80;
               switch( frameNum ) //now pick image for which frame its on
               {
                  case "1":
                     Image jukebox_1 = new Image("jukebox_frame_1_small.png");
                     image = jukebox_1;
                     break;
                  case "2":
                     Image jukebox_2 = new Image("jukebox_frame_2_small.png");
                     image = jukebox_2;
                     break;      
               }
               break;
            //case: 999 //for other misc in the future
         }
      }
      
      
      public String getValue()
      {
         return value;
      }
      public Image getImage()
      {
         return image;
      }
      public int getHeight()
      {
         return height;
      }
      public int getWidth()
      {
         return width;
      }
      public void nextFrame() //moves animation to next frame
      {
         if( frameNum.equals("1") )
            frameNum = "2";
         else
            frameNum = "1";
      }
   }
   
   
}
