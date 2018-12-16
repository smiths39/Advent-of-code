import java.util.*;

public class ChronalCharge {

	private final static int PUZZLE_INPUT = 4842;

	private static int[][] grid = new int[301][301];
	private static int xCo = -1, yCo = -1, square = 0; 

	private static void calculatePowerLevel() {
		for (int col = 1; col < grid.length; col++) {
			for (int row = 1; row < grid.length; row++) {
				int rackId = col + 10;
				int startPowerLevel = ((rackId * row) + PUZZLE_INPUT) * rackId;
				int hundredDigit = (startPowerLevel / 100) % 10;
				int powerLevel = hundredDigit - 5;

				grid[row][col] = powerLevel;
			}
		}
	}

	private static void getLargestTotalPowerSquare() {
		int max = Integer.MIN_VALUE;

		for (int col = 1; col < grid.length-2; col++) {
			for (int row = 1; row < grid.length-2; row++) {
				int totalSum = 0;

				for (int x = col; x <= col + 2; x++) {
					for (int y = row; y <= row + 2; y++) {
						totalSum += grid[x][y];
					}
				}

				if (totalSum > max) {
					max = totalSum;
					xCo = row;
					yCo = col;
				}
			}
		}
	}

	private static void getLargestTotalPowerSquareAnySize() {
		int max = Integer.MIN_VALUE;

		for (int col = 1; col < grid.length-2; col++) {
			for (int row = 1; row < grid.length-2; row++) {

				for (int tempCol = 1; tempCol < grid.length - col; tempCol++) {
					for (int tempRow = 1; tempRow < grid.length - row; tempRow++) {
						int totalSum = 0;

						if (tempCol != tempRow) {
							continue;
						}
						for (int x = col; x <= col + tempCol; x++) {
							for (int y = row; y <= row + tempRow; y++) {
								totalSum += grid[x][y];
							}
						}

						if (totalSum > max) {
							max = totalSum;
							xCo = row;
							yCo = col;
							square = ++tempCol;
						}
					}
				}
			}
		}
	}

	public static void main(String [] args) {
		calculatePowerLevel();

		getLargestTotalPowerSquare();
		System.out.println("Part 1: " + xCo + "," + yCo);
		
		getLargestTotalPowerSquareAnySize();
		System.out.println("Part 2: " + xCo + "," + yCo + "," + square);
	}
}