package minesveiper;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MinesveiperGUI extends VBox {
    
    private final double BREDDE = 216;
    private final double HØYDE = 216;
      
   
    private HBox meny = new HBox();
    private Button ny;
    private Label antBomber;
    

    public MinesveiperGUI(){
        tegnGUI();
    }
    
    public void tegnGUI(){
        
        ny = new Button("Nytt");
        ny.setOnAction((ActionEvent ActionEvent) -> {
           Minesveiper.tegnMS();
           antBomber.setText("Antall flagg: " + Minesveiper.antFlagg());
        });
        antBomber = new Label("Antall flagg: " + Minesveiper.antFlagg());
        antBomber.setPadding(new Insets(5, 80, 0, 0));
        
        meny.getChildren().addAll(antBomber, ny);
        meny.setPadding(new Insets(8, 5, 5, 10));
        //canvas.getGraphicsContext2D().fillRect(0, 0, BREDDE, HØYDE);
        Canvas canvas = Minesveiper.tegnMS();
        
        
        canvas.setOnMouseClicked((MouseEvent e) -> {
            if (e.getButton() == MouseButton.PRIMARY)
                Minesveiper.venstreTrykk(e.getX(), e.getY());
            else if (e.getButton() == MouseButton.SECONDARY){
                Minesveiper.høyreTrykk(e.getX(), e.getY());
                antBomber.setText("Antall flagg: " + Minesveiper.antFlagg());
            }
                
        });
        
        this.getChildren().addAll(meny, canvas);
        
    }
    
}
