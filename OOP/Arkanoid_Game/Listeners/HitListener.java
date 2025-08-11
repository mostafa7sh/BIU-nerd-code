package Listeners;

import Sprites.Ball;
import Sprites.Block;

/**
 * Interface HitListener.
 * represent a collection of every class that can be a listener.
 */
public interface HitListener {
    /**
     * whenever the ball hit any block, we call this method on some classes,
     * some cases we must delete the block, other cases we must delete the ball.
     * @param beingHit the block that got hit by the ball.
     * @param hitter the hitting ball.
     */
    void hitEvent(Block beingHit, Ball hitter);
}