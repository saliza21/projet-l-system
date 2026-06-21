package un.caen.view;


import un.caen.config.Config;
import un.caen.controller.GameController;
import un.caen.model.items.*;
import un.caen.model.player.Fighter;
import un.caen.model.player.Player;
import un.caen.model.player.PlayerFactory;
import un.caen.model.player.PlayerProxy;
import un.caen.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * Represents the game board view for the battle game.
 * This class handles the graphical user interface (GUI) for the game,
 * including the display of the game grid, player stats, and player actions.
 */
public class GameBoardView extends JFrame implements KeyListener {
    private GameController controller;

    Color[] colorList = new Color[] { Color.BLACK,Color.BLUE,  Color.GREEN, Color.YELLOW, Color.RED, };
    //table
    private JPanel tablePanel;
    private JTable gridTable;
    private un.caen.view.GridModel tableModel;
    //player status
    private JPanel statusPanel;
    private DefaultTableModel statsModel;
    private JTable statsTable;
    private JLabel playerInfoLabel;

    // action modes
    private boolean isGunMode = false;
    private boolean isBombMode = false;
    private boolean isMineMode = false;

    List<PlayerFrame> playerFrameList = new ArrayList<PlayerFrame>();

    private ImageIcon playerImage = new ImageIcon(getClass().getResource("/soldier.png"));
    private ImageIcon bombImage = new ImageIcon(getClass().getResource("/bomb.png"));
    private ImageIcon mineImage = new ImageIcon(getClass().getResource("/mine.png"));
    private ImageIcon pillImage = new ImageIcon(getClass().getResource("/pill.png"));
    private ImageIcon wallImage = new ImageIcon(getClass().getResource("/brick-wall.png"));


    static ImageIcon resizeIcon(ImageIcon icone){
        return new ImageIcon(icone.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
    }
    /**
     * Constructs a GameBoardView with the specified GameController.
     *
     * @param controller the GameController that manages the game logic
     */
    public GameBoardView(GameController controller/*, Grid gameGrid*/) {
        this.controller = controller;
        // Set up the frame
        setTitle("Battle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the grid table
        selectionPhase();
        initializeGridTable();
        // Create player info label
        playerInfoLabel = new JLabel();
        playerInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updatePlayerInfo();

        setLayout(new BorderLayout());
        JPanel parentPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout to center content

// Create tablePanel with BorderLayout
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(gridTable, BorderLayout.CENTER); // Add gridTable to tablePanel
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding

// Add tablePanel to the parent panel with GridBagConstraints for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Single column
        gbc.gridy = 0; // Single row
        gbc.weightx = 1.0; // Allow horizontal expansion
        gbc.weighty = 1.0; // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER; // Center the tablePanel
        gbc.fill = GridBagConstraints.NONE; // Do not stretch the component

        parentPanel.add(tablePanel, gbc); // A

        statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(getPlayerStatsPanel(), BorderLayout.CENTER);
        add(parentPanel,BorderLayout.CENTER);
        add(getPlayerStatsPanel(),BorderLayout.SOUTH);

        add(playerInfoLabel, BorderLayout.NORTH);
        for(Player player:controller.getPlayers()){
            playerFrameList.add(new PlayerFrame(player,gridTable.getModel()));
        }
        updatePlayersFrames(gridTable.getModel());

        // Add key listener
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocus();
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                requestFocus();
            }

            @Override
            public void focusLost(FocusEvent e) {
                requestFocus();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * Initializes the grid table with game grid dimensions and appearance.
     */
    private void initializeGridTable() {
        // Create table model with game grid dimensions
        tableModel = new un.caen.view.GridModel(controller.getGrid());
        gridTable = new JTable(tableModel);
        controller.getGrid().display();
        // Customize table appearance
        gridTable.setRowHeight(Config.CELL_SIZE);
        for (int i = 0; i < gridTable.getColumnCount(); i++) {
            TableColumn column = gridTable.getColumnModel().getColumn(i);
            column.setMinWidth(Config.CELL_SIZE);
            column.setMaxWidth(Config.CELL_SIZE);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                if (value != null) {
                    if(((Cell) value).getItem() instanceof Player){
                        setBackground(colorList[((Player)((Cell)value).getItem()).getId()]);
//                        setValue("👤");  // Person emoji
//                        setIcon(resizeIcon(playerImage));  // Person emoji
//                        setValue(String.format("P%d",((Player)((Cell)value).getItem()).getId()));  // Person emoji
//                        setIcon(PLAYER_ICON);
                    }else if(((Cell) value).getItem() instanceof Bomb){
                        setBackground(Color.RED);
//                        setIcon(resizeIcon(bombImage));
//                        setValue("💣");  // Bomb emoji
                    }else if(((Cell) value).getItem() instanceof Mine){
                        setBackground(Color.YELLOW);
//                        setIcon(resizeIcon(mineImage));
                        setValue("🚩");  // Collision emoji
                    }else if(((Cell) value).getItem() instanceof Wall){
                        setBackground(Color.DARK_GRAY);
//                        setIcon(resizeIcon(wallImage));
                        setValue("");  // Brick emoji
                    }else if(((Cell) value).getItem() instanceof Energy){
                        setBackground(Color.cyan);
//                        setIcon(resizeIcon(pillImage));
                        setValue("E");  // Brick emoji
                    }else if(((Cell) value).getItem() instanceof Trace){
                        setBackground(Color.ORANGE);
                        setValue("*");  // Brick emoji
                    }else{
                        setBackground(Color.WHITE);
//                        setIcon(null);
                        setValue("");
                    }
                } else {
                    setBackground(Color.WHITE);
                }
                return c;
            }


        };

        for (int row = 0; row < gridTable.getRowCount(); row++) {
            for (int col = 0; col < gridTable.getColumnCount(); col++) {
                // You can access or modify specific cells using row and column indices
                Object value = gridTable.getValueAt(row, col);

                // If you want to set a specific renderer for certain cells
                gridTable.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);

            }
        }

        // Remove row and column headers
        gridTable.setTableHeader(null);
//        gridTable.setShowGrid(true);
//        gridTable.setGridColor(Color.BLACK);
    }

    /**
     * Updates the grid view to reflect the current state of the game.
     */
    public void updateGridView() {
        // Clear the table
        for (int row = 0; row < Config.GRID_HEIGHT; row++) {
            for (int col = 0; col < Config.GRID_WIDTH; col++) {
                tableModel.setValueAt(controller.getGrid().getCell(new Position(row, col)), row, col);
            }
        }
        updatePlayerInfo();
        gridTable.repaint();
        updateStats();
        updatePlayersFrames(gridTable.getModel());
    }

    /**
     * Updates the player frames with the current game data.
     *
     * @param table the TableModel containing the current game data
     */
    private void updatePlayersFrames(TableModel table) {
        for(PlayerFrame playerFrame: this.playerFrameList){
            playerFrame.updateData(table);
        }
    }

    /**
     * Updates the player information label with the current player's details.
     */
    private void updatePlayerInfo() {
        if (controller.getPlayers().size() == 1) {
            String winnerMessage = String.format("The winner is %s", controller.getPlayers().get(0).getName());

            // Create the dialog
            JDialog dialog = new JDialog();
            dialog.setTitle("Game Over");

            // Add the message to the dialog
            JLabel messageLabel = new JLabel(winnerMessage, SwingConstants.CENTER);
            dialog.add(messageLabel);

            // Set dialog properties
            dialog.setSize(300, 150); // Adjust size as needed
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setModal(true); // Block interaction with other windows until the dialog is closed
            dialog.setVisible(true);
        }

        Player currentPlayer = controller.getCurrentPlayer();
        if (currentPlayer != null) {
            playerInfoLabel.setText(String.format("Current Player: %s | Position: (%d,%d) | Energy: %d",
                    currentPlayer.getName(),
                    currentPlayer.getPosition().getX() + 1,
                    currentPlayer.getPosition().getY() + 1,
                    currentPlayer.getEnergy()));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but required by KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        PlayerAction action = null;
        Direction direction = null;
        Weapon selectedWeapon = null;
        Player currentPlayer = controller.getCurrentPlayer();

        if (e.getKeyCode() == KeyEvent.VK_T) {
            controller.nextTurn();
            updatePlayerInfo();
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            robotAction();
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            controller.getCurrentPlayer().addWeapon(new Bomb(3,controller.getCurrentPlayer().getId(),true));
            updatePlayerInfo();
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            controller.getCurrentPlayer().addWeapon(new Mine(1));
            updatePlayerInfo();
            return;
        }
        // Enable Gun mode
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isGunMode = true;
            return; // Wait for next key press for direction
        }

        // Enable Bomb or Mine mode
        if (e.getKeyCode() == KeyEvent.VK_B) {
            isBombMode = true;
            return; // Wait for numpad input
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            isMineMode = true;
            return; // Wait for numpad input
        }

        // Handle Gun mode attack
        if (isGunMode) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    direction = Direction.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    direction = Direction.DOWN;
                    break;
                case KeyEvent.VK_LEFT:
                    direction = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = Direction.RIGHT;
                    break;
                default:
                    isGunMode = false; // Exit gun mode if unrelated key is pressed
                    return;
            }
            selectedWeapon = findWeapon(currentPlayer, Gun.class);
            if(((Gun)selectedWeapon).getAmmo() == 0) isGunMode= false;
            action = PlayerAction.ATTACK;

//            isGunMode = false; // Reset gun mode after attack
        }

        // Handle Bomb/Mine placement based on numpad input
        if (isBombMode || isMineMode) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_NUMPAD7:
                    direction = Direction.UP_LEFT;
                    break;
                case KeyEvent.VK_NUMPAD8:
                    direction = Direction.UP;
                    break;
                case KeyEvent.VK_NUMPAD9:
                    direction = Direction.UP_RIGHT;
                    break;
                case KeyEvent.VK_NUMPAD4:
                    direction = Direction.LEFT;
                    break;
                case KeyEvent.VK_NUMPAD6:
                    direction = Direction.RIGHT;
                    break;
                case KeyEvent.VK_NUMPAD1:
                    direction = Direction.DOWN_LEFT;
                    break;
                case KeyEvent.VK_NUMPAD2:
                    direction = Direction.DOWN;
                    break;
                case KeyEvent.VK_NUMPAD3:
                    direction = Direction.DOWN_RIGHT;
                    break;
                default:
                    isBombMode = false;
                    isMineMode = false;
                    return;
            }

            selectedWeapon = isBombMode ?
                    findWeapon(currentPlayer, Bomb.class) :
                    findWeapon(currentPlayer, Mine.class);

            action = PlayerAction.ATTACK;
        }

        // Handle standard movement and other actions
        if (!isGunMode && !isBombMode && !isMineMode) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    action = PlayerAction.MOVE;
                    direction = Direction.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    action = PlayerAction.MOVE;
                    direction = Direction.DOWN;
                    break;
                case KeyEvent.VK_LEFT:
                    action = PlayerAction.MOVE;
                    direction = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    action = PlayerAction.MOVE;
                    direction = Direction.RIGHT;
                    break;
                case KeyEvent.VK_E:
                    action = PlayerAction.ENERGY;
                    break;
            }
        }

        // Execute the action if valid
        if (action != null) {
            boolean actionSuccessful = controller.applyPlayerAction(
                    currentPlayer,
                    action,
                    selectedWeapon,
                    direction
            );

            if (actionSuccessful) {
                updateGridView();
                if(isBombMode){
                    java.util.Timer timer = new java.util.Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            updateGridView();
                        }
                    };
                    timer.schedule(task,3200);
                }
                java.util.Timer timer = new java.util.Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        controller.clearTrace();
                    }
                };
                timer.schedule(task,1000);
                isGunMode=false;
                isMineMode=false;
                isBombMode=false;

                if (controller.checkGameOver()) {
                    controller.announceWinner();
                    dispose(); // Close the game window
                }
                controller.nextTurn();
            }
        }
    }
        /**
         * make a random action
         * **/
    private void robotAction() {
        Random random = new Random();
        Player currentPlayer = controller.getCurrentPlayer();
        double randomValue = random.nextDouble();
        if(currentPlayer.getStrategy() == Strategy.MOVE){
            if (randomValue < 0.6) {
                randomAction(PlayerAction.MOVE);
            } else {
                randomAction(PlayerAction.ATTACK);
            }
        }else{
            if (randomValue < 0.6) {
                randomAction(PlayerAction.ATTACK);
            } else {
                randomAction(PlayerAction.MOVE);
            }
        }
    }

    /**
     * spesific random choice for a random action
     * @param action to specify a random choice fit thie type of action
     * **/
    private void randomAction(PlayerAction action) {
        Random random = new Random();
        Weapon weapon = controller.getCurrentPlayer().getWeapons().get(random.nextInt(controller.getCurrentPlayer().getWeapons().size()));
        Direction direction = getRandomDirectionOption(action,weapon);
            boolean actionSuccessful = controller.applyPlayerAction(
                    controller.getCurrentPlayer(),
                    action,
                    weapon,
                    direction
            );

            if (actionSuccessful) {
                updateGridView();
                if(isBombMode){
                    java.util.Timer timer = new java.util.Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            updateGridView();
                        }
                    };
                    timer.schedule(task,3200);
                }
                java.util.Timer timer = new java.util.Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        controller.clearTrace();
                    }
                };
                timer.schedule(task,1000);
                isGunMode=false;
                isMineMode=false;
                isBombMode=false;

                if (controller.checkGameOver()) {
                    controller.announceWinner();
                    dispose(); // Close the game window
                }
                controller.nextTurn();
            }
    }

    /**
     * Retrieves a random direction option based on the given player action and weapon.
     *
     * @param action the player action
     * @param weapon the weapon being used
     * @return a randomly selected direction option
     */
    private static Direction getRandomDirectionOption(PlayerAction action, Weapon weapon) {
        Direction[] directionOptions;
        if (action == PlayerAction.MOVE || (action == PlayerAction.ATTACK && weapon instanceof Gun)) {
            directionOptions = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        } else {
            directionOptions = Direction.values();
        }
        Random random = new Random();
        return directionOptions[random.nextInt(directionOptions.length)];
    }

    /**
     * Finds the first weapon of the specified type in the player's weapon list.
     *
     * @param player the player whose weapons are being searched
     * @param weaponType the type of weapon to search for
     * @return the first weapon of the specified type, or null if not found
     */
    private Weapon findWeapon(Player player, Class<? extends Weapon> weaponType) {
        for (Weapon weapon : player.getWeapons()) {
            if (weaponType.isInstance(weapon)) {
                return weapon;
            }
        }
        return null;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used, but required by KeyListener interface
    }

    // Helper method to show direction selection dialog for attacks

    /**
     * Handles the player selection phase, allowing players to choose their types and weapons.
     */
    void selectionPhase() {
        java.util.List<Player> players = new ArrayList<>();
        System.out.println("##### Player Setup #####");
        for (int i = 1; i <= Config.NUMBER_OF_PLAYERS; i++) {
            Fighter fighter = selectPlayerTypeDialog(i);
            List<Weapon> weaponsList = selectWeaponsDialog(fighter);
            buildPlayersWeapons(i, fighter, weaponsList);
            players.add((Player) fighter);
        }
        controller.selectionPhase(players);
        controller.positionPhase();
    }

    /**
     * Displays a dialog for selecting the type of player.
     *
     * @param playerId the ID of the player being selected
     * @return the selected Fighter type
     */
    public Fighter selectPlayerTypeDialog(int playerId) {
        // Display a dialog box with the player type options
        Object[] options = {"Leger (move strategy)", "Lourd (move strategy)","Leger (attack strategy)","lourd (attack strategy)"};
        int choice = JOptionPane.showOptionDialog(null,
                "Enter your choice for Player " + playerId,
                "Player Type Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        switch (choice) {
            case 0: return PlayerFactory.createLightPlayer(playerId, String.format("Player %d", playerId),Strategy.MOVE);
            case 1: return PlayerFactory.createHeavyPlayer(playerId, String.format("Player %d", playerId),Strategy.MOVE);
            case 2: return PlayerFactory.createHeavyPlayer(playerId, String.format("Player %d", playerId),Strategy.ATTACK);
            case 3: return PlayerFactory.createHeavyPlayer(playerId, String.format("Player %d", playerId),Strategy.ATTACK);
            default: throw new IllegalStateException("Invalid player type selection");
        }
    }

    /**
     * Displays a dialog for selecting weapons for the specified fighter.
     *
     * @param fighter the fighter for whom to select weapons
     * @return a list of selected weapons
     */
    public List<Weapon> selectWeaponsDialog(Fighter fighter) {
        List<Weapon> weaponList = new ArrayList<Weapon>();
        int weaponsLimit = fighter.getWeaponsLimits();

        for (int i = 1; i <= weaponsLimit; i++) {
            // Display a dialog box with the weapon options
            Object[] options = {"Gun", "Mine", "Bomb"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Select weapon " + i + " for Player " + ((Player) fighter).getName(),
                    "Weapon Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0: weaponList.add(new Gun(Config.DEFAULT_GUN_AMMO, ((Player) fighter).getId(), true)); break;
                case 1: weaponList.add(new Mine(((Player) fighter).getId())); break;
                case 2: weaponList.add(new Bomb(Config.DEFAULT_MINE_COUNTDOWN, ((Player) fighter).getId(), false)); break;
                default: throw new IllegalStateException("Invalid weapon selection");
            }
        }

        return weaponList;
    }

    /**
     * Builds the player's weapons from the selected list and assigns them to the player.
     *
     * @param id       the ID of the player
     * @param fighter  the fighter to assign weapons to
     * @param weaponsList the list of weapons to assign
     */
    public void buildPlayersWeapons(int id, Fighter fighter, List<Weapon> weaponsList) {
        ((Player) fighter).setWeapons(weaponsList);
        System.out.println("Player " + id + " created successfully!");
    }

    /**
     * Creates a panel displaying the state of all players in the game.
     *
     * @return a JPanel containing the player state information
     */
    private JPanel createPlayerStateTable() {
        JPanel panel = new JPanel(new BorderLayout());
        List<PlayerProxy> players = controller.getPlayerProxies();
        String[] columnNames = {"Name", "Energy", "Position X", "Position Y", "Weapons"};
        Object[][] data = new Object[players.size()+1][columnNames.length];
        data[0][0] = "Name";
        data[0][1] = "Energy";
        data[0][2] = "Position X";
        data[0][3] = "Position Y";
        data[0][4] = "Weapons";
        for (int i = 1; i <= players.size(); i++) {
            PlayerProxy player = players.get(i-1);
            data[i][0] = player.getName();
            data[i][1] = player.getEnergy();
            data[i][2] = player.getPosition().getX();
            data[i][3] = player.getPosition().getY();
            data[i][4] = displayWeaponsInfo(player.getWeapons());
        }

        statsModel = new DefaultTableModel(data, columnNames);
        statsTable = new JTable(statsModel);
        statsTable.setRowHeight(30);
        statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        panel.add(statsTable, BorderLayout.CENTER);

        return panel;

    }

    /**
     * Displays the information of the specified list of weapons as a string.
     *
     * @param weapons the list of weapons to display
     * @return a string representation of the weapons
     */
    private String displayWeaponsInfo(List<Weapon> weapons) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < weapons.size(); i++) {
            Weapon weapon = weapons.get(i);
            if ( weapon instanceof Gun){
                sb.append(String.format("%d- Gun(%d)%n",i+1, ((Gun) weapon).getAmmo()));
            }else{
                sb.append(String.format("%d- %s%n",i+1, weapon instanceof Mine?"Mine":"Bomb"));
            }
        }
        return sb.toString().trim();
    }

    /**
     * Gets the panel that displays player statistics.
     *
     * @return a JPanel containing player statistics
     */
    private JPanel getPlayerStatsPanel() {
        return createPlayerStateTable();
    }

    /**
     * Updates the player statistics table with the current game data.
     */
    private void updateStats() {
        List<Player> players = controller.getPlayers();
        for (int i = 1; i <= players.size(); i++) {
            Player player = players.get(i-1);
            statsModel.setValueAt(player.getName(), i, 0);
            statsModel.setValueAt(player.getEnergy(), i, 1);
            statsModel.setValueAt(player.getPosition().getX(), i, 2);
            statsModel.setValueAt(player.getPosition().getY(), i, 3);
            statsModel.setValueAt(displayWeaponsInfo(player.getWeapons()), i, 4);
        }
        statsTable.repaint();
    }
    public static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

}