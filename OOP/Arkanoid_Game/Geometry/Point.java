package Geometry;

/**
 * Class Point.
 * Represents a point in the axes x and y.
 * point can be represented as 2 double values.
 */
public class Point {
    /**
     * x value of the point.
     */
    private double x;
    /**
     * y value of the point.
     */
    private double y;
    /**
     * Changes the x and y of that point.
     * @param x x value of the point.
     * @param y y value of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Gets two points values and calculates the distance between them.
     * @param other the other point that we want to measure distance with.
     * @return the distance of two points.
     */
    public double distance(Point other) {
        return Math.sqrt((this.x - other.x) * (this.x - other.x)
                + (this.y - other.y) * (this.y - other.y));
    }
    /**
     * Gets two points values and checks if they got same x and y values.
     * @param other the other point that we want to compare it with first one.
     * @return ture if both points got the same x and y values,
     *         false otherwise.
     */
    public boolean equals(Point other) {
        return this.x == other.getX() && this.y == other.getY();
    }

    /**
     * @return x value of the point.
     */
    public double getX() {
        return this.x;
    }
    /**
     * @return y value of the point.
     */
    public double getY() {
        return this.y;
    }
}