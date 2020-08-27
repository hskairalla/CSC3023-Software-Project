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




public class PracticeApplication extends Application
{
   public void start(Stage stage)
   {
      //Create flowpane root
      FlowPane root = new FlowPane();
      
      //instantiate PPCanvas object canvas 
      //PPCanvas canvas = new PPCanvas();
      
      //add canvas to root flowpane
      //root.getChildren().add(canvas);
            
      
      Button b1 = new Button("Start");
      Button b2 = new Button("Load");
      b1.setPrefSize(150, 75);
      b2.setPrefSize(150, 75);
      
      
      TilePane r = new TilePane();
      r.getChildren().add(b1);
      r.getChildren().add(b2);
      //b1.setOnAction(new ButtonHandler());
      
      
      root.getChildren().add(r);
      
      root.setBackground(new Background( new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            

      //Set scene
      Scene scene = new Scene(root, 800, 800);//sets up window 
      stage.setScene(scene);
      stage.setTitle("Puzzle Project Application");
      stage.show();
   
      //canvas.requestFocus();
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   public static void main(String[] args)
   {
      launch(args);//launch program
     
   }

   
  
}
