package Animation;

import biuoop.DrawSurface;

/**
 * interface Animation.
 * unites every animation screen with several methods.
 */
public interface Animation {
    /**
     * draw a screen on the gui.
     * @param d the draw surface.
     */
    void doOneFrame(DrawSurface d);

    /**
     * checks whenever must stop the animation.
     * @return true if we must stop, false otherwise.
     */
    boolean shouldStop();
}