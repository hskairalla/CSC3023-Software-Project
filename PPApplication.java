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


//trying to add image
//import javafx.scene.image.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.stage.Stage; 

//





public class PPApplication extends Application
{
   public void start(Stage stage) throws FileNotFoundException 
   {
      
      
      
      
      
      
      
      
      //Create flowpane root
      FlowPane root = new FlowPane();
      
      //instantiate PPCanvas object canvas 
      PPCanvas canvas = new PPCanvas();
      
      //add canvas to root flowpane
      root.getChildren().add(canvas);
      
      
      
      
      
      /*
      //ADD MENU IMAGE
      Image menuImage = new Image("https://www.myabandonware.com/media/screenshots/c/contraption-zack-1dc/contraption-zack_1.gif");
      ImageView imageView = new ImageView(menuImage);
      root.getChildren().add(imageView);
      */
      
      
      
      Image image = new Image("https://www.myabandonware.com/media/screenshots/c/contraption-zack-1dc/contraption-zack_1.gif");
      ImageView mv = new ImageView(image);
      
      //Group root = new Group();
      root.getChildren().addAll(mv);




      //Set scene
      Scene scene = new Scene(root, 500,500);//sets up window
      stage.setScene(scene);
      stage.setTitle("Puzzle Project Application");
      
      Button start = new Button("Start");
      Button load = new Button("load");
      
      canvas.getChildren().add(start);
      canvas.getChildren().add(load);
      //
      
      
      
      
      
      
      
      
      
      
      
      
      
      //
      stage.show();
   
      canvas.requestFocus();
   }
   
   public static void main(String[] args)
   {
      launch(args);//launch program
     
   }

}