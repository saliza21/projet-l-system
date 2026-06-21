package un.caen.model.player;

import un.caen.config.Config;
import un.caen.util.Strategy;

/**
 * The `LightFighter` class represents a type of `Player` that is a `Fighter`.
 * It extends the `Player` class and implements the `Fighter` interface.
 */
public class LightFighter extends Player implements Fighter {
    /**
     * Constructs a new `LightFighter` object with the given parameters.
     *
     * @param id       the unique identifier of the player
     * @param name     the name of the player
     * @param strategy the strategy used by the player
     */
    public LightFighter(int id, String name, Strategy strategy) {
        super(id, name, strategy);
        energy = Config.DEFAULT_ENERGY;
        weaponsLimits = Config.DEFAULT_WEAPONS_SIZE + 2;
    }
}
