/**
 * The `PlayerProxy` class is a proxy for the `Player` class, which is part of the `un.caen.model.player` package.
 * The `PlayerProxy` class provides a way to access and update the properties of a `Player` object without directly interacting with the `Player` class.
 *
 * @author [Your Name]
 * @version 1.0
 */
package un.caen.model.player;

import un.caen.model.items.Weapon;
import un.caen.util.Position;

import java.util.List;

/**
 * The `PlayerProxy` class is a proxy for the `Player` class, which is part of the `un.caen.model.player` package.
 * The `PlayerProxy` class provides a way to access and update the properties of a `Player` object without directly interacting with the `Player` class.
 */
public class PlayerProxy {
    /**
     * The name of the player.
     */
    private String name;

    /**
     * The position of the player.
     */
    private Position position;

    /**
     * The energy level of the player.
     */
    private int energy;

    /**
     * The list of weapons the player is carrying.
     */
    private List<Weapon> weapons;

    /**
     * Constructs a new `PlayerProxy` object with the properties of the given `Player` object.
     *
     * @param player the `Player` object to be proxied
     */
    public PlayerProxy(Player player) {
        this.name = player.getName();
        this.position = player.getPosition();
        this.energy = player.getEnergy();
        this.weapons = player.getWeapons();
    }

    /**
     * Updates the properties of the `PlayerProxy` object with the properties of the given `Player` object.
     *
     * @param player the `Player` object to be used for the update
     */
    public void update(Player player) {
        this.name = player.getName();
        this.position = player.getPosition();
        this.energy = player.getEnergy();
        this.weapons = player.getWeapons();
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the position of the player.
     *
     * @return the position of the player
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns the energy level of the player.
     *
     * @return the energy level of the player
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Returns the list of weapons the player is carrying.
     *
     * @return the list of weapons the player is carrying
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }
}
