package OrderAndChaos.Game;

import OrderAndChaos.Game.Constants.ColorList;
import OrderAndChaos.Players.IAI;
import OrderAndChaos.Players.IObserver;
import OrderAndChaos.Players.Jasmin.AIJasmin;
import OrderAndChaos.Players.Player;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Sehnsucht
 */
public class Game {

    private final ArrayList<GameListener> gameListeners;
    private final ArrayList<Player> playerList;
    private final ArrayList<IObserver> observerList;
    private Player currentPlayer;
    private final WinnerHistory winnerHistory;

    private final int width, height;
    private int winner, roundCount;
    private boolean pointsGiven;
    private int playerStarted = 0;

    //Used for random colors on the board
    private Color[][] colors;

    private int[][] board;
    private ArrayList<Point> moveHistoryList;
    private Point mostRecentMove;
    private int chaosPlayerId; //ID of player playing as Chaos

    public final int DEBUG_LEVEL = 0; // 0 = off, 1 = show heatmap, 2 = show heatmap + scores
    public final int HEATMAP_PID = 2; // Player ID to show heatmap for
    public final boolean SHOW_PLAY_ORDER = true; // true to show which order the plays were in
    public final int USE_RANDOM_COLORS = 0; // 0 = normal colors, 1 = random with the 2 standard colors, 2 = completely random colors

    public Game(int width, int height) {
        this.gameListeners = new ArrayList<>();
        this.playerList = new ArrayList<>();
        this.observerList = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.winner = 0;
        this.winnerHistory = new WinnerHistory(50);
        initGame();
    }

    //Update game by 1 tick
    public void tick() {
        if (winner == 0) {
            if (isBoardFull()) {
                //Chaos player won
                winner = chaosPlayerId;
                System.out.println("Chaos won");
                // Notify all players that the round has ended before resetting the game.
                for (IObserver o : observerList) {
                    o.roundEnded(winner);
                }

                resetGame();
            }
            winner = checkForWinner(board);
            if (currentPlayer instanceof IAI && winner == 0) {
                currentPlayer.playRound();
            }
            notifyListeners();
        } else {
            if (!pointsGiven) {
                playerList.get(winner - 1).givePoint();
                winnerHistory.saveWinner(winner);
                pointsGiven = true;

                // Notify all observers that the round has ended before resetting the game.
                for (IObserver o : observerList) {
                    o.roundEnded(winner);
                }
                if(winner == 1 || winner == 2){
                    //resetGame(); //automatically start new game after someone wins (for fast ai vs ai games)
                }
            }
            winner = 0;

        }
    }

    private void initGame() {
        this.board = new int[width][height];
        //this.board = JasminUtils.invertMatrix(AIJasmin.problemBoard);
        //this.board = AIPlayer.invertMatrix(AIPlayer.winPosTest1);

        playerList.add(new Player(1, this));
        playerList.add(new Player(2, this));
        //playerList.add(new AIJasmin(1, this));
        //playerList.add(new AILoki(1, this, true));
        //playerList.add(new AIJasmin(2, this));
        /*AILoki loki = new AILoki(2, this, false);
        playerList.add(loki);
        observerList.add(loki);*/
        //playerList.add(new AIJohan(2, this, false));
        //playerList.add(new AIPlayer(3, this));
        //playerList.add(new AIJimmyOld(2, this));

        this.chaosPlayerId = (int) Math.round(Math.random())+1; //Randomly decide who starts as Chaos
        this.playerStarted = 0;
        this.currentPlayer = playerList.get(playerStarted);
        //currentPlayer = playerList.get(1); //Player 2 always starts (can anyone beat AIPlayer when he starts?)
        if (USE_RANDOM_COLORS >= 1) {
            this.colors = generateRandomColors(); //Random colors
        }
        this.moveHistoryList = new ArrayList<>();
        this.mostRecentMove = new Point(-1, -1);
    }

    public void resetGame() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = 0;
            }
        }
        winner = 0;
        pointsGiven = false;
        roundCount = 0;
        if (USE_RANDOM_COLORS >= 1) {
            colors = generateRandomColors(); //Random colors
        }
        moveHistoryList = new ArrayList<>();
        mostRecentMove = new Point(-1, -1);

        //Switch who plays as Chaos
        chaosPlayerId = (chaosPlayerId == 1 ? 2 : 1);
        
        // Switch players.
        playerStarted++;
        if (playerStarted == playerList.size()) {
            playerStarted = 0;
        }
        currentPlayer = playerList.get(playerStarted);
        //currentPlayer = playerList.get(1); //Player 2 always starts (can anyone beat AIPlayer when he starts?)
    }

    private Color[][] generateRandomColors() {
        Color[][] color = new Color[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (USE_RANDOM_COLORS == 1) {
                    color[i][j] = (Math.random() >= 0.5 ? ColorList.colors.get(0) : ColorList.colors.get(1)); //Random with just the standard colors

                } else {
                    color[i][j] = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)); //Random colors
                }
            }
        }
        return color;
    }

    public void nextPlayer() {
        int currID = currentPlayer.getID();
        if (currID != playerList.size()) {
            currentPlayer = playerList.get(currID);
        } else {
            currentPlayer = playerList.get(0);
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < width ; i++) {
            for (int j = 0; j < height ; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addGameListener(GameListener gl) {
        gameListeners.add(gl);
    }

    /*
     Checks if a player has won.
     Returns 0 if no winner, or the player ID if someone has won.
     */
    public int checkForWinner(int[][] tiles) {
        for (int p = 1; p <= getNumberOfPlayers(); p++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    //Check rows
                    if (x <= width - 5 && (tiles[x][y] == p && tiles[x + 1][y] == p && tiles[x + 2][y] == p && tiles[x + 3][y] == p && tiles[x + 4][y] == p)) {
                        System.out.println(chaosPlayerId);
                        return this.chaosPlayerId;
                    }
                    //Check columns
                    if (y <= height - 5 && (tiles[x][y] == p && tiles[x][y + 1] == p && tiles[x][y + 2] == p && tiles[x][y + 3] == p && tiles[x][y + 4] == p)) {
                        System.out.println(chaosPlayerId);
                        return this.chaosPlayerId;
                    }
                    //Check diagonals
                    if (x >= 2 && x <= width - 3 && y >= 2 && y <= height - 3
                            && ((tiles[x - 2][y - 2] == p && tiles[x - 1][y - 1] == p && tiles[x][y] == p && tiles[x + 1][y + 1] == p && tiles[x + 2][y + 2] == p)
                            || (tiles[x + 2][y - 2] == p && tiles[x + 1][y - 1] == p && tiles[x][y] == p && tiles[x - 1][y + 1] == p && tiles[x - 2][y + 2] == p))) {
                        System.out.println(chaosPlayerId);
                        return this.chaosPlayerId;
                    }
                }
            }
        }
        return 0;
    }

    //Notify all listeners that the game has changed
    private void notifyListeners() {
        for (GameListener gl : gameListeners) {
            gl.gameChanged();
        }
    }

    // #############
    // ## Getters ##
    // #############
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumberOfPlayers() {
        return playerList.size();
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWinner() {
        return winner;
    }

    public int getTile(int x, int y) {
        return board[x][y];
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean getPointsGiven() {
        return pointsGiven;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public Color[][] getColors() {
        return colors;
    }

    public WinnerHistory getWinnerHistory() {
        return winnerHistory;
    }

    public Point getMostRecentMove() {
        return mostRecentMove;
    }

    public ArrayList<Point> getMoveHistory() {
        return moveHistoryList;
    }
    
    public int getChaosPlayerId(){
        return chaosPlayerId;
    }

    // #############
    // ## Setters ##
    // #############
    //Should remake this system and make it safer
    public void setTile(int x, int y, boolean cross) {
        board[x][y] = (cross ? 1 : 2);
        mostRecentMove = new Point(x, y);
        moveHistoryList.add((Point) mostRecentMove.clone());
        // Notify all players that a move has been made.
        for (IObserver o : observerList) {
            o.moveMade(new Point(x, y));
        }
    }

    public void incrementRoundCount() {
        roundCount++;
    }

    public void addObserver(IObserver observer) {
        observerList.add(observer);
    }
}
