package Listeners;

import Animation.GameLevel;
import Sprites.Ball;
import Sprites.Block;
import Sprites.Counter;

/**
 * Class BlockRemover.
 * this class helps me to delete the blocks whenever they get hit by a ball.
 */
public class BlockRemover implements HitListener {
    /**
     * the game.
     */
    private GameLevel gameLevel;
    /**
     * block number.
     */
    private Counter remainingBlocks;

    /**
     * block remover constructor.
     * @param gameLevel the game.
     * @param removedBlocks block number.
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.remainingBlocks.decrease(1);
        this.gameLevel.decreaseBlockCount();
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.gameLevel);
    }
}