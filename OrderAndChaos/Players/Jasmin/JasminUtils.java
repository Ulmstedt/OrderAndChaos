package OrderAndChaos.Players.Jasmin;

import java.awt.Point;

/**
 * Utility functions for AIJasmin
 *
 * @author Sehnsucht
 */
public class JasminUtils {

    public static int[][] newPointGrid(int width, int height) {
        int[][] newPointGrid = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newPointGrid[i][j] = (i <= width - i - 1 ? i : width - i - 1) + (j <= height - j - 1 ? j : height - j - 1);
            }
        }
        return newPointGrid;
    }

    public static Point findBestMove(int[][] grid) {
        int bestX = 0, bestY = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] > grid[bestX][bestY]) {
                    bestX = i;
                    bestY = j;
                }
            }
        }
        return new Point(bestX, bestY);
    }

    /*
     Returns 0 if five in a row cant be achieved, 1 if it can and 1 direction is open, and 2 if it can and both directions are open.
     */
    public static int checkWinPossibility(int[][] board, Point p1, Point p2, int playerID) {
        int TwoOpenEndsRetVal = 3;
        int xDistance = p1.x - p2.x;
        int yDistance = p1.y - p2.y;
        int length = (Math.abs(xDistance) > Math.abs(yDistance) ? Math.abs(xDistance) : Math.abs(yDistance)) + 1; // length of combo between p1 and p2
        //Check horizontally
        if (p1.y == p2.y) {
            int openLeft = 0, openRight = 0;
            //Check to the left
            for (int dx = 1; p1.x - dx >= 0; dx++) {
                if (board[p1.x - dx][p1.y] == 0) { // || board[p1.x - dx][p1.y] == playerID
                    openLeft++;
                } else {
                    break;
                }
            }
            //Check to the right
            for (int dx = 1; p2.x + dx < board.length; dx++) {
                if (board[p2.x + dx][p2.y] == 0) { // || board[p2.x + dx][p2.y] == playerID
                    openRight++;
                } else {
                    break;
                }
            }
            //System.out.println("Max: " + (openLeft + length + openRight) + ", openLeft: " + openLeft + ", openRight: " + openRight);
            if (openLeft + length + openRight < 5) {
                //System.out.println("Five in a row is not possible.");
                return 0;
            } else if (openLeft >= 1 && openRight >= 1) {
                //System.out.println("Open in both directions.");
                return TwoOpenEndsRetVal;
            } else if (openLeft >= 1 || openRight >= 1) {
                //System.out.println("Open in one direction.");
                return 1;
            }
        } //Check vertically
        else if (p1.x == p2.x) {
            int openUp = 0, openDown = 0;
            //Check above
            for (int dy = 1; p1.y - dy >= 0; dy++) {
                if (board[p1.x][p1.y - dy] == 0) { // || board[p1.x][p1.y - dy] == playerID
                    openUp++;
                } else {
                    break;
                }
            }
            //Check below
            for (int dy = 1; p2.y + dy < board[0].length; dy++) {
                if (board[p2.x][p2.y + dy] == 0) { // || board[p2.x][p2.y + dy] == playerID
                    openDown++;
                } else {
                    break;
                }
            }
            //System.out.println("Max: " + (openAbove + length + openBelow) + ", openAbove: " + openAbove + ", openBelow: " + openBelow);
            if (openUp + length + openDown < 5) {
                //System.out.println("Five in a row is not possible.");
                return 0;
            } else if (openUp >= 1 && openDown >= 1) {
                //System.out.println("Open in both directions.");
                return TwoOpenEndsRetVal;
            } else if (openUp >= 1 || openDown >= 1) {
                //System.out.println("Open in one direction.");
                return 1;
            }
        }//Check diagonal 1 ( \ )
        else if (p1.x < p2.x && p1.y < p2.y) {
            int openUpLeft = 0, openDownRight = 0;
            //Check up left
            for (int dxy = 1; (p1.x - dxy >= 0 && p1.y - dxy >= 0); dxy++) {
                if (board[p1.x - dxy][p1.y - dxy] == 0 ) { //|| board[p1.x - dxy][p1.y - dxy] == playerID
                    openUpLeft++;
                } else {
                    break;
                }
            }
            //Check down right
            for (int dxy = 1; (p2.x + dxy < board.length && p2.y + dxy < board[0].length); dxy++) {
                if (board[p2.x + dxy][p2.y + dxy] == 0) { // || board[p2.x + dxy][p2.y + dxy] == playerID
                    openDownRight++;
                } else {
                    break;
                }
            }
            //System.out.println("Max: " + (openUpLeft + length + openDownRight) + ", openUpLeft: " + openUpLeft + ", openDownRight: " + openDownRight);
            if (openUpLeft + length + openDownRight < 5) {
                //System.out.println("Five in a row is not possible.");
                return 0;
            } else if (openUpLeft >= 1 && openDownRight >= 1) {
                //System.out.println("Open in both directions.");
                return TwoOpenEndsRetVal;
            } else if (openUpLeft >= 1 || openDownRight >= 1) {
                //System.out.println("Open in one direction.");
                return 1;
            }
        }//Check diagonal 2 ( / )
        else if (p1.x > p2.x && p1.y < p2.y) {
            int openUpRight = 0, openDownLeft = 0;
            //Check up right
            for (int dxy = 1; (p1.x + dxy < board.length && p1.y - dxy >= 0); dxy++) {
                if (board[p1.x + dxy][p1.y - dxy] == 0) { // || board[p1.x + dxy][p1.y - dxy] == playerID
                    openUpRight++;
                } else {
                    break;
                }
            }
            //Check down left
            for (int dxy = 1; (p2.x - dxy >= 0 && p2.y + dxy < board[0].length); dxy++) {
                if (board[p2.x - dxy][p2.y + dxy] == 0) { // || board[p2.x - dxy][p2.y + dxy] == playerID
                    openDownLeft++;
                } else {
                    break;
                }
            }
            //System.out.println("Max: " + (openUpRight + length + openDownLeft) + ", openUpRight: " + openUpRight + ", openDownLeft: " + openDownLeft);
            if (openUpRight + length + openDownLeft < 5) {
                //System.out.println("Five in a row is not possible.");
                return 0;
            } else if (openUpRight >= 1 && openDownLeft >= 1) {
                //System.out.println("Open in both directions.");
                return TwoOpenEndsRetVal;
            } else if (openUpRight >= 1 || openDownLeft >= 1) {
                //System.out.println("Open in one direction.");
                return 1;
            }
        }
        return 0;
    }

    // ##################
    // ## Setup checks ##
    // ##################
    /**
     * Checks for setups horizontally.
     *
     * @param tiles Board to check setup on
     * @param xc X to check
     * @param yc Y to check
     * @param length Length of chain to look for
     * @param ID Player to check for
     * @return Returns the end points if setup is found for player [ID]
     */
    public static Point[] checkForSetupRow(int[][] tiles, int xc, int yc, int length, int ID) {
        int lowestX, highestX, lowest, highest;
        //Find lower loop bound
        lowestX = (xc - (length - 1) > 0 ? xc - (length - 1) : 0);
        lowest = lowestX - xc;
        //Find upper loop bound
        highestX = (xc < (tiles.length - length) ? xc : (tiles.length - length));
        highest = highestX - xc;

        Outer:
        for (int dx = lowest; dx <= highest; dx++) {
            for (int i = 0; i < length; i++) {
                if (tiles[xc + dx + i][yc] != ID) {
                    continue Outer;
                }
            }
            //If this is reached, a setup has been found and the end points are returned
            return new Point[]{new Point(xc + dx, yc), new Point(xc + dx + (length - 1), yc)};
        }
        //If this is reached, no setup has been found and null is returned
        return null;
    }

    /**
     * Checks for setups vertically.
     *
     * @param tiles Board to check setup on
     * @param xc X to check
     * @param yc Y to check
     * @param length Length of chain to look for
     * @param ID Player to check for
     * @return Returns the end points if setup is found for player [ID]
     */
    public static Point[] checkForSetupCol(int[][] tiles, int xc, int yc, int length, int ID) {
        int lowestY, highestY, lowest, highest;
        //Find lower loop bound
        lowestY = (yc - (length - 1) > 0 ? yc - (length - 1) : 0);
        lowest = lowestY - yc;
        //Find upper loop bound
        highestY = (yc < (tiles[0].length - length) ? yc : (tiles[0].length - length));
        highest = highestY - yc;

        Outer:
        for (int dy = lowest; dy <= highest; dy++) {
            for (int i = 0; i < length; i++) {
                if (tiles[xc][yc + dy + i] != ID) {
                    continue Outer;
                }
            }
            //If this is reached, a setup has been found and the end points are returned
            return new Point[]{new Point(xc, yc + dy), new Point(xc, yc + dy + (length - 1))};
        }
        //If this is reached, no setup has been found and null is returned
        return null;
    }

    /**
     * Checks for setups diagonally ( \ ).
     *
     * @param tiles Board to check setup on
     * @param xc X to check
     * @param yc Y to check
     * @param length Length of chain to look for
     * @param ID Player to check for
     * @return Returns the end points if setup is found for player [ID]
     */
    public static Point[] checkForSetupDiagonal1(int[][] tiles, int xc, int yc, int length, int ID) {
        int lowestX, highestX, lowestY, highestY, lowest, highest;
        //Find lower loop bound
        lowestX = (xc - (length - 1) > 0 ? xc - (length - 1) : 0);
        lowestY = (yc - (length - 1) > 0 ? yc - (length - 1) : 0);
        int lowestXOffset = lowestX - xc; //xc - lowestX
        int lowestYOffset = lowestY - yc; // yc - lowestY
        lowest = (lowestXOffset > lowestYOffset ? lowestXOffset : lowestYOffset); // Lowest bound = highest of the mins // >
        //Find upper loop bound
        highestX = (xc < (tiles.length - length) ? xc : (tiles.length - length));
        highestY = (yc < (tiles[0].length - length) ? yc : (tiles[0].length - length));
        int highestXDistance = highestX - xc;
        int highestYDistance = highestY - yc;
        highest = (highestXDistance < highestYDistance ? highestXDistance : highestYDistance); // Highest bound = lowest of the maxs

        //If lowest > highest, a setup is not possible in this position (can happen near corners)
        if (lowest > highest) {
            return null;
        }

        Outer:
        for (int dxy = lowest; dxy <= highest; dxy++) {
            for (int i = 0; i < length; i++) {
                if (tiles[xc + dxy + i][yc + dxy + i] != ID) {
                    continue Outer;
                }
            }
            //If this is reached, a setup has been found and the end points are returned
            return new Point[]{new Point(xc + dxy, yc + dxy), new Point(xc + dxy + (length - 1), yc + dxy + (length - 1))};
        }
        //If this is reached, no setup has been found and null is returned
        return null;
    }

    /**
     * Checks for setups diagonally ( / ).
     *
     * @param tiles Board to check setup on
     * @param xc X to check
     * @param yc Y to check
     * @param length Length of chain to look for
     * @param ID Player to check for
     * @return Returns the end points if setup is found for player [ID]
     */
    public static Point[] checkForSetupDiagonal2(int[][] tiles, int xc, int yc, int length, int ID) {
        int lowestX, highestX, lowestY, highestY, lowest, highest;
        //Find lower loop bound
        lowestX = (xc + (length - 1) < (tiles.length - 1) ? xc + (length - 1) : (tiles.length - 1));
        lowestY = (yc - (length - 1) > 0 ? yc - (length - 1) : 0);
        int lowestXOffset = xc - lowestX;
        int lowestYOffset = lowestY - yc;
        lowest = (lowestXOffset > lowestYOffset ? lowestXOffset : lowestYOffset); // Lowest bound = highest of the mins
        //Find upper loop bound
        highestX = (xc > (length - 1) ? xc : (length - 1));
        highestY = (yc < (tiles[0].length - length) ? yc : (tiles[0].length - length));
        int highestXDistance = xc - highestX;
        int highestYDistance = highestY - yc;
        highest = (highestXDistance < highestYDistance ? highestXDistance : highestYDistance); // Highest bound = lowest of the maxs

        //If lowest > highest, a setup is not possible in this position (can happen near corners)
        if (lowest > highest) {
            return null;
        }

        Outer:
        for (int dxy = lowest; dxy <= highest; dxy++) {
            for (int i = 0; i < length; i++) {
                if (tiles[xc - dxy - i][yc + dxy + i] != ID) {
                    continue Outer;
                }
            }
            //If this is reached, a setup has been found and the end points are returned
            return new Point[]{new Point(xc - dxy, yc + dxy), new Point(xc - dxy - (length - 1), yc + dxy + (length - 1))};
        }
        //If this is reached, no setup has been found and null is returned
        return null;
    }

    public static int[][] invertMatrix(int[][] matrix) {
        int[][] newMatrix = new int[matrix[0].length][matrix.length];
        for (int x = 0; x < newMatrix[0].length; x++) {
            for (int y = 0; y < newMatrix.length; y++) {
                newMatrix[x][y] = matrix[y][x];
            }
        }
        return newMatrix;
    }
    
    public static int[][] cloneMatrix(int[][] matrix) {
        int[][] clonedMatrix = new int[matrix.length][];

        for (int i = 0; i < matrix.length; i++) {
            clonedMatrix[i] = matrix[i].clone();
        }

        return clonedMatrix;
    }
}
