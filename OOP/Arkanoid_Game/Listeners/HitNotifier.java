package Listeners;

/**
 * Interface HitNotifier.
 * represents every possible object that can notify other listener object,
 * whenever a ball hits a block.
 */
public interface HitNotifier {
    /**
     * add a listener to list of listeners.
     * @param hl a listener.
     */
    void addHitListener(HitListener hl);

    /**
     * remove a listener from list of listeners.
     * @param hl a listener.
     */
    void removeHitListener(HitListener hl);
}