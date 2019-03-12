package ch.bfh.freyt2.minesweeper.entities;

import ch.bfh.freyt2.minesweeper.application.MinesweeperApplication;
import ch.bfh.freyt2.minesweeper.gamestates.GameState;
import ch.bfh.freyt2.minesweeper.settings.Settings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * This is the most important graphic element of the game.
 * This block displays the numbers and the flags set
 */
public class Block extends Pane {
    private boolean bomb, clicked = false, flagged = false;
    private int x, y;
    private Label label = new Label("");
    public Block(boolean isBomb,int x,int y){
        this.setWidth(32);
        this.setHeight(32);
        this.x = x;
        this.y = y;
        setDefaultStyle();
        // create the graphics
        label.setAlignment(Pos.CENTER);
        label.setMinSize(32,32);
        label.setFont(new Font("Arial", 24));
        this.getChildren().addAll(label);
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
        if(isClicked()||MinesweeperApplication.gamestate!=GameState.RUNNING)return;
        flagged = !flagged;
        if(isFlagged()){
            MinesweeperApplication.changeBombsLeft(-1);
            if((x+y)%2 == 0){
                this.setStyle("-fx-background-color: "+Settings.tileColor1+";-fx-background-image: url(images/flag.png)");
            }else{
                this.setStyle("-fx-background-color: "+Settings.tileColor2+";-fx-background-image: url(images/flag.png)");
            }
        }else{
            setDefaultStyle();
            MinesweeperApplication.changeBombsLeft(1);
        }
    }

    /**
     * used to click a square
     */
    private void clickSquare(){
        if(isFlagged()||isClicked()||MinesweeperApplication.gamestate!=GameState.RUNNING)return;
        //if this is the first click
        if(MinesweeperApplication.firstClick){
            if(isBomb()||calculateAdjacentBombs()!=0){
                MinesweeperApplication.resetBombs();
                this.clickSquare();
            }else{
                MinesweeperApplication.firstClick = false;
            }
        }
        clicked = true;
        // game lost is bomb
        if(isBomb()){
            this.setStyle("-fx-background-color: Red");
            MinesweeperApplication.lose();
        }else{
            MinesweeperApplication.decreaseBlockLeft();
            // calculate adjacent Bombs
            int neighborBombs = calculateAdjacentBombs();
            if((x+y)%2 == 0){
                this.setStyle("-fx-background-color: "+Settings.tileColorClicked1);
            }else{
                this.setStyle("-fx-background-color: "+Settings.tileColorClicked2);
            }
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

    private void clickNeighbors(){
        for(Block neighbor: getNeighbors()){
            neighbor.clickSquare();
        }
    }

    private ArrayList<Block> getNeighbors(){
        ArrayList<Block> neighbors = new ArrayList<>();
        if(this.x+1<Settings.SIZE&&this.y>0) {
            neighbors.add(MinesweeperApplication.getNodeByRowColumnIndex(this.y-1,this.x+1));
        }
        if(this.x+1<Settings.SIZE) {
            neighbors.add(MinesweeperApplication.getNodeByRowColumnIndex(this.y,this.x+1));
        }
        if(this.x+1<Settings.SIZE&&this.y+1<Settings.SIZE) {
            neighbors.add(MinesweeperApplication.getNodeByRowColumnIndex(this.y+1,this.x+1));
        }

        if(this.y>0) {
            neighbors.add(MinesweeperApplication.getNodeByRowColumnIndex(this.y-1,this.x));
        }
        if(this.y+1<Settings.SIZE) {
            neighbors.add(MinesweeperApplication.getNodeByRowColumnIndex(this.y+1,this.x));
        }

        if(this.x>0&&this.y>0) {
            neighbors.add(MinesweeperApplication.getNodeByRowColumnIndex(this.y-1,this.x-1));
        }
        if(this.x>0) {
            neighbors.add(MinesweeperApplication.getNodeByRowColumnIndex(this.y,this.x-1));
        }
        if(this.x>0&&this.y+1<Settings.SIZE) {
            neighbors.add(MinesweeperApplication.getNodeByRowColumnIndex(this.y+1,this.x-1));
        }
        return neighbors;
    }

    private void setDefaultStyle(){
        if((this.x+this.y)%2 == 0){
            this.setStyle("-fx-background-color: "+Settings.tileColor1);
        }else{
            this.setStyle("-fx-background-color: "+Settings.tileColor2);
        }
    }

    public void resetBlock(){
        label.setText("");
        setDefaultStyle();
        setBomb(false);
        clicked = false;
        flagged = false;
    }
}
