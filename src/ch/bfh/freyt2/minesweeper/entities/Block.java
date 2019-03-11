package ch.bfh.freyt2.minesweeper.entities;

import ch.bfh.freyt2.minesweeper.gamestates.GameState;
import ch.bfh.freyt2.minesweeper.settings.Settings;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * This is the most important graphic element of the game.
 * This block displays the numbers and the flags set
 */
public class Block extends Pane {
    private boolean bomb, clicked = false, flagged = false;
    private Rectangle rectangle = new Rectangle(32,32);
    private int x, y;
    private Label label = new Label("");
    public Block(boolean isBomb,int x,int y){
        this.x = x;
        this.y = y;
        // create the graphics
        label.setAlignment(Pos.CENTER);
        label.setMinSize(32,32);
        label.setFont(new Font("Arial", 30));
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        this.getChildren().addAll(rectangle,label);
        this.bomb = isBomb;
        this.setOnMouseClicked(event -> {
            // check which mouseButton clicked the Block
            // if it was the secondary button then change the isFlagged variable
            if (event.getButton() == MouseButton.SECONDARY) {
                changeFlagged();
            } else if (event.getButton() == MouseButton.PRIMARY) {
                clickSquare();
            }
        });
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isBomb() {
        return bomb;
    }

    public boolean isFlagged() {
        return flagged;
    }

    private void changeFlagged(){
        if(isClicked())return;
        flagged = !flagged;
    }

    /**
     * used to click a square
     */
    private void clickSquare(){
        if(isFlagged()||isClicked())return;
        clicked = true;
        // game lost is bomb
        if(isBomb()){
            Settings.gamestate = GameState.LOST;
        }else{
            // TODO:check if won
            // calculate adjacent Bombs
            int neighborBombs = calculateAdjacentBombs();
            rectangle.setFill(Color.GREEN);
            if(neighborBombs >0){
                label.setText(String.valueOf(neighborBombs));
                label.setTextFill(Color.RED);
            }else{
                clickNeighbors();
            }
        }
    }

    /**
     * sets the text to the amount of adjacent bombs
     */
    private int calculateAdjacentBombs() {
        int bombneighbors = 0;
        for(Block neighbor: getNeighbors()){
            if(neighbor.isBomb()){
                bombneighbors++;
            }
        }
        return bombneighbors;
    }

    private Block getNodeByRowColumnIndex(final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = this.getParent().getChildrenUnmodifiable();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return (Block)result;
    }

    private void clickNeighbors(){
        for(Block neighbor: getNeighbors()){
            neighbor.clickSquare();
        }
    }

    private ArrayList<Block> getNeighbors(){
        ArrayList<Block> neighbors = new ArrayList<>();
        if(this.x+1<Settings.SIZE&&this.y>0) {
            neighbors.add(getNodeByRowColumnIndex(this.y-1,this.x+1));
        }
        if(this.x+1<Settings.SIZE) {
            neighbors.add(getNodeByRowColumnIndex(this.y,this.x+1));
        }
        if(this.x+1<Settings.SIZE&&this.y+1<Settings.SIZE) {
            neighbors.add(getNodeByRowColumnIndex(this.y+1,this.x+1));
        }

        if(this.y>0) {
            neighbors.add(getNodeByRowColumnIndex(this.y-1,this.x));
        }
        if(this.y+1<Settings.SIZE) {
            neighbors.add(getNodeByRowColumnIndex(this.y+1,this.x));
        }

        if(this.x>0&&this.y>0) {
            neighbors.add(getNodeByRowColumnIndex(this.y-1,this.x-1));
        }
        if(this.x>0) {
            neighbors.add(getNodeByRowColumnIndex(this.y,this.x-1));
        }
        if(this.x>0&&this.y+1<Settings.SIZE) {
            neighbors.add(getNodeByRowColumnIndex(this.y+1,this.x-1));
        }
        return neighbors;
    }
}
