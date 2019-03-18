package ch.bfh.freyt2.minesweeper.settings;

public class Difficultie {
    private final String name;
    private final int width;
    private final int height;
    private final int mines;

    public Difficultie(String name, int width, int height, int mines){
        this.name = name;
        this.width = width;
        this.height = height;
        this.mines = mines;
    }

    public void applyDifficulty(){
        Settings.setBoardheight(this.height);
        Settings.setBoardwidth(this.width);
        Settings.setBombs(this.mines);
    }

    @Override
    public String toString() {
        return name;
    }
}
