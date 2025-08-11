package Animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Class LoseScreen.
 * opens a screen with "Game Over!" to the player.
 */
public class LoseScreen implements Animation {
    private KeyboardSensor keyboard;
    private int score;
    private boolean stop;

    /**
     * lose screen constructor.
     * @param k a keyboard sensor.
     * @param score the cumulative score.
     */
    public LoseScreen(KeyboardSensor k, int score) {
        this.keyboard = k;
        this.score = score;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(100, d.getHeight() / 2, "Game Over. Your score is " + this.score, 32);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
