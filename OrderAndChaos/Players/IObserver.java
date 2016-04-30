package OrderAndChaos.Players;

import java.awt.Point;

/**
 *
 * @author Sehnsucht
 */
public interface IObserver {
    void moveMade(Point move);
    void roundEnded(int winner);
}
