/**
 * The `Cell` class represents a single cell in a game or simulation.
 * Each cell can contain an `Item` object, which represents some kind of game
 * element or object.
 */
package un.caen.util;

import un.caen.model.items.Item;

/**
 * The `Cell` class provides methods for managing the item contained within the cell.
 */
public class Cell {
    /**
     * The `item` field represents the item currently contained within the cell.
     */
    private Item item;

    /**
     * The `getItem()` method returns the item currently contained within the cell.
     * @return the `Item` object currently contained within the cell
     */
    public Item getItem() {
        return item;
    }

    /**
     * The `setItem(Item item)` method sets the item contained within the cell.
     * @param item the `Item` object to be set within the cell
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * The `clearCell()` method removes the item currently contained within the cell,
     * effectively clearing the cell.
     */
    public void clearCell(){
        this.item = null;
    }

    /**
     * The `isEmpty()` method returns a boolean value indicating whether the cell is
     * currently empty (i.e., does not contain an item).
     * @return `true` if the cell is empty, `false` otherwise
     */
    public Boolean isEmpty(){
        return item == null;
    }
}
