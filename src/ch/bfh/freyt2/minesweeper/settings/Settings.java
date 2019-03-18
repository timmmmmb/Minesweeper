package ch.bfh.freyt2.minesweeper.settings;

public class Settings {
    private static int boardheight = 10;
    private static int boardwidth = 10;
    private static int bombs = (boardheight*boardwidth)/4;
    private static int height = boardheight*32+80;
    private static int width = boardwidth*32+14;

    private static final String tileColor1 = "#5cf218";
    private static final String tileColor2 = "#7af442";
    private static final String tileColorHover1 = "#4ae006";
    private static final String tileColorHover2 = "#9df774";
    private static final String tileColorClicked1 = "#dbef6b";
    private static final String tileColorClicked2 = "#ebf998";
    private static final String adjacentBombsColor1 = "#2651ff";
    private static final String adjacentBombsColor2 = "#1bd12a";
    private static final String adjacentBombsColor3 = "#e53b19";
    private static final String adjacentBombsColor4 = "#006aff";
    private static final String adjacentBombsColor5 = "#e1ff00";
    private static final String adjacentBombsColor6 = "#ff9d00";
    private static final String adjacentBombsColor7 = "#ff2a00";
    private static final String adjacentBombsColor8 = "#ff0094";


    public static int getBoardheight() {
        return boardheight;
    }

    public static int getBoardwidth() {
        return boardwidth;
    }

    public static int getBombs() {
        return bombs;
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    public static String getTileColor1() {
        return tileColor1;
    }

    public static String getTileColor2() {
        return tileColor2;
    }

    public static String getTileColorHover1() {
        return tileColorHover1;
    }

    public static String getTileColorHover2() {
        return tileColorHover2;
    }

    public static String getTileColorClicked1() {
        return tileColorClicked1;
    }

    public static String getTileColorClicked2() {
        return tileColorClicked2;
    }

    public static String getAdjacentBombsColor1() {
        return adjacentBombsColor1;
    }

    public static String getAdjacentBombsColor2() {
        return adjacentBombsColor2;
    }

    public static String getAdjacentBombsColor3() {
        return adjacentBombsColor3;
    }

    public static String getAdjacentBombsColor4() {
        return adjacentBombsColor4;
    }

    public static String getAdjacentBombsColor5() {
        return adjacentBombsColor5;
    }

    public static String getAdjacentBombsColor6() {
        return adjacentBombsColor6;
    }

    public static String getAdjacentBombsColor7() {
        return adjacentBombsColor7;
    }

    public static String getAdjacentBombsColor8() {
        return adjacentBombsColor8;
    }

    static void setBoardheight(int boardheight) {
        Settings.boardheight = boardheight;
        Settings.height = boardheight*32+80;
    }

    static void setBoardwidth(int boardwidth) {
        Settings.boardwidth = boardwidth;
        Settings.width = boardwidth*32+14;
    }

    static void setBombs(int bombs) {
        Settings.bombs = bombs;
    }
}
