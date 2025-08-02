import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;

    public Game() {
        this.sprites     = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui         = new GUI("Arkanoid", 800, 600);
    }

    public GUI getGui() {
        return this.gui;
    }

    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    public void initialize() {
        // 1) Paddle (now with screenWidth=800 and ballRadius=8)
        KeyboardSensor keyboard   = gui.getKeyboardSensor();
        Rectangle paddleRect      = new Rectangle(new Point(350, 560), 100, 20);
        double    paddleSpeed     = 5;
        int       screenWidth     = 800;
        double    ballRadius      = 8;
        Paddle paddle = new Paddle(
                paddleRect,
                keyboard,
                paddleSpeed,
                screenWidth,
                ballRadius
        );
        paddle.addToGame(this);

        // 2) Two balls
        Ball ball1 = new Ball(new Point(390, 550), 8, Color.RED,  environment);
        Ball ball2 = new Ball(new Point(410, 550), 8, Color.BLUE, environment);
        ball1.setVelocity(3, -3);
        ball2.setVelocity(-3, -3);
        ball1.addToGame(this);
        ball2.addToGame(this);

        // 3) Four walls (visible blocks)
        Color wallColor = Color.GRAY;
        new Block(new Rectangle(new Point(0,   0), 800, 20), wallColor).addToGame(this); // top
        new Block(new Rectangle(new Point(0, 580), 800, 20), wallColor).addToGame(this); // bottom
        new Block(new Rectangle(new Point(0,  20), 20, 560), wallColor).addToGame(this); // left
        new Block(new Rectangle(new Point(780,20), 20, 560), wallColor).addToGame(this); // right

        // 4) Six rows of blocks: 12→7, right-aligned
        Color[] rowColors = {
                Color.DARK_GRAY,
                Color.GRAY,
                Color.LIGHT_GRAY,
                Color.BLACK,
                Color.WHITE,
                Color.BLUE
        };
        int blockWidth   = 60;
        int blockHeight  = 20;
        int topOffsetY   = 50;
        int rightMarginX = 20;

        for (int row = 0; row < 6; row++) {
            int blocksInRow = 12 - row;
            double startX = 800 - rightMarginX - (blocksInRow * blockWidth);
            double y      = topOffsetY + row * blockHeight;
            for (int col = 0; col < blocksInRow; col++) {
                double x = startX + col * blockWidth;
                new Block(
                        new Rectangle(new Point(x, y), blockWidth, blockHeight),
                        rowColors[row]
                ).addToGame(this);
            }
        }
    }

    public void run() {
        Sleeper sleeper = new Sleeper();
        int fps       = 60;
        int frameTime = 1000 / fps;

        while (true) {
            long start = System.currentTimeMillis();

            DrawSurface d = gui.getDrawSurface();
            sprites.drawAllOn(d);
            gui.show(d);
            sprites.notifyAllTimePassed();

            long used = System.currentTimeMillis() - start;
            long left = frameTime - used;
            if (left > 0) {
                sleeper.sleepFor(left);
            }
        }
    }
}
