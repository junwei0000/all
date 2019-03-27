package com.longcheng.volunteer.utils.teetime;

/**
 * The simple Array wheel adapter
 *
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {

    // items
    private T items[];
    // length
    private int length;

    /**
     * Constructor
     *
     * @param items  the items
     * @param length the max items length
     */
    public ArrayWheelAdapter(T items[], int length) {
        this.items = items;
        this.length = length;
    }

    /**
     * Contructor
     *
     * @param items the items
     */
    public ArrayWheelAdapter(T items[]) {
        this(items, -1);
    }

    public String getItem(int index) {
        if (index >= 0 && index < items.length) {
            return items[index].toString();
        }
        return null;
    }

    public int getItemsCount() {
        return items.length;
    }

    public int getMaximumLength() {
        return length;
    }

}
