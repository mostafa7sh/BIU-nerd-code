package Sprites;

/**
 * Class Counter.
 * simply a counter :).
 */
public class Counter {
    /**
     * a counter.
     */
    private int counter;

    /**
     * counter constructor.
     */
    public Counter() {
        this.counter = 0;
    }

    /**
     * counter constructor.
     * @param counter starting number to count.
     */
    public Counter(int counter) {
        this.counter = counter;
    }

    /**
     * increase the counter by the parameter number.
     * @param number how much to add to the counter.
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * decrease the counter by the parameter number.
     * @param number how much to decrease from the number.
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * @return the current count.
     */
    public int getValue() {
        return this.counter;
    }
}