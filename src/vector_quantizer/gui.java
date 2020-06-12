package vector_quantizer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.util.ArrayList;



import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
public class gui extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("vector  ");
        TextField hieght=new TextField();
        TextField width=new TextField();
        TextField codebook=new TextField();
        compression n1=new compression();
        Button button = new Button("  compress ");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ImageClass i=new ImageClass();
                int [][]pixels=ImageClass.readImage("D:\\cameraMan.jpg");
              /*  int[][] pixels={ { 1, 2, 7, 9, 4, 11 }, { 3, 4, 6, 6, 12, 12 }, { 4, 9, 15, 14, 9, 9 }, { 10, 10, 20, 18, 8, 8 },
                        { 4, 3, 17, 16, 1, 4 }, { 4, 5, 18, 18, 5, 6 } };
*/
                String x=hieght.getText();
                String y=width.getText();
                String z=codebook.getText();
                int h=Integer.parseInt(x);
                int w=Integer.parseInt(y);
                int c=Integer.parseInt(z);

                try {
                    n1.compress(pixels,h,w,c);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button2 = new Button(" decompress ");
        ImageView iv1 = new ImageView();
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    n1.decompress();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }

        });
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(hieght);
        layout.getChildren().addAll(width);
        layout.getChildren().addAll(codebook);

        HBox layout1 = new HBox(10);
        layout1.setPadding(new Insets(20, 20, 20, 20));
        layout1.getChildren().addAll(button, button2);





        BorderPane borderPane = new BorderPane();
        borderPane.setTop(layout);
        borderPane.setLeft(layout1);

        Scene scene = new Scene(borderPane, 300, 250);
        window.setScene(scene);


        window.show();


    }

}