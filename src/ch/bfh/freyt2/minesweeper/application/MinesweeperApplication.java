package ch.bfh.freyt2.minesweeper.application;

import ch.bfh.freyt2.minesweeper.entities.Block;
import ch.bfh.freyt2.minesweeper.gamestates.GameState;
import ch.bfh.freyt2.minesweeper.settings.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * TODO: change the header to look ok
 * TODO: add multiple difficulties
 * TODO: add a bot that tests if the game can be solved everytime
 * TODO: find a better flag image
 * TODO: remove wrong placed flags if you loose
 */
public class MinesweeperApplication extends Application {
    private static final String textStyle = "-fx-font-size: 24px;\n" +
            "    -fx-font-weight: bold;\n" +
            "    -fx-text-fill: white;\n" +
            "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );";
    public static Timeline timeline;
    public static boolean firstClick = true;
    private static GridPane gamePane;
    private static int minesleft = Settings.getBombs();
    private static int time = 0;
    private static Label minesleftlabel = new Label(String.valueOf(minesleft));
    private static Label gameStateLabel = new Label("");
    private static Label timerLabel = new Label(String.valueOf(time));
    private static int blocksleft;
    private static Stage gameStage;
    public static GameState gamestate = GameState.RUNNING;

    @Override
    public void start(Stage primaryStage) {
        gameStage = primaryStage;
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> increaseTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        createGrid();
        HBox titleTextPane = new HBox(timerLabel, minesleftlabel);
        VBox gameBox = new VBox(titleTextPane,gamePane);
        gameBox.setAlignment(Pos.CENTER);
        AnchorPane rootPane = new AnchorPane(gameBox,gameStateLabel);

        gameBox.setLayoutX(0);
        gameBox.setLayoutY(0);
        gamePane.setAlignment(Pos.CENTER);
        gamePane.setMinHeight(Settings.getBoardheight()*32);
        gamePane.setMinWidth(Settings.getBoardwidth()*32);
        rootPane.setStyle("-fx-background-color: black");
        Scene gameScene = new Scene(rootPane, Settings.getWidth(), Settings.getHeight());
        restart();
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                restart();
            }
        });
        minesleftlabel.setMinSize((double)Settings.getWidth()/2-10, 42);
        gameStateLabel.setMinSize(Settings.getWidth(), 40);
        gameStateLabel.setLayoutY((double)(Settings.getHeight()-40)/2);
        gameStateLabel.toBack();
        timerLabel.setMinSize((double)Settings.getWidth()/2-10, 42);
        gameStateLabel.setAlignment(Pos.CENTER);
        minesleftlabel.setAlignment(Pos.CENTER);
        timerLabel.setAlignment(Pos.CENTER);
        timerLabel.setStyle(textStyle);
        gameStateLabel.setStyle(textStyle);
        minesleftlabel.setStyle(textStyle);
        gameStage.setResizable(false);
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    /**
     * removes all of the bombs and places them again random
     */
    public static void resetBombs() {
        //remove all bombs
        for (Node block : gamePane.getChildren()) {
            ((Block) block).resetBlock();
        }
        //add them again
        addBombs();
    }

    /**
     * adds bombs to the GridPane
     */
    private static void addBombs() {
        int i = 0;
        while (i < Settings.getBombs()) {
            int x = (int) ((Math.random()) * ((Settings.getBoardwidth())));
            int y = (int) ((Math.random()) * ((Settings.getBoardheight())));
            Block selected = getNodeByRowColumnIndex(y, x);
            if (!selected.isBomb()) {
                selected.setBomb(true);
                i++;
            }
        }
    }

    /**
     * resets all the variables used for running the game
     */
    private void restart() {
        gameStateLabel.setTextFill(Color.WHITE);
        gameStage.setWidth(Settings.getWidth());
        gameStage.setHeight(Settings.getHeight());
        time = 0;
        timerLabel.setText(String.valueOf(time));
        firstClick = true;
        gamestate = GameState.RUNNING;
        minesleft = Settings.getBombs();
        gameStateLabel.setText("");
        minesleftlabel.setText(String.valueOf(minesleft));
        gameStateLabel.toBack();
        resetBombs();
        blocksleft = (((Settings.getBoardwidth()) * (Settings.getBoardheight())) - Settings.getBombs());
    }

    /**
     * creates a new gamegrid
     */
    private void createGrid() {
        gamePane = new GridPane();
        // add the game squares
        for (int i = 0; i < Settings.getBoardwidth(); i++) {
            for (int j = 0; j < Settings.getBoardheight(); j++) {
                gamePane.add(new Block(false, i, j), i, j);
            }
        }
        addBombs();
    }

    /**
     * get a block by its position in the grid
     * @param row the row of the block
     * @param column the column of the block
     * @return the block at the specified position
     */
    public static Block getNodeByRowColumnIndex(final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = gamePane.getChildren();

        for (Node node : childrens) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return (Block) result;
    }

    /**
     * used to count how many mines are left
     * @param amount how many bombs were added or removed
     */
    public static void changeBombsLeft(int amount) {
        minesleft += amount;
        minesleftlabel.setText(String.valueOf(minesleft));
    }

    /**
     * used to count how many bocks are left to uncover
     */
    public static void decreaseBlockLeft() {
        blocksleft--;
        //System.out.println("decreaseBlockLeft "+blocksleft+" / "+(((Settings.SIZE) * (Settings.SIZE)) - Settings.BOMBS));
        if (blocksleft == 0) {
            win();
        }
    }

    public static void win() {
        gamestate = GameState.WON;
        gameStateLabel.setText("WON");
        gameStateLabel.setTextFill(Color.GREEN);
        gameStateLabel.toFront();
        timeline.stop();
    }

    public static void lose() {
        gamestate = GameState.LOST;
        gameStateLabel.setText("Lost");
        gameStateLabel.setTextFill(Color.RED);
        gameStateLabel.toFront();
        timeline.stop();
    }

    private void increaseTime(){
        time++;
        timerLabel.setText(String.valueOf(time));
    }
}
