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
   
   public static void main(String[] args)
   {
      launch(args);//launch program
     
   }

}
