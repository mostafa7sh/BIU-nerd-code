package Sprites;

import Geometry.Point;

/**
 * Class CollisionInfo.
 * provide information about the object that will collide.
 * the object itself and the collision point.
 */
public class CollisionInfo {
    /**
     * collision point of the object.
     */
    private Point collisionPoint;
    /**
     * the object itself.
     */
    private Collidable collisionObject;

    /**
     * a constructor to the collider object.
     * @param point the collision point.
     * @param collisionObject the collider object.
     */
    public CollisionInfo(Point point, Collidable collisionObject) {
        this.collisionPoint = point;
        this.collisionObject = collisionObject;
    }

    /**
     * @return the collision point.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collider object.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}