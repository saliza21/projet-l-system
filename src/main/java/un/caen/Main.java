package un.caen;

import un.caen.config.Config;
import un.caen.controller.GameController;
import un.caen.model.Game;
import un.caen.view.GameBoardView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController(new Game(Config.GRID_WIDTH, Config.GRID_HEIGHT));
            new GameBoardView(controller);
        });

    }
}