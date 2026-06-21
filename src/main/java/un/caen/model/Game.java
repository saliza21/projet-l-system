package un.caen.model;

import un.caen.config.Config;
import un.caen.model.feild.Grid;
import un.caen.model.items.*;
import un.caen.model.player.Player;
import un.caen.util.Cell;
import un.caen.util.Direction;
import un.caen.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents the game logic for the battle game.
 * This class manages the game grid, players, turns, and actions such as moving and attacking.
 */
public class Game {
    private Grid grid;
    private List<Player> players;
    private int currentPlayerIndex;

    /**
     * Constructs a Game with a specified width and height for the grid.
     *
     * @param width  the width of the game grid
     * @param height the height of the game grid
     */
    public Game(int width, int height) {
        this.grid = new Grid(width, height);
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }

    /**
     * Constructs a Game with a specified width, height, and a list of players.
     *
     * @param width   the width of the game grid
     * @param height  the height of the game grid
     * @param players the list of players participating in the game
     */
    public Game(int width, int height, List<Player> players) {
        this.grid = new Grid(width, height);
        this.players = players;
        this.currentPlayerIndex = 0;
    }

    /**
     * Advances to the next player's turn.
     */
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Gets the current player whose turn it is.
     *
     * @return the current Player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Gets the game grid.
     *
     * @return the Grid object representing the game grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Gets the list of players in the game.
     *
     * @return the list of Player objects
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players in the game.
     *
     * @param players the new list of Player objects
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Adds a player to the game.
     *
     * @param player the Player to add
     */
    public void addPlayer(Player player) {
        players.add(player);
    }


    /**
     * Displays the status of all players, including their positions and energies.
     */
    public void playerStatus(){
        for (Player p : players){
            System.out.println(String.format("%d- %s (%d,%d) : %d",p.getId(),p.getName(),p.getPosition().getX(),p.getPosition().getY(),p.getEnergy()));
            for(Weapon w : p.getWeapons()){
                System.out.println("    |-"+w.getId()+"- "+w.getClass());
            }
        }
    }

    /**
     * Handles moving a player in a specified direction.
     *
     * @param player    the Player to move
     * @param direction the direction to move the player
     * @return true if the move was successful, false otherwise
     */
    public Boolean handleMove(Player player, Direction direction) {
        Position currentPosition = new Position(player.getPosition().getX(), player.getPosition().getY());
        Position newPosition = player.getNextPosition(direction);
        if(grid.isPositionValid(newPosition) && grid.isCellAvailable(newPosition)){
            if(grid.getCell(newPosition).getItem() instanceof Mine){
                Mine mine =(Mine) grid.getCell(newPosition).getItem();
                for(Position pos : mine.getPositionsAround()){
                    if(grid.getCell(pos).getItem() instanceof Player){
                        applyAttackEffect(mine, pos);
                        continue;
                    }
                    if(grid.getCell(pos).getItem() instanceof Wall)continue;
                    grid.setCell(new Trace(),pos);
                }
            }
            if(grid.getCell(newPosition).getItem() instanceof Energy){
                player.setEnergy(Config.DEFAULT_ENERGYKIT_VALUE);
            }
            player.move(direction);
            Boolean isMineExistInNextPosition = grid.getCell(newPosition).getItem() instanceof Mine;
            grid.moveItem(currentPosition, newPosition);
            if(isMineExistInNextPosition)grid.setCell(new Trace(),currentPosition);
            return true;
        }
        return false;
    }

    /**
     * Handles attacking with a specified weapon in a given direction.
     *
     * @param player    the Player performing the attack
     * @param weapon    the Weapon used for the attack
     * @param direction the direction of the attack
     * @return true if the attack was successful, false otherwise
     */
    public Boolean handleAttack(Player player, Weapon weapon, Direction direction) {
       if(weapon instanceof Gun){
          return GunAttack(player,weapon, direction);
       }
        if(weapon instanceof Mine || weapon instanceof Bomb){
            return BombMineAttack(player,weapon, direction);
        }
        return false;
    }

    /**
     * Handles a gun attack from a player in a specified direction.
     *
     * @param player    the Player performing the attack
     * @param weapon    the Gun used for the attack
     * @param direction the direction of the attack
     * @return true if the attack was successful, false otherwise
     */
    public Boolean GunAttack(Player player, Weapon weapon, Direction direction) {
        if(((Gun) weapon).getAmmo()==0){
            return false;
        }
        ((Gun) weapon).use();
        switch (direction) {
            case UP:
                for (int i = player.getPosition().getY(); i>1 && i >player.getPosition().getY()-weapon.getRange(); i--) {
                    Position nextPosition = new Position(player.getPosition().getX(),i-1);
                    if( grid.getCell(nextPosition).getItem() instanceof Wall) break;
                    if( grid.getCell(nextPosition).getItem() instanceof Player){
                        applyAttackEffect(weapon, nextPosition);
                        break;
                    }
                    grid.setCell(new Trace(),nextPosition);

                }
                return true;
            case DOWN:
                for (int i = player.getPosition().getY(); i< Config.GRID_HEIGHT-1 && i <player.getPosition().getY()+weapon.getRange(); i++) {
                    Position nextPosition = new Position(player.getPosition().getX(),i+1);
                    if( grid.getCell(nextPosition).getItem() instanceof Wall) break;
                    if( grid.getCell(nextPosition).getItem() instanceof Player){
                        applyAttackEffect(weapon, nextPosition);
                        break;
                    }
                    grid.setCell(new Trace(),nextPosition);
                }
                return true;
            case LEFT:
                for (int i = player.getPosition().getX(); i>1 && i >player.getPosition().getX()-weapon.getRange(); i--) {
                    Position nextPosition = new Position(i-1,player.getPosition().getY());
                    if( grid.getCell(nextPosition).getItem() instanceof Wall) break;
                    if( grid.getCell(nextPosition).getItem() instanceof Player){
                        applyAttackEffect(weapon, nextPosition);
                        break;
                    }
                    grid.setCell(new Trace(),nextPosition);
                }
                return true;
            case RIGHT:
                for (int i = player.getPosition().getX(); i<Config.GRID_WIDTH-1 && i <player.getPosition().getX()+weapon.getRange(); i++) {
                    Position nextPosition = new Position(i+1,player.getPosition().getY());
                    if( grid.getCell(nextPosition).getItem() instanceof Wall) break;
                    if( grid.getCell(nextPosition).getItem() instanceof Player){
                        applyAttackEffect(weapon, nextPosition);
                        break;
                    }
                    grid.setCell(new Trace(),nextPosition);
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * Applies the attack effect of a weapon on a player at the specified position.
     *
     * @param weapon        the Weapon used for the attack
     * @param nextPosition  the position of the attacked player
     */
    private void applyAttackEffect(Weapon weapon, Position nextPosition) {
        weapon.apply((Player) grid.getCell(nextPosition).getItem());
        if(((Player) grid.getCell(nextPosition).getItem()).getEnergy()==0){
            players.removeIf(p -> p.equals(grid.getCell(nextPosition).getItem()));
            grid.setCell(null, nextPosition);
        }
    }

    /**
     * Handles a bomb or mine attack from a player in a specified direction.
     *
     * @param player    the Player performing the attack
     * @param weapon    the Bomb or Mine used for the attack
     * @param direction the direction of the attack
     * @return true if the attack was successful, false otherwise
     */
    public Boolean BombMineAttack(Player player, Weapon weapon, Direction direction) {
        Timer timer = new Timer();
        Position nextPosition = weapon.getNextPosition(direction);
        if( !grid.isPositionValid(nextPosition) || grid.getCell(nextPosition).getItem() instanceof Wall){
            return false;
        }else{
            weapon.setPosition(nextPosition);
            grid.setCell(weapon,nextPosition);
            grid.display();
            player.getWeapons().remove(weapon);
            if(weapon instanceof Bomb){
                ((Bomb) weapon).tick();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        for(Position pos : weapon.getPositionsAround()){
                            if(grid.getCell(pos).getItem() instanceof Player){
                                applyAttackEffect(weapon, pos);
                            }
                            if(grid.getCell(pos).getItem() instanceof Wall)continue;
                            if(grid.getCell(pos).getItem() instanceof Player)continue;
                            grid.setCell(new Trace(),pos);
                        }
                        grid.display();
                        grid.setCell(new Trace(),weapon.getPosition());
                    }
                };
                timer.schedule(task,  ((Bomb) weapon).tick());
            }
            return true;
        }
    }

    /**
     * Clears all trace items from the grid.
     */
    public void clearTrace(){
            for (int y = 0; y < grid.getHeight(); y++) {
                for (int x = 0; x < grid.getWidth(); x++) {
                    if(grid.getCell(new Position(x,y)).getItem() instanceof Trace){
                        grid.getCell(new Position(x,y)).setItem(null);
                    }
                }
            }
    }


}