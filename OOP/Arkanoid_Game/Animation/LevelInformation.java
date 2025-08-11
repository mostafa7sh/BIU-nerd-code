package Animation;

import java.util.List;

import Geometry.Velocity;
import Sprites.Ball;
import Sprites.Block;
import Sprites.ScoreIndicator;
import Sprites.Sprite;

/**
 * interface LevelInformation.
 * we unite every level with some information to provide them back to the initialize method.
 */
public interface LevelInformation {
    /**
     * @return the number of the balls in that level.
     */
    int numberOfBalls();

    /**
     * @return a list of the balls velocity.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return paddle speed.
     */
    int paddleSpeed();

    /**
     * @return paddle width.
     */
    int paddleWidth();

    /**
     * @return level name.
     */
    String levelName();

    /**
     * @return the background of the game.
     */
    Sprite getBackground();

    /**
     * @return the score at this level.
     */
    ScoreIndicator getScore();

    /**
     * @return list of the balls in the game.
     */
    List<Ball> balls();

    /**
     * @return list of the blocks in the game.
     */
    List<Block> blocks();

    /**
     * @return how many blocks in the game.
     */
    int numberOfBlocksToRemove();
}