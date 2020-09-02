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

   public void start(Stage stage)
   {
      //set up canvas and scene

   }
    
   
   public class PPCanvas extends Canvas
   {
   
      //set up text file to read level data from
   
      public PPCanvas()   
      {
         //set up graphics context
        
         
          
         //animation timer to handle animated objects
         new AnimationTimer()
         {
            public void handle( long currentTime)
            {
               //read text file to create 2D object array (load data)
               loaddata(test.txt, objectArray)
               
               //check for key press, update player movement 
               
               
               
               //check if character has done a switch press (update data)
               
               //call draw() on updated data (draw data)
               
               //save data back to text file -> Maybe use dictionary? <- (save data)
            
            
            }
         }.start();
     
      }    
   }
   
   
   public void draw(GraphicsContext gc)
   {
      //read from the 2D object array and based on data draw the level
   
   }
   
   public class KeyHandler implements EventHandler<KeyEvent>
   {
      public void handle(KeyEvent event)
      {
         //based on arrow key press, move player
         
         //check for collision
         checkCollision(player);
          
      }
      
      //call draw() method
   }
      
   
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
      }
   }

   public class ButtonHandler implements EventHandler<ActionEvent>
   {
      public void handle( ActionEvent e)
      {  
         //if menu button pressed, remove menu
      }
   }
}
