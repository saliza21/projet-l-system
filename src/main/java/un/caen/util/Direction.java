/**
 * The `Direction` enum represents the eight possible directions of movement in a
 * two-dimensional grid or game board.
 */
package un.caen.util;

/**
 * The `Direction` enum defines the following eight directions:
 * <ul>
 *   <li>`UP`</li>
 *   <li>`DOWN`</li>
 *   <li>`LEFT`</li>
 *   <li>`RIGHT`</li>
 *   <li>`UP_LEFT`</li>
 *   <li>`UP_RIGHT`</li>
 *   <li>`DOWN_LEFT`</li>
 *   <li>`DOWN_RIGHT`</li>
 * </ul>
 * These directions can be used to represent the movement of objects or characters
 * in a game or simulation.
 */
public enum Direction {
    /**
     * Represents movement in the upward direction.
     */
    UP,
    /**
     * Represents movement in the downward direction.
     */
    DOWN,
    /**
     * Represents movement in the leftward direction.
     */
    LEFT,
    /**
     * Represents movement in the rightward direction.
     */
    RIGHT,
    /**
     * Represents movement in the upward-leftward direction.
     */
    UP_LEFT,
    /**
     * Represents movement in the upward-rightward direction.
     */
    UP_RIGHT,
    /**
     * Represents movement in the downward-leftward direction.
     */
    DOWN_LEFT,
    /**
     * Represents movement in the downward-rightward direction.
     */
    DOWN_RIGHT
}
