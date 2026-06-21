package un.caen.controller;

import un.caen.config.Config;
import un.caen.model.*;
import un.caen.model.feild.Grid;
import un.caen.model.items.*;
import un.caen.model.player.Player;
import un.caen.model.player.PlayerProxy;
import un.caen.util.Direction;
import un.caen.util.PlayerAction;
import un.caen.util.Position;

import java.util.*;
/**
 * The GameController class manages the game logic and player interactions in a grid-based game.
 * It handles the setup of the game environment, including walls and energy kits,
 * manages player positioning, actions (move, attack, energy), and checks for game over conditions.
 */
public class GameController {

    private Game model;
    private List<PlayerProxy> playerProxies = new ArrayList<>();

    /**
     * Constructs a GameController with the specified game model.
     *
     * @param game the game model to be controlled
     */
    public GameController(Game game) {
        this.model = game;
    }

    /**
     * Returns the list of PlayerProxy objects representing the players in the game.
     *
     * @return a list of PlayerProxy objects
     */
    public List<PlayerProxy> getPlayerProxies() {
        return playerProxies;
    }

    /**
     * Initiates the positioning phase of the game, setting up player positions.
     */
    public void positionPhase() {
        playersSetup(envirementSetup());
    }

    /**
     * Sets up player positions on the grid while avoiding wall positions.
     *
     * @param wallsPoistions a set of positions occupied by walls
     */
    private void playersSetup(Set<Position> wallsPoistions) {
        Set<Position> playersPositionsSet = new HashSet<Position>();
        while (playersPositionsSet.size()< this.model.getPlayers().size()){
            Random random = new Random();
            int x = random.nextInt(Config.GRID_WIDTH-1);
            int y = random.nextInt(Config.GRID_HEIGHT-1);
            if (!wallsPoistions.contains(new Position(x, y))){
                playersPositionsSet.add(new Position(x, y));
            }
        }
        List<Position> playersPositionsList = new ArrayList<Position>(playersPositionsSet);
        for (int i = 0; i < playersPositionsList.size(); i++) {
            this.model.getPlayers().get(i).setPosition(playersPositionsList.get(i));
            this.model.getGrid().setCell(this.model.getPlayers().get(i),playersPositionsList.get(i));
        }
        for (int i = 0; i < playersPositionsList.size(); i++) {
            this.model.getPlayers().get(i).setPosition(playersPositionsList.get(i));
            this.model.getGrid().setCell(this.model.getPlayers().get(i),playersPositionsList.get(i));
        }
        for(Player player: model.getPlayers()){
            playerProxies.add(new PlayerProxy(player));
        }
    }

    /**
     * Sets up the game environment, including walls and energy kits.
     *
     * @return a set of positions occupied by walls and energy kits
     */
    private Set<Position> envirementSetup() {
        // set up walls
        Set<Position> wallPositions = new HashSet<Position>();
        Random random = new Random();
        while(wallPositions.toArray().length<Config.MAX_WALL) {
            int x = random.nextInt(Config.GRID_WIDTH - 1);
            int y = random.nextInt(Config.GRID_HEIGHT - 1);
            wallPositions.add(new Position(x, y));
        }
        List<Position> wallPositionsList = new ArrayList<>(wallPositions);
        for(int i = 0; i < wallPositionsList.size(); i++) {
            Wall wall = new Wall(wallPositionsList.get(i));
            this.model.getGrid().setCell(wall,wall.getPosition());
        }

        // set up energy kits
        Set<Position> energykits = new HashSet<Position>();
        while(energykits.toArray().length<Config.TOTAL_ENERGY_KIT) {
            int x = random.nextInt(Config.GRID_WIDTH - 1);
            int y = random.nextInt(Config.GRID_HEIGHT - 1);
            energykits.add(new Position(x, y));
        }
        List<Position> energykitsList = new ArrayList<>(energykits);
        for(int i = 0; i < energykitsList.size(); i++) {
            Energy energyKit = new Energy(energykitsList.get(i));
            this.model.getGrid().setCell(energyKit,energyKit.getPosition());
        }
        Set<Position> itemsPosition = new HashSet<Position>();
                itemsPosition.addAll(wallPositions);
        itemsPosition.addAll(energykits);;
        return itemsPosition;
    }

    /**
     * Sets the players for the game.
     *
     * @param players a list of players to be set in the game model
     */
    public void selectionPhase(List<Player> players) {
            this.model.setPlayers(players);
    }

    /**
     * Applies the action of the current player based on the specified action type.
     *
     * @param currentPlayer the player performing the action
     * @param action the action to be performed (MOVE, ATTACK, ENERGY)
     * @param weapon the weapon used for the attack (if applicable)
     * @param direction the direction of the action
     * @return true if the action was successful, false otherwise
     */
    public Boolean applyPlayerAction(Player currentPlayer, PlayerAction action, Weapon weapon, Direction direction) {
        switch (action) {
            case MOVE:
                Boolean move = model.handleMove(currentPlayer,direction);
                updateProxies(model.getPlayers());
                return move;
            case ATTACK:
                Boolean attack = model.handleAttack(currentPlayer,weapon,direction);
                updateProxies(model.getPlayers());
                return attack;
            case ENERGY:
                return true;
            default:
                return false;
        }
    }

    /**
     * Updates the PlayerProxy objects to reflect the current state of the players.
     *
     * @param players the list of players to update the proxies with
     */
    private void updateProxies(List<Player> players) {
        for (int i = 0; i < playerProxies.size(); i++) {
            playerProxies.get(i).update(players.get(i));
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over (1 or fewer players), false otherwise
     */
    public boolean checkGameOver() {
        return model.getPlayers().size() <= 1;
    }

    /**
     * Announces the winner of the game.
     *
     * @return the winning player
     */
    public Player announceWinner() {
        return model.getPlayers().get(0);
    }

    /**
     * Retrieves the list of players currently in the game.
     *
     * @return a list of players
     */
    public List<Player> getPlayers(){
        return model.getPlayers();
    }

    /**
     * Retrieves the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer(){
        return model.getCurrentPlayer();
    }

    /**
     * Retrieves the status of the players.
     */
    public void getPlayerStatus(){
        model.playerStatus();
    }

    /**
     * Advances to the next turn in the game.
     */
    public void nextTurn(){
        model.nextTurn();
    }

    /**
     * Displays the current state of the game board.
     */
    public void getBoardDisplay(){
        model.getGrid().display();
    }

    /**
     * Retrieves the grid of the game.
     *
     * @return the grid of the game
     */
    public Grid getGrid(){
        return model.getGrid();
    }

    /**
     * Clears the trace of actions in the game.
     */
    public void clearTrace(){
        model.clearTrace();
    }
}