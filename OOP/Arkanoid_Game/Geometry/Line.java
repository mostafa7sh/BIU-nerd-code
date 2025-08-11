package Geometry;

/**
 * Class Line.
 * represents a line that connects two points.
 * line can be represented by two points,start and end point.
 */
public class Line {

    /**
    * x value of the starting point.
    */
    private double x1;

    /**
     * x value of the ending point.
     */
    private double x2;

    /**
     * y value of the starting point.
     */
    private double y1;

    /**
     * y value of the ending point.
     */
    private double y2;

    /**
     * a constructor that builds a new line.
     * @param start the starting point of the line.
     * @param end the ending point of the line.
     */
    public Line(Point start, Point end) {
        if (start.getX() == end.getX()) {
            if (start.getY() < end.getY()) {
                this.x1 = start.getX();
                this.y1 = start.getY();
                this.x2 = end.getX();
                this.y2 = end.getY();
            } else {
                this.x1 = end.getX();
                this.y1 = end.getY();
                this.x2 = start.getX();
                this.y2 = start.getY();
            }
        } else if (start.getX() < end.getX()) {
            this.x1 = start.getX();
            this.y1 = start.getY();
            this.x2 = end.getX();
            this.y2 = end.getY();
        } else {
            this.x1 = end.getX();
            this.y1 = end.getY();
            this.x2 = start.getX();
            this.y2 = start.getY();
        }
    }

    /**
     * a constructor that builds a new line.
     * @param x1 x value of the starting point.
     * @param y1 y value of the starting point.
     * @param x2 x value of the ending point.
     * @param y2 y value of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        if (x1 == x2) {
            if (y1 < y2) {
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
            } else {
                this.x1 = x2;
                this.y1 = y2;
                this.x2 = x1;
                this.y2 = y1;
            }
        } else if (x1 < x2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        } else {
            this.x1 = x2;
            this.y1 = y2;
            this.x2 = x1;
            this.y2 = y1;
        }
    }

    /**
     * helps me calculate the length of the line.
     * @return the length of line.
     */
    public double length() {
        return Math.sqrt((this.end().getX() - this.start().getX()) * (this.end().getX() - this.start().getX())
                        + (this.end().getY() - this.start().getY()) * (this.end().getY() - this.start().getY()));
    }

    /**
     * calculate the x/y values of the middle point in the line.
     * @return the middle point in the line.
     */
    public Point middle() {
        double x = (this.end().getX() + this.start().getX()) / 2;
        double y = (this.end().getY() + this.start().getY()) / 2;
        return new Point(x, y);
    }

    /**
     * @return the starting point of the line.
     */
    public Point start() {
        if (this.x1 == this.x2) {
            if (this.y1 < this.y2) {
                return new Point(this.x1, this.y1);
            } else {
                return new Point(this.x2, this.y2);
            }
        } else if (this.x1 < this.x2) {
            return new Point(this.x1, this.y1);
        } else {
            return new Point(this.x2, this.y2);
        }
    }

    /**
     * @return the ending point of the line.
     */
    public Point end() {
        if (this.x1 == this.x2) {
            if (this.y1 < this.y2) {
                return new Point(this.x2, this.y2);
            } else {
                return new Point(this.x1, this.y1);
            }
        } else if (this.x1 < this.x2) {
            return new Point(this.x2, this.y2);
        } else {
            return new Point(this.x1, this.y1);
        }
    }

    /**
     * check if two lines intersect.
     * @param other the other line that we want to check intersection with.
     * @return true if both lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        // if both are vertical
        if (this.isVertical() && other.isVertical()) {
            // has same x value
            if (this.start().getX() == other.start().getX()) {
                if (this.end().getY() >= other.end().getY()) {
                    return this.start().getY() <= other.end().getY();
                } else if (other.end().getY() > this.end().getY()) {
                    return other.start().getY() >= this.end().getY();
                }
            }
            // no intersection point for both vertical lines that have different x values
            return false;
            // if the first vertical and the other not vertical
        } else if (this.isVertical() && !other.isVertical()) {
            if (other.start().getX() <= this.start().getX() && this.start().getX() <= other.end().getX()) {
                double m = other.getSlope();
                double b = other.getB();
                double checkY = m * this.start().getX() + b;

                return this.start().getY() <= checkY && checkY <= this.end().getY();
            }
            return false;
            // if the first not vertical and the other is vertical
        } else if (!this.isVertical() && other.isVertical()) {
            if (this.start().getX() <= other.start().getX() && other.start().getX() <= this.end().getX()) {
                double m = this.getSlope();
                double b = this.getB();
                double checkY = m * other.start().getX() + b;

                return other.start().getY() <= checkY && checkY <= other.end().getY();
            }
            return false;
            // both are not vertical
        } else {
            double m1 = this.getSlope();
            double b1 = this.getB();
            double m2 = other.getSlope();
            double b2 = other.getB();

            // both lines are horizontal
            if (m1 == m2) {
                if (b1 == b2) {
                    if (this.end().getX() > other.end().getX()) {
                        return this.start().getX() == other.end().getX();
                    }
                    if (other.end().getX() > this.end().getX()) {
                        return other.start().getX() == this.end().getX();
                    }
                }
                return false;
            }

            double x = (b2 - b1) / (m1 - m2);
            return this.start().getX() <= x && x <= this.end().getX()
                    && other.start().getX() <= x && x <= other.end().getX();
        }
    }

    /**
     * calculate the intersection point of two lines.
     * @param other the other line that intersect with the first one.
     * @return intersection point of two lines if they intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (!this.isIntersecting(other)) {
            return null;
        }
        // if both lines are vertical
        if (this.isVertical() && other.isVertical()) {
            if (this.end().getX() == other.start().getX()) {
                return new Point(this.end().getX(), this.end().getY());
            } else if (other.end().getX() == this.start().getX()) {
                return new Point(other.end().getX(), other.end().getY());
            } else {
                return null;
            }
            // if the first line is vertical and the other line is not
        } else if (this.isVertical() && !other.isVertical()) {
            double m = other.getSlope();
            double b = other.getB();
            double y = m * this.start().getX() + b;

            return new Point(this.start().getX(), y);
            // if the first line is not vertical and the other one is vertical
        } else if (!this.isVertical() && other.isVertical()) {
            double m = this.getSlope();
            double b = this.getB();
            double y = m * other.start().getX() + b;

            return new Point(other.start().getX(), y);
            // both lines are not vertical
        } else {
            double m1 = this.getSlope();
            double b1 = this.getB();
            double m2 = other.getSlope();
            double b2 = other.getB();

            // if both lines are horizontal
            if (m1 == m2) {
                if (b1 == b2) {
                    if (this.end().getX() > other.end().getX()) {
                        if (this.start().getX() == other.end().getX()) {
                            return new Point(this.start().getX(), this.start().getY());
                        }
                    }
                    if (other.end().getX() > this.end().getX()) {
                        if (other.start().getX() == this.end().getX()) {
                            return new Point(other.start().getX(), other.start().getY());
                        }
                    }
                }
                return null;
            }
            double x = (b2 - b1) / (m1 - m2);
            double y = m1 * x + b1;

            return new Point(x, y);
        }
    }

    /**
     * @param other the other line that we want to check equation with.
     * @return true if both lines are the same, false otherwise.
     */
    public boolean equals(Line other) {
        return this.start().getX() == other.start().getX()
                && this.start().getY() == other.start().getY()
                && this.end().getX() == other.end().getX()
                && this.end().getY() == other.end().getY();
    }

    /**
     * check if start point and end point have same x value, we conclude that this line is vertical.
     * @return true if the line vertical, false otherwise.
     */
    public boolean isVertical() {
        return this.start().getX() == this.end().getX();
    }

    /**
     * calculates the m in "y = mx + b".
     * @return the slope of the line.
     */
    public double getSlope() {
        return (this.end().getY() - this.start().getY()) / (this.end().getX() - this.start().getX());
    }

    /**
     * calculates the b in "y = mx + b".
     * @return y value of the intersection point between a line and y-axis.
     */
    public double getB() {
        return this.start().getY() - (this.getSlope() * this.start().getX());
    }

    /**
     * calculates the closest point could be found of a line intersecting a rectangle.
     * @param rect is the rectangle that we want to check intersection with.
     * @return the closest intersection point to the stating point of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> points = rect.intersectionPoints(this);
        if (points.size() == 0) {
            return null;
        }
        Point point = points.get(0);
        double distance = point.distance(this.start());
        for (Point p : points) {
            if (p.distance(this.start()) < distance) {
                point = p;
                distance = p.distance(this.start());
            }
        }
        return point;
    }
}