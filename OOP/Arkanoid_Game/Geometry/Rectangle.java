package Geometry;

import java.util.LinkedList;

/**
 * Class Rectangle.
 * represents a rectangle, its upper left point, and its width and height.
 */
public class Rectangle {
    /**
     * the upper left point.
     */
    private Point upperLeft;
    /**
     * the loser left point.
     */
    private Point lowerLeft;
    /**
     * the upper right point.
     */
    private Point upperRight;
    /**
     * the loser right point.
     */
    private Point lowerRight;
    /**
     * the width.
     */
    private double width;
    /**
     * the height.
     */
    private double height;

    /**
     * creates a new rectangle, using the index of the upper left point, and its width and height.
     * @param upperLeft the upper left point.
     * @param width the width.
     * @param height the height.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.height = height;
        this.width = width;
        this.upperLeft = upperLeft;
        this.lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        this.upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        this.lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * calculates the intersection points of a specified line (if found),
     * and return them as a collection of points (linked list).
     * @param line a line that intersect the rectangle.
     * @return all the intersection points where the line and the rectangle intersect.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        Line line1 = new Line(this.upperLeft, this.upperRight);
        Line line2 = new Line(this.lowerLeft, this.upperLeft);
        Line line3 = new Line(this.lowerLeft, this.lowerRight);
        Line line4 = new Line(this.lowerRight, this.upperRight);
        Line[] lines = new Line[4];
        lines[0] = line1;
        lines[1] = line2;
        lines[2] = line3;
        lines[3] = line4;
        java.util.List<Point> points = new LinkedList<Point>();

        for (Line line0 : lines) {
            if (line0.isIntersecting(line)) {
                Point point = line0.intersectionWith(line);
                if (point != null) {
                    points.add(point);
                }
            }
        }
        return points;
    }

    /**
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * @return the upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * @return the lower left point of the rectangle.
     */
    public Point getLowerLeft() {
        return this.lowerLeft;
    }
    /**
     * @return the upper right point of the rectangle.
     */
    public Point getUpperRight() {
        return this.upperRight;
    }
    /**
     * @return the lower right point of the rectangle.
     */
    public Point getLowerRight() {
        return this.lowerRight;
    }
}