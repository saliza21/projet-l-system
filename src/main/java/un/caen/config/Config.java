package un.caen.config;
/**
 * The Config class contains constant configuration values for the game.
 * These constants define various parameters such as grid dimensions, player settings,
 * energy values, and weapon specifications.
 */
public class Config {
    /** The width of the game grid. */
    public static final int GRID_WIDTH = 6;

    /** The height of the game grid. */
    public static final int GRID_HEIGHT = 6;

    /** The size of each cell in the grid (in pixels). */
    public static final int CELL_SIZE = 50;

    /** The number of players in the game. */
    public static final int NUMBER_OF_PLAYERS = 2;

    /** The total number of energy kits available in the game. */
    public static final int TOTAL_ENERGY_KIT = 2;

    /** The default amount of energy for each player at the start of the game. */
    public static final int DEFAULT_ENERGY = 100;

    /** The default number of weapons that each player starts with. */
    public static final int DEFAULT_WEAPONS_SIZE = 1;

    /** The default amount of ammo for guns. */
    public static final int DEFAULT_GUN_AMMO = 12;

    /** The default countdown time for mines (in seconds). */
    public static final int DEFAULT_MINE_COUNTDOWN = 10;

    /** The maximum number of walls that can be placed on the grid. */
    public static final int MAX_WALL = 5;

    /** The default value of an energy kit when collected. */
    public static final int DEFAULT_ENERGYKIT_VALUE = 50;
}