package Listeners;

import Animation.GameLevel;
import Sprites.Ball;
import Sprites.Block;
import Sprites.Counter;

/**
 * Class BallRemover.
 * this class helps me to delete the balls whenever they fall down from the screen.
 */
public class BallRemover implements HitListener {
    /**
     * the game.
     */
    private GameLevel gameLevel;
    /**
     * ball number.
     */
    private Counter remainingBlocks;

    /**
     * ball remover constructor.
     * @param gameLevel the game.
     * @param removedBlocks ball number.
     */
    public BallRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * whenever the ball hits the bottom screen, I remove it from the game.
     * @param beingHit
     * @param hitter
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.remainingBlocks.decrease(1);
        this.gameLevel.decreaseBallCount();
        hitter.removeFromGame(this.gameLevel);
    }
}