package OrderAndChaos.GUI;

import OrderAndChaos.Game.GameListener;
import OrderAndChaos.Game.Constants.Constants;
import OrderAndChaos.Game.Game;
import OrderAndChaos.Game.WinnerHistory;
import OrderAndChaos.Players.IAI;
import OrderAndChaos.Players.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * The GameComponent class is a graphical representation of a Game object.
 *
 * @author Sehnsucht
 */
public class GameComponent extends JComponent implements GameListener, MouseListener {

    private final Game game;
    final private int width, height;

    /**
     *
     * @param game game that the component should display
     */
    public GameComponent(Game game) {
        this.game = game;
        this.width = Constants.SQUARE_SIZE * game.getWidth() + Constants.LINE_THICKNESS;
        this.height = Constants.SQUARE_SIZE * game.getHeight() + Constants.PADDING_TOP + Constants.LINE_THICKNESS + Constants.PADDING_BOTTOM;
        addMouseListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int currentPlayer = game.getCurrentPlayer().getID();

        //Draw whos turn it is
        g2d.setFont(new Font("Serif", Font.BOLD, 14));
        //g2d.setColor(ColorList.colors.get(currentPlayer - 1));
        g2d.drawString("Player " + currentPlayer + "'s turn", 3, 14);
        //Draw round number
        g2d.setColor(Color.BLACK);
        g2d.drawString("Round: " + game.getRoundCount(), width / 2 - 30, 14);

        //Draw scores and stats
        g2d.setColor(Color.BLACK);
        //Total
        int player1ScoreTotal = game.getPlayerList().get(0).getScore();
        int player2ScoreTotal = game.getPlayerList().get(1).getScore();
        double player1PercentTotal = ((player1ScoreTotal + player2ScoreTotal) == 0 ? 0 : ((double) player1ScoreTotal / (player1ScoreTotal + player2ScoreTotal)) * 100);
        double player2PercentTotal = ((player1ScoreTotal + player2ScoreTotal) == 0 ? 0 : ((double) player2ScoreTotal / (player1ScoreTotal + player2ScoreTotal)) * 100);
        String player1PercentTotalString = String.format("%.1f", player1PercentTotal);
        String player2PercentTotalString = String.format("%.1f", player2PercentTotal);
        String player1StringTotal = "P1: " + player1ScoreTotal + " (" + player1PercentTotalString + "%)";
        String player2StringTotal = "P2: " + player2ScoreTotal + " (" + player2PercentTotalString + "%)";

        WinnerHistory winnerHistory = game.getWinnerHistory();
        //Recent
        int player1ScoreRecent = (winnerHistory.getWinnerCount().get(1) == null ? 0 : winnerHistory.getWinnerCount().get(1));
        int player2ScoreRecent = (winnerHistory.getWinnerCount().get(2) == null ? 0 : winnerHistory.getWinnerCount().get(2));
        double player1PercentRecent = ((player1ScoreRecent + player2ScoreRecent) == 0 ? 0 : ((double) player1ScoreRecent / (player1ScoreRecent + player2ScoreRecent)) * 100);
        double player2PercentRecent = ((player1ScoreRecent + player2ScoreRecent) == 0 ? 0 : ((double) player2ScoreRecent / (player1ScoreRecent + player2ScoreRecent)) * 100);
        String player1PercentRecentString = String.format("%.1f", player1PercentRecent);
        String player2PercentRecentString = String.format("%.1f", player2PercentRecent);
        String player1StringRecent = "P1: " + player1ScoreRecent + " (" + player1PercentRecentString + "%)";
        String player2StringRecent = "P2: " + player2ScoreRecent + " (" + player2PercentRecentString + "%)";

        g2d.drawString("Total:  " + player1StringTotal + " / " + player2StringTotal + "  |  Last " + winnerHistory.getHistoryLength() + " games:  " + player1StringRecent + " / " + player2StringRecent, 3, height - 5);

        if (game.DEBUG_LEVEL >= 1) {
            int[][] AIScoreGrid = game.getPlayerList().get(game.HEATMAP_PID - 1).getPointGrid();
            int highestScore = game.getPlayerList().get(game.HEATMAP_PID - 1).findHighestScore();
            //Draw heatmap of bots decision
            for (int x = 0; x < game.getWidth(); x++) {
                for (int y = 0; y < game.getHeight(); y++) {
                    g2d.setColor(new Color((int) (((double) AIScoreGrid[x][y] / (double) highestScore) * 255), 50, 120));
                    g2d.fillRect(x * Constants.SQUARE_SIZE, y * Constants.SQUARE_SIZE + Constants.PADDING_TOP, Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
                    if (game.DEBUG_LEVEL >= 2) {
                        //Draw AI's  score grid (for debugging)
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(new Font("Serif", Font.BOLD, 16));
                        g2d.drawString("" + AIScoreGrid[x][y], Constants.SQUARE_SIZE / 2 + Constants.SQUARE_SIZE * x - 28, Constants.SQUARE_SIZE / 2 + Constants.SQUARE_SIZE * y + Constants.PADDING_TOP - 16);
                    }
                }
            }
        } else {
            //Draw background
            g2d.setColor(new Color(0, 50, 120));
            g2d.fillRect(0, Constants.PADDING_TOP, game.getWidth() * Constants.SQUARE_SIZE, game.getHeight() * Constants.SQUARE_SIZE); // FUNKAR EJ
        }
        //Draw square lines
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < width; i += Constants.SQUARE_SIZE) {
            g2d.fillRect(i, Constants.PADDING_TOP, Constants.LINE_THICKNESS, height - Constants.PADDING_TOP - Constants.PADDING_BOTTOM);
        }
        for (int i = 0; i < height; i += Constants.SQUARE_SIZE) {
            g2d.fillRect(0, i + Constants.PADDING_TOP, width, Constants.LINE_THICKNESS);
        }

        Color[][] colors = game.getColors();
        Point mostRecentMove = game.getMostRecentMove();

        for (int p = 1; p <= game.getNumberOfPlayers(); p++) {
            for (int x = 0; x < game.getWidth(); x++) {
                for (int y = 0; y < game.getHeight(); y++) {
                    if (game.getTile(x, y) == p) {
                        //Draw border around most recent move
                        if (mostRecentMove.x == x && mostRecentMove.y == y) {
                            g2d.setColor(Color.ORANGE);
                            g2d.drawRect(x * Constants.SQUARE_SIZE + 3, y * Constants.SQUARE_SIZE + Constants.PADDING_TOP + 3, Constants.SQUARE_SIZE - 4, Constants.SQUARE_SIZE - 4);
                        }
                        if (game.USE_RANDOM_COLORS >= 1) {
                            g2d.setColor(colors[x][y]); //Random colors                            
                        } else {
                            //g2d.setColor(ColorList.colors.get(p % ColorList.colors.size() - 1)); //Standard colors
                        }
                        //Draw markers
                        if (p <= 2) {
                            g2d.setFont(new Font("Monospaced", Font.BOLD, 70));
                            g2d.drawString((p == 1 ? "X" : "O"), Constants.SQUARE_SIZE / 2 + Constants.SQUARE_SIZE * x - 19, Constants.SQUARE_SIZE / 2 + Constants.SQUARE_SIZE * y + 22 + Constants.PADDING_TOP);
                        } else {
                            g2d.setFont(new Font("Serif", Font.BOLD, 50));
                            g2d.drawString("" + p, Constants.SQUARE_SIZE / 2 + Constants.SQUARE_SIZE * x - 10, Constants.SQUARE_SIZE / 2 + Constants.SQUARE_SIZE * y + 15 + Constants.PADDING_TOP);
                        }
                    }

                }
            }
        }

        //Draw play order numbers
        if (game.SHOW_PLAY_ORDER) {
            ArrayList<Point> moveHistoryList = game.getMoveHistory();
            for (int i = 0; i < moveHistoryList.size(); i++) {
                //System.out.println("Move (" + moveHistoryList.get(i).x + ", " + moveHistoryList.get(i).y + ")");
                g2d.setColor(Color.ORANGE);
                g2d.setFont(new Font("Monospaced", Font.BOLD, 12));
                g2d.drawString("" + (i + 1), (moveHistoryList.get(i).x + 1) * Constants.SQUARE_SIZE - 12, moveHistoryList.get(i).y * Constants.SQUARE_SIZE + Constants.PADDING_TOP + 13);
            }
        }

        int winner = game.getWinner();
        if (winner != 0) {
            g2d.setColor(new Color(0f, 0f, 0f, 0.4f));
            g2d.fillRect(0, 0, width, height + Constants.PADDING_TOP + Constants.PADDING_BOTTOM);
            //g2d.setColor(ColorList.colors.get(winner - 1));
            g2d.setFont(new Font("Serif", Font.BOLD, 50));
            g2d.drawString("Player " + winner + " wins!", width / 2 - 143, height / 2 - 10);
        }
    }

    //Called when GameListeners are notified of a change
    @Override
    public void gameChanged() {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!game.getPointsGiven()) {
            if (!SwingUtilities.isRightMouseButton(e)) {
                //game.setTile(e.getX() / Constants.SQUARE_SIZE, e.getY() / Constants.SQUARE_SIZE, 1);
                Player currentPlayer = game.getCurrentPlayer();
                if (!(currentPlayer instanceof IAI)) {
                    currentPlayer.setCurrentChoice(e.getX() / Constants.SQUARE_SIZE, (e.getY() - Constants.PADDING_TOP) / Constants.SQUARE_SIZE);
                    //System.out.println("XX: " + e.getX() / Constants.SQUARE_SIZE + " YY: " + (e.getY() - Constants.PADDING_TOP) / Constants.SQUARE_SIZE);
                    currentPlayer.playRound();
                }
            } else {
                if (game.DEBUG_LEVEL >= 1) {
                    game.setTile(e.getX() / Constants.SQUARE_SIZE, (e.getY() - Constants.PADDING_TOP) / Constants.SQUARE_SIZE, 0);
                }
            }
        } else {
            game.resetGame();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
