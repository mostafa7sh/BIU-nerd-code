import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

public class Paddle implements Sprite, Collidable {
    private static final double EPS = 1e-4;

    private Rectangle rect;
    private KeyboardSensor keyboard;
    private double speed;
    private int screenWidth;
    private double ballRadius;

    public Paddle(Rectangle rect, KeyboardSensor keyboard, double speed,
                  int screenWidth, double ballRadius) {
        this.rect        = rect;
        this.keyboard    = keyboard;
        this.speed       = speed;
        this.screenWidth = screenWidth;
        this.ballRadius  = ballRadius;
    }

    public void moveLeft() {
        double x = rect.getUpperLeft().getX() - speed;
        if (x < -ballRadius) {
            x = screenWidth + ballRadius - rect.getWidth();
        }
        rect = new Rectangle(new Point(x, rect.getUpperLeft().getY()),
                rect.getWidth(), rect.getHeight());
    }

    public void moveRight() {
        double x = rect.getUpperLeft().getX() + speed;
        if (x + rect.getWidth() > screenWidth + ballRadius) {
            x = -ballRadius;
        }
        rect = new Rectangle(new Point(x, rect.getUpperLeft().getY()),
                rect.getWidth(), rect.getHeight());
    }

    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) rect.getUpperLeft().getX();
        int y = (int) rect.getUpperLeft().getY();
        int w = (int) rect.getWidth();
        int h = (int) rect.getHeight();
        d.setColor(Color.ORANGE);
        d.fillRectangle(x, y, w, h);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        Point ul = rect.getUpperLeft();
        return new Rectangle(
                new Point(ul.getX(), ul.getY() - ballRadius),
                rect.getWidth(),
                rect.getHeight() + ballRadius
        );
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double x = this.rect.getUpperLeft().getX();
        double collisionX = collisionPoint.getX();
        double width = this.rect.getWidth() / 5;
        double x1 = x + width;
        double x2 = x + 2 * width;
        double x3 = x + 3 * width;
        double x4 = x + 4 * width;
        double x5 = x + 5 * width;
        Point start = new Point(currentVelocity.getDx(), currentVelocity.getDy());
        Point end = currentVelocity.applyToPoint(start);
        Line line = new Line(start, end);
        if (x <= collisionX && collisionX <= x1) {
            return Velocity.fromAngleAndSpeed(300, line.length());
        } else if (x1 <= collisionX && collisionX <= x2) {
            return Velocity.fromAngleAndSpeed(330, line.length());
        } else if (x2 <= collisionX && collisionX <= x3) {
            return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
        } else if (x3 <= collisionX && collisionX <= x4) {
            return Velocity.fromAngleAndSpeed(30, line.length());
        } else if (x4 <= collisionX && collisionX <= x5) {
            return Velocity.fromAngleAndSpeed(60, line.length());
        }
        return currentVelocity;
    }

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
