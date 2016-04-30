package OrderAndChaos.GUI;

import OrderAndChaos.Game.Game;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

/**
 *
 * @author Sehnsucht
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final Game game = new Game(6, 6);
        new GameFrame(game);

        final Action tick = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.tick();
            }
        };

        final Timer clockTimer = new Timer(10, tick);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

}
