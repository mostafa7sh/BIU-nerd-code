import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.awt.Color;

public class Ass3Game {
    public static void main(String[] args) {
        // 1. Create and configure the Game
        Game game = new Game();

        // 2. Add the paddle
        //    – now passing screenWidth=800 and ballRadius=8
        KeyboardSensor keyboard = game.getGui().getKeyboardSensor();
        Rectangle paddleRect = new Rectangle(new Point(350, 560), 100, 20);
        Paddle paddle =
                new Paddle(paddleRect, keyboard, 3 /* speed */, 800 /* screenW */, 8 /* ballR */);
        paddle.addToGame(game);

        // 3. Add two balls (they’ll bounce off all blocks & walls)
        Ball ball1 = new Ball(new Point(390, 550), 8, Color.RED,  game.getEnvironment());
        Ball ball2 = new Ball(new Point(410, 550), 8, Color.BLUE, game.getEnvironment());
        ball1.setVelocity(3, -3);
        ball2.setVelocity(-3, -3);
        ball1.addToGame(game);
        ball2.addToGame(game);

        // 4. Add screen borders as visible blocks
        Color wallColor = Color.GRAY;
        new Block(new Rectangle(new Point(0,   0), 800, 20), wallColor).addToGame(game); // top
        new Block(new Rectangle(new Point(0, 580), 800, 20), wallColor).addToGame(game); // bottom
        new Block(new Rectangle(new Point(0,  20), 20, 560), wallColor).addToGame(game); // left
        new Block(new Rectangle(new Point(780,20), 20, 560), wallColor).addToGame(game); // right

        // 5. Add 6 right-aligned rows of blocks: 12,11,10,9,8,7 blocks
        Color[] rowColors = {
                Color.YELLOW,
                Color.GRAY,
                Color.RED,
                Color.ORANGE,
                Color.PINK,
                Color.BLUE
        };
        int blockWidth  = 60;
        int blockHeight = 20;
        int topOffsetY  = 50;
        int rightMargin = 20;

        for (int row = 0; row < 6; row++) {
            int blocksInRow = 12 - row;
            double startX = 800 - rightMargin - (blocksInRow * blockWidth);
            double y      = topOffsetY + row * blockHeight;
            for (int col = 0; col < blocksInRow; col++) {
                double x = startX + col * blockWidth;
                new Block(
                        new Rectangle(new Point(x, y), blockWidth, blockHeight),
                        rowColors[row]
                ).addToGame(game);
            }
        }

        // 6. Start the animation loop
        game.run();
    }
}
