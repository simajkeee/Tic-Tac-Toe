package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static final int TIC_TAC_TOE_CLASSIC = 9;

    static final char player1 = 'X';
    static final char player2 = 'O';

    public static void main(String[] args) {
        // write your code here
        Scanner s = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String userInput = "_________";
        char[] userArrayInput = new char[TIC_TAC_TOE_CLASSIC];
        for(int i = 0; i < userInput.length(); i++) {
            userArrayInput[i] = userInput.charAt(i);
        }
        char playerStarts = player1;
        outputTicTacToe(userArrayInput);
        while(!isGameEnded(userArrayInput)) {
            System.out.print("Enter the coordinates: ");
            String coordinates = s.nextLine();
            String[] userCoords = coordinates.split(" ");
            if (!onlyDigitsArray(userCoords)) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int[] coords = convertStringArrayToInt(userCoords);
            if (!areCoordsInGameBoundaries(coords)) {
                System.out.println("Coordinates should be from 1 to " + (int)Math.sqrt(TIC_TAC_TOE_CLASSIC) +"!");
                continue;
            }

            if (areCellsOccupied(coords, userArrayInput)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            switch(playerStarts) {
                case player1:
                    fillCellByCoordinate(coords, userArrayInput, player1);
                    playerStarts = player2;
                    break;
                case player2:
                    fillCellByCoordinate(coords, userArrayInput, player2);
                    playerStarts = player1;
                    break;
            }
            outputTicTacToe(userArrayInput);

            if (!isPossible(userArrayInput) || (isWinner(userArrayInput, player1) && isWinner(userArrayInput, player2))) {
                System.out.println("Impossible");
            } else {

                if (isWinner(userArrayInput, player1)) {
                    System.out.println(player1 + " wins");
                    break;
                }

                if (isWinner(userArrayInput, player2)) {
                    System.out.println(player2 + " wins");
                    break;
                }

//                if (!isWinner(userArrayInput, player1) && !isWinner(userArrayInput, player2) && userInput.indexOf('_') != -1) {
//                    System.out.println("Game not finished");
//                }

                if (!isWinner(userArrayInput, player1) && !isWinner(userArrayInput, player2) && isGameEnded(userArrayInput)) {
                    System.out.println("Draw");
                    break;
                }
            }

        }
    }

    public static void fillCellByCoordinate(int[] coords, char[] ticTacToe, char player) {
        ticTacToe[(coords[0] - 1) * 3 + coords[1] - 1] = player;
    }

    public static boolean areCellsOccupied(int[] coords, char[] ticTacToe) {
        if (ticTacToe[(coords[0] - 1) * 3 + coords[1] - 1] == player1 || ticTacToe[(coords[0] - 1) * 3 + coords[1] - 1] == player2) {
            return true;
        }
        return false;
    }

    public static boolean areCoordsInGameBoundaries(int[] coords) {
        for(int coord : coords) {
            if (coord > (int)Math.sqrt(TIC_TAC_TOE_CLASSIC) || coord < 1 ) {
                return false;
            }
        }
        return true;
    }

    public static int[] convertStringArrayToInt(String[] arrayStrings) {
        int[] integers = new int[arrayStrings.length];
        for(int i = 0; i < arrayStrings.length; i++) {
            integers[i] = Integer.parseInt(arrayStrings[i]);
        }
        return integers;
    }

    public static boolean onlyDigitsArray(String[] arrayStrings) {
        for(String str : arrayStrings) {
            if (!onlyDigits(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean onlyDigits(String str) {
        // Regex to check string
        // contains only digits
        String regex = "[0-9]+";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }

    public static void outputTicTacToe(char[] ticTacToe) {
        System.out.println("---------");
        for(int i = 0; i < ticTacToe.length; i++) {
            if (i%3 == 0) {
                System.out.print("| ");
            }

            System.out.print(ticTacToe[i] + " ");

            if ((i+1)%3 == 0) {
                System.out.println("|");
            }
        }
        System.out.println("---------");
    }

    public static boolean isGameEnded(char[] ticTacToe) {
        for (int i = 0; i < ticTacToe.length; i++) {
            if (ticTacToe[i] != player1 && ticTacToe[i] != player2) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWinner(char[] ticTacToe, char player) {
        int N = (int)Math.sqrt(ticTacToe.length);

        //check horizontals
        for(int i = 0; i < ticTacToe.length; i+=N) {
            int counter = N;
            for(int j = i; j < i+N; j++) {
                if (player == ticTacToe[j]) {
                    counter--;
                }
            }
            if (counter == 0) {
                return true;
            }
        }

        //check verticals
        for(int i = 0; i < N; i++) {
            int counter = N;
            for(int j = i; j <= i+N*(N-1); j+=N) {
                if (player == ticTacToe[j]) {
                    counter--;
                }
            }
            if (counter == 0) {
                return true;
            }
        }

        //check left diagonal
        for(int i = 0; i < 1; i++) {
            int counter = N;
            for(int j = i; j < ticTacToe.length; j += N+1) {
                if (ticTacToe[j] == player) {
                    counter--;
                }
            }

            if (counter == 0) {
                return true;
            }
        }

        //check right diagonal
        for(int i = N-1; i < N+1; i++) {
            int counter = N;
            for(int j = i; j < ticTacToe.length - (N-1); j += N-1) {
                if (ticTacToe[j] == player) {
                    counter--;
                }
            }

            if (counter == 0) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPossible(char[] ticTacToe) {
        int counter1 = 0;
        int counter2 = 0;
        for(int i = 0; i < ticTacToe.length; i++) {
            if (ticTacToe[i] == ' ' || ticTacToe[i] == '_') {
                continue;
            }
            if (player1 == ticTacToe[i]) {
                counter1++;
                continue;
            }
            counter2++;
        }
        return (Math.abs(counter1 - counter2) > 1) ? false : true;
    }
}
