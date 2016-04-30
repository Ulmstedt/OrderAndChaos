package OrderAndChaos.GUI;

import OrderAndChaos.Game.Game;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Sehnsucht
 */
public class GameFrame extends JFrame {

    GameComponent gameComponent;
    Game game;
    //JPanel gamePanel;

    public GameFrame(Game game) {
        super("Five in a row");
        this.game = game;
        this.gameComponent = new GameComponent(game);

        this.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.game.addGameListener(gameComponent);

        this.add(gameComponent);
        
        this.setResizable(false);
        this.pack();
        this.setLocation(600, 200);
        this.setVisible(true);
    }

}
