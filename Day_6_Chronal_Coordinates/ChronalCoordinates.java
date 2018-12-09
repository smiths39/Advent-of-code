import java.io.*;
import java.util.*;

class Coordinates {
	int id;
	int row;
	int col;

	public Coordinates(int id, int row, int col) {
		this.id = id;
		this.row = row;
		this.col = col;
	} 
}

public class ChronalCoordinates {

	private static ArrayList<Coordinates> readCoordinates() {
		ArrayList<Coordinates> inputData = new ArrayList<>();
		BufferedReader bufferedReader = null;

		int id = 0;
		int row;
		int col;

		try {
			bufferedReader = new BufferedReader(new FileReader("coordinates_input.txt"));
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				row = Integer.parseInt(line.split(", ")[0]);
				col = Integer.parseInt(line.split(", ")[1]);

				inputData.add(new Coordinates(id, row, col));
				id++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return inputData;
	}

	private static int[][] generatedGrid(ArrayList<Coordinates> inputData) {
		int [][] grid = new int[1000][1000];
		int distance;

		for (int row = 0; row < 1000; row++) {
			for (int col = 0; col < 1000; col++) {
				int closestCell = Integer.MAX_VALUE;

				for (Coordinates coordinates : inputData) {
					distance = Math.abs(coordinates.row - row) + Math.abs(coordinates.col - col);

					if (distance == closestCell) {
						grid[row][col] = -1;
					} else if (distance < closestCell) {
						closestCell = distance;
						grid[row][col] = coordinates.id;
					}
				}
			}
		}

		return grid;
	}



	private static ArrayList<Integer> findGridEdges(int [][] grid) {
		ArrayList<Integer> edgeCoordinates = new ArrayList<>();

		for (int i = 0; i < 999; i++) {
			edgeCoordinates.add(grid[999][i]);
			edgeCoordinates.add(grid[i][999]);
			edgeCoordinates.add(grid[0][i]);
			edgeCoordinates.add(grid[i][0]);
		}

		return edgeCoordinates;
	}

	private static int[] calculateArea(int [][] grid, ArrayList<Integer> edges, ArrayList<Coordinates> inputData) {
		int [] areaCount = new int[inputData.size()];

		for (int [] area : grid) {
			for (int id : area) {
				if (!edges.contains(id)) {
					areaCount[id]++;
				}
			}
		}

		return areaCount;
	}

	private static int getMaxSpace(int [] areaCount) {
		int maxSpace = 0;

		for (int area : areaCount) {
			if (area > maxSpace) {
				maxSpace = area;
			}
		}

		return maxSpace;
	}

	private static int getTotalSpace(ArrayList<Coordinates> inputData) {
		int totalSpace = 0;

        for (int row = 0; row < 1000; row++) {
            for (int col = 0; col < 1000; col++) {
		        int distance = 0;

		        System.out.println(inputData.size());
	            
	            for (int i = 0; i < inputData.size(); i++) {
                    distance += Math.abs(row - inputData.get(i).row) + Math.abs(col - inputData.get(i).col);
                }

	            if (distance < 10000) {
	                totalSpace++;
	            }
	        }
        }

        return totalSpace;
	}

	public static void main(String [] args) {
		ArrayList<Coordinates> inputData = readCoordinates();
		int [][] grid = generatedGrid(inputData);

		ArrayList<Integer> edges = findGridEdges(grid);
		int [] areaCount = calculateArea(grid, edges, inputData);

		System.out.println("Max Space: " + getMaxSpace(areaCount));
		System.out.println("Total Space: " + getTotalSpace(inputData));
	}
}

//44202