package Animation;

import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;
import Sprites.Ball;
import Sprites.Block;
import Sprites.Counter;
import Sprites.ScoreIndicator;
import Sprites.Sprite;

/**
 * Class DirectHit.
 * this class initialize balls, blocks, and paddle of the direct hit level.
 */
public class DirectHit implements LevelInformation {
    private GUI gui;
    private Counter blockCounter;
    private Counter ballCounter;
    private ScoreIndicator score;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;

    /**
     * direct hit constructor.
     * @param gui the gui.
     * @param score the score of the last level, so we continue counting from that level.
     */
    public DirectHit(GUI gui, ScoreIndicator score) {
        this.gui = gui;
        this.ballCounter = new Counter(1);
        this.blockCounter = new Counter(1);
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
        return 5;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "DirectHit";
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
        Ball ball = new Ball(new Point(400, 300), 5, Color.white);
        ball.setVelocity(0, 3);
        List<Ball> balls = new LinkedList<Ball>();
        balls.add(ball);
        return balls;
    }

    @Override
    public List<Block> blocks() {
        Point upperLeft = new Point(380, 150);
        Rectangle shape = new Rectangle(upperLeft, 40, 40);
        Block block = new Block(shape, Color.red);
        List<Block> blocks = new LinkedList<Block>();
        blocks.add(block);
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blockCounter.getValue();
    }
}
