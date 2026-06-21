package un.caen.model.player;

/**
 * Represents a fighter in the game.
 * This interface defines the essential methods for any entity that can fight,
 * including managing energy and weapon limits.
 */
public interface Fighter {

    /**
     * Gets the current energy of the fighter.
     *
     * @return the current energy level of the fighter
     */
    int getEnergy();

    /**
     * Gets the limit on the number of weapons the fighter can carry.
     *
     * @return the maximum number of weapons allowed for the fighter
     */
    int getWeaponsLimits();

    /**
     * Reduces the fighter's energy by the specified damage amount.
     *
     * @param damage the amount of energy to reduce
     */
    void reduceEnergy(int damage);
}