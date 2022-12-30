import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int[][] grid = {
                {96, 33, 44, 98, 75, 68, 99, 84},
                {10, 41, 1, 86, 46, 24, 53, 93},
                {83, 97, 94, 27, 65, 51, 30, 7},
                {56, 70, 47, 64, 22, 88, 67, 12},
                {91, 11, 77, 48, 13, 71, 92, 15},
                {32, 59, 17, 25, 31, 4, 16, 63},
                {79, 5, 14, 23, 78, 37, 40, 74},
                {35, 89, 52, 66, 82, 20, 95, 21}
        };
        ArrayList<Integer> path = findPath(grid);
        System.out.println("Starting square: " + "\n" + path.get(0) + "\n");
        System.out.println("Path: ");
        for (int i = 0; i < path.size(); i++) {
            if (i < path.size() - 1)
                System.out.print(path.get(i) + " -> ");
            else {
                int finalGem = path.get(i);
                int finalVault = 0;
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[0][j] == finalGem) {
                        finalVault = j + 1;
                        break;
                    }
                }
                System.out.println(finalGem + " -> " + "Vault " + finalVault);
            }
        }
        System.out.println("\nMax Gems:\n" + path.stream().mapToInt(i -> i.intValue()).sum());
    }

    public static ArrayList<Integer> findPath(int[][] grid) {
        int[][] sumGrid = new int[grid.length][grid.length];
        for(int i = 0; i < grid.length; i++)
            sumGrid[i] = grid[i].clone();
        ArrayList<Integer> path = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            for (int k = 0; k < sumGrid[i].length; k++) {
                if (k == 0) {
                    sumGrid[i][k] = sumGrid[i][k] + Math.max(sumGrid[i + 1][k], sumGrid[i + 1][k + 1]);
                } else if (k == sumGrid[i].length - 1) {
                    sumGrid[i][k] = sumGrid[i][k] + Math.max(sumGrid[i + 1][k], sumGrid[i + 1][k - 1]);
                } else {
                    sumGrid[i][k] = sumGrid[i][k] + Math.max(sumGrid[i + 1][k], Math.max(sumGrid[i + 1][k - 1], sumGrid[i + 1][k + 1]));
                }
            }
        }

        int maxAt = 0;
        for (int i = 0; i < sumGrid[0].length; i++) {
            maxAt = sumGrid[0][i] > sumGrid[0][maxAt] ? i : maxAt;
        }

        int currentRow = 0;
        int currentCol = maxAt;
        path.add(grid[0][maxAt]);
        while (currentRow != 7) {
            int max = 0;
            if (currentCol == 0) {
                max = Math.max(sumGrid[currentRow + 1][currentCol], sumGrid[currentRow + 1][currentCol + 1]);
                if (max == sumGrid[currentRow + 1][currentCol]) {
                    currentRow += 1;
                    path.add(grid[currentRow][currentCol]);
                } else if (max == sumGrid[currentRow + 1][currentCol + 1]) {
                    currentRow += 1;
                    currentCol += 1;
                    path.add(grid[currentRow][currentCol]);
                }
            } else if (currentCol == 7) {
                max = Math.max(sumGrid[currentRow + 1][currentCol], sumGrid[currentRow + 1][currentCol - 1]);
                if (max == sumGrid[currentRow + 1][currentCol]) {
                    currentRow += 1;
                    path.add(grid[currentRow][currentCol]);
                } else if (max == sumGrid[currentRow + 1][currentCol - 1]) {
                    currentRow += 1;
                    currentCol -= 1;
                    path.add(grid[currentRow][currentCol]);
                }
            } else {
                max = Math.max(sumGrid[currentRow + 1][currentCol], Math.max(sumGrid[currentRow + 1][currentCol - 1], sumGrid[currentRow + 1][currentCol + 1]));
                if (max == sumGrid[currentRow + 1][currentCol]) {
                    currentRow += 1;
                    path.add(grid[currentRow][currentCol]);
                } else if (max == sumGrid[currentRow + 1][currentCol - 1]) {
                    currentRow += 1;
                    currentCol -= 1;
                    path.add(grid[currentRow][currentCol]);
                } else if (max == sumGrid[currentRow + 1][currentCol + 1]) {
                    currentRow += 1;
                    currentCol += 1;
                    path.add(grid[currentRow][currentCol]);
                }
            }
        }
        Collections.reverse(path);
        return path;
    }
}
