package gamelogic;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameLogic {
    enum gameStates {
        PLAYER1_TURN,
        PLAYER2_TURN,
        PLAYER1_WIN,
        PLAYER2_WIN,
        IMPOSSIBLE
    }

    final int rows = 10;
    final int cols = 10;
    Scanner scanner;
    PrintStream printStream;

    GameField gameField1;
    GameField gameField2;

    Player p1;
    Player p2;

    gameStates currentState;
    String coordRegex = "[" + GameField.startingRow + "-" +(char) (GameField.startingRow+rows-1) + "][0-9]+";

    public GameLogic(PrintStream printstream, Scanner scanner) {
        this.printStream=printstream;
        this.scanner = scanner;
        this.gameField1 = new GameField(rows, cols);
        this.gameField2 = new GameField(rows, cols);
        this.p1 = new Player( "Player 1", gameField1, gameField2);
        this.p2 = new Player( "Player 2", gameField2, gameField1);

        gameField1.setOwner(p1);
        gameField2.setOwner(p2);
    }

    public void startGame() {
        try {
            //Initialize
            this.currentState = gameStates.PLAYER1_TURN;

            this.positionAllShips(p1);
            this.switchTurns(currentState);

            this.positionAllShips(p2);
            this.switchTurns(currentState);
            Player currentPlayer;
            Player otherPlayer;

            //Everything ready. Start the GameLoop!
            while (currentState != gameStates.PLAYER1_WIN || currentState != gameStates.PLAYER2_WIN) {
                if (currentState == gameStates.PLAYER1_TURN) {
                    currentPlayer = this.p1;
                    otherPlayer = this.p2;
                } else if (currentState == gameStates.PLAYER2_TURN) {
                    currentPlayer = this.p2;
                    otherPlayer = this.p1;
                } else {
                    break;
                }
                //Print the Fields
                currentPlayer.getOpponentField().printGameField(currentPlayer);
                System.out.println("---------------------");
                currentPlayer.getOwnGameField().printGameField(currentPlayer);
                //Handle Input
                this.printStream.printf(currentPlayer.getName() + ", it's your turn:%n");

                boolean shotFired = false;
                while (!shotFired) {
                    try {
                        Square hitSquare = this.fireShot(currentPlayer);
                        if (!hitSquare.isOccupied()) {
                            this.printStream.println("You missed!");
                        } else {
                            if (hitSquare.getOccupyingShip().isSunken()) {
                                if (otherPlayer.allShipsSunk()) {
                                    if (currentState == gameStates.PLAYER1_TURN) {
                                        currentState = gameStates.PLAYER1_WIN;
                                    } else {
                                        currentState = gameStates.PLAYER2_WIN;
                                    }
                                } else {
                                    this.printStream.println("You sank a ship!");
                                }
                            } else {
                                this.printStream.println("You hit a ship!");
                            }
                        }
                        shotFired = true;
                    } catch (Exception e) {
                        this.printStream.println(e.getMessage());
                    }
                }
                if (currentState == gameStates.PLAYER1_TURN || currentState == gameStates.PLAYER2_TURN) {
                    currentState = this.switchTurns(currentState);
                }
            }
            if (currentState == gameStates.PLAYER1_WIN) {
                this.printStream.println("Player 1 sank the last ship. You won. Congratulations!");
            } else if (currentState == gameStates.PLAYER2_WIN) {
                this.printStream.println("Player 2 sank the last ship. You won. Congratulations!");
            } else {this.printStream.println("Game finished unexpectedly.");}

        }
        catch (Exception e)
        {
            this.printStream.println(e.getMessage());
        }
    }

    private gameStates switchTurns(gameStates currentState) {
        printStream.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        if(currentState == gameStates.PLAYER1_TURN) {
            return gameStates.PLAYER2_TURN;
        }
        else if(currentState == gameStates.PLAYER2_TURN) {
            return gameStates.PLAYER1_TURN;
        }
        else return  gameStates.IMPOSSIBLE;
    }

    public void positionAllShips(Player player) throws Exception {
        this.printStream.println(player.getName() + ", place your ships on the game field");

        player.getOwnGameField().printGameField(player);
        for(shipTypes t : shipTypes.values()) {
            boolean placed = false;
            this.printStream.printf("%nEnter the coordinates of the %s (%d cells):%n",t.label, t.nrOfCells);
            while(!placed) {
                String input = scanner.nextLine();
                    if(Pattern.matches(coordRegex + "[\\s]+" + coordRegex, input)) {
                    Matcher matcher = Pattern.compile(coordRegex).matcher(input);
                    matcher.find();
                    String token1 = matcher.group();
                    matcher.find();
                    String token2 = matcher.group();

                    int row1 = token1.charAt(0) - GameField.startingRow;
                    int col1 = Integer.parseInt(""+token1.substring(1))-1;

                    int row2 = token2.charAt(0)- GameField.startingRow;
                    int col2 = Integer.parseInt(""+token2.substring(1))-1;
                    //Order the coordinates from smallest to largest (row then col)
                    if (row1==row2) {
                        int temp = Math.max(col1, col2);
                        col1 = Math.min(col1, col2);
                        col2 = temp;
                    }
                    else if (col1==col2) {
                        int temp = Math.max(row1, row2);
                        row1 = Math.min(row1, row2);
                        row2 = temp;
                    }

                    try {
                        player.placeShip(row1, col1, row2, col2, t);
                        placed = true;
                    } catch (Exception e) {
                        this.printStream.println(e.getMessage());
                    }
                }
                else {
                    this.printStream.printf("%nError: Wrong input! Please try again...%n");
                }
            }
            player.getOwnGameField().printGameField(player);
        }
    }

    public Square fireShot(Player player) throws Exception { //player currently firing the shot.
        String input = scanner.nextLine();
        if(Pattern.matches(coordRegex, input)) {
            int row = input.charAt(0)- GameField.startingRow;
            int col = Integer.parseInt(""+input.substring(1))-1;

            if(row >= player.getOpponentField().getNumberOfRows() || col >= player.getOpponentField().getNumberOfCols()) {
                throw new Exception("Error! You entered the wrong coordinates! Try again:");
            }
            else {
                return player.getOpponentField().registerShot(row, col);
            }
        }
        else {
            throw new Exception("Wrong Coordinate input!");
        }
    }

}
