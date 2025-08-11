package Geometry;

/**
 * Class Velocity.
 * represents the speed of the ball.
 * velocity can be represented by changing the index of the center point of the ball.
 */
public class Velocity {
    /**
     * difference between every step at x-axis.
     */
    private double dx;
    /**
     * difference between every step at y-axis.
     */
    private double dy;
    /**
     * set a speed to the ball.
     * @param dx the speed on x-axis.
     * @param dy the speed on y-axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * change the index of the center point based on the speed of dx and dy.
     * @param p the point before moving it.
     * @return the point after moving it.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
    /**
     * convert the change of speed from angle and speed to difference between x and y,
     * which we call dx and dy.
     * @param angle the direction that we want the ball to move to it.
     * @param speed the speed of the ball that we want the ball to use it.
     *              (higher speed means bigger difference between first and last point).
     * @return velocity which we defined earlier in the begging of the class.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.sin(angle - 90);
        double dy = speed * Math.cos(angle - 90);
        return new Velocity(dx, dy);
    }
    /**
     * @return the speed of the ball on x-axis.
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * @return the speed of the ball on y-axis.
     */
    public double getDy() {
        return this.dy;
    }
}