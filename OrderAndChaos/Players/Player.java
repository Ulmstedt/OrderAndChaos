package OrderAndChaos.Players;

import OrderAndChaos.Game.Game;

/**
 *
 * @author Sehnsucht
 */
public class Player implements IPlayer{

    protected int ID, score, currX, currY;
    protected Game game;
    protected int[][] pointGrid; // Higher score => better move (used for AI)

    public Player(int id, Game game) {
        this.ID = id;
        this.score = 0;
        this.game = game;
        pointGrid = new int[game.getWidth()][game.getHeight()];
    }

    public void setCurrentChoice(int x, int y) {
        currX = x;
        currY = y;
    }

    public int getID() {
        return ID;
    }

    public int getScore() {
        return score;
    }

    // AI
    public int[][] getPointGrid() {
        return pointGrid;
    }

    // AI
    public int findHighestScore() {
        int highest = 1;
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                if (pointGrid[x][y] > highest) {
                    highest = pointGrid[x][y];
                }
            }
        }
        return highest;
    }

    public void resetScore() {
        score = 0;
    }

    public void givePoint() {
        score++;
    }

    public void playRound() {
        if (game.getTile(currX, currY) == 0) {
            game.setTile(currX, currY, ID);
            game.incrementRoundCount();
            game.nextPlayer();
        }
    }
}
