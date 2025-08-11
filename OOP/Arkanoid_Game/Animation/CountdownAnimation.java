package Animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.*;

import Sprites.SpriteCollection;

/**
 * Class CountdownAnimation.
 * count down from the given number to 0 before starting the game.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private double countFrom;
    private SpriteCollection gameScreen;
    private Sleeper sleeper;
    private boolean stop;
    private double timePassed;

    /**
     * count down animation constructor.
     * @param numOfSeconds how much time must the count-down last.
     * @param countFrom starting number of counting down.
     * @param gameScreen the game screen.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.sleeper = new Sleeper();
        this.stop = false;
        this.timePassed = 0;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        double timePerNumber = this.numOfSeconds / this.countFrom;
        int currentNumber = (int) (this.countFrom - (this.timePassed / timePerNumber)) + 1;

        this.gameScreen.drawAllOn(d);
        d.setColor(Color.black);
        d.drawText(d.getWidth() / 2 - 50, d.getHeight() / 2, Integer.toString(currentNumber), 100);

        if (currentNumber > 0) {
            this.timePassed += 1 / 60.0;
            this.sleeper.sleepFor(1000 / 60);
        } else {
            d.drawText(d.getWidth() / 2 - 50, d.getHeight() / 2, "Go!", 100);
            this.stop = true;
        }
    }


    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}