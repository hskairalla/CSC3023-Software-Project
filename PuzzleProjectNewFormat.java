import javax.imageio.*;

public class PuzzleProjectNewFormat extends Application
{
   //Instantiate canvas and FlowPane
   PPCanvas canvas = new PPCanvas();
   FlowPane fp = new FlowPane();
   
   //set up buttons
   Button b1 = new Button("Start");// Button to start the game
   Button b2 = new Button("Load");// Button to load the game
   
   
   int x = 350; //int x for keeping track of player movement in the x (Start at x=350)
   int y = 600; //int y for keeping track of player movement in the y (Start at x=600)
   int z = 0; //int z for keeping track of the direction the player is facing
   boolean a = false; //boolean used for animating player movement (Switches between 2 states)
   
   
   public void start(Stage stage)
   {
      //set up root
      Group root = new Group();
      Scene scene = new Scene( root );
      stage.setScene( scene );
      stage.setTitle( "Puzzle Project" );
     
      //set up flow pane for menu
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
               level.updateData();
               level.writeData();
               
               /* NOTE: MIGHT MAKE COLLISION DETECTION DIFFICULT TO ONLY DO EVERY 10 ITERATIONS, so not doing it
               if( count % 10 == 0 ) //only runs code every 20 iterations of timer
               {
                  
                  System.out.println( count + " " + half_second);
                  level.loadData();
                  level.draw(gc);
                  level.updateData();
                  level.writeData();        
                  
               }
               count ++;
               */
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
      ArrayList<String> connectingRoomsArray = new ArrayList<String>();
      
      String levelName = "menu"; //start game on menu
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
               
               /*
               //now read in the player coordinates
               x = Integer.parseInt( read.next() );
               y = Integer.parseInt( read.next() );
               */
               
               //now read in the portal connections array
               for( int k=0; k<4; k++)
               {
                  //read next num from file
                  String connectingRoom = read.next();
                  connectingRoomsArray.add( connectingRoom );
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

            gc.setFill(Color.WHITE);
            //gc.fillRect(x, y, 40, 40);
            
            //Switching between images to "animate" the player based on int Z and boolean a
            //boolean a swtiches every key press, int z switches based on which key is pressed
            if(z == 4)//right arrow pressed
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
            if(z == 2 || z == 3)// left arrow pressed or down arrow
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
            if(z == 1)//up arrow pressed
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
            if(z == 0)// starting position
            {
               Image moveLeft1 = new Image("moveLeft1.png");// character moving left 1
               gc.drawImage(moveLeft1, x, y, 40, 40);
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
                  
                  /*
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
                  */
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
                  pw.println( connectingRoomsArray.get(k) );
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
      
      /* IN CASE YOU CHANGE PLAYER MOVEMENT SYSTEM, USE THESE ACCESSORS AND MUTATORS
      
      //4 accessors to get Player data
      public int getX()
      {
         return x;
      }
      public int getY()
      {
         return y;
      }
      public int getZ()
      {
         return z;
      }
      public boolean getA()
      {
         return a;
      }
      //4 mutators to change player data
      public void setX( int x_in)
      {
         x = x_in;
      }
      public void setY( int y_in)
      {
         y = y_in;
      }
      public void setZ( int z_in)
      {
         z = z_in;
      }
      public void setA( boolean a_in)
      {
         a = a_in;
      }
      */
      
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
         
            if(event.getCode() == KeyCode.UP)//if up key is pressed
            {
               y=y-3; //move square up by subtracting y coordinate by 3
               z=1;//used in draw method to show up movement         }
            }
            if(event.getCode() == KeyCode.LEFT)//if left key is pressed
            {
               x=x-3; //move square left by subtracting x coordinate by 3
               z=2;//used in draw method to show left movement
            }
            if(event.getCode() == KeyCode.DOWN)//if down key is pressed
            {
               y=y+3; //move square down by adding y coordinate by 3 
               z=3;//used in draw method to show down movement
            }
            if(event.getCode() == KeyCode.RIGHT)//if right key is pressed
            {
               x=x+3; //move square down by adding y coordinate by 3
               z=4;//used in draw method to show right movement
            }
            if(a == true)//this is used to switch the boolean a between states to "animate" the player in the draw method
            {
               a = false;
            }
            else
               a = true;
            
            /*
            //now that corresponding change has been made to player values, update the level player vars
            canvas.getLevel().setX(x);
            canvas.getLevel().setY(y);
            canvas.getLevel().setZ(z);
            canvas.getLevel().setA(a);
            */
         }
      }
   
           
          //check for collision
          //checkCollision(player);  
   }
   
   public class ButtonHandler implements EventHandler<ActionEvent>
   {
      public void handle( ActionEvent e)
      {  
         //if menu button pressed, change level from menu to level 1
         System.out.println( "button press");
         
         canvas.getLevel().setLevelName( "jukebox_room.txt");
         canvas.getLevel().setOutputFile( "jukebox_room.txt"); //NOTE: maybe use this command to add functionality for loading a save state in the future
         
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
               //big tile so make size big
               height = 160;
               width = 160;
               
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
            case "202": //wall tile
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
      public void update() //moves animation to next frame
      {
   
         if( frameNum.equals("1") )
            frameNum = "2";
         else
            frameNum = "1";
         
         value = ""+ value.charAt(0) + value.charAt(1) + frameNum;
         
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
