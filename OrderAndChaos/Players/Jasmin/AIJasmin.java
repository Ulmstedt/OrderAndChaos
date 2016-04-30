package OrderAndChaos.Players.Jasmin;

import OrderAndChaos.Game.Game;
import OrderAndChaos.Players.IAI;
import OrderAndChaos.Players.Player;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Sehnsucht
 */
public class AIJasmin extends Player implements IAI {

    public static final int[][] boardTemplate = new int[][]{
        //0 1  2  3  4  5  6  7  8  9  0  1  2  3  4
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 0
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 1
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 2
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 3
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 4
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 5
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 6
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 7
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 8
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 9
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 0
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 1
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 2
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 3
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} // 4
    };
    public static final int[][] problemBoard = new int[][]{
        //0 1  2  3  4  5  6  7  8  9  0  1  2  3  4
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 0
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 1
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 2
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 3
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 4
        {0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0},// 5
        {0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0},// 6
        {0, 0, 0, 0, 0, 0, 2, 1, 1, 0, 0, 0, 0, 0, 0},// 7
        {0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 0, 0, 0, 0, 0},// 8
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},// 9
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 0
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 1
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 2
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 3
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} // 4
    };
    int checkX = 4, checkY = 13, checkLength = 2;

    private int width, height;

    //Defensive
    private final int WIN_SETUP_SCORE = 500000;
    private final int WIN_BLOCK_SCORE = 50000;
    private final int FOUR_SETUP_SCORE = 2500;
    private final int FOUR_BLOCK_SCORE = 1400;
    private final int THREE_SETUP_SCORE = 500;
    private final int THREE_BLOCK_SCORE = 400;
    private final int TWO_SETUP_SCORE = 20;
    private final int TWO_BLOCK_SCORE = 10;
    /* Old
    private final int WIN_SETUP_SCORE = 50000;
    private final int WIN_BLOCK_SCORE = 12000;
    private final int FOUR_SETUP_SCORE = 3000;
    private final int FOUR_BLOCK_SCORE = 1500;
    private final int THREE_SETUP_SCORE = 400;
    private final int THREE_BLOCK_SCORE = 250;
    private final int TWO_SETUP_SCORE = 20;
    private final int TWO_BLOCK_SCORE = 10;
    */
    //Aggressive (bad numbers)
    /*private final int WIN_SETUP_SCORE = 5000;
     private final int WIN_BLOCK_SCORE = 1200;
     private final int FOUR_SETUP_SCORE = 400;
     private final int FOUR_BLOCK_SCORE = 250;
     private final int THREE_SETUP_SCORE = 45;
     private final int THREE_BLOCK_SCORE = 25;
     private final int TWO_SETUP_SCORE = 2;
     private final int TWO_BLOCK_SCORE = 1;*/

    private final int[] SETUP_SCORE = new int[]{0, 0, TWO_SETUP_SCORE, THREE_SETUP_SCORE, FOUR_SETUP_SCORE};
    private final int[] BLOCK_SCORE = new int[]{0, 0, TWO_BLOCK_SCORE, THREE_BLOCK_SCORE, FOUR_BLOCK_SCORE};

    private final double SIMULATION_INTENSITY = 0.5; //Lower value simulates more cases
    private final int SIMULATION_DEPTH = 9; //How many rounds in the future that is simulated

    public AIJasmin(int id, Game game) {
        super(id, game);
        width = game.getWidth();
        height = game.getHeight();
    }

    @Override
    public void playRound() {
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        //System.out.println("Setup " + (checkForSetupDiagonal1(AIPlayer.invertMatrix(AIPlayer.setupDiaAt55), checkX, checkY, checkLength, 1) ? "found" : "not found"));
        //System.out.println("Setup " + (checkForSetupDiagonal2(AIPlayer.invertMatrix(AIPlayer.setupDia2), checkX, checkY, checkLength, 1) ? "found" : "not found"));
        //System.out.println("Setup " + (checkForSetupRow(AIPlayer.invertMatrix(AIPlayer.setupRow), checkX, checkY, checkLength, 1) ? "found" : "not found"));
        //System.out.println("Setup " + (checkForSetupCol(AIPlayer.invertMatrix(AIPlayer.setupCol), checkX, checkY, checkLength, 1) ? "found" : "not found"));
        //Point p1 = new Point(2, 0);
        //Point p2 = new Point(0, 2);
        //System.out.println("" + checkWinPossibility(AIPlayer.invertMatrix(AIPlayer.winPosTest1), p1, p2, 1));
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        //System.out.println();

        System.out.println("###### START OF ROUND #######################");
        //Start time of AI's turn
        long startTime = System.currentTimeMillis();

        // -----------------------
//        System.out.println("---------");
//        System.out.println("Winner of test simulation: " + simulateGame(spinMatrix(AIPlayer.knownProblem2), ID, SIMULATION_DEPTH));
//        System.out.println("---------");
        // -----------------------
        int[][] gameBoard = JasminUtils.cloneMatrix(game.getBoard());
        //int[][] gameBoard = spinMatrix(knownProblem1);

        pointGrid = calculatePointGrid(gameBoard, this.ID);
        Point p = JasminUtils.findBestMove(pointGrid);

        int pointsToSimulate = pointsToSimulate(pointGrid);
        //System.out.println("Heatmap says: (" + p.x + ", " + p.y + ")");

        System.out.println("A maximum of " + pointsToSimulate + " points will be simulated.");
        for (int sim = 1; sim <= pointsToSimulate; sim++) {
            int[][] tempBoard = JasminUtils.cloneMatrix(gameBoard);
            tempBoard[p.x][p.y] = this.ID;

            System.out.println("------ SIMULATION " + sim + ": (" + p.x + ", " + p.y + ") -----------------");
            int simulatedWinner = simulateGame(tempBoard, this.ID, SIMULATION_DEPTH);
            System.out.println("------ END OF SIMULATION --------------------");
            if (simulatedWinner == 0 || simulatedWinner == this.ID) {
                break;
            }
            System.out.println("Throwing away (" + p.x + ", " + p.y + ")");
            //Set score of current point to 0 since it would make us lose
            pointGrid[p.x][p.y] = 0;
            p = JasminUtils.findBestMove(pointGrid);
        }

        System.out.print("AI played (" + p.x + ", " + p.y + "), taking ");
        if (game.getTile(p.x, p.y) == 0) {
            game.setTile(p.x, p.y, ID);
            game.incrementRoundCount();
            game.nextPlayer();
        }

        //End time of AI's turn
        long endTime = System.currentTimeMillis();
        long timeSpent = endTime - startTime;
        System.out.println(timeSpent + " ms.");

        //For debugging
        //pointGrid = calculatePointGrid(game.getBoard(), 1);
        System.out.println("###### END OF ROUND #########################");
    }

    private int simulateGame(int[][] gameBoard, int lastID, int roundsLeft) {
        int winner = game.checkForWinner(gameBoard);
        if (winner != 0) {
            System.out.println("Simulated winner: P" + winner);
            return winner;
        } else if (roundsLeft > 0) {
            //Calculate who starts next turn
            int currentID;
            ArrayList<Player> pl = game.getPlayerList();
            if (lastID != pl.size()) {
                currentID = pl.get(lastID).getID();
            } else {
                currentID = pl.get(0).getID();
            }
            int[][] boardCopy = JasminUtils.cloneMatrix(gameBoard);
            int[][] pGrid = calculatePointGrid(boardCopy, currentID);
            Point p = JasminUtils.findBestMove(pGrid);
            
            System.out.println("P" + currentID + " played (" + p.x + ", " + p.y + ") - " + (roundsLeft - 1) + " rounds left to simulate.");
            boardCopy[p.x][p.y] = currentID;
            return simulateGame(boardCopy, currentID, roundsLeft - 1);
        } else {
            return 0;
        }
    }

    private int pointsToSimulate(int[][] pointGrid) {
        Point p = JasminUtils.findBestMove(pointGrid);
        int pointsToSimulate = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //Check if score is high enough, and if so simulate for playing (x,y), and only simulate if its atleast a 4 setup
                if (pointGrid[x][y] >= TWO_BLOCK_SCORE && pointGrid[x][y] >= (SIMULATION_INTENSITY * pointGrid[p.x][p.y])) {
                    pointsToSimulate++;
                }
            }
        }
        return pointsToSimulate;
    }

    private int[][] calculatePointGrid(int[][] board, int playerID) {
        int[][] tempPointGrid = JasminUtils.newPointGrid(width, height);
        int[][] tempBoard = JasminUtils.cloneMatrix(board); //Copy board
        Point[] endPoints;
        int pointFactor;
        for (int p = 1; p <= game.getNumberOfPlayers(); p++) {
            for (int x = 0; x < game.getWidth(); x++) {
                for (int y = 0; y < game.getHeight(); y++) {
                    if (board[x][y] == 0) {
                        tempBoard[x][y] = p;
                        //Check if placement would give victory
                        if (game.checkForWinner(tempBoard) == p) {
                            tempPointGrid[x][y] += (p == playerID ? WIN_SETUP_SCORE : WIN_BLOCK_SCORE);
                        }
                        //Check for setups of length 2-4 and give points accordingly
                        for (int length = 2; length <= 4; length++) {
                            //Row
                            endPoints = JasminUtils.checkForSetupRow(tempBoard, x, y, length, p);
                            if (endPoints != null) {
                                pointFactor = JasminUtils.checkWinPossibility(tempBoard, endPoints[0], endPoints[1], p);
                                if (x == -1 && y == 7) {
                                    System.out.println("---");
                                    System.out.println("P1: (" + endPoints[0].x + ", " + endPoints[0].y + ")");
                                    System.out.println("P2: (" + endPoints[1].x + ", " + endPoints[1].y + ")");
                                    System.out.println("Point factor: " + pointFactor);
                                    System.out.println("Point base: " + (p == playerID ? SETUP_SCORE[length] : BLOCK_SCORE[length]));
                                    System.out.println("Length: " + length);
                                    System.out.println("Player: " + p);
                                    System.out.println("---");
                                }
                                tempPointGrid[x][y] += pointFactor * (p == playerID ? SETUP_SCORE[length] : BLOCK_SCORE[length]);
                            }
                            //Col
                            endPoints = JasminUtils.checkForSetupCol(tempBoard, x, y, length, p);
                            if (endPoints != null) {
                                pointFactor = JasminUtils.checkWinPossibility(tempBoard, endPoints[0], endPoints[1], p);
                                tempPointGrid[x][y] += pointFactor * (p == playerID ? SETUP_SCORE[length] : BLOCK_SCORE[length]);
                            }
                            //Dia 1
                            endPoints = JasminUtils.checkForSetupDiagonal1(tempBoard, x, y, length, p);
                            if (endPoints != null) {
                                pointFactor = JasminUtils.checkWinPossibility(tempBoard, endPoints[0], endPoints[1], p);
                                tempPointGrid[x][y] += pointFactor * (p == playerID ? SETUP_SCORE[length] : BLOCK_SCORE[length]);
                            }
                            //Dia 2
                            endPoints = JasminUtils.checkForSetupDiagonal2(tempBoard, x, y, length, p);
                            if (endPoints != null) {
                                pointFactor = JasminUtils.checkWinPossibility(tempBoard, endPoints[0], endPoints[1], p);
                                tempPointGrid[x][y] += pointFactor * (p == playerID ? SETUP_SCORE[length] : BLOCK_SCORE[length]);
                            }
                        }
                        tempBoard[x][y] = 0;

                    } else {
                        tempPointGrid[x][y] = 0;
                    }
                }
            }
        }
        return tempPointGrid;
    }

}
