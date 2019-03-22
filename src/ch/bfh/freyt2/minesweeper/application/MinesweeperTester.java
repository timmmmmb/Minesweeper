package ch.bfh.freyt2.minesweeper.application;

import ch.bfh.freyt2.minesweeper.gamestates.GameState;
import ch.bfh.freyt2.minesweeper.settings.Settings;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class MinesweeperTester extends Application {

    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            @Override
            public void run() {
                Application.launch(MinesweeperTester.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }
    /**
     * Tests if on the first click there might be a bomb
     */
    @Test
    public void testBombOnFirstClick() {
        for(int i = 0; i<= 1; i++){
            int x = (int) ((Math.random()) * ((Settings.getBoardwidth())));
            int y = (int) ((Math.random()) * ((Settings.getBoardheight())));
            MinesweeperApplication.getNodeByRowColumnIndex(x,y).clickSquare();
            assertSame(MinesweeperApplication.gamestate, GameState.RUNNING);
            MinesweeperApplication.restart();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
