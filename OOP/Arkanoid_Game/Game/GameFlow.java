package Game;

import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.util.List;

import Animation.AnimationRunner;
import Animation.GameLevel;
import Animation.LevelInformation;
import Animation.LoseScreen;
import Animation.WinScreen;

/**
 * Class GameFlow.
 * here we gather all the levels and run them one after another.
 */
public class GameFlow {
    private AnimationRunner runner;
    private KeyboardSensor keyboardSensor;
    private GUI gui;

    /**
     * game flow constructor.
     * @param ar the animation runner.
     * @param ks the keyboard sensor.
     * @param gui the gui.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui) {
        this.runner = ar;
        this.keyboardSensor = ks;
        this.gui = gui;
    }

    /**
     * the method that runs all the levels one after another.
     * @param levels list of the levels that we want to play.
     */
    public void runLevels(List<LevelInformation> levels) {
        int finalScore = 0;
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(this.gui, levelInfo);
            level.initialize();
            level.run();
            if (level.getBallCounter() == 0) {
                LoseScreen loseScreen = new LoseScreen(this.keyboardSensor, level.getFinalScore());
                this.runner.run(loseScreen);
                return;
            }
            finalScore = level.getFinalScore();
        }
        WinScreen winScreen = new WinScreen(this.keyboardSensor, finalScore);
        this.runner.run(winScreen);
    }
}
