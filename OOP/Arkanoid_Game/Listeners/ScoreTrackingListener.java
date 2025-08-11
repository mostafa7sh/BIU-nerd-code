package Listeners;

import Sprites.Ball;
import Sprites.Block;
import Sprites.Counter;

/**
 * Class ScoreTrackingListener.
 * here we track the score and check whenever a block has been removed,
 * so we increase the score by 10.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * score tracking listener constructor.
     */
    public ScoreTrackingListener() {
        this.currentScore = new Counter();
    }

    /**
     * @return the counter itself.
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
       this.currentScore.increase(10);
    }
}