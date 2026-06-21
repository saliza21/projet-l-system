package un.caen.model.items;

import un.caen.util.Position;

/**
 * Represents an energy item in the game.
 * This item can be collected by players to restore energy.
 */
public class Energy extends Item {

    /**
     * Constructs an Energy item at the specified position.
     *
     * @param position the Position where the energy item is located
     */
    public Energy(Position position) {
        super();
        setPosition(position);
    }
}