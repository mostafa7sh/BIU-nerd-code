package Animation;

import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.*;
import java.util.Collections;
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
 * Class WideEasY.
 * a level in the game witch is rly wide easy.
 */
public class WideEasY implements LevelInformation {
    private GUI gui;
    private Counter blockCounter;
    private Counter ballCounter;
    private ScoreIndicator score;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;

    /**
     * wide easy constructor.
     * @param gui the gui.
     * @param score the cumulative score.
     */
    public WideEasY(GUI gui, ScoreIndicator score) {
        this.gui = gui;
        this.ballCounter = new Counter(10);
        this.blockCounter = new Counter(15);
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
        return 3;
    }

    @Override
    public int paddleWidth() {
        return 600;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
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
        Ball ball1 = new Ball(200, 370, 5, Color.white);
        ball1.setVelocity(4, 2.5);
        Ball ball2 = new Ball(240, 330, 5, Color.white);
        ball2.setVelocity(4, 2.5);
        Ball ball3 = new Ball(280, 310, 5, Color.white);
        ball3.setVelocity(4, 2.5);
        Ball ball4 = new Ball(320, 290, 5, Color.white);
        ball4.setVelocity(4, 2.5);
        Ball ball5 = new Ball(360, 280, 5, Color.white);
        ball5.setVelocity(4, 2.5);
        Ball ball6 = new Ball(450, 280, 5, Color.white);
        ball6.setVelocity(4, 2.5);
        Ball ball7 = new Ball(490, 290, 5, Color.white);
        ball7.setVelocity(4, 2.5);
        Ball ball8 = new Ball(530, 310, 5, Color.white);
        ball8.setVelocity(4, 2.5);
        Ball ball9 = new Ball(570, 330, 5, Color.white);
        ball9.setVelocity(4, 2.5);
        Ball ball10 = new Ball(610, 370, 5, Color.white);
        ball10.setVelocity(4, 2.5);
        List<Ball> ballList = new LinkedList<Ball>();
        ballList.add(ball1);
        ballList.add(ball2);
        ballList.add(ball3);
        ballList.add(ball4);
        ballList.add(ball5);
        ballList.add(ball6);
        ballList.add(ball7);
        ballList.add(ball8);
        ballList.add(ball9);
        ballList.add(ball10);
        return ballList;
    }

    @Override
    public List<Block> blocks() {
        int x = 20;
        int y = 180;
        Block[] blocks = new Block[15];
        for (int i = 0; i < 15; i++) {
            Point upperLeft = new Point(x + (i * 51), y);
            Rectangle shape = new Rectangle(upperLeft, 51, 25);
            Block block = new Block(shape, Color.white);
            blocks[i] = block;
        }
        blocks[0].setColor(Color.red);
        blocks[1].setColor(Color.red);
        blocks[2].setColor(Color.orange);
        blocks[3].setColor(Color.orange);
        blocks[4].setColor(Color.yellow);
        blocks[5].setColor(Color.yellow);
        blocks[6].setColor(Color.green);
        blocks[7].setColor(Color.green);
        blocks[8].setColor(Color.green);
        blocks[9].setColor(Color.blue);
        blocks[10].setColor(Color.blue);
        blocks[11].setColor(Color.pink);
        blocks[12].setColor(Color.pink);
        blocks[13].setColor(Color.cyan);
        blocks[14].setColor(Color.cyan);
        List<Block> blockList = new LinkedList<Block>();
        Collections.addAll(blockList, blocks);
        return blockList;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blockCounter.getValue();
    }
}