/* practice main for animation 
written by Jake Bell */

import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.animation.*;
import java.util.*;
import java.net.*;
import javafx.geometry.*;

public class practice_main extends Application
{
   
   public void start( Stage stage)
   {
      //set up root
      Group root = new Group();
      Scene scene = new Scene( root);
      stage.setScene( scene );
      stage.setTitle( "practice animation time" );
      
      //instantiate the canvas object
      Canvas canvas = new Canvas(400, 200);
      
      //add canvas to root
      root.getChildren().add(canvas);
      
      //set up graphics
      GraphicsContext gc = canvas.getGraphicsContext2D();

      //add image
      Image jukebox_1 = new Image("pics\\jukebox_frame_1_small.png");
      Image jukebox_2 = new Image("pics\\jukebox_frame_2_small.png");
      //gc.drawImage( image, 100, 50);
      
      
      //set up animation timer
      final long startTime = System.nanoTime();

      
      new AnimationTimer()
      {
         public void handle( long currentTime)
         {
            double time_past = (currentTime - startTime) / 100000000; //this makes it in seconds
            
            System.out.println( time_past);
            
            if( time_past % 5 == 0 ) //checks if even or odd second, activates if even
            {
               //System.out.println("Divisible by 2");
               gc.setFill( Color.WHITE);
               gc.fillRect( 0, 0, 400, 200); //clear screen
               gc.drawImage( jukebox_1, 100, 50); //place image
            }
            else
            {
               gc.setFill( Color.WHITE);
               gc.fillRect( 0, 0, 400, 200); //clear screen
               gc.drawImage( jukebox_2, 100, 50); //place image
            }
            
            //gc.draw();
         }
      
      }.start();

      
      stage.show();
      //canvas.requestFocus(); //don't know what this does
   }
   
   public static void main( String[] args)
   {
      launch(args);
   } 
}