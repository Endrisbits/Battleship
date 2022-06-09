
import battleship.gamelogic.GameLogic;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        // Write your code here
        GameLogic newGame = new GameLogic(System.out, new Scanner(System.in));
        newGame.startGame();
    }
}
