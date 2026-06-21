package un.caen.model.player;

import un.caen.config.Config;
import un.caen.model.items.Item;
import un.caen.model.items.Weapon;
import un.caen.util.Direction;
import un.caen.util.Position;
import un.caen.util.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 * The player has a name, a list of weapons, energy, and a strategy for movement.
 * This class implements the Fighter interface, providing methods to manage energy and weapons.
 */
public class Player extends Item implements Fighter{
    private String name;
    private List<Weapon> weapons;
    protected int energy = Config.DEFAULT_ENERGY;
    protected int weaponsLimits = Config.DEFAULT_WEAPONS_SIZE;
    protected Strategy strategy;

    /**
     * Constructs a Player with the specified ID, name, and strategy.
     *
     * @param id       the unique identifier for the player
     * @param name     the name of the player
     * @param strategy the movement strategy for the player
     */
    public Player(int id, String name,Strategy strategy) {
        this.id = id;
        this.name = name;
        this.weapons = new ArrayList<>();
        this.position = new Position();
        this.strategy = strategy;
    }

    /**
     * Moves the player in the specified direction.
     *
     * @param direction the direction to move the player
     * @return the new position of the player after the move
     */
    public Position move(Direction direction) {
            Position nextPosition = getNextPosition(direction);
            if(nextPosition.isValid()){
                setPosition(nextPosition);
                System.out.println(name + " moves " + direction + " to position (" + position.getX() + ", " + position.getY() + ")");
            }
        return nextPosition;
    }

    /**
     * Gets the next position of the player based on the specified direction.
     *
     * @param direction the direction to calculate the next position
     * @return the next Position of the player
     */
    public Position getNextPosition(Direction direction) {
        Position nextPosition = new Position(position);
        switch (direction) {
            case UP:
                nextPosition.setY(position.getY() - 1);
                break;
            case DOWN:
                nextPosition.setY( position.getY() + 1);
                break;
            case LEFT:
                nextPosition.setX(position.getX() - 1);
                break;
            case RIGHT:
                nextPosition.setX( position.getX() + 1);
                break;
        }
        return nextPosition;
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current energy of the player.
     *
     * @return the current energy level of the player
     */
    @Override
    public int getEnergy() {
        return energy;
    }

    /**
     * Increases the player's energy by the specified amount.
     *
     * @param kitEnergy the amount of energy to add
     */
    public void setEnergy(int kitEnergy) {
        this.energy += kitEnergy;
    }

    /**
     * Gets the strategy of the player.
     *
     * @return the player's movement strategy
     */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * Gets the limit on the number of weapons the player can carry.
     *
     * @return the maximum number of weapons allowed for the player
     */
    @Override
    public int getWeaponsLimits() {
        return weaponsLimits;
    }

    /**
     * Reduces the player's energy by the specified damage amount.
     *
     * @param damage the amount of energy to reduce
     */
    @Override
    public void reduceEnergy(int damage) {
        this.energy = energy - damage;
    }


    /**
     * Sets the position of the player and updates the position of all owned weapons.
     *
     * @param position the new Position of the player
     */
    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        for(Weapon w : weapons){
            w.setPosition(position);
        }
    }

    /**
     * Gets the count of weapons owned by the player.
     *
     * @return the number of weapons the player currently has
     */
    public int getWeaponsCount() {
        return weapons.size();
    }

    /**
     * Gets the list of weapons owned by the player.
     *
     * @return a List of the player's weapons
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Adds a weapon to the player's inventory.
     *
     * @param weapon the Weapon to add to the player's inventory
     */
    public void addWeapon(Weapon weapon) {
        weapon.setPosition(position);
        weapons.add(weapon);
    }

    /**
     * Sets the list of weapons for the player.
     *
     * @param weapons the new List of weapons for the player
     */
    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

}

