/**
 * The `PlayerAction` enum represents the different actions a player can perform
 * in a game or simulation.
 */
package un.caen.util;

/**
 * The `PlayerAction` enum defines the following four actions:
 * <ul>
 *   <li>`MOVE`</li>
 *   <li>`ATTACK`</li>
 *   <li>`DEFEND`</li>
 *   <li>`ENERGY`</li>
 * </ul>
 * These actions can be used to represent the different ways a player can interact
 * with the game or simulation.
 */
public enum PlayerAction {
    /**
     * Represents the action of moving the player's character or object.
     */
    MOVE,
    /**
     * Represents the action of attacking another character or object.
     */
    ATTACK,

    /**
     * Represents the action of saving energy .
     */
    ENERGY
}
