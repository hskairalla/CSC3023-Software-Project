//Puzzle Project Canvas
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
import java.net.*;
import javafx.geometry.*;
import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import java.io.*;
import javax.imageio.*;

import java.io.File; 
import java.io.IOException; 
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

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
      
      //Set up event handler
      setOnKeyPressed(new KeyHandler());
      BufferedImage image1 = null;

      
      try 
      {
         // Read from a file
         File brickimage = new File("1Brick.png");
         image1 = ImageIO.read(brickimage);
        
      
  
      }
      catch(Exception ex)
      {
         ex.printStackTrace();
      }

      draw(gc);
   }
            
      /*
      //Setting the image view 
      ImageView imageView = new ImageView(image); 
      
      //Setting the position of the image 
      imageView.setX(20); 
      imageView.setY(20); 
      
      //setting the fit height and width of the image view 
      imageView.setFitHeight(20); 
      imageView.setFitWidth(20); 
      
      //Setting the preserve ratio of the image view 
      imageView.setPreserveRatio(true);  
      
      //Creating a Group object  
      Group root = new Group(imageView);
      */ 
   public void draw(GraphicsContext gc)//method to draw the level
   {      
      for(int i=0; i<40; i++)
      {
         for(int j=0; j<40; j++)
         {
            if(level.getData(i,j) == 0)//if value is 0 draw white square at i,j
            {
               gc.setFill(Color.BLACK);
               gc.fillRect(j*20.0, i*20.0, 20, 20);  
            }
            if(level.getData(i,j)==1)//if value is 1 draw black square at i,j
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
               Image image = new Image("1Brick.png");
               
               gc.drawImage(image, i-3, j-3,40,40);
            }
            
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
         
            if(y<5)
            {
               String upCheck = level.nextUp();
            
               if(upCheck.equals("NONE"))
               {
                  y=5;
               }
               /*else
               {
                  fileName = level.nextUp();
                  y=495;
                  level = new LAB7Level(fileName);
               }*/
            }
         }
      
         if(event.getCode() == KeyCode.LEFT)//if left key is pressed
         {
            x=x-10; //move square left by subtracting x coordinate by 3
         
            if(x<5)
            {
               String leftCheck = level.nextLeft();
            
               if(leftCheck.equals("NONE"))
               {
                  x=5;
               }
              /* else
              {
                  fileName = level.nextLeft();
                  x=350;
                  level = new LAB7Level(fileName);
               }*/
            }
         }
      
         if(event.getCode() == KeyCode.DOWN)//if down key is pressed
         {
            y=y+10; //move square down by adding y coordinate by 3
         
            if(y>800)
            {
               String downCheck = level.nextDown();
            
               if(downCheck.equals("NONE"))
               {
                  y=800;
               }
               /*else
               {
                  fileName = level.nextDown();
                  y=50;
                  level = new LAB7Level(fileName);
               }*/
            }
         }
      
         if(event.getCode() == KeyCode.RIGHT)//if right key is pressed
         {
            x=x+10; //move square down by adding y coordinate by 3
         
            if(x>800)
            {
               String rightCheck = level.nextRight();
            
               if(rightCheck.equals("NONE"))
               {
                  x=800;
               }
               /*else
               {
                  fileName = level.nextRight();
                  x=50;
                  level = new LAB7Level(fileName);
               }*/
            }
         }
      
      draw(gc);
      }
      
      
   }


}
