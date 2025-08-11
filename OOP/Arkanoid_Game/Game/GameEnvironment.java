package Game;

import biuoop.DrawSurface;

import java.util.LinkedList;
import java.util.List;

import Geometry.Line;
import Geometry.Point;
import Sprites.Collidable;
import Sprites.CollisionInfo;

/**
 * Class GameEnvironment.
 * has collection of every collidable object in the game.
 * we use that class to draw these objects, add new objects and even use specification of these objects.
 */
public class GameEnvironment {
    /**
     * a linked list collecting every possible colliding object in the game.
     */
    private List<Collidable> collidableList;

    /**
     * creates new environment to the game.
     */
    public GameEnvironment() {
        this.collidableList = new LinkedList<Collidable>();
    }

    /**
     * add another collidable object to the game.
     * @param c
     */
    public void addCollidable(Collidable c) {
        this.collidableList.add(c);
    }

    /**
     * removes a block from my list.
     * @param collidable a block in the game.
     */
    public void removeCollidable(Collidable collidable) {
        this.collidableList.remove(collidable);
    }

    /**
     * check if there will be any collision in game,
     * if found, we return information about that object,
     * otherwise return null.
     * @param trajectory the trajectory of the object that will (maybe) collide soon.
     * @return information about the object that we will collide with it soon,
     *         if we will not collide, return null.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (collidableList.size() == 0) {
            return null;
        }
        int counter = 0;
        double distance = 999999;
        CollisionInfo closestObject = null;
        for (Collidable collidables : collidableList) {
            Point p = trajectory.closestIntersectionToStartOfLine(collidables.getCollisionRectangle());
            if (p != null) {
                if (trajectory.start().distance(p) < distance) {
                    distance = trajectory.start().distance(p);
                    closestObject = new CollisionInfo(p, collidables);
                    counter++;
                }
            }
        }
        if (counter == 0) {
            return null;
        }
        return closestObject;
    }

    /**
     * draw every collidable object in the game.
     * @param surface the surface that we draw on it.
     */
    public void drawCollidables(DrawSurface surface) {
        for (Collidable collidable : this.collidableList) {
            double x = collidable.getCollisionRectangle().getUpperLeft().getX();
            double y = collidable.getCollisionRectangle().getUpperLeft().getY();
            double width = collidable.getCollisionRectangle().getWidth();
            double height = collidable.getCollisionRectangle().getHeight();
            surface.fillRectangle((int) x, (int) y, (int) width, (int) height);
        }
    }

    /**
     * @return a list of every collidable object in the game.
     */
    public List<Collidable> getCollidableList() {
        return this.collidableList;
    }
}