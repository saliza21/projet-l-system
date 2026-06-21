package un.caen.model.items;

import un.caen.model.player.Player;
import un.caen.util.Direction;

/**
 * Represents an abstract weapon in the game.
 * This class serves as a base for all specific weapon types (e.g., Gun, Bomb, Mine).
 * Each weapon has a range, damage value, and an owner player ID.
 */
public abstract class Weapon extends Item {
    protected int range; // The effective range of the weapon
    protected int damage; // The damage dealt by the weapon
    protected int ownerPlayerId; // The ID of the player who owns the weapon

    /**
     * Constructs a Weapon with the specified range, damage, and owner player ID.
     *
     * @param range         the effective range of the weapon
     * @param damage        the damage dealt by the weapon
     * @param ownerPlayerId the ID of the player who owns the weapon
     */
    public Weapon(int range, int damage, int ownerPlayerId) {
        this.range = range;
        this.damage = damage;
        this.ownerPlayerId = ownerPlayerId;
    }

    /**
     * Applies the weapon's effect to a specified player by reducing their energy.
     *
     * @param player the Player to apply the weapon's effect to
     */
    public void apply(Player player) {
        player.reduceEnergy(damage); // Reduce the player's energy by the damage amount
    }

    /**
     * Uses the weapon at a specified position (to be implemented by subclasses).
     *
     * @param x the x-coordinate where the weapon is used
     * @param y the y-coordinate where the weapon is used
     */
    public abstract void use(int x, int y);

    /**
     * Uses the weapon in a specified direction (to be implemented by subclasses).
     *
     * @param direction the Direction in which the weapon is used
     */
    public abstract void use(Direction direction);

    /**
     * Gets the damage dealt by the weapon.
     *
     * @return the damage value of the weapon
     */
    public int getDamage() {
        return damage; // Returns the damage value
    }

    /**
     * Gets the effective range of the weapon.
     *
     * @return the range of the weapon
     */
    public int getRange() {
        return range; // Returns the range value
    }
}