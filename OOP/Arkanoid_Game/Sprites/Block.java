package Sprites;

import biuoop.DrawSurface;

import java.util.List;

import Animation.GameLevel;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;
import Listeners.HitListener;
import Listeners.HitNotifier;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class Block.
 * simply a block can be represented by a rectangle and color.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    /**
     * the body of the block (a rectangle).
     */
    private Rectangle block;

    /**
     * the color of the block.
     */
    private Color color;

    /**
     * list if listeners.
     */
    private List<HitListener> hitListeners;

    /**
     * a constructor that build a block.
     * @param block the shape of the block.
     * @param color the color of the block
     */
    public Block(Rectangle block, Color color) {
        this.block = block;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * draws a block on the screen.
     * @param surface the surface that we want to draw on.
     */
    @Override
    public void drawOn(DrawSurface surface) {

        surface.setColor(this.color);
        double x = this.block.getUpperLeft().getX();
        double y = this.block.getUpperLeft().getY();
        double width = this.block.getWidth();
        double height = this.block.getHeight();
        surface.fillRectangle((int) x, (int) y, (int) width, (int) height);
        surface.setColor(Color.black);
        surface.drawRectangle((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * we use that for choosing the block that we want to collide with.
     * @return the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * changes the direction of the ball depending on which side the ball hits.
     * @param collisionPoint that point that the ball will hit soon.
     * @param currentVelocity current velocity that the ball has.
     * @return a new velocity if the ball hits any side of the block.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        // that one for the left and right sides
        if (this.block.getUpperLeft().getX() - dx <= collisionPoint.getX()
                && this.block.getUpperLeft().getX() + dx >= collisionPoint.getX()
                || this.block.getUpperRight().getX() + dx <= collisionPoint.getX()
                && this.block.getUpperRight().getX() - dx >= collisionPoint.getX()) {
            dx = dx * -1;
            this.notifyHit(hitter);
            return new Velocity(dx, dy);
            // that one for the upper and lower side
        } else if (this.block.getUpperLeft().getY() + dy >= collisionPoint.getY()
                && this.block.getUpperLeft().getY() - dy <= collisionPoint.getY()
                || this.block.getLowerRight().getY() + dy <= collisionPoint.getY()
                && this.block.getLowerRight().getY() - dy >= collisionPoint.getY()) {
            dy = dy * -1;
            this.notifyHit(hitter);
            return new Velocity(dx, dy);
        } else {
            this.notifyHit(hitter);
            return currentVelocity;
        }
    }

    /**
     * @return the block of the rectangle.
     */
    public Rectangle getBlock() {
        return block;
    }

    /**
     * set a new color to the block.
     * @param color the new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * idk lol.
     */
    @Override
    public void timePassed() {
    }

    /**
     * add the block to the sprites and collidables.
     * @param gameLevel the game.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    /**
     * remove the block from the game.
     * @param g the game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    /**
     * I notify all the listeners, evey listener to do what he must do.
     * @param hitter the ball
     */
    public void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}