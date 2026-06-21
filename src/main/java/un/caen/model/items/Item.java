package un.caen.model.items;

import un.caen.util.Direction;
import un.caen.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract item in the game.
 * This class serves as a base for all specific item types (e.g., bombs, mines, walls).
 * Each item has an ID, visibility status, and a position on the grid.
 */
public abstract class Item {
protected int id;
protected Boolean visible;
protected Position position;
public static int ItemCount = 0;

    /**
     * Constructs an Item and increments the global item count.
     */
    public Item(){
        ItemCount++;
    }


    /**
     * Gets the ID of the item.
     *
     * @return the ID of the item
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the item.
     *
     * @param id the new ID of the item
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if the item is visible.
     *
     * @return true if the item is visible, false otherwise
     */
    public Boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the item.
     *
     * @param visible true to make the item visible, false to hide it
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets the current position of the item.
     *
     * @return the Position of the item
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the item.
     *
     * @param position the new Position of the item
     */
    public void setPosition(Position position) {
        this.position = position;
    }


    /**
     * Gets the next position of the item based on the specified direction.
     *
     * @param direction the Direction to move
     * @return the Position of the item in the specified direction
     */
    public Position getNextPosition(Direction direction){
        Position nextPosition = null;
        switch (direction) {
            case UP:
                nextPosition = new Position(position.getX(), position.getY() - 1);
                break;
            case DOWN:
                nextPosition = new Position(position.getX(), position.getY() + 1);
                break;
            case LEFT:
                nextPosition = new Position(position.getX() - 1, position.getY());
                break;
            case RIGHT:
                nextPosition = new Position(position.getX() + 1, position.getY());
                break;
            case UP_LEFT:
                nextPosition = new Position(position.getX() - 1, position.getY() - 1);
                break;
            case UP_RIGHT:
                nextPosition = new Position(position.getX() + 1, position.getY() - 1);
                break;
            case DOWN_LEFT:
                nextPosition = new Position(position.getX() - 1, position.getY() + 1);
                break;
            case DOWN_RIGHT:
                nextPosition = new Position(position.getX() + 1, position.getY() + 1);
                break;
            default:
                nextPosition =position;
        }
        return nextPosition;
    }


    /**
     * Gets a list of positions around the item based on all possible directions.
     *
     * @return a List of Positions around the item
     */
    public List<Position> getPositionsAround(){
        List<Position> list = new ArrayList<>();
        for(Direction direction : Direction.values()){
            if(getNextPosition(direction).isValid())
                list.add(getNextPosition(direction));
        }
        return list;
    }
}