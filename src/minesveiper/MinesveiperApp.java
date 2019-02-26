package minesveiper;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MinesveiperApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        MinesveiperGUI m = new MinesveiperGUI();
        StackPane root = new StackPane();
        root.getChildren().add(m);
        Scene scene = new Scene(root, 200, 232);
        primaryStage.setTitle("MS");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
