package Animation;

import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;
import Sprites.Ball;
import Sprites.Block;
import Sprites.Counter;
import Sprites.ScoreIndicator;
import Sprites.Sprite;

/**
 * Class Green3.
 * the hardest level in the game, Green3.
 */
public class Green3 implements LevelInformation {
    private GUI gui;
    private Counter blockCounter;
    private Counter ballCounter;
    private ScoreIndicator score;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;

    /**
     * Green3 constructor.
     * @param gui the gui.
     * @param score the score of the last level.
     */
    public Green3(GUI gui, ScoreIndicator score) {
        this.gui = gui;
        this.ballCounter = new Counter(2);
        this.blockCounter = new Counter(50);
        this.score = score;
        this.runner = new AnimationRunner(this.gui, 60);
        this.running = true;
        this.keyboard = this.gui.getKeyboardSensor();
    }
    @Override
    public int numberOfBalls() {
        return this.ballCounter.getValue();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return null;
    }

    @Override
    public int paddleSpeed() {
        return 8;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "Green3";
    }

    @Override
    public Sprite getBackground() {
        return null;
    }

    @Override
    public ScoreIndicator getScore() {
        return this.score;
    }

    @Override
    public List<Ball> balls() {
        List<Ball> ballList = new LinkedList<Ball>();
        Ball ball1 = new Ball(370, 550, 5, Color.white);
        ball1.setVelocity(4, 2.7);
        Ball ball2 = new Ball(430, 550, 5, Color.white);
        ball2.setVelocity(-4, 2.7);
        ballList.add(ball1);
        ballList.add(ball2);
        return ballList;
    }

    @Override
    public List<Block> blocks() {
        List<Block> blockList = new LinkedList<Block>();
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            for (int j = i; j < 12; j++) {
                Point upperLeft = new Point(180 + 50 * j, 120 + 25 * i);
                Rectangle shape = new Rectangle(upperLeft, 50, 25);
                Color color = new Color(r, g, b);
                Block block = new Block(shape, color);
                blockList.add(block);
            }
        }
        return blockList;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blockCounter.getValue();
    }
}