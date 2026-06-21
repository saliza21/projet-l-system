package un.caen.model.items;

import un.caen.model.player.Player;
import un.caen.util.Direction;

/**
 * Represents a gun weapon in the game that can be used by players.
 * The gun has a certain amount of ammunition and can apply damage to players.
 */
public class Gun extends Weapon {
    private int ammo;
    private int ownerPlayerId;
    private boolean visibleToAll;


    /**
     * Constructs a Gun with the specified ammunition, owner player ID, and visibility setting.
     *
     * @param ammo         the initial amount of ammunition for the gun
     * @param ownerPlayerId the ID of the player who owns the gun
     * @param visibleToAll  true if the gun is visible to all players, false otherwise
     */
    public Gun(int ammo, int ownerPlayerId, boolean visibleToAll) {
        super(4, 10, ownerPlayerId);
        this.ammo = 12;
        this.ownerPlayerId = ownerPlayerId;
        this.visibleToAll = visibleToAll;
    }

    /**
     * Checks if the gun is visible to a specific player.
     *
     * @param playerId the ID of the player to check visibility for
     * @return true if the gun is visible to the player, false otherwise
     */
    public boolean isVisibleToPlayer(int playerId) {
        return visibleToAll || playerId == ownerPlayerId;
    }

    /**
     * Gets the ID of the player who owns the gun.
     *
     * @return the owner player ID
     */
    public int getOwnerPlayerId() {
        return ownerPlayerId;
    }

    /**
     * Applies the gun's effect to a player by reducing their energy.
     *
     * @param player the Player to apply the gun's effect to
     */
    @Override
    public void apply(Player player) {
        if(ammo>0){
            player.reduceEnergy(damage);
        }
    }

    /**
     * Uses the gun at a specified position (not implemented).
     *
     * @param x the x-coordinate where the gun is used
     * @param y the y-coordinate where the gun is used
     */
    @Override
    public void use(int x, int y) {

    }

    /**
     * Uses the gun, reducing the ammunition count by one.
     */
    public void use() {
            ammo--;
    }

    /**
     * Gets the current amount of ammunition in the gun.
     *
     * @return the current ammo count
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * Uses the gun in a specified direction (not implemented).
     *
     * @param direction the Direction in which the gun is used
     */
    @Override
    public void use(Direction direction) {

    }
}