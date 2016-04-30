package OrderAndChaos.Players;


/**
 * Created by pzyber on 2015-12-30.
 */
public interface IPlayer {
    int getID();
    int getScore();
    void givePoint();
    void playRound();
    void resetScore();
    void setCurrentChoice(int x, int y);
}
