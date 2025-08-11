package Sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;

import Animation.GameLevel;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;

/**
 * Class Paddle.
 * simply this class represents the paddle in arkanoid game.
 */
public class Paddle implements Sprite, Collidable {
    /**
     * keyboard sensor.
     */
    private KeyboardSensor keyboard;
    /**
     * the body of the paddle.
     */
    private Rectangle rectangle;
    /**
     * the speed of the paddle.
     */
    private double speed;

    /**
     * paddle constructor.
     * @param rectangle the body of the paddle.
     * @param keyboard keyboard sensor.
     * @param speed the speed of the paddle.
     */
    public Paddle(Rectangle rectangle, KeyboardSensor keyboard, double speed) {
        this.rectangle = rectangle;
        this.keyboard = keyboard;
        this.speed = speed;
    }

    /**
     * moves the paddle to the left.
     */
    public void moveLeft() {
        if (this.rectangle.getUpperLeft().getX() > 20) {
            Point newIndex = new Point(this.rectangle.getUpperLeft().getX() - speed,
                    this.rectangle.getUpperLeft().getY());
            this.rectangle = new Rectangle(newIndex, this.rectangle.getWidth(), this.rectangle.getHeight());
        }
    }

    /**
     * moves the paddle to the right.
     */
    public void moveRight() {
        if (this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() < 780) {
            Point newIndex = new Point(this.rectangle.getUpperLeft().getX() + speed,
                    this.rectangle.getUpperLeft().getY());
            this.rectangle = new Rectangle(newIndex, this.rectangle.getWidth(), this.rectangle.getHeight());
        }
    }
    /**
     * sprite things, to move the paddle.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.yellow);
        d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double x = this.rectangle.getUpperLeft().getX();
        double collisionX = collisionPoint.getX();
        double width = this.rectangle.getWidth() / 5;
        double x1 = x + width;
        double x2 = x + 2 * width;
        double x3 = x + 3 * width;
        double x4 = x + 4 * width;
        double x5 = x + 5 * width;
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Point start = new Point(currentVelocity.getDx(), currentVelocity.getDy());
        Point end = currentVelocity.applyToPoint(start);
        Line line = new Line(start, end);
        if (this.rectangle.getUpperLeft().getX() - dx <= collisionPoint.getX()
                && this.rectangle.getUpperLeft().getX() + dx >= collisionPoint.getX()
                || this.rectangle.getUpperRight().getX() + dx <= collisionPoint.getX()
                && this.rectangle.getUpperRight().getX() - dx >= collisionPoint.getX()) {
            dx = -1 * currentVelocity.getDx();
            return new Velocity(dx, currentVelocity.getDy());
        }
        if (this.rectangle.getUpperLeft().getY() + dy >= collisionPoint.getY()
                && this.rectangle.getUpperLeft().getY() - dy <= collisionPoint.getY()
                || this.rectangle.getLowerRight().getY() + dy <= collisionPoint.getY()
                && this.rectangle.getLowerRight().getY() - dy >= collisionPoint.getY()) {
            if (x <= collisionX && collisionX <= x1) {
                return Velocity.fromAngleAndSpeed(300, line.length());
            } else if (x1 <= collisionX && collisionX <= x2) {
                return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
            } else if (x2 <= collisionX && collisionX <= x3) {
                return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
            } else if (x3 <= collisionX && collisionX <= x4) {
                return Velocity.fromAngleAndSpeed(30, line.length());
            } else if (x4 <= collisionX && collisionX <= x5) {
                return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
            }
            dy = -1 * currentVelocity.getDy();
            return new Velocity(currentVelocity.getDx(), dy);
        }
        return currentVelocity;
    }

    /**
     * add the paddle to the game.
     * @param gameLevel the game.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }
}