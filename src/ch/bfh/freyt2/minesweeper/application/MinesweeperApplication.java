package ch.bfh.freyt2.minesweeper.application;

import ch.bfh.freyt2.minesweeper.entities.Block;
import ch.bfh.freyt2.minesweeper.settings.Settings;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MinesweeperApplication extends Application {
    @Override
    public void start(Stage primaryStage){
        GridPane gamePane = new GridPane();
        // add the game squares
        for(int i = 0; i< Settings.SIZE; i++){
            for(int j = 0; j< Settings.SIZE; j++){
                gamePane.add(new Block(false,i,j),i,j);
            }
        }
        AnchorPane rootPane = new AnchorPane(gamePane);
        Scene gameScene = new Scene(rootPane, Settings.WIDTH,Settings.HEIGHT);
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }


}
