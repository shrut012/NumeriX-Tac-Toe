package com.numerixtactoe;
import java.util.*;

public class Main {

    private static final int BOARD_SIZE = 3;
    private static final int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private static final Set<Integer> availableMoves = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    private static final Set<Integer> availablePositions = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    private static boolean isPlayer1Turn = true;
    private static boolean isGameOver = false;
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Number Tic Tac Toe!");

        System.out.println("Positions: ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(3*i+j+1 + " | ");
            }
            System.out.println();
        }
        System.out.println();

        while(!isGameOver){
            printBoard();

            if (isPlayer1Turn) {
                int number = getPlayerNumber(scanner);
                int position = getPlayerPosition(scanner);
                updateBoard(number, position);
            }

            if(checkForWinningMove()){
                printBoard();
                if (isPlayer1Turn) {
                    System.out.println("Congratulations, You win!");
                }
                else {
                    System.out.println("Computer wins!");
                }
                isGameOver = true;
                return;
            }
            else if (availableMoves.isEmpty()) {
                printBoard();
                System.out.println("It's a draw!");
                isGameOver = true;
                return;
            }
            else {
                isPlayer1Turn = !isPlayer1Turn;
            }

            if(!isPlayer1Turn) {
                int number = getComputerNumber();
                int position = getComputerPosition();
                System.out.println("Computer chose number " + number + " and position " + position + ".");
                updateBoard(number, position);
            }

        }
    }

    private static void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.print("  | ");
                } else {
                    System.out.print(board[i][j] + " | ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int getPlayerNumber(Scanner scanner) {
        int number = 0;
        boolean validNumber = false;

        while (!validNumber) {
            System.out.print("Choose a number (1-9): ");
            number = scanner.nextInt();

            if (number < 1 || number > 9) {
                System.out.println("\t || Invalid number. Please choose a number between 1 and 9 ||");
            }
            else if (!availableMoves.contains(number)) {
                System.out.println("\t || Number already used. Please choose a different number. ||");
            }
            else {
                validNumber = true;
            }
        }

        availableMoves.remove(number);
        return number;
    }

    private static int getPlayerPosition(Scanner scanner) {
        int position = 0;
        boolean validPosition = false;

        while (!validPosition) {
            System.out.print("Choose a position (1-9): ");
            position = scanner.nextInt();

            if (position < 1 || position > 9) {
                System.out.println("\t || Invalid position. Please choose a position between 1 and 9. ||");
            }
            else if (!availablePositions.contains(position)) {
                System.out.println("\t || Position already used. Please choose a different position. ||");
            }
            else {
                validPosition = true;
            }
        }

        availablePositions.remove(position);
        return position;
    }

    private static int getComputerNumber() {
        List<Integer> availableNumbers = new ArrayList<>(availableMoves);
        int move=availableNumbers.get(random.nextInt(availableNumbers.size()));
        availableMoves.remove(move);
        return move;
    }

    private static int getComputerPosition() {
        List<Integer> availablePos = new ArrayList<>(availablePositions);
        int pos=availablePos.get(random.nextInt(availablePos.size()));
        availablePositions.remove(pos);
        return pos;
    }

    private static void updateBoard(int number, int position) {
        int row = (position - 1) / BOARD_SIZE;
        int col = (position - 1) % BOARD_SIZE;

        board[row][col] = number;
    }

    private static boolean checkForWinningMove() {
        // Checking rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            int sum = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                sum += board[i][j];
            }
            if (sum == 15) {
                return true;
            }
        }

        // Checking columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            int sum = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                sum += board[j][i];
            }
            if (sum == 15) {
                return true;
            }
        }

        // Checking diagonals
        int sum = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            sum += board[i][i];
        }
        if (sum == 15) {
            return true;
        }

        sum = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            sum += board[i][BOARD_SIZE - i - 1];
        }
        return sum == 15;
    }

}