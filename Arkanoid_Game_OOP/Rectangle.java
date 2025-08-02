import java.util.ArrayList;
import java.util.List;

/**
 * A class representing an axis-aligned rectangle in 2D space.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructs a new Rectangle.
     *
     * @param upperLeft the upper-left corner of the rectangle
     * @param width     the width of the rectangle (horizontal)
     * @param height    the height of the rectangle (vertical)
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return upper-left corner
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Computes and returns all intersection points between this rectangle
     * and the given line segment.
     * The rectangle edges are treated as line segments.
     *
     * @param line the line segment to test
     * @return a (possibly empty) List of intersection Points
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();

        // define the four edges of the rectangle
        Point ul = this.upperLeft;
        Point ur = new Point(ul.getX() + width, ul.getY());
        Point ll = new Point(ul.getX(), ul.getY() + height);
        Point lr = new Point(ul.getX() + width, ul.getY() + height);

        Line top    = new Line(ul, ur);
        Line bottom = new Line(ll, lr);
        Line left   = new Line(ul, ll);
        Line right  = new Line(ur, lr);

        // test each edge for intersection
        addIfIntersect(line.intersectionWith(top), intersections);
        addIfIntersect(line.intersectionWith(bottom), intersections);
        addIfIntersect(line.intersectionWith(left), intersections);
        addIfIntersect(line.intersectionWith(right), intersections);

        return intersections;
    }

    /**
     * Helper: if p is non-null and not already in list, add it.
     */
    private void addIfIntersect(Point p, List<Point> list) {
        if (p != null) {
            // avoid adding duplicates (same coordinates)
            for (Point existing : list) {
                if (existing.equals(p)) {
                    return;
                }
            }
            list.add(p);
        }
    }
}
