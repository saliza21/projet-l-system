package un.caen.model.player;

import un.caen.config.Config;
import un.caen.util.Strategy;

/**
 * The `HeavyFighter` class represents a type of `Player` that is a `Fighter`.
 * It extends the `Player` class and implements the `Fighter` interface.
 */
public class HeavyFighter extends Player implements Fighter {
    /**
     * Constructs a new `HeavyFighter` object with the given parameters.
     *
     * @param id       the unique identifier of the player
     * @param name     the name of the player
     * @param strategy the strategy used by the player
     */
    public HeavyFighter(int id, String name, Strategy strategy) {
        super(id, name, strategy);
        energy = Config.DEFAULT_ENERGY + 50;
        weaponsLimits = Config.DEFAULT_WEAPONS_SIZE;
    }
}
