package Sprites;

import biuoop.DrawSurface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Animation.GameLevel;
import Game.GameEnvironment;
import Geometry.Line;
import Geometry.Point;
import Geometry.Velocity;
import Listeners.HitListener;

/**
 * Class Ball.
 * Represents a ball which can has color and move like a ball :).
 * ball can be represented as center point, color, movement speed, and its size.
 */
public class Ball implements Sprite {
    /**
     * the center point of the ball.
     */
    private Point point;
    /**
     * the speed of the ball.
     */
    private Velocity velocity;
    /**
     * the size of the ball.
     */
    private int size;
    /**
     * the color of the ball.
     */
    private Color color;
    /**
     * the environment of the game.
     */
    private GameEnvironment gameEnvironment;

    /**
     * list if listeners.
     */
    private List<HitListener> hitListeners;

    /**
     * creating a new ball.
     * @param center the center point of the ball.
     * @param r the size of the ball.
     * @param color the color of the ball.
     * @param game the game environment of the ball.
     */
    public Ball(Point center, int r, Color color, GameEnvironment game) {
        this.point = center;
        this.size = r;
        this.color = color;
        this.gameEnvironment = game;
        this.hitListeners = new ArrayList<HitListener>();
    }
    /**
     * creates a new ball, and giving it index of the center, size and color.
     * @param center the center point of the ball.
     * @param r is the size of the ball.
     * @param color is the color of the ball.
     */
    public Ball(Point center, int r, Color color) {
        this.point = new Point(center.getX(), center.getY());
        this.color = color;
        this.size = r;
        this.hitListeners = new ArrayList<HitListener>();
    }
    /**
     * creates a new ball, and giving it index of the center, size and color.
     * @param x the x value of the center point.
     * @param y the y value of the center point.
     * @param r is the size of the ball.
     * @param color is the color of the ball.
     */
    public Ball(int x, int y, int r, Color color) {
        this.point = new Point(x, y);
        this.color = color;
        this.size = r;
        this.hitListeners = new ArrayList<HitListener>();
    }
    /**
     * @return the x value of the center of the ball.
     */
    public int getX() {
        return (int) this.point.getX();
    }
    /**
     * @return the y value of the center of the ball.
     */
    public int getY() {
        return (int) this.point.getY();
    }
    /**
     * @return the size of the ball.
     */
    public int getSize() {
        return this.size;
    }
    /**
     * @return the color of the ball.
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * draw the bal on the screen.
     * @param surface the surface that we draw the ball on it.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.point.getX(), (int) this.point.getY(), this.size);
    }

    /**
     * set a speed to the ball.
     * @param v the speed of the ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * set a speed to the ball.
     * @param dx the speed of the ball on the x-axis.
     * @param dy the speed of the ball on the y-axis.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * @return the speed of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }
    /**
     * move the ball to its new place.
     */
    public void moveOneStep() {
        Point end = this.velocity.applyToPoint(this.point);
        Line ballPath = new Line(this.point, end);
        CollisionInfo blockInfo = this.gameEnvironment.getClosestCollision(ballPath);
        if (blockInfo == null) {
            this.point = end;
        } else {
            Point lineEnd = blockInfo.collisionPoint();
            Line almost = new Line(this.point, lineEnd);
            Line sooAlmost = new Line(this.point, almost.middle());
            this.point = sooAlmost.middle();
            this.velocity = blockInfo.collisionObject().hit(this, this.point, this.velocity);
        }
    }
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * remove the ball from the game if it went out of the screen.
     * @param g the game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    /**
     * add all the balls to the game.
     * @param gameLevel the game :).
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    /**
     * set a new environment to the ball.
     * @param environment the new environment.
     */
    public void setEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }
}