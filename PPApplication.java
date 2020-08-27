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
import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.stage.Stage; 







public class PPApplication extends Application
{
   public void start(Stage stage) throws FileNotFoundException 
   {
      
      //Create flowpane root
      FlowPane root = new FlowPane();
      
      //instantiate PPCanvas object canvas 
      PPCanvas canvas = new PPCanvas();
      
      //add canvas to root flowpane
      //root.getChildren().add(canvas);
      
      
      
      
      //ADD MENU IMAGE
   
      Image menuImage = new Image("https://www.myabandonware.com/media/screenshots/c/contraption-zack-1dc/contraption-zack_1.gif");
      //ImageView imageView = new ImageView(menuImage);

      BackgroundImage menuBackgroundImage = new BackgroundImage(menuImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
      //root.setBackground(new Background(menuBackgroundImage));
      
      // create Background 
      Background background = new Background(menuBackgroundImage); 
  
      // set background 
      root.setBackground(background);
      
      
      
      
      
       
      //Add menu buttons
      Button start = new Button("Start");
      Button load = new Button("load");
      root.getChildren().add(start);
      root.getChildren().add(load);      
      
      
      
      
      
      
      

      //Set scene
      Scene scene = new Scene(root, 500,500);//sets up window
      stage.setScene(scene);
      stage.setTitle("Puzzle Project Application");
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      //
      stage.show();
   
      canvas.requestFocus();
   }
   
   public static void main(String[] args)
   {
      launch(args);//launch program
     
   }

}
