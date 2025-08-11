package Sprites;

import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;

/**
 * interface Collidable.
 * help us use every collidable object at once.
 */
public interface Collidable {
    /**
     * @return the collision shape of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     * @param collisionPoint the collision point of the object.
     * @param currentVelocity the velocity before the collision.
     * @param hitter the ball.
     * @return a new velocity based on which side the ball hits the object.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}