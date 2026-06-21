package un.caen;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class ImageTableExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageTableExample().createAndShowGUI());
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("JTable Image Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Table Model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Name", "Image"}, 0);
        JTable table = new JTable(tableModel) {
            // Override method to render image
            @Override
            public Class<?> getColumnClass(int column) {
                return (column == 1) ? Icon.class : String.class;
            }
        };

        ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/bomb.png"))
                .getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH)); // Scale image

        // Add the image and name to the table
        tableModel.addRow(new Object[]{"test", imageIcon});
        // Add components to the frame
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
