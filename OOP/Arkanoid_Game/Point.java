/**
 * A simple class for representing a point in 2D space.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructs a Point with x and y coordinates.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate.
     * @return x-coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate.
     * @return y-coordinate.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Computes the Euclidean distance between this point and another.
     * @param other the other point.
     * @return the distance as a double.
     */
    public double distance(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
