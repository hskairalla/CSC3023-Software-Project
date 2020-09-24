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
   //Instantiate canvas and FlowPane
   PPCanvas canvas = new PPCanvas();
   FlowPane fp = new FlowPane();
   
   
   //set up buttons
   Button b1 = new Button("New Game");// Button to start the game
   Button b2 = new Button("Load");// Button to load the game
   Button b3 = new Button("Restart Area");// Button to restart the area/room
   Button b4 = new Button("Restart Level");//Button to restart the level
   Button b5 = new Button("Save");//Button to save area
   Button b6 = new Button("Menu");//Button to send you back to the menu
   
   int x = 17; //int x for keeping track of player movement in the x (Start at x=16)(Pixel location is times 20)
   int y = 29; //int y for keeping track of player movement in the y (Start at y=16)(Pixel location is times 20)
   int z = 0; //int z for keeping track of the direction the player is facing
   boolean a = false; //boolean used for animating player movement (Switches between 2 states)
   //booleans that tell the key handler whether the player can move or not (collison control
   boolean up = true;
   boolean down = true;
   boolean left = true;
   boolean right = true;
   boolean addMenuButtons = false;
   
   String currentLevelLoad;
   String levelName = "menu"; //start game on menu
   
   
   
   public void start(Stage stage)
   {
      //set up root
      Group root = new Group();
      Scene scene = new Scene( root );
      stage.setScene( scene );
      stage.setTitle( "Puzzle Project" );
     
      //set up flow pane for menu
      fp.setPrefWrapLength( 300);
      fp.setBackground(new Background( new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
      
      //add Canvas and FlowPane to "root" group
      root.getChildren().add(canvas);
      root.getChildren().add(fp);
  
      //set size of buttons and add them to the FlowPane
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
      GraphicsContext gc = getGraphicsContext2D(); //Instantiate gc object
      PPLevel level = new PPLevel(); //instantiate PPLevel object
      int count = 0;
      
      public PPCanvas()   
      {
         setWidth(800);//set Canvas width
         setHeight(800);//setCanvas height
          
         setOnKeyPressed(new KeyHandler());//Set up the KeyHandler 
         
         /*
         level.loadData();
         level.draw(gc);
         level.writeData(); 
         */ 
         
         final long startTime = System.nanoTime();//Used for starting time within the animation
         new AnimationTimer() // Animation timer for animated objects 
         {
            public void handle( long currentTime)
            {
               level.loadData();
               level.draw(gc);
               level.writeData();
               
               //adds start and load button to menu
               if(addMenuButtons == true)
               {  
                  fp.getChildren().clear();
                  
                  b1.setPrefSize(150, 75);
                  b2.setPrefSize(150, 75);
                  fp.getChildren().add(b1);
                  fp.getChildren().add(b2);
                  
                  b1.setFocusTraversable(false);
                  b1.setOnAction( new ButtonHandler() );
                  b2.setFocusTraversable(false);
                  b2.setOnAction( new ButtonHandler() );
                  
                  addMenuButtons = false;
               }
               
               
               
                
               // NOTE: MIGHT MAKE COLLISION DETECTION DIFFICULT TO ONLY DO EVERY 10 ITERATIONS, so not doing it
               if( count % 20 == 0 ) //only runs code every 20 iterations of timer
               {
                  
                  //System.out.println( count/20);
                  level.updateData();           
                  
               }
               count ++;
               
               //check if character has done a switch press (update data)
               
               //call draw() on updated data (draw data)
               
               //save data back to text file -> Maybe use dictionary? <- (save data)
               
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
      //data structures to hold level data, no constructor needed
      Object[][] objectArray = new Object[40][40];
      String [] connectingRoomsArray = new String[4];
      String[][] resetLevel = new String[40][40];
      
      //String levelName = "menu"; //start game on menu
      String outputFile = "outputFile.txt"; //starting output file
      
      /*
      int x; //player x coordinate var
      int y; //player y coordinate var      
      int z=0; //int z for keeping track of the direction the player is facing
      boolean a=false; //boolean used for animating player movement (Switches between 2 states)
      */
      
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
               
               //read in object data
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
                           objectArray[i][j] = new ObjectCollision(value);
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
               
               //now read in the portal connections array
               for( int k=0; k<4; k++)
               {
                  //read next num from file
                  String connectingRoom = read.next();
                  connectingRoomsArray[k] = connectingRoom;
                  //System.out.print( connectingRoom + " ");
               }
               //System.out.println();
               
               //Collison Control (IN PROGRESS) FUNCTIONS CORRECTLY(Considering making this a class with methods...) -HK  
               Set<String> names = new HashSet<String>();// set containing all object types player cant pass through
               //ADD IMPASSIBLE ITEMS BELLOW IN SAME FORMAT
               names.add("100");//black square
               names.add("202");//metal tile
               names.add("222");//blank space NON-TRAVERABLE
               names.add("901");//Jukebox
               names.add("324");//metal wall tile
               names.add("319"); //walls of diff. sizes
               names.add("318");
               names.add("331");
               names.add("321");
               names.add("311");
               //add values for spikes that are up
               names.add("4001");
               names.add("4011");
               names.add("4101");
               names.add("4111");
               names.add("4201");
               names.add("4211");
               names.add("4301");
               names.add("4311");
               names.add("4401");
               names.add("4411");
               
               
               
               //Code below is used for testing where you are and what the value the object you are standing on is
               //System.out.print(objectArray[((y/20))][(x/20)].getValue());//test (MIGHT NOT BE ACCURATE)
               //System.out.print("x: "+x/20+" y:"+y/20+" ");//test for displaying where character is
               //check above 
               if(names.contains(objectArray[(y-1)][x].getValue()) || names.contains(objectArray[y-1][x+1].getValue()))//for above character
               {
                  up = false;
               }
               else
                  up = true;
               //check below
               if(names.contains(objectArray[(y+2)][x].getValue()) || names.contains(objectArray[(y+2)][x+1].getValue()))//for below character
               {
                  down = false;
               }
               else
                  down = true;
               //check left
               if(names.contains(objectArray[y][(x-1)].getValue()) || names.contains(objectArray[y+1][(x-1)].getValue()))//for left of character
               {
                  left = false;
               }
               else
                  left = true;
               //check right
               if(names.contains(objectArray[y][(x+2)].getValue()) || names.contains(objectArray[y+1][(x+2)].getValue()))//for right of character
               {
                  right = false;
               }
               else
                  right = true;
               /*
               //now read in the player coordinates
               x = Integer.parseInt( read.next() );
               y = Integer.parseInt( read.next() );
               */
               
               
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
            //do the background color
            gc.setFill(Color.GREY);
            gc.fillRect(0, 0, 800, 800);
            
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
                     gc.drawImage( objectArray[i][j].getImage(), 20*j, 20*i, width, height);
                  }
               }
            }

            
            
            //Switching between images to "animate" the player based on int Z and boolean a
            //boolean a swtiches every key press, int z switches based on which key is pressed
            if(z == 4)//right arrow pressed
            {
               if(a == true)
               {
                  Image moveRight1 = new Image("moveRight1.png");// character moving right 1
                  gc.drawImage(moveRight1, x*20, y*20, 40, 40);
               }
               else
               {
                  Image moveRight2 = new Image("moveRight2.png");// character moving right 2
                  gc.drawImage(moveRight2, x*20, y*20, 40, 40);
               }
            }
            if(z == 2 || z == 3)// left arrow pressed or down arrow
            {
               if(a == true)
               {
                  Image moveLeft1 = new Image("moveLeft1.png");// character moving left 1
                  gc.drawImage(moveLeft1, x*20, y*20, 40, 40);
               }
               else
               {
                  Image moveLeft2 = new Image("moveLeft2.png");// character moving left 2
                  gc.drawImage(moveLeft2, x*20, y*20, 40, 40);
               }
            }
            if(z == 1)//up arrow pressed
            {
               if(a == true)
               {
                  Image moveUp1 = new Image("moveUp1.png");// character moving up 1
                  gc.drawImage(moveUp1, x*20, y*20, 40, 40);
               }
               else
               {
                  Image moveUp2 = new Image("moveUp2.png");// character moving up 2
                  gc.drawImage(moveUp2, x*20, y*20, 40, 40);
               }
            }
            if(z == 0)// starting position
            {
               Image moveLeft1 = new Image("moveLeft1.png");// character moving left 1
               gc.drawImage(moveLeft1, x*20, y*20, 40, 40);
            }
         }
         else// drawing the menu if "menu" is set
         {
            //System.out.println( "no draw, menu open");
            //set up menu 
            gc.setFill(Color.BLACK);
            gc.fillRect( 0, 0, 800, 800);
            Image background = new Image("background.jpg");
            gc.drawImage(background, 100, 100, 600, 700);
            gc.setFill(Color.WHITE);
            gc.fillText("BY Harry, Hudson, and Jake", 500, 500);
         }
      }
      
      /*
      b1.setPrefSize(150, 75);
      b2.setPrefSize(150, 75);
      fp.getChildren().add(b1);
      fp.getChildren().add(b2);
      
      b1.setFocusTraversable(false);
      b1.setOnAction( new ButtonHandler() );
      b2.setFocusTraversable(false);
      b2.setOnAction( new ButtonHandler() );
      */
      
      //update method to update values
      public void updateData()
      {
         if( levelName != "menu")
         {
            
            for( int i=0; i<40; i++)
            {
               for( int j=0; j<40; j++)
               {
                  //for every object, call the update() method, which for animated objects will push to next frame
                  objectArray[i][j].update();
                }  
            }
            
            //System.out.println( objectArray[0][0].getValue() );
         }  
      }
      
      
      //write method to write the level back to the .txt file
      public void writeData()
      {
         if( levelName != "menu")
         {
            //test different methods for the objects  
            //System.out.println( objectArray[0][0].getValue() );
            
            //try catch to output object array to a specified text file
            try
            {
               FileOutputStream fos2 = new FileOutputStream( outputFile, false); //false means that we overwrite the file
               PrintWriter pw = new PrintWriter(fos2);
               
               for( int i=0; i<40; i++)
               {
                  for( int j=0; j<40; j++)
                  {
                     //System.out.print(  objectArray[i][j].getValue() + " ");
                     pw.print( objectArray[i][j].getValue() + " " );
                  }
                  //System.out.println();
                  pw.println();
               } 
               
               //pw.println( x + " " + y ); //output player coordinates
               
               for( int k=0; k<4; k++) //output connecting rooms
               {
                  pw.println( connectingRoomsArray[k] );
               }
               
               pw.close(); 
            }
            catch( FileNotFoundException fnfe)
            {
               System.out.println("fnfe exception");
            }

            
         }
         else
         {
            //System.print.ln("no write data, in menu");
         }   

      }
      
      //returns the current level name
      public String getLevelName()
      {
         return levelName;
      }
      //mutator to change the current level
      public void setLevelName( String levelName_in)
      {
         levelName = levelName_in;
         System.out.println("level changed to: " + levelName);
      } 
      public void setOutputFile( String outputFile_in )
      {
         outputFile = outputFile_in;
      }
      //method to change the current level
      public void changeLevel( String newFile)
      {
         levelName = newFile;
         outputFile = newFile;
         //set starting player coordinates
         switch( newFile)
         {
            case "jukebox_room.txt":
               x = 17;
               y = 29;
               z = 0;
               a = false;
               break;
            case "room1.txt":
               x = 18;
               y = 31;
               z = 0;
               a = false;
               break;
            case "room2.txt":
               x = 18;
               y = 25;
               z = 0;
               a = false;
               break; 
         }
         loadData();
      }
      
      //collision detection methods, broad phase and narrow phase RETURN TRUE IF COLLISION PRESENT, FALSE IF NO COLLISIONS
      public boolean collisionCheck()
      {
         //read in the area where the player is, as well as the area around it, into an array (size 6x6)
         //System.out.println( objectArray[y][x].getValue());
         
         //Object[][] collisionArray = new Object[4][4];
         
         
         System.out.println("broad phase collision");
         //nested for loops to test each value
         for( int i=0; i<5; i++)
         {
            for( int j=0; j<5; j++)
            {
               int yval = y-3+i;
               int xval = x-3+j;
               //System.out.print( xval + " " + yval + " "); //xval +" " + yval);
               System.out.print( objectArray[yval][xval].getValue() +" " );
               
               
               //get object in question's bounds for use in collision algorithm
               int objectX = xval;
               int objectY = yval;
               int objectHeight = objectArray[yval][xval].getHeight() / 20; //divide by 20 to get numerical values
               int objectWidth = objectArray[yval][xval].getWidth() / 20;
               //get player bounds
               int playerX = x; 
               int playerY = y;
               int playerHeight = 40 / 20;
               int playerWidth = 40 / 20;
               
               //NARROW PHASE COLLISION SECTION 
                              
               //check the object's 1st num to see if it is a special object
               String value = objectArray[yval][xval].getValue();
               String firstChar = ""+ value.charAt(0);
               switch( firstChar)
               {
                  case "5": //pressure plate (button)
                     //means that there is a pressure plate in the area
                     if (playerX < objectX + objectWidth &&
                        playerX + playerWidth > objectX &&
                        playerY < objectY + objectHeight &&
                        playerY + playerHeight > objectY)
                     {   
                        System.out.println( "player has collided with a pressure plate");
                        
                        String lastChar = ""+value.charAt(3);
                        if( lastChar.equals("0") )
                           lastChar = "1";
                        else
                           lastChar = "0";
                        
                     }
                     break;
                  case "6": //spring
                     break;
                  case "8": //portal
                     if (playerX < objectX + objectWidth &&
                        playerX + playerWidth > objectX &&
                        playerY < objectY + objectHeight &&
                        playerY + playerHeight > objectY)
                     {   
                        System.out.println( "player has collided with a portal");
                        //now determine where to move player based on connectingRoomsArray
                        switch( value )
                        {//Part2
                           case "800": //up arrow
                              System.out.println( "old file:" + levelName);
                              changeLevel( connectingRoomsArray[0]);
                              System.out.println( "new file:" + levelName);
                              break;
                           case "801": //left arrow
                              System.out.println( "old file:" + levelName);
                              changeLevel( connectingRoomsArray[1]);
                              System.out.println( "new file:" + levelName);
                              break;
                           case "802": //down arrow
                              System.out.println( "old file:" + levelName);
                              changeLevel( connectingRoomsArray[2]);
                              System.out.println( "new file:" + levelName);
                              break;
                           case "803": //right arrow
                              System.out.println( "old file:" + levelName);
                              changeLevel( connectingRoomsArray[3]);
                              System.out.println( "new file:" + levelName);
                              break;
                              
                              
                        }
                     }
                     break;
                  case "9": //screwdriver
                     break;
               }
               
            }
            System.out.println();
         }
         
         
         //check each value in array to see if it is a potential collision object
         
         //call narrowPhaseCollision on all potential collisions
         return false;
      }
      public boolean narrowPhaseCollision()
      {
         return false;
      }
   } 
   
   
   //key & button handlers
   public class KeyHandler implements EventHandler<KeyEvent>
   {
      
      public void handle(KeyEvent event)
      {
         /*
         int x = canvas.getLevel().getX();
         int y = canvas.getLevel().getY();
         int z = canvas.getLevel().getZ();
         boolean a = canvas.getLevel().getA();
         */
         
         String currentLevel = canvas.getLevel().getLevelName();
         
         if( currentLevel.equals("menu") )
         {
            System.out.println("no keys, menu open");
         }
         else
         {
         
            if(event.getCode() == KeyCode.UP && up == true)//if up key is pressed
            {
               y=y-1; //move square up by subtracting y coordinate by 1
               z=1;//used in draw method to show up movement         }
            }
            if(event.getCode() == KeyCode.LEFT && left == true)//if left key is pressed
            {
               x=x-1; //move square left by subtracting x coordinate by 1
               z=2;//used in draw method to show left movement
            }
            if(event.getCode() == KeyCode.DOWN && down == true)//if down key is pressed
            {
               y=y+1; //move square down by adding y coordinate by 1 
               z=3;//used in draw method to show down movement
            }
            if(event.getCode() == KeyCode.RIGHT && right == true)//if right key is pressed
            {
               x=x+1; //move square down by adding y coordinate by 1
               z=4;//used in draw method to show right movement
            }
            if(a == true)//this is used to switch the boolean a between states to "animate" the player in the draw method
            {
               a = false;
            }
            else
               a = true;
            
            //////// COLLISION CHECK FOR SPECIAL OBJECTS -Jake
            
            //after movement, check to see if in range of notable object, and make appropriate changes (buttons, spikes, springs)
            canvas.getLevel().collisionCheck();
         }
      }
   }
   
   public class ButtonHandler implements EventHandler<ActionEvent>
   {
      public void handle( ActionEvent e)
      {  
                  
         //button instructions
         System.out.println( "button press");
         String currentLevel = canvas.getLevel().getLevelName();
         //String currentLevelLoad
         
         
         //if the current room is the menu and the button is pressed, change the level to the jukebox room
         if(currentLevel.equals("menu"))
         {
            //start (or new game) //***Working
            if(e.getSource() == b1)
            {
               canvas.getLevel().setLevelName( "jukebox_room.txt");
               canvas.getLevel().setOutputFile( "jukebox_room.txt");
            }
            
            //load game //***Not working
            else if(e.getSource() == b2)
            {
               //canvas.getLevel().setLevelName(currentLevelLoad);
               //canvas.getLevel().setOutputFile(currentLevelLoad);
            }
            
            
            
            fp.getChildren().clear();
         }
         
         else
         {
            //if "Restart Area" is pressed, reset the room to the current room //***Working
            //should also reset the room
            if(e.getSource() == b3)
            {
               if(canvas.getLevel().getLevelName().equals("jukebox_room.txt"))
               {
                  canvas.getLevel().setLevelName(currentLevel);
                  canvas.getLevel().setOutputFile(currentLevel);
                  canvas.getLevel().changeLevel(currentLevel);
               }
               
               else if(canvas.getLevel().getLevelName().equals("room1.txt"))
               {
                  canvas.getLevel().setLevelName("room1_backup.txt");               
                  canvas.getLevel().setOutputFile("room1.txt");
                  canvas.getLevel().loadData();
                  canvas.getLevel().writeData();
                  canvas.getLevel().changeLevel("room1.txt");
               }
               
               else if(canvas.getLevel().getLevelName().equals("room2.txt"))
               {
                  canvas.getLevel().setLevelName("room2_backup.txt");               
                  canvas.getLevel().setOutputFile("room2.txt");
                  canvas.getLevel().loadData();
                  canvas.getLevel().writeData();
                  canvas.getLevel().changeLevel("room2.txt");
               }
               
               
               fp.getChildren().clear();
            }
            
            //if "Restart Level" is pressed, change the room to the jukebox room //***Working
            //should also reset the rooms
            else if(e.getSource() == b4)
            {
               //String ln = currentLevel.getLevelName();
               
               
               //room1 reset
               canvas.getLevel().setLevelName("room1_backup.txt");               
               canvas.getLevel().setOutputFile("room1.txt");
               canvas.getLevel().loadData();
               canvas.getLevel().writeData();
               
               //room2 reset
               canvas.getLevel().setLevelName("room2_backup.txt");               
               canvas.getLevel().setOutputFile("room2.txt");
               canvas.getLevel().loadData();
               canvas.getLevel().writeData();
               
               
               
               
               //change room to jukebox
               canvas.getLevel().setLevelName("jukebox_room.txt");
               canvas.getLevel().setOutputFile("jukebox_room.txt");
               canvas.getLevel().changeLevel("jukebox_room.txt");
               //canvas.getLevel().loadData();
               //canvas.getLevel().writeData();
               
               
               fp.getChildren().clear();
            }
            //if Save button is pressed //***Possibly working
            else if(e.getSource() == b5)
            {
               currentLevelLoad = canvas.getLevel().getLevelName();
               System.out.println("Level Saved");
               
               fp.getChildren().clear();
            }
            //if Menu button is pressed //***Working
            else if(e.getSource() == b6)
            {
               //sets boolean to true telling the command in the animation handler to redraw the menu
               
               levelName = "menu";
               fp.getChildren().clear();
               addMenuButtons = true;
               
               
            }
            
                        
         }
         //add restart area/level buttons to the rooms
         //set size of buttons and add them to the FlowPane
         b3.setPrefSize(150, 75);
         b4.setPrefSize(150, 75);
         b5.setPrefSize(150, 75);
         b6.setPrefSize(150, 75);
         fp.getChildren().add(b3);
         fp.getChildren().add(b4);
         fp.getChildren().add(b5);
         fp.getChildren().add(b6);
         
         b3.setFocusTraversable(false);
         b3.setOnAction( new ButtonHandler() );
         b4.setFocusTraversable(false);
         b4.setOnAction( new ButtonHandler() );
         b5.setFocusTraversable(false);
         b5.setOnAction( new ButtonHandler() );
         b6.setFocusTraversable(false);
         b6.setOnAction( new ButtonHandler() );
         
      }
   }
   
    
   //Inheritance Section:
   public abstract class Object
   {
      String value;
      int height = 80;
      int width = 80;
      Image image;
   
      public Object()
      {
      }
      //methods
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
      public void update()
      {
         
      }
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
               height = 20;
               width = 20;
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
               break;
            case "104": //black tile
               Image blackTile = new Image("Black_Tile.png");//104
               image = blackTile;
               break;
            case "105": //stone tile
               Image stoneTile = new Image("Cobblestone_Tile.png");//105
               image = stoneTile;
               break;
            case "106": //grey tile
               Image greyTile = new Image("Grey_Tile.png");//106
               image = greyTile;
               break;
            case "107": //grey tile4
               Image greyTile4 = new Image("Grey_Tile4.png");//107
               image = greyTile4;
               //big tile so make h&w big
               height = 160;
               width = 160; 
               break;
            case "108": //grey tile 1x4
               Image greyTile1x4 = new Image("Grey_Tile1x4.png"); //108
               image = greyTile1x4;
               height = 20;
               width = 80;
               break;
            case "109": //grey tile 4x8
               Image greyTile4x8 = new Image("Grey_Tile4x8.png");
               image = greyTile4x8;
               height = 80;
               width = 160;
               break;
            case "110": //brick tile
               Image brick = new Image("1Brick.png");//110
               image = brick;
               break;
            case "111": //stone tile 3x8
               Image stoneTile3x8 = new Image("Cobblestone_Tile.png");//111
               image = stoneTile3x8;
               height = 60;
               width = 180;
               break;
            case "112": //stone tile 1x6
               Image stoneTile1x6 = new Image("Cobblestone_Tile.png"); //112
               image = stoneTile1x6;
               height = 20;
               width = 120;
               break;
           
         }      
      }
   }
   
   public class ObjectCollision extends Object
   {
      
      public ObjectCollision( String value_in )
      {
         value = value_in;
      }
   }
   
   public class Spike extends Object
   {
      public Spike( String value_in )
      {
         value = value_in;
         String first2Chars = ""+value.charAt(0)+value.charAt(1); 
         String orientation = ""+value.charAt(2);
         String isActive = ""+ value.charAt(3);
         
         //set dimensions based on orientation
         if( orientation.equals("0") )
         {
            height = 20;
            width = 80;
         }
         if( orientation.equals("1") )
         {
            height = 80;
            width = 20;
            
         }
         //set image based on value numbers
         switch( value )
         {
            //orange spikes
            case "4000":
               //orange spike, horizontal, not active
               Image spikeOrangeHorNA = new Image("orangeHole.png");
               image = spikeOrangeHorNA;
               break;
            case "4001":
               //orange spike, horizontal, active
               Image spikeOrangeHorA = new Image("orangeSpike.png");
               image = spikeOrangeHorA;
               break;
            case "4010":
               //orange spike, vertical, not active
               Image spikeOrangeVertNA = new Image("orangeHoleR.png");
               image = spikeOrangeVertNA;
               break;
            case "4011":
               //orange spike, vertical, active
               Image spikeOrangeVertA = new Image("orangeSpikeR.png");
               image = spikeOrangeVertA;
               break;
            //yellow spikes
            case "4100":
               //yellow spike, horizontal, not active
               Image spikeYellowHorNA = new Image("yellowHole.png");
               image = spikeYellowHorNA;
               break;
            case "4101":
               //Yellow spike, horizontal, active
               Image spikeYellowHorA = new Image("yellowSpike.png");
               image = spikeYellowHorA;
               break;
            case "4110":
               //Yellow spike, vertical, not active
               Image spikeYellowVertNA = new Image("yellowHoleR.png");
               image = spikeYellowVertNA;
               break;
            case "4111":
               //Yellow spike, vertical, active
               Image spikeYellowVertA = new Image("yellowSpikeR.png");
               image = spikeYellowVertA;
               break;
            //Green spikes
            case "4200":
               //Green spike, horizontal, not active
               Image spikeGreenHorNA = new Image("greenHole.png");
               image = spikeGreenHorNA;
               break;
            case "4201":
               //Green spike, horizontal, active
               Image spikeGreenHorA = new Image("greenSpike.png");
               image = spikeGreenHorA;
               break;
            case "4210":
               //Green spike, vertical, not active
               Image spikeGreenVertNA = new Image("greenHoleR.png");
               image = spikeGreenVertNA;
               break;
            case "4211":
               //Green spike, vertical, active
               Image spikeGreenVertA = new Image("greenSpikeR.png");
               image = spikeGreenVertA;
               break;
            //blue spikes
            case "4300":
               //Blue spike, horizontal, not active
               Image spikeBlueHorNA = new Image("blueHole.png");
               image = spikeBlueHorNA;
               break;
            case "4301":
               //Blue spike, horizontal, active
               Image spikeBlueHorA = new Image("blueSpike.png");
               image = spikeBlueHorA;
               break;
            case "4310":
               //Blue spike, vertical, not active
               Image spikeBlueVertNA = new Image("blueHoleR.png");
               image = spikeBlueVertNA;
               break;
            case "4311":
               //Blue spike, vertical, active
               Image spikeBlueVertA = new Image("blueSpikeR.png");
               image = spikeBlueVertA;
               break;
            //purple spikes
            case "4400":
               //Purple spike, horizontal, not active
               Image spikePurpleHorNA = new Image("purpleHole.png");
               image = spikePurpleHorNA;
               break;
            case "4401":
               //Purple spike, horizontal, active
               Image spikePurpleHorA = new Image("purpleSpike.png");
               image = spikePurpleHorA;
               break;
            case "4410":
               //Purple spike, vertical, not active
               Image spikePurpleVertNA = new Image("purpleHoleR.png");
               image = spikePurpleVertNA;
               break;
            case "4411":
               //Purple spike, vertical, active
               Image spikePurpleVertA = new Image("purpleSpikeR.png");
               image = spikePurpleVertA;
               break;
         }
      }
   }
   
   public class PressurePlate extends Object
   {
      public PressurePlate( String value_in )
      {
         value = value_in;
         
         switch( value)
         {
            //orange pressure plates
            case "5000":
               //orange pressure plate, square, not pressed
               Image ppOrangeSqrNP = new Image("orangeBU.png");
               image = ppOrangeSqrNP;
               break;
            case "5001":
               //orange pressure plate, square, pressed
               Image ppOrangeSqrP = new Image("orangeBD.png");
               image = ppOrangeSqrP;
               break;
            case "5010":
               //orange pressure plate, circle, not pressed
               Image ppOrangeCirNP = new Image("orangeCirBU.jpg");
               image = ppOrangeCirNP;
               break;
            case "5011":
               //orange pressure plate, circle, pressed
               Image ppOrangeCirP = new Image("orangeCirBD.jpg");
               image = ppOrangeCirP;
               break;
            //yellow pressure plates
            case "5100":
               //Yellow pressure plate, square, not pressed
               Image ppYellowSqrNP = new Image("yellowBU.png");
               image = ppYellowSqrNP;
               break;
            case "5101":
               //Yellow pressure plate, square, pressed
               Image ppYellowSqrP = new Image("yellowBD.png");
               image = ppYellowSqrP;
               break;
            case "5110":
               //Yellow pressure plate, circle, not pressed
               Image ppYellowCirNP = new Image("yellowCirBU.jpg");
               image = ppYellowCirNP;
               break;
            case "5111":
               //Yellow pressure plate, circle, pressed
               Image ppYellowCirP = new Image("yellowCirBD.jpg");
               image = ppYellowCirP;
               break;
            //Green pressure plates
            case "5200":
               //Green pressure plate, square, not pressed
               Image ppGreenSqrNP = new Image("greenBU.png");
               image = ppGreenSqrNP;
               break;
            case "5201":
               //Green pressure plate, square, pressed
               Image ppGreenSqrP = new Image("greenBD.png");
               image = ppGreenSqrP;
               break;
            case "5210":
               //Green pressure plate, circle, not pressed
               Image ppGreenCirNP = new Image("greenCirBU.jpg");
               image = ppGreenCirNP;
               break;
            case "5211":
               //Green pressure plate, circle, pressed
               Image ppGreenCirP = new Image("greenCirBD.jpg");
               image = ppGreenCirP;
               break;
            //blue pressure plates
            case "5300":
               //Blue pressure plate, square, not pressed
               Image ppBlueSqrNP = new Image("blueBU.png");
               image = ppBlueSqrNP;
               break;
            case "5301":
               //Blue pressure plate, square, pressed
               Image ppBlueSqrP = new Image("blueBD.png");
               image = ppBlueSqrP;
               break;
            case "5310":
               //Blue pressure plate, circle, not pressed
               Image ppBlueCirNP = new Image("blueCirBU.jpg");
               image = ppBlueCirNP;
               break;
            case "5311":
               //Blue pressure plate, circle, pressed
               Image ppBlueCirP = new Image("blueCirBD.jpg");
               image = ppBlueCirP;
               break;
            //purple pressure plates
            case "5400":
               //Purple pressure plate, square, not pressed
               Image ppPurpleSqrNP = new Image("purpleBU.png");
               image = ppPurpleSqrNP;
               break;
            case "5401":
               //Purple pressure plate, square, pressed
               Image ppPurpleSqrP = new Image("purpleBD.png");
               image = ppPurpleSqrP;
               break;
            case "5410":
               //Purple pressure plate, circle, not pressed
               Image ppPurpleCirNP = new Image("purpleCirBU.jpg");
               image = ppPurpleCirNP;
               break;
            case "5411":
               //Purple pressure plate, circle, pressed
               Image ppPurpleCirP = new Image("purpleCirBD.jpg");
               image = ppPurpleCirP;
               break;
         }    
      }
   }
   
   public class Spring extends Object
   {
      public Spring( String value_in )
      {
         value = value_in;
        
         switch( value)
         {
            case "600":
               //north, not active
               Image springNActivated = new Image("springTileActivated.png");
               image = springNActivated;
               break;
            case "601":
               //north, active
               Image springNActive = new Image( "springTileNS.png");
               image = springNActive;
               break;
            case "610":
               //west, not active
               Image springWActivated = new Image("springTileActivated.png");
               image = springWActivated;
               break;
            case "611": 
               //west, active
               Image springWActive = new Image( "springTileWE.png");
               image = springWActive;
               break;
            case "620":
               //south, not active
               Image springSActivated = new Image("springTileActivated.png");
               image = springSActivated;break;
            case "621":
               //south, active
               Image springSActive = new Image( "springTileNS.png");
               image = springSActive;
               break;
            case "630":
               //east, not active
               Image springEActivated = new Image("springTileActivated.png");
               image = springEActivated;
               break;
            case "631":
               //east, active
               Image springEActive = new Image( "springTileWE.png");
               image = springEActive;
               break;
         }
         
         
      }

   }
   
   public class Wall extends Object
   {
      public Wall( String value_in )
      {
         value = value_in;
         
         Image wall = new Image( "Metal_Tile.png"); //3##
         image = wall;
         
         //pull out wall dimensions from value 
         int wallHeight = Integer.parseInt( ""+value.charAt(1) );
         int wallWidth = Integer.parseInt( ""+value.charAt(2) );
         //set wall dimensions
         height = 20*wallHeight;
         width = 20*wallWidth;
  
      }
     
      
   }
   
   public class Portal extends Object
   {
      String connectingRoom;
      
      public Portal( String value_in )
      {
         value = value_in;
         
         switch( value )
         {
            case "800": //Up Portal
               Image upPortal = new Image("arrowUp.png");//21
               image = upPortal;
               height = 60;
               width = 40;
               break;
            case "801": //Left Portal
               Image leftPortal = new Image("arrowLeft.png");//21
               image = leftPortal;
               height = 40;
               width = 60;
               break;
            case "802": //Down Portal
               Image downPortal = new Image("arrowDown.png");//21
               image = downPortal;
               height = 60;
               width = 40;
               break;
            case "803": //Right Portal
               Image rightPortal = new Image("arrowRight.png");//21
               image = rightPortal;
               height = 40;
               width = 60;
               break;
         }
      }

      
   }
   
   public class Gate extends Object
   {
      public Gate( String value_in )
      {
         value = value_in;
         height = 60;
         width = 20;
         
         switch( value)
         {
            case "700":
               Image gateClosed = new Image("gate.jpg");
               image = gateClosed;
               break;
            case "701":
               Image gateOpen = new Image("Cobblestone_Tile.png");
               image = gateOpen;
         }
      }
      
   }
   
   public class Misc extends Object
   {
      int timeCount; //used to space out the animation changes
      String frameNum;
      String first2Chars;
      
      public Misc( String value_in )
      {
         timeCount = 0;
         value = value_in;
         
         first2Chars = ""+value.charAt(0) + value.charAt(1); //only use 2 chars to allow variability in frame counter
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
            case "91": //screwdriver
               height = 40;
               width = 120;
               Image screwdriver = new Image( "screwdriver.jpg");
               image = screwdriver;
               break;
            //case: 999 //for other misc in the future
         }
      }
      
      public void update() //moves animation to next frame
      {
         //only do if a jukebox, not for 999 values
         if( first2Chars.equals("90") )
         {
            System.out.print("Jukebox updated" + frameNum );
            if( frameNum.equals("1") )
               frameNum = "2";
            else
               frameNum = "1";
            
            value = ""+ value.charAt(0) + value.charAt(1) + frameNum;
            System.out.println(" "+ frameNum );
         }   
         /* USE FOR MAKING THE UPDATE SWITCH NOT AS FREQUENTLY
         if( first2Chars.equals("90") ) //what to do for jukebox
         {
            timeCount = timeCount + 1;
            
            System.out.println( "update called Jukebox" + timeCount);
            
            if( timeCount % 20 == 0) //only switch frames every 20 calls
            {
               timeCount = 0;
               System.out.println( "DONE");
               
               if( frameNum.equals("1") )
                  frameNum = "2";
               else
                  frameNum = "1";
               
               value = ""+ value.charAt(0) + value.charAt(1) + frameNum; //edit the value string for animation
            }
         }
         */
      }
      
   }
   
}

