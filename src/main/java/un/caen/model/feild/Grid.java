package un.caen.model.feild;

import un.caen.model.items.Bomb;
import un.caen.model.items.Item;
import un.caen.model.items.Mine;
import un.caen.model.items.Wall;
import un.caen.model.player.Player;
import un.caen.util.Cell;
import un.caen.util.Position;
/**
 * Represents the game grid, which consists of cells that can hold various items,
 * including players, bombs, mines, and walls. This class provides methods to manipulate
 * the grid, check cell availability, and display the current state of the grid.
 */
public class Grid {
    private Cell[][] cells;
    private int width;
    private int height;

    /**
     * Constructs a Grid with the specified width and height.
     *
     * @param width  the width of the grid
     * @param height the height of the grid
     * @throws IllegalArgumentException if width or height is less than or equal to zero
     */
    public Grid(int width, int height) {
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("Width and height must be positive integers");
        }else {
            this.width = width;
            this.height = height;
            cells = new Cell[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    cells[y][x] = new Cell();
                }
            }
        }
    }

    /**
     * Gets the cell at the specified position.
     *
     * @param position the position of the cell to retrieve
     * @return the Cell at the specified position
     * @throws IllegalArgumentException if the position is invalid
     */
    public Cell getCell(Position position) {
        if (isPositionValid(position)) {
            return cells[position.getY()][position.getX()];
        } else {
            throw new IllegalArgumentException("Invalid coordinates");
        }
    }

    /**
     * Sets the item in the cell at the specified position.
     *
     * @param item     the Item to set in the cell
     * @param position the position of the cell to set the item in
     * @throws IllegalArgumentException if the position is invalid
     */
    public void setCell(Item item, Position position) {
        if (isPositionValid(position)) {
            cells[position.getY()][position.getX()].setItem(item);
        }else {
            throw new IllegalArgumentException("Invalid coordinates");
        }

    }

    /**
     * Moves an item from one position to another in the grid.
     *
     * @param position    the current position of the item
     * @param newPosition the new position to move the item to
     * @throws IllegalArgumentException if either position is invalid
     */
    public void moveItem(Position position, Position newPosition) {
        if (isPositionValid(position) && isPositionValid(newPosition)){
            Item item = getCell(position).getItem();
            getCell(position).clearCell();
            setCell(item, newPosition);
        }else {
            throw new IllegalArgumentException("Invalid coordinates");
        }

    }

    /**
     * Displays the current state of the grid in the console.
     * Each cell is represented by a character or emoji based on its contents.
     */
    public void display() {
        System.out.println("------------ Game Grid ------------");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String cellChar = "";
                Cell cell = cells[y][x];
                if (cell.getItem() != null) {
                    if (cell.getItem() instanceof Bomb) {
//                        cellChar = String.format("[💣%d]", ((Bomb) cell.getItem()).getOwnerPlayerId());
                        cellChar = String.format("[B%d]", ((Bomb) cell.getItem()).getOwnerPlayerId());
                    } else if (cell.getItem() instanceof Mine) {
//                        cellChar = String.format("[🚩%d]", ((Mine) cell.getItem()).getOwnerPlayerId());
                        cellChar = String.format("[M%d]", ((Mine) cell.getItem()).getOwnerPlayerId());
                    } else if (cell.getItem() instanceof Player) {
//                        cellChar = String.format("[🥷%d]", ((Player) cell.getItem()).getId());
                        cellChar = String.format("[P%d]", ((Player) cell.getItem()).getId());
                    } else if (cell.getItem() instanceof Wall){
//                        cellChar = "[🧱🧱]";
                        cellChar = "[W]";
                    }
                }else{
                    cellChar = "[ . ]";
                }
                System.out.print(cellChar);
            }
            System.out.println();
        }System.out.println("----------------------------------");
    }

    /**
     * Gets the height of the grid.
     *
     * @return the height of the grid
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the grid.
     *
     * @return the width of the grid
     */
    public int getWidth() {
        return width;
    }
    public Boolean isPositionValid(Position position) {
        if(position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height){
            return true;
        }
        return false;
    }


    /**
     * Checks if the specified position is valid within the grid.
     *
     * @param position the position to check
     * @return true if the position is valid, false otherwise
     */
    public Boolean isCellAvailable(Position position) {
        if(getCell(position).getItem() instanceof Player || getCell(position).getItem() instanceof Wall){
            return false;
        }
        return true;
    }
}