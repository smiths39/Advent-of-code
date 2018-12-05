import java.io.*;
import java.util.*;

public class NoMatterHowYouSliceIt {
	private static ArrayList<Integer> claimId = new ArrayList<>();
	private static ArrayList<Integer> leftInch = new ArrayList<>();
	private static ArrayList<Integer> topInch = new ArrayList<>();
	private static ArrayList<Integer> width = new ArrayList<>();
	private static ArrayList<Integer> height = new ArrayList<>();

	private static ArrayList<String> readFabricClaims() {
		ArrayList<String> inputData = new ArrayList<>();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader("fabric_input.txt"));
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				inputData.add(line);
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

	private static void splitFabricData(ArrayList<String> inputData) {
		for (String data : inputData) {
			claimId.add(Integer.parseInt(substringBetweenCharacters(data, "#", "@")));
			leftInch.add(Integer.parseInt(substringBetweenCharacters(data, "@", ",")));
			topInch.add(Integer.parseInt(substringBetweenCharacters(data, ",", ":")));
			width.add(Integer.parseInt(substringBetweenCharacters(data, ":", "x")));
			height.add(Integer.parseInt(substringBetweenCharacters(data, "x", "")));
		}
	}

	private static String substringBetweenCharacters(String str, String startChar, String endChar) {
		if (endChar.isEmpty()) {
			return str.substring(str.indexOf(startChar) + 1, str.length());
		} 
		return str.substring(str.indexOf(startChar) + 1, str.indexOf(endChar)).trim();
	}

	private static Integer getOverlappingClaims() {
		ArrayList<String> coordinates = new ArrayList<>();
		ArrayList<String> alreadyFound = new ArrayList<>();

		int totalWidth = 1000;
		int totalHeight = 1000;

		int leftInchIndex = 0;
		int topInchIndex = 0; 
		int widthIndex = 0;
		int heightIndex = 0;
	
		int overlapCounter = 0;
		String rowCol = "";

		boolean claimOverlapped = false;

		for (int row = 0; row < totalHeight; row++) {
			for (int col = 0; col < totalWidth; col++) {
				for (int i = 0; i < claimId.size(); i++) {
					leftInchIndex = leftInch.get(i);
					topInchIndex = topInch.get(i); 
					widthIndex = width.get(i);
					heightIndex = height.get(i);

					if (topInchIndex <= row && row < (topInchIndex + heightIndex)) {
						if (leftInchIndex <= col && col < (leftInchIndex + widthIndex)) {
							rowCol = Integer.toString(row) + " " + Integer.toString(col);
							if (coordinates.contains(rowCol) && !alreadyFound.contains(rowCol)) {
								overlapCounter++;
								alreadyFound.add(rowCol);
								claimOverlapped = true;
							}  else if (!coordinates.contains(rowCol)) {
								coordinates.add(rowCol);
							}
						}
					} 
				}
			}
		}

		return overlapCounter;
	}

	public static void main(String [] args) {
		ArrayList<String> fabricData = readFabricClaims();
		splitFabricData(fabricData);

		int overlappingClaims = getOverlappingClaims();
		System.out.println("Overlapping claims: " + overlappingClaims);
	}
}

/**
answer 1 = 111266
answer 2 = 123
*/