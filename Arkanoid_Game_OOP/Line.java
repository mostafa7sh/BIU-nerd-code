import java.util.List;

/**
 * The {@code Line} class represents a line segment defined by two {@code Point} objects.
 * It provides methods to calculate the length of the line, find the middle point,
 * check for intersections with other lines, and more.
 */
public class Line {

    private Point start;
    private Point end;

    /**
     * Constructs a {@code Line} object using two {@code Point} objects.
     *
     * @param start The starting point of the line.
     * @param end   The ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a {@code Line} object using four coordinate values.
     *
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return The length of the line as a {@code double}.
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return A {@code Point} representing the middle point of the line.
     */
    public Point middle() {
        double midX = (start.getX() + end.getX()) / 2;
        double midY = (start.getY() + end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * Returns the start point of the line.
     *
     * @return The {@code Point} representing the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return The {@code Point} representing the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks if this line intersects with another line.
     *
     * @param other The other line to check for intersection.
     * @return {@code true} if the lines intersect, {@code false} otherwise.
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * Checks if this line intersects with two other lines.
     *
     * @param other1 The first line to check for intersection.
     * @param other2 The second line to check for intersection.
     * @return {@code true} if the line intersects with both other lines, {@code false} otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /**
     * Returns the intersection point of this line with another line, or {@code null} if they do not intersect.
     *
     * @param other The other line to check for intersection.
     * @return The {@code Point} of intersection, or {@code null} if no intersection exists.
     */
    public Point intersectionWith(Line other) {
        double x1 = this.start.getX(), y1 = this.start.getY();
        double x2 = this.end.getX(),   y2 = this.end.getY();
        double x3 = other.start.getX(), y3 = other.start.getY();
        double x4 = other.end.getX(),   y4 = other.end.getY();

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (Math.abs(denominator) < 1e-10) {
            // Parallel or coincident
            return null;
        }

        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = ((x1 - x3) * (y1 - y2) - (y1 - y3) * (x1 - x2)) / denominator;

        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            double ix = x1 + t * (x2 - x1);
            double iy = y1 + t * (y2 - y1);
            return new Point(ix, iy);
        }
        return null;
    }

    /**
     * If this line does not intersect the given rectangle, returns null.
     * Otherwise returns the closest intersection point to the start of this line.
     *
     * @param rect The rectangle to test against.
     * @return The closest intersection {@code Point}, or {@code null} if none.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Compute the direction vector
        double dx = this.end.getX() - this.start.getX();
        double dy = this.end.getY() - this.start.getY();

        // Extend the line to 3x the original length
        Point extendedEnd = new Point(this.start.getX() + 2 * dx , this.start.getY() + 2 * dy);
        Line extendedLine = new Line(this.start, extendedEnd);

        List<Point> points = rect.intersectionPoints(extendedLine);
        if (points.isEmpty()) {
            return null;
        }

        Point closest = points.get(0);
        double minDist = this.start.distance(closest);
        for (Point p : points) {
            double d = this.start.distance(p);
            if (d < minDist) {
                minDist = d;
                closest = p;
            }
        }
        return closest;
    }



    /**
     * Checks if this line is equal to another line.
     *
     * @param other The other line to compare.
     * @return {@code true} if the lines are equal (regardless of direction), {@code false} otherwise.
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }
}
