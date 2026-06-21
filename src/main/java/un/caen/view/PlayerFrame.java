package un.caen.view;

import un.caen.config.Config;
import un.caen.model.items.*;
import un.caen.model.player.Player;
import un.caen.util.Cell;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
/**
 * Represents the player-specific view of the game.
 * This class displays the game grid and player statistics in a JFrame.
 */
public class PlayerFrame extends JFrame {
    private JTable table;
    private TableModel tableData;
    private JTable statsTable;
    private DefaultTableModel statsModel;
    private Player player;

    /**
     * Constructs a PlayerFrame for a specified player with the given table data.
     *
     * @param player    the Player whose view is being created
     * @param tableData the TableModel containing the game grid data
     */
    public PlayerFrame(Player player,TableModel tableData) {
        this.player = player;
        setTitle("Player " + (player.getId()) + " View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.table =new JTable(tableData);
        table.setRowHeight(Config.CELL_SIZE);
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
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
                        setBackground((player.getId() ==((Player)((Cell) value).getItem()).getId())?Color.BLUE:Color.RED);
                        setValue("👤");  // Person emoji
                    }else if(((Cell) value).getItem() instanceof Bomb){
                        setBackground(Color.RED);
                        setValue("💣");  // Bomb emoji
                    }else if(((Cell) value).getItem() instanceof Mine){
                        if((((Mine) ((Cell) value).getItem()).getOwnerPlayerId() == player.getId())){
                            setBackground(Color.YELLOW);
                            setValue("🚩");  // Collision emoji
                        }else{
                            setBackground(Color.WHITE);
                            setValue("");
                        }
                    }else if(((Cell) value).getItem() instanceof Wall){
                        setBackground(Color.DARK_GRAY);
                        setValue("");  // Brick emoji
                    }else if(((Cell) value).getItem() instanceof Energy){
                        setBackground(Color.cyan);
                        setValue("💊");  // Brick emoji
                    }else if(((Cell) value).getItem() instanceof Trace){
                        setBackground(Color.ORANGE);
                        setValue("");  // Brick emoji
                    }else{
                        setBackground(Color.WHITE);
                        setValue("");
                    }
                } else {
                    setBackground(Color.WHITE);
                }
                return c;
            }
        };

        for (int row = 0; row < table.getRowCount(); row++) {
            for (int col = 0; col < table.getColumnCount(); col++) {
                // You can access or modify specific cells using row and column indices
                Object value = table.getValueAt(row, col);

                // If you want to set a specific renderer for certain cells
                table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);

            }
        }

        // Remove row and column headers
        table.setTableHeader(null);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        // Create main panel with BorderLayout
        setLayout(new BorderLayout());
        add(this.table, BorderLayout.CENTER);

        // Create stats panel
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.EAST);

        pack();
        setLocation(400 + (player.getId() * 5), 100);
        setVisible(true);
    }

    /**
     * Creates a panel to display player statistics.
     *
     * @return a JPanel containing the player's statistics
     */
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"Stat", "Value"};
        Object[][] data = {
                {"Name", player.getName()},
                {"Energy", player.getEnergy()},
                {"Position X", player.getPosition().getX()},
                {"Position Y", player.getPosition().getY()},
                {"Weapons", player.getWeaponsCount()}
        };

        statsModel = new DefaultTableModel(data, columnNames);

        statsTable = new JTable(statsModel);
        statsTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(statsTable);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Updates the data displayed in the player frame.
     *
     * @param table the new TableModel containing the updated game grid data
     */
    public void updateData(TableModel table) {
        tableData = table;
        updateStats();
        this.table.repaint();
    }

    /**
     * Updates the statistics displayed in the stats panel.
     */
    private void updateStats() {
        statsModel.setValueAt(player.getEnergy(), 1, 1);
        statsModel.setValueAt(player.getPosition().getX(), 2, 1);
        statsModel.setValueAt(player.getPosition().getY(), 3, 1);
        statsModel.setValueAt(player.getWeaponsCount(), 4, 1);
        statsTable.repaint();
    }
}
