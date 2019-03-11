package ch.bfh.freyt2.minesweeper.application;

import ch.bfh.freyt2.minesweeper.entities.Block;
import ch.bfh.freyt2.minesweeper.settings.Settings;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MinesweeperApplication extends Application {
    @Override
    public void start(Stage primaryStage){
        GridPane gamePane = createGrid();
        AnchorPane rootPane = new AnchorPane(gamePane);
        Scene gameScene = new Scene(rootPane, Settings.WIDTH,Settings.HEIGHT);
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    /**
     * adds bombs to the GridPane
     * @param gamePane
     */
    private void addBombs(GridPane gamePane){
        int i = 0;
        while( i<Settings.BOMBS){
            int x = (int)((Math.random()) * ((Settings.SIZE)));
            int y = (int)((Math.random()) * ((Settings.SIZE)));
            Block selected = getNodeByRowColumnIndex(x,y,gamePane);
            if(selected.isBomb()){
                continue;
            }else{
                selected.setBomb(true);
                i++;
            }
        }
    }

    /**
     * creates a new gamegrid
     * @return the newly created grid
     */
    private GridPane createGrid(){
        GridPane gamePane = new GridPane();
        // add the game squares
        for(int i = 0; i< Settings.SIZE; i++){
            for(int j = 0; j< Settings.SIZE; j++){
                gamePane.add(new Block(false,i,j),i,j);
            }
        }
        addBombs(gamePane);
        return gamePane;
    }

    private Block getNodeByRowColumnIndex(final int row, final int column, GridPane gamePane) {
        Node result = null;
        ObservableList<Node> childrens = gamePane.getChildren();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return (Block)result;
    }

}
