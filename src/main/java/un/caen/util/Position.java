/**
 * The `Position` class represents a two-dimensional position on a grid or game board.
 * It provides methods for accessing and modifying the x and y coordinates of the position,
 * as well as methods for checking the validity of the position.
 */
package un.caen.util;

import un.caen.config.Config;

import java.util.Objects;

/**
 * The `Position` class encapsulates the x and y coordinates of a position on a grid or game board.
 */
public class Position {
    /**
     * The `x` field represents the horizontal coordinate of the position.
     */
    private int x;
    /**
     * The `y` field represents the vertical coordinate of the position.
     */
    private int y;

    /**
     * The default constructor creates a `Position` object with both x and y coordinates set to 0.
     */
    public Position() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * The constructor creates a `Position` object with the specified x and y coordinates.
     * @param x the horizontal coordinate of the position
     * @param y the vertical coordinate of the position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The copy constructor creates a new `Position` object with the same coordinates as the
     * specified `Position` object.
     * @param position the `Position` object to be copied
     */
    public Position(Position position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    /**
     * The `getX()` method returns the horizontal coordinate of the position.
     * @return the horizontal coordinate of the position
     */
    public int getX() {
        return x;
    }

    /**
     * The `getY()` method returns the vertical coordinate of the position.
     * @return the vertical coordinate of the position
     */
    public int getY() {
        return y;
    }

    /**
     * The `setX(int x)` method sets the horizontal coordinate of the position.
     * @param x the new horizontal coordinate of the position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * The `setY(int y)` method sets the vertical coordinate of the position.
     * @param y the new vertical coordinate of the position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * The `hashCode()` method returns a hash code value for the `Position` object.
     * @return a hash code value for the `Position` object
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * The `equals(Object obj)` method compares the `Position` object to another object
     * and returns `true` if they are equal, `false` otherwise.
     * @param obj the object to be compared
     * @return `true` if the objects are equal, `false` otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position other = (Position) obj;
        return this.x == other.getX() && this.y == other.getY();
    }

    /**
     * The `isValid()` method checks whether the position is valid within the game or
     * simulation's grid or board.
     * @return `true` if the position is valid, `false` otherwise
     */
    public boolean isValid(){
        return x >= 0 && y >= 0 && x < Config.GRID_WIDTH && y < Config.GRID_HEIGHT;
    }
}
