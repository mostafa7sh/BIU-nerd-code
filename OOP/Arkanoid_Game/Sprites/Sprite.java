package Sprites;

import biuoop.DrawSurface;

/**
 * Interface Sprite.
 * simply collecting every moving object.
 */
public interface Sprite {
    /**
     * draw sprites on screen.
     * @param d the surface.
     */
    void drawOn(DrawSurface d);

    /**
     * notify sprites that time has passed.
     */
    void timePassed();
}