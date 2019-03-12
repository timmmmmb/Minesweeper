package ch.bfh.freyt2.minesweeper.application;

import ch.bfh.freyt2.minesweeper.entities.Block;
import ch.bfh.freyt2.minesweeper.gamestates.GameState;
import ch.bfh.freyt2.minesweeper.settings.Settings;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * TODO: change the color of the Labels dependant of the amount of adjacent bombs
 * TODO: change the header to look ok
 * TODO: add a bot that tests if the game can be solved everytime
 * TODO: add multiple difficulties
 * TODO: add a color when hovering blocks
 */
public class MinesweeperApplication extends Application {
    public static boolean firstClick = true;
    private static GridPane gamePane;
    private static int minesleft = Settings.BOMBS;
    private static Label minesleftlabel = new Label(String.valueOf(minesleft));
    private static Label gameStateLabel = new Label("");
    private static int blocksleft;
    public static GameState gamestate = GameState.RUNNING;
    @Override
    public void start(Stage primaryStage){
        createGrid();
        restart();
        VBox rootPane = new VBox(gameStateLabel, minesleftlabel, gamePane);
        Scene gameScene = new Scene(rootPane, Settings.WIDTH,Settings.HEIGHT);
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                restart();
            }
        });
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public static void resetBombs(){
        //remove all bombs
        for(Node block:gamePane.getChildren()){
            ((Block) block).resetBlock();
        }
        //add them again
        addBombs();
    }

    /**
     * adds bombs to the GridPane
     */
    private static void addBombs(){
        int i = 0;
        while( i<Settings.BOMBS){
            int x = (int)((Math.random()) * ((Settings.SIZE)));
            int y = (int)((Math.random()) * ((Settings.SIZE)));
            Block selected = getNodeByRowColumnIndex(x,y);
            if(!selected.isBomb()){
                selected.setBomb(true);
                i++;
            }
        }
    }

    private void restart(){
        firstClick = true;
        gamestate = GameState.RUNNING;
        minesleft = Settings.BOMBS;
        gameStateLabel.setText("");
        minesleftlabel.setText(String.valueOf(minesleft));
        blocksleft = (Settings.SIZE*Settings.SIZE)-Settings.BOMBS;
        resetBombs();
        minesleftlabel.setMinSize(320,80);
        gameStateLabel.setMinSize(320,40);
        gameStateLabel.setFont(new Font("Arial", 24));
        gameStateLabel.setAlignment(Pos.CENTER);
        minesleftlabel.setFont(new Font("Arial", 24));
        minesleftlabel.setAlignment(Pos.CENTER);
    }

    /**
     * creates a new gamegrid
     */
    private void createGrid(){
        gamePane = new GridPane();
        // add the game squares
        for(int i = 0; i< Settings.SIZE; i++){
            for(int j = 0; j< Settings.SIZE; j++){
                gamePane.add(new Block(false,i,j),i,j);
            }
        }
        addBombs();
    }

    public static Block getNodeByRowColumnIndex(final int row, final int column) {
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

    public static void changeBombsLeft(int amount){
        minesleft += amount;
        minesleftlabel.setText(String.valueOf(minesleft));
    }

    public static void decreaseBlockLeft(){
        blocksleft--;
        if(blocksleft == 0){
            win();
        }
    }

    public static void win(){
        gamestate = GameState.WON;
        gameStateLabel.setText("WON");
    }

    public static void lose(){
        gamestate = GameState.LOST;
        gameStateLabel.setText("Lost");
    }
}
