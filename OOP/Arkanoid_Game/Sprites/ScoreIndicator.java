package Sprites;

import biuoop.DrawSurface;

import java.awt.*;

import Listeners.ScoreTrackingListener;

/**
 * Class ScoreIndicator.
 * here I show the score on the screen.
 */
public class ScoreIndicator implements Sprite {
    private ScoreTrackingListener score;

    /**
     * score indicator constructor.
     */
    public ScoreIndicator() {
        this.score = new ScoreTrackingListener();
    }

    /**
     * @return the score.
     */
    public ScoreTrackingListener getScore() {
        return this.score;
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText(380, 15, "score: " + this.score.getCurrentScore().getValue(), 15);
    }

    @Override
    public void timePassed() {
    }
}
