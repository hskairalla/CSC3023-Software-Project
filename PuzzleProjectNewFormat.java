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

   //menu flag
   int flag = 0;
   
   //universal vars
   
   String fileName;
   
   
   public void start(Stage stage)
   {
      
      //objectArray = new Object[40][40];
      
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
     
     
      //maybe move these 2 thing?
      fileName = "test_format_2.txt";
      
            

      //Show scene
      stage.show();
   
      //canvas.requestFocus();
      
      
      
   }
    
   
   public class PPCanvas extends Canvas
   {
   
      //instantiate graphics context "gc"
      GraphicsContext gc = getGraphicsContext2D();
      
      //instantiat level object
      PPLevel level;
      
      //x and y vars for moving zack
      int x;
      int y;

      private String fileName = "test_format_2.txt"; //starting file name for program
      
      private String levelName = fileName; //instantiate level name
      
      public PPCanvas()   
      {
         setWidth(800);//set width
         setHeight(800);//set heigth
         
         //x and y variables for player
         x = 100;
         y = 100;
         
         
         
         
         //animation timer to handle animated objects
         final long startTime = System.nanoTime();
         new AnimationTimer()
         {
            public void handle( long currentTime)
            {
               double half_second = (currentTime - startTime) / 500000000; //this makes it in half seconds
               
               level.loadData();
               
               /*
               if( half_second % 1 == 0 ) //activates every half second 
               {
                  //read text file to create 2D object array (load data)
                  loadData(fileName);
                  
                  //check for key press, update player movement 
                  //draw(gc);
               }*/
               
               //check if character has done a switch press (update data)
               
               //call draw() on updated data (draw data)
               
               //save data back to text file -> Maybe use dictionary? <- (save data)
            
            
            }
         }.start();
     
      }    
   }
   
   public class PPLevel
   {
      //Object[][] objectArray;;
      
      //try with 2d arraylist
      ArrayList<ArrayList<Object>> objectArrayList;
      
      
      public PPLevel()   
      {
         //set up 2d object array
         //objectArray = new Object[40][40];
         
         //arraylist
         objectArrayList = new ArrayList<>(40);
         for( int i=0; i< 40; i++)
         {
            objectArrayList.add( new ArrayList() );
         }
         
      }
      
      //load method to loadData the data from a .txt file
      public void loadData()
      {
         fileName = "test_format_2.txt";
         
         
         
         try //Try catch loop to catch fnfe
         {
            File inputFile = new File(fileName);
            Scanner read = new Scanner(inputFile);//scanner to read in the file
   
            for( int i=0; i<40; i++)
            {
               for( int j=0; j<40; j++)
               {
                  //read next num from file
                  String value = read.next();
                  
                  //separate the num into an arraylist
                  List<Character> valueList = new ArrayList<>();
                  for( int k=0; k< value.length(); k++)
                  {
                     valueList.add(value.charAt(k));
                  }
                  
                  //create Object based on the first # in list, then use value as the arguments for the Object
                  char firstNum = valueList.get(0);
                  switch( firstNum ) 
                  {
                     case '1': //the value is a Tile
                        objectArrayList.get(i).add( new Tile( valueList) );
                        //objectArray[i][j] = new Tile( valueList); 
                        //System.out.print(objectArray[i][j].getValue() + " "); 
                        break;
                     default:
                        objectArrayList.get(i).add( new Tile( valueList) );
                        //objectArray[i][j] = new Tile(valueList); 
                        //System.out.print(objectArray[i][j].getValue() + " ");
                  }
               }
              System.out.println();
            }
            
         }
         catch(FileNotFoundException fnfe)
         {
            System.out.println( "File " + fileName + " not found"); 
         }
         
         //check that the code functions properly
         //System.out.print(objectArray[0][0].getValue() + " ");
         
         for( int i=0; i<40; i++)
         {
            for( int j=0; j<40; j++)
            {
               //System.out.print(objectArray[i][j].getValue() + " ");
            }
            //System.out.println();
         }
         
      }

      //draw method to draw the level to the screen
      
      //write method to write the level back to the .txt file
   
   }
   
   
   public void draw(GraphicsContext gc)
   {
      //read from the 2D object array and based on data draw the level
      gc.fillRect( 10, 10, 30, 30);
   
   }
   /*
   public void loadData( String fileName_in)
   {
      //given file name, read in the .txt file into the 2D object array
      String fileName = fileName_in;
      
      //set up temp object for filling up the 2d array
      //Object temp;
      
      System.out.println("Load() called");
      
      try //Try catch loop to catch fnfe
      {
         File inputFile = new File(fileName);
         Scanner read = new Scanner(inputFile);//scanner to read in the file
         
         
         //read the text file into the Object Array
         for(int i=0; i<40; i++)
         {
            for(int j=0; j<40; j++)
            {
               int value = read.nextInt();
               
               //System.out.print( value + " "); //testing
               
               //turn "value" into a list of each individual #
               List<Character> valueList = new ArrayList<>();
               for( char ch : Integer.toString(value).toCharArray())
               {
                  valueList.add(ch);
               }
               
               //generic Object to be placed into the Object array
               //Object temp;
               
               switch( valueList.get(0) ) //create object type based on the first # in list
               {
                  case '1': //the value is a Tile
                     
                     temp = new Tile(valueList);
                     System.out.println("add attempt");
                     objectArray[i][j] = temp; 
                     System.out.println(objectArray[i][j]); 
                     break;
                     
                  case '2': //the value is a RaisedTile
                  
                     temp = new RaisedTile(valueList);
                     objectArray[i][j] = temp;
                     break;
                  
                  case '3': //the value is a Wall
                     temp = new Wall(valueList);
                     objectArray[i][j] = temp;
                     break; 
                  
                  case '4': //the value is a Spike
                     temp = new Spike(valueList);
                     objectArray[i][j] = temp;
                     break;
                  
                  case '5': //the value is a Pressure Plate
                     temp = new PressurePlate(valueList);
                     objectArray[i][j] = temp;  
                     break;
                  
                  case '6': //the value is a Spring
                     temp = new Spring(valueList);
                     objectArray[i][j] = temp;
                     break;
                  
                  case '7': //the value is a Gate
                     temp = new Gate(valueList);
                     objectArray[i][j] = temp; 
                     break; 
                  
                  case '8': //the value is a Portal
                     temp = new Portal(valueList);
                     objectArray[i][j] = temp;
                     break;
                  
                  case '9': //the value is Miscellaneous (Misc)
                     temp = new Misc(valueList);
                     objectArray[i][j] = temp;
                     break;     
               }
             
            }
            //System.out.println(); //testing
         }        
         //end nested for loops  
      }
       catch(FileNotFoundException fnfe)
      {
         System.out.println( "File " + fileName + " not found"); 
      }
      
      System.out.println("Load done");
   }
   */
   
   public class KeyHandler implements EventHandler<KeyEvent>
   {
      public void handle(KeyEvent event)
      {
         //based on arrow key press, move player
         
         //check for collision
         //checkCollision(player);
          
      }
      
      //call draw() method
   }
   
      
   //not needed anymore
   /*
   public class PPLevel
   {
      //vars to read in numerical level data
       
      public PPLevel(String fileName_in)
      {
         //use a file reader and nested for loops to go through file
      }
      
     
      public int getData(int x_in, int y_in)
      {
         //accessor to get level data at specific point
         return 1;
      }
   }
   */
   
   public class ButtonHandler implements EventHandler<ActionEvent>
   {
      public void handle( ActionEvent e)
      {  
         //if menu button pressed, remove menu
      }
   }
   
   
   
      
   
   
   
   
   //Inheritance Section:
   
   public abstract class Object
   {
      protected int height = 20;
      protected int width = 20;
   
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
      
      Image emptySquare = new Image("Empty_Square.jpg");//100
      //Image whiteSquare = new Image("White_Square.png");//101 NOT NEEDED
      //Image blackSquare = new Image("Black_Square.png");//102 NOT NEEDED

      Image whiteTile = new Image("White_Tile.png");//103
      Image blackTile = new Image("Black_Tile.png");//104
      Image metalTile = new Image("Metal_Tile.png");//105
      Image greyTile = new Image("Grey_Tile.png");//106
      Image greyTile4 = new Image("Grey_Tile4.png");//107
      Image brick = new Image("1Brick.png");//110 
      //Image greyBrick = new Image("greybrick.png");//111
      
      Image jukebox_1 = new Image("jukebox_frame_1_small.png");//901
      Image jukebox_2 = new Image("jukebox_frame_2_small.png");//902
         
      
      String colorNum;
      String value;
      Image image = emptySquare;
      
      
      public Tile( List<Character> valueList)
      {
         //Figure out the tile color based on the 2 & 3rd list numbers and reassemble the list to get "value"
         value = ""+valueList.get(0) + valueList.get(1)+valueList.get(2);
         colorNum = ""+valueList.get(0) + valueList.get(1);
         
         switch( colorNum)
         {
            case "100": //empty tile
               image = emptySquare;
               break;
               
            case "103": //white tile
               image = whiteTile;
               break;
            case "104": //black tile
               image = blackTile;
               break;
            case "105": //metal tile
               image = metalTile;
               break;
            case "106": //grey tile
               image = greyTile;
               break;
            case "107": //grey tile4
               image = greyTile4;
               break;
            case "110": //brick tile
               image = brick;
               break;
            case "111": //grey brick tile
               //image = greyBrick;
               break;
            /*
            case "101": //white square
               image = whiteSquare;
               break;
            case "102": //grey square
               image = greySquare;
               break;
               
            case "108": //half metal tile
               image = halfMetalTile;
               break;
            case "109": //half grey tile
               image = greyGrey4;
               break;
            */
         }
         
         //System.out.println( valueList.get(0) + " " + valueList.get(1) + " " + valueList.get(2));
               
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
      String value;
      Image image;
      public RaisedTile( List<Character> valueList_in )
      {
       
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
      String value;
      Image image;
      public Spike( List<Character> valueList_in )
      {
       
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
      String value;
      Image image;
      public PressurePlate( List<Character> valueList_in )
      {
       
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
      String value;
      Image image;
      public Spring( List<Character> valueList_in )
      {
       
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
      String value;
      Image image;
      public Wall( List<Character> valueList_in )
      {
       
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
      String value;
      Image image;
      public Portal( List<Character> valueList_in )
      {
       
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
      String value;
      Image image;
      public Gate( List<Character> valueList_in )
      {
       
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
      String value;
      Image image;
      public Misc( List<Character> valueList_in )
      {
       
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
   
   
}
