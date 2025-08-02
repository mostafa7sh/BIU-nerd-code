import java.awt.Color;
import biuoop.DrawSurface;

/**
 * Represents a block in the game that can be collided with.
 */
public class Block implements Collidable, Sprite {
    private Rectangle rect;
    private Color color;

    /**
     * Creates a new Block with the given rectangle and color.
     * @param rect the collision shape of the block
     * @param color the draw color of the block
     */
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
    }

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Returns the collision rectangle of this block.
     * @return the rectangle shape
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Notify this block that it was hit at collisionPoint with a given velocity.
     * Returns the new velocity after the hit (bounces horizontally or vertically).
     * @param collisionPoint the point at which the ball hit
     * @param currentVelocity the ball's velocity before the hit
     * @return the new velocity after collision
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        double leftX = rect.getUpperLeft().getX();
        double rightX = leftX + rect.getWidth();
        double topY = rect.getUpperLeft().getY();
        double bottomY = topY + rect.getHeight();
        // hit on left or right side
        if (x == leftX || x == rightX) {
            dx = -dx;
        }
        // hit on top or bottom side
        if (y == topY || y == bottomY) {
            dy = -dy;
        }
        return new Velocity(dx, dy);
    }

    /**
     * Draws this block on the given DrawSurface.
     * @param d the surface to draw on
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int w = (int) this.rect.getWidth();
        int h = (int) this.rect.getHeight();
        d.setColor(this.color);
        d.fillRectangle(x, y, w, h);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }
    public void timePassed() {
        // currently does nothing
    }
}
