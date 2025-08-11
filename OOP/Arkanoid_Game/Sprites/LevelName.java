package Sprites;

import biuoop.DrawSurface;

import java.awt.*;

/**
 * Class LevelName.
 * a class that contains the level name, and prints it on the screen.
 */
public class LevelName implements Sprite {
    private String levelName;

    /**
     * level name constructor.
     * @param levelName the level name.
     */
    public LevelName(String levelName) {
        this.levelName = levelName;
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText(580, 15, "Level Name: " + this.levelName, 15);
    }

    @Override
    public void timePassed() {
    }
}
