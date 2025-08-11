package Animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.*;

import Game.GameEnvironment;
import Geometry.Point;
import Geometry.Rectangle;
import Listeners.BallRemover;
import Listeners.BlockRemover;
import Sprites.Ball;
import Sprites.Block;
import Sprites.Collidable;
import Sprites.Counter;
import Sprites.LevelName;
import Sprites.Paddle;
import Sprites.ScoreIndicator;
import Sprites.Sprite;
import Sprites.SpriteCollection;

/**
 * Class Game.
 * contains every thing the game could have, blocks, balls and a paddle.
 * we create these object in this class and run the game here.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Counter blockCounter;
    private Counter ballCounter;
    private ScoreIndicator score;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation level;
    private LevelName levelName;

    /**
     * game constructor, here we initialize basic things.
     * @param gui the gui.
     * @param level all the information about that level.
     */
    public GameLevel(GUI gui, LevelInformation level) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = gui;
        this.sleeper = new Sleeper();
        this.blockCounter = new Counter();
        this.ballCounter = new Counter();
        this.score = level.getScore();
        this.runner = new AnimationRunner(this.gui, 60);
        this.running = true;
        this.keyboard = this.gui.getKeyboardSensor();
        this.level = level;
        this.levelName = new LevelName(level.levelName());
    }

    /**
     * initialize every object in the game.
     */
    public void initialize() {

        // create the balls.
        BallRemover ballRemover = new BallRemover(this, new Counter(this.level.numberOfBalls()));
        for (Ball ball: this.level.balls()) {
            ball.setEnvironment(this.environment);
            ball.addToGame(this);
        }
        this.ballCounter.increase(this.level.numberOfBalls());

        // create the borders.
        Rectangle border = new Rectangle(new Point(0, 0), 20, 600);
        Block border1 = new Block(border, Color.gray);
        border = new Rectangle(new Point(0, 600), 800, 20);
        Block border2 = new Block(border, Color.gray);
        border2.addHitListener(ballRemover);
        border = new Rectangle(new Point(780, 0), 20, 600);
        Block border3 = new Block(border, Color.gray);
        border = new Rectangle(new Point(0, 0), 800, 20);
        Block border4 = new Block(border, Color.gray);
        Block[] borders = {border1, border2, border3, border4};
        // create the paddle.

        Point upperLeftOfThePaddle = new Point(400 - (int) (this.level.paddleWidth() / 2), 560);
        Rectangle rectangleOfPaddle = new Rectangle(upperLeftOfThePaddle,
                this.level.paddleWidth(), 20);
        KeyboardSensor keyboard = this.keyboard;
        Paddle paddle = new Paddle(rectangleOfPaddle, keyboard, this.level.paddleSpeed());
        paddle.addToGame(this);
        for (Block borderr : borders) {
            borderr.addToGame(this);
        }
        BlockRemover blockRemover = new BlockRemover(this,
                new Counter(this.level.numberOfBlocksToRemove()));

        // create the blocks.
        for (Block block: this.level.blocks()) {
            block.addToGame(this);
            block.addHitListener(blockRemover);
            block.addHitListener(this.score.getScore());
        }
        this.blockCounter.increase(this.level.numberOfBlocksToRemove());
        this.addSprite(this.score);
        this.addSprite(this.levelName);
    }

    /**
     * simply run the game.
     */
    public void run() {
        while (!this.shouldStop()) {
            this.runner.run(new CountdownAnimation(3, 10, this.sprites));
            this.running = true;
            // use our runner to run the current animation -- which is one turn of
            // the game.
            this.runner.run(this);
        }
    }

    /**
     * add collidable objects to the game environment.
     * @param c collidable thing.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * remove blocks from the game environment.
     * @param c a block in the game environment.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * add sprties objects to the sprite collection.
     * @param s sprite collection.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * remove blocks from the game environment.
     * @param s a block in the game environment.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * decrease the number of the blocks by 1.
     */
    public void decreaseBlockCount() {
        this.blockCounter.decrease(1);
    }

    /**
     * decrease the number of the balls by 1.
     */
    public void decreaseBallCount() {
        this.ballCounter.decrease(1);
    }

    /**
     * we initialize almost every object in the game, balls , blocks, and paddle.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        // game-specific logic
        d.setColor(Color.blue);
        d.fillRectangle(0, 0, 800, 600);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }

        // stopping condition
        if (this.blockCounter.getValue() == 0 || this.ballCounter.getValue() == 0) {
            if (this.blockCounter.getValue() == 0) {
                this.score.getScore().getCurrentScore().increase(100);
            }
            this.running = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * @return the number of the balls.
     */
    public int getBallCounter() {
        return this.ballCounter.getValue();
    }

    /**
     * @return the score of that level.
     */
    public int getFinalScore() {
        return this.score.getScore().getCurrentScore().getValue();
    }
}