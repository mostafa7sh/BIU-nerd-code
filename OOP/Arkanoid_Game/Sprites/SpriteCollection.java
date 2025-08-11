package Sprites;

import biuoop.DrawSurface;

import java.util.LinkedList;

/**
 * Class SpriteCollection.
 * represents a collection of sprites.
 */
public class SpriteCollection {

    /**
     * a collection of every sprite in the game.
     */
    private LinkedList<Sprite> spriteCollection;

    /**
     * creates new collection of sprites.
     */
    public SpriteCollection() {
        this.spriteCollection = new LinkedList<>();
    }

    /**
     * add a sprite to the collection.
     * @param s a sprite.
     */
    public void addSprite(Sprite s) {
        this.spriteCollection.add(s);
    }

    /**
     * remove a sprite from the game.
     * @param s a sprite.
     */
    public void removeSprite(Sprite s) {
        this.spriteCollection.remove(s);
    }

    /**
     * call timePassed() to all the sprites.
     */
    public void notifyAllTimePassed() {
        LinkedList<Sprite> spritesCopy = new LinkedList<>(this.spriteCollection);
        for (Sprite sprite : spritesCopy) {
            sprite.timePassed();
        }
    }
    /**
     * call drawOn(d) on all sprites.
     * @param d the surface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.spriteCollection) {
            sprite.drawOn(d);
        }
    }
}