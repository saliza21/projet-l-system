package un.caen.model.player;

import un.caen.util.Strategy;

/**
 * The `PlayerFactory` class is responsible for creating instances of `Fighter` objects.
 */
public class PlayerFactory {
    /**
     * Creates a new `LightFighter` object with the given parameters.
     *
     * @param id       the unique identifier of the player
     * @param name     the name of the player
     * @param strategy the strategy used by the player
     * @return a new `LightFighter` object
     */
    public static Fighter createLightPlayer(int id, String name, Strategy strategy) {
        return new LightFighter(id, name, strategy);
    }

    /**
     * Creates a new `HeavyFighter` object with the given parameters.
     *
     * @param id       the unique identifier of the player
     * @param name     the name of the player
     * @param strategy the strategy used by the player
     * @return a new `HeavyFighter` object
     */
    public static Fighter createHeavyPlayer(int id, String name, Strategy strategy) {
        return new HeavyFighter(id, name, strategy);
    }
}
