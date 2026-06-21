package un.caen.model.items;

import un.caen.util.Position;

/**
 * Represents a wall item in the game.
 * This class serves as an obstacle that players cannot pass through.
 */
public class Wall extends Item {

    /**
     * Constructs a Wall item at the specified position.
     *
     * @param position the Position where the wall is located
     */
    public Wall(Position position) {
        super(); // Calls the constructor of the Item class
        setPosition(position); // Sets the position of the wall
    }
}