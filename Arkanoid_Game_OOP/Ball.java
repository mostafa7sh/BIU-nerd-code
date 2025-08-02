// Ball.java
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.Objects;

/**
 * Represents a ball that moves according to its Velocity
 * and bounces off Collidable objects in a GameEnvironment.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment environment;

    public Ball(Point center, int r, Color color, GameEnvironment env) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.environment = Objects.requireNonNull(env);
    }

    public void addToGame(Game g) {
        g.addSprite(this);
    }

    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle((int) center.getX(), (int) center.getY(), this.radius);
        d.setColor(Color.BLACK);
        d.drawCircle((int) center.getX(), (int) center.getY(), this.radius);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    public void moveOneStep() {
        double eps = 1e-3;

        // 1) Compute trajectory
        Point nextCenter = new Point(center.getX() + velocity.getDx(),
                center.getY() + velocity.getDy());
        Line trajectory = new Line(center, nextCenter);

        // 2) Check for collision
        CollisionInfo info = environment.getClosestCollision(trajectory);
        if (info == null) {
            // no collision
            this.center = nextCenter;
        } else {
            // collision!
            Point collisionP = info.collisionPoint();
            Velocity oldV = this.velocity;
            Velocity newV = info.collisionObject().hit(collisionP, oldV);

            // detect which components flipped
            boolean flipX = Math.signum(oldV.getDx()) != Math.signum(newV.getDx());
            boolean flipY = Math.signum(oldV.getDy()) != Math.signum(newV.getDy());
            double cx = collisionP.getX(), cy = collisionP.getY();
            double finalX = cx, finalY = cy;

            // back off only on flipped axes
            if (flipX) { finalX = cx - Math.signum(oldV.getDx()) * eps; }
            if (flipY) { finalY = cy - Math.signum(oldV.getDy()) * eps; }

            this.center   = new Point(finalX, finalY);
            this.velocity = newV;
        }
    }

    public Point getCenter() { return this.center; }
    public int getRadius() { return this.radius; }
}
