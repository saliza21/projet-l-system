package un.caen.model.items;

import un.caen.util.Direction;

/**
 * Represents a mine weapon in the game that can be placed by players.
 * The mine is hidden until it is triggered, at which point it explodes.
 */
public class Mine extends Weapon {
    private int ownerPlayerId; // The ID of the player who placed the mine

    /**
     * Constructs a Mine with the specified owner player ID.
     *
     * @param ownerPlayerId the ID of the player who owns the mine
     */
    public Mine(int ownerPlayerId) {
        super(10, 10, ownerPlayerId); // Calls the constructor of the Weapon class
        this.ownerPlayerId = ownerPlayerId;
        setVisible(false); // The mine is initially not visible
    }

    /**
     * Triggers the explosion of the mine.
     * This method simulates the action of the mine exploding.
     */
    public void explode() {
        System.out.println("Mine explodes!"); // Displays explosion message
    }

    /**
     * Uses the mine at a specified position (not implemented).
     *
     * @param x the x-coordinate where the mine is used
     * @param y the y-coordinate where the mine is used
     */
    @Override
    public void use(int x, int y) {
        // Implementation to be defined
    }

    /**
     * Uses the mine in a specified direction (not implemented).
     *
     * @param direction the Direction in which the mine is used
     */
    @Override
    public void use(Direction direction) {
        // Implementation to be defined
    }

    /**
     * Gets the ID of the player who placed the mine.
     *
     * @return the owner player ID
     */
    public int getOwnerPlayerId() {
        return ownerPlayerId; // Returns the owner player ID
    }
}