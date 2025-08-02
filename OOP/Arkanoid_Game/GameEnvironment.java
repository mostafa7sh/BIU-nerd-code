import java.util.ArrayList;
import java.util.List;

/**
 * Holds a collection of Collidable objects and computes collisions.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Creates an empty GameEnvironment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a Collidable to the environment.
     * @param c the Collidable to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Given a trajectory, returns information about the closest collision
     * with any Collidable in the environment, or null if no collision.
     * @param trajectory the line representing the object's movement
     * @return a CollisionInfo with the closest collision, or null
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestObject = null;

        for (Collidable c : this.collidables) {
            Rectangle rect = c.getCollisionRectangle();
            Point intersection = trajectory.closestIntersectionToStartOfLine(rect);
            if (intersection != null) {
                if (closestPoint == null ||
                        intersection.distance(trajectory.start())
                                < closestPoint.distance(trajectory.start())) {
                    closestPoint = intersection;
                    closestObject = c;
                }
            }
        }

        if (closestPoint != null) {
            return new CollisionInfo(closestPoint, closestObject);
        }
        return null;
    }
    public List<Collidable> getCollidables() {
        return this.collidables;
    }
}
