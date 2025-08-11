package Animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Class WinScreen.
 * a winning screen special for you (ofc if you are a good player).
 */
public class WinScreen implements Animation {
    private KeyboardSensor keyboard;
    private int score;
    private boolean stop;

    /**
     * win screen constructor.
     * @param k the keyboard sensor.
     * @param score the cumulative score.
     */
    public WinScreen(KeyboardSensor k, int score) {
        this.keyboard = k;
        this.score = score;
        this.stop = false;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(200, d.getHeight() / 2, "You Win! Your score is " + this.score, 32);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}