package un.caen.model.items;

import un.caen.util.Direction;

/**
 * Represents a bomb item in the game, which can be placed by players.
 * The bomb has a countdown timer and visibility settings based on the owner.
 */
public class Bomb extends Weapon {
    private int countdown; // The countdown duration for the bomb in seconds
    private boolean visibleToAll; // Indicates if the bomb is visible to all players

    /**
     * Constructs a Bomb with the specified countdown, owner player ID, and visibility setting.
     *
     * @param countdown     the countdown duration for the bomb (in seconds)
     * @param ownerPlayerId the ID of the player who placed the bomb
     * @param visibleToAll  true if the bomb is visible to all players, false otherwise
     */
    public Bomb(int countdown, int ownerPlayerId, boolean visibleToAll) {
        super(10, 10, ownerPlayerId);
        this.countdown = countdown;
        this.visibleToAll = visibleToAll;
    }

    /**
     * Checks if the bomb is visible to a specific player.
     *
     * @param playerId the ID of the player to check visibility for
     * @return true if the bomb is visible to the player, false otherwise
     */
    public boolean isVisibleToPlayer(int playerId) {
        return visibleToAll || playerId == super.ownerPlayerId;
    }

    /**
     * Gets the ID of the player who placed the bomb.
     *
     * @return the owner player ID
     */
    public int getOwnerPlayerId() {
        return super.ownerPlayerId;
    }

    /**
     * Returns the countdown time for the bomb in milliseconds.
     *
     * @return the countdown time in milliseconds
     */
    public int tick() {
        return countdown * 1000;
    }

    /**
     * Uses the bomb at a specified position (not implemented).
     *
     * @param x the x-coordinate where the bomb is used
     * @param y the y-coordinate where the bomb is used
     */
    @Override
    public void use(int x, int y) {
        // Implementation to be defined
    }

    /**
     * Uses the bomb in a specified direction (not implemented).
     *
     * @param direction the Direction in which the bomb is used
     */
    @Override
    public void use(Direction direction) {
        // Implementation to be defined
    }
}