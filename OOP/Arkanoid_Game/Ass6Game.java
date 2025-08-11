import biuoop.GUI;
import biuoop.KeyboardSensor;

import Sprites.ScoreIndicator;
import Animation.AnimationRunner;
import Animation.LevelInformation;
import Animation.DirectHit;
import Animation.WideEasY;
import Animation.Green3;
import Game.GameFlow;

import java.util.LinkedList;
import java.util.List;

/**
 * Class Ass6Game.
 * the main project ends here.
 */
public class Ass6Game {
    /**
     * speed is the best.
     * @param args the order of the levels.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", 800, 600);
        ScoreIndicator score = new ScoreIndicator();
        KeyboardSensor ks = gui.getKeyboardSensor();
        GameFlow flow = new GameFlow(new AnimationRunner(gui, 60), ks, gui);
        List<LevelInformation> gameLevels = new LinkedList<LevelInformation>();

        for (String arg : args) {
            switch (arg) {
                case "1" -> gameLevels.add(new DirectHit(gui, score));
                case "2" -> gameLevels.add(new WideEasY(gui, score));
                case "3" -> gameLevels.add(new Green3(gui, score));
                default -> {
                }
            }
        }
        if (gameLevels.size() == 0) {
            gameLevels.add(new DirectHit(gui, score));
            gameLevels.add(new WideEasY(gui, score));
            gameLevels.add(new Green3(gui, score));
        }
        flow.runLevels(gameLevels);
        gui.close();
    }
}