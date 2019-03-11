package ch.bfh.freyt2.minesweeper.entities;

import ch.bfh.freyt2.minesweeper.gamestates.GameState;
import ch.bfh.freyt2.minesweeper.settings.Settings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is the most important graphic element of the game.
 * This block displays the numbers and the flags set
 */
public class Block extends Rectangle {
    private boolean bomb, clicked = false, flagged = false;
    private int x, y;
    public Block(boolean isBomb,int x,int y){
        super(32,32);
        this.x = x;
        this.y = y;
        // create the graphics
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.BLACK);

        this.bomb = isBomb;
        this.setOnMouseClicked(event -> {
            // check which mouseButton clicked the Block
            // if it was the secondary button then change the isFlagged variable
            if (event.getButton() == MouseButton.SECONDARY) {
                changeFlagged();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                clickSquare();
            }
        });
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
            calculateAdjacentBombs();

            // TODO:change the text to the amount of adjacent bombs

            // TODO: show this square
            this.setFill(Color.GREEN);
            // TODO: if adjacentbombs = 0 then click all of the adjacent squares
        }
    }

    /**
     * sets the text to the amount of adjacent bombs
     */
    private int calculateAdjacentBombs() {
        int bombneighbors = 0;
        if(this.x<Settings.SIZE&&this.y>0) {
            if(getNodeByRowColumnIndex(this.x+1,this.y-1).isBomb()){
                bombneighbors++;
            }
        }
        if(this.x<Settings.SIZE) {
            if(getNodeByRowColumnIndex(this.x+1,this.y).isBomb()){
                bombneighbors++;
            }
        }
        if(this.x<Settings.SIZE&&this.y<Settings.SIZE) {
            if(getNodeByRowColumnIndex(this.x+1,this.y+1).isBomb()){
                bombneighbors++;
            }
        }

        if(this.y>0) {
            if(getNodeByRowColumnIndex(this.x,this.y-1).isBomb()){
                bombneighbors++;
            }
        }
        if(this.y<Settings.SIZE) {
            if(getNodeByRowColumnIndex(this.x,this.y+1).isBomb()){
                bombneighbors++;
            }
        }

        if(this.x>0&&this.y>0) {
            if(getNodeByRowColumnIndex(this.x-1,this.y-1).isBomb()){
                bombneighbors++;
            }
        }
        if(this.x>0) {
            if(getNodeByRowColumnIndex(this.x-1,this.y).isBomb()){
                bombneighbors++;
            }
        }
        if(this.x>0&&this.y<Settings.SIZE) {
            if(getNodeByRowColumnIndex(this.x-1,this.y+1).isBomb()){
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
}
