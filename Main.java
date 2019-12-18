package tictactoe;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    private static final int gridSize = 3;
    private static char[][] grid;
    private static boolean hasUnderscores = false;
    private static Scanner scanner;

    private static boolean initGrid(String input) {
        grid = new char[gridSize][gridSize];
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = input.charAt(i*gridSize + j);
                if (grid[i][j] == 'X') {
                    xCount++;
                } else if (grid[i][j] == 'O') {
                    oCount++;
                } else {
                    hasUnderscores = true;
                }
            }
        }
        return true;
    }

    private static void printGrid() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static boolean checkWinAt(int y, int x) {
                // Row
        if (    grid[y][x] == grid[y][(x+1)%gridSize] && grid[y][x] == grid[y][(x+2)%gridSize] ||
                // Column
                grid[y][x] == grid[(y+1)%gridSize][x] && grid[y][x] == grid[(y+2)%gridSize][x])
        {
            return true;
        }
        if (y == x && grid[x][x] == grid[(y+1)%gridSize][(x+1)%gridSize] && grid[y][x] == grid[(y+2)%gridSize][(x+2)%gridSize]) {
            return true;
        }
        if (y == gridSize-x-1 && grid[y][x] == grid[(y+1)%gridSize][(x+2)%gridSize] && grid[y][x] == grid[(y+2)%gridSize][(x+1)%gridSize]) {
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        //System.out.print("Enter cells: ");
        scanner = new Scanner(System.in);
        String input = "_________";
        initGrid(input);
        printGrid();

        Pattern twoIntsPattern = Pattern.compile("(\\d+) (\\d+)");

        boolean xTurn = true;
        int turnLimit = gridSize*gridSize;
        int turn = 1;

        while (true) {
            int x, y;
            while (true) {
                System.out.print("Enter the coordinates: ");
                String next = scanner.nextLine();
                Matcher matcher = twoIntsPattern.matcher(next);
                if (!matcher.matches()) {
                    System.out.println("You should enter numbers!");
                    continue;
                }
                x = Integer.parseInt(matcher.group(1)) - 1;
                y = gridSize - Integer.parseInt(matcher.group(2));
                if (x < 0 || x > 2 ||
                        y < 0 || y > 2) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                if (grid[y][x] != '_') {
                    System.out.println("This cell is occupied! Choose another one! ");
                    continue;
                }
                break;
            }
            if (xTurn) {
                grid[y][x] = 'X';
                xTurn = false;
            } else {
                grid[y][x] = 'O';
                xTurn = true;
            }
            printGrid();
            boolean somebodyWon = checkWinAt(y, x);
            if (somebodyWon) {
                if (!xTurn) {
                    System.out.println("X wins");
                } else {
                    System.out.println("O wins");
                }
                break;
            }
            if (turn == turnLimit) {
                System.out.println("Draw");
                break;
            }
            turn++;
        }

    }
}
