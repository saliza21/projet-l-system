package un.caen.view;

import un.caen.config.Config;
import un.caen.controller.GameController;
import un.caen.model.items.Bomb;
import un.caen.model.items.Gun;
import un.caen.model.items.Mine;
import un.caen.model.player.Fighter;
import un.caen.model.player.Player;
import un.caen.model.items.Weapon;
import un.caen.model.player.PlayerFactory;
import un.caen.util.Direction;
import un.caen.util.PlayerAction;
import un.caen.util.Strategy;

import java.util.*;

public class ConsoleView {
    private Scanner scanner;
    private GameController controller;

    public ConsoleView(GameController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
        runGame();
    }

    public void runGame() {
        intializeGame();
        gameLoop();
    }

    public void intializeGame() {
        selectionPhase();
        controller.positionPhase();
    }

    void selectionPhase() {
        List<Player> players = new ArrayList<>();
        System.out.println("##### Player Setup #####");
        for (int i = 1; i <= Config.NUMBER_OF_PLAYERS; i++) {
            System.out.println(String.format("Player %d Setup:(%d/%d)",i, i, Config.NUMBER_OF_PLAYERS));
            Fighter fighter = selectPlayerType(i);
            List<Weapon> weaponsList = selectWeapons(fighter);
            buildPlayersWeapons(i, fighter, weaponsList);
            players.add((Player) fighter);
        }
        controller.selectionPhase(players);
    }

    public void gameLoop() {
        boolean gameOver = false;
        displayGameState( controller.getCurrentPlayer());
//        view = new GameView(model.getGrid());
        while (!gameOver) {
            Player currentPlayer = controller.getCurrentPlayer();
            displayGameState(currentPlayer);
//            view.update(model);
            handlePlayerTurn(currentPlayer);

            if (controller.checkGameOver()) {
                gameOver = true;
                announceWinner();
            } else {
                controller.nextTurn();
            }
        }
    }


    public Fighter selectPlayerType(int playerId) {
        System.out.println("Enter your choice (1-4):");
        System.out.println("1. Leger (move strategy)");
        System.out.println("2. Lourd (move strategy)");
        System.out.println("3. Leger (attack strategy)");
        System.out.println("4. Lourd (attack strategy)");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0: return PlayerFactory.createLightPlayer(playerId, String.format("Player %d", playerId), Strategy.MOVE);
            case 1: return PlayerFactory.createHeavyPlayer(playerId, String.format("Player %d", playerId),Strategy.MOVE);
            case 2: return PlayerFactory.createHeavyPlayer(playerId, String.format("Player %d", playerId),Strategy.ATTACK);
            case 3: return PlayerFactory.createHeavyPlayer(playerId, String.format("Player %d", playerId),Strategy.ATTACK);
            default: throw new IllegalStateException("Invalid player type selection");
        }
    }

    public List<Weapon> selectWeapons(Fighter fighter) {
        System.out.println("Select weapon for Player " + ((Player) fighter).getName() + ":");
        List<Weapon> weaponList = new ArrayList<Weapon>();
        for (int i = 1; i <=fighter.getWeaponsLimits() ; i++) {
            System.out.println(String.format("select weapon N° : %d (%d/%d)",i,i,fighter.getWeaponsLimits()));
            System.out.println("1. Gun");
            System.out.println("2. Mine");
            System.out.println("3. Bomb");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: weaponList.add(new Gun(Config.DEFAULT_GUN_AMMO,((Player) fighter).getId(),true));break;
                case 2: weaponList.add(new Mine(((Player) fighter).getId()));break;
                case 3: weaponList.add(new Bomb(Config.DEFAULT_MINE_COUNTDOWN,((Player) fighter).getId(),false));break;
                default: throw new IllegalStateException("Invalid player type selection");
            }
        }
        return weaponList;
    }

    public void buildPlayersWeapons(int id, Fighter fighter, List<Weapon> weaponsList) {
        ((Player) fighter).setWeapons(weaponsList);
        System.out.println("Player " + id + " created successfully!");
    }

    public void displayGameState(Player currentPlayer) {
        System.out.println("\n=== " + currentPlayer.getName() + "'s Turn ===");
        System.out.println("Position: (" + (currentPlayer.getPosition().getX()+1) +
                "," + (currentPlayer.getPosition().getY()+1) + ")");
        System.out.println("Health: " + currentPlayer.getEnergy());
        controller.getPlayerStatus();
        controller.getBoardDisplay();
    }

    public Direction selectDirection(PlayerAction action, Weapon weapon) {
        if (action.equals(PlayerAction.MOVE) || (action.equals(PlayerAction.ATTACK) && weapon instanceof Gun)) {
            System.out.println("Choose direction:");
            System.out.println("1. Up");
            System.out.println("2. Down");
            System.out.println("3. Left");
            System.out.println("4. Right");
            int choice = getValidInput(1, 4);
            return Direction.values()[choice - 1];
        }
        if(action.equals(PlayerAction.ATTACK) && (weapon instanceof Bomb || weapon instanceof Mine)){
            System.out.println("Choose direction:");
            System.out.println("1. Up");
            System.out.println("2. Down");
            System.out.println("3. Left");
            System.out.println("4. Right");
            System.out.println("5. Up-Left");
            System.out.println("6. Up-Right");
            System.out.println("7. Down-Left");
            System.out.println("8. Down-Right");
            int choice = getValidInput(1, 8);
            return Direction.values()[choice - 1];
        }
        return null;
    }

    public PlayerAction selectAction() {
        System.out.println("Choose an action:");
        System.out.println("1. Move");
        System.out.println("2. Attack");

        int choice = getValidInput(1, 3);
        switch (choice) {
            case 1: return PlayerAction.MOVE;
            case 2: return PlayerAction.ATTACK;
            default: throw new IllegalStateException("Invalid action selection");
        }
    }

    public int getValidInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
            } catch (NumberFormatException e) {
                // Ignore and continue
            }
            System.out.println("Please enter a number between " + min + " and " + max);
        }
    }
    public void handlePlayerTurn(Player currentPlayer) {
        PlayerAction action = selectAction();
        Weapon weapon = null;
        Direction direction = null;
        if (action.equals(PlayerAction.ATTACK))
            weapon = selectWeapon(currentPlayer);
        if (!action.equals(PlayerAction.MOVE))
            direction = selectDirection(action,weapon);
        if(controller.applyPlayerAction(currentPlayer, action,weapon,direction)){
           controller.getPlayerStatus();
            controller.getGrid().display();
        }else{
            handlePlayerTurn(currentPlayer);
        }
    }

    public Weapon selectWeapon(Player player) {
        System.out.println("select weapon:");
        for (int i = 0; i < player.getWeapons().size(); i++) {
            System.out.println(String.format("%d- %s", i+1,player.getWeapons().get(i)));
        }

        int choice = getValidInput(1, player.getWeaponsLimits());
        return player.getWeapons().get(choice-1);
    }

    public void announceWinner() {
        if (controller.getPlayers().isEmpty()) {
            Player winner =controller.getPlayers().get(0);
            System.out.println("\n🎉 Player " + winner.getId() + " wins! 🎉");
        } else {
            System.out.println("\nGame Over - No winners!");
        }
    }

}