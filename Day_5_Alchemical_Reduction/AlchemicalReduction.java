import java.io.*;
import java.util.*;

public class AlchemicalReduction  {
	private static String readUnitInput() {
		String inputData = "";
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader("unit_input.txt"));
			inputData = bufferedReader.readLine();
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

	private static int removePolymer(String inputData) {
		for (int i = 0; i < inputData.length(); i++) {
            if (i + 2 < inputData.length()) {
            	if (inputData.substring(i, i+1).toLowerCase().equals(inputData.substring(i+1,i+2).toLowerCase()) && !inputData.substring(i, i+1).equals(inputData.substring(i+1, i+2))) {
                    inputData = inputData.substring(0, i) + inputData.substring(i+2); //Remove char from line
                        
                    if (i-2 < -1) {
                        i = -1; // Go back to start of for loop
                    } else {
                        i = i - 2;
                    }
                }
			}
		}
		return inputData.length();
	}

	private static int shortestPolymer(String inputData) {
		int currentLength;
		int shortestLength = inputData.length();
		String line = inputData;

		for (char letter = 'a'; letter <= 'z'; letter++) {
			line = line.replace(Character.toString(Character.toLowerCase(letter)), "");
			line = line.replace(Character.toString(Character.toUpperCase(letter)), "");

			currentLength = removePolymer(line);

			if (shortestLength > currentLength) {
				shortestLength = currentLength;
			}

			line = inputData;
		}

		return shortestLength;
	}

	public static void main(String [] args) {
		String inputData = readUnitInput();
		System.out.println("Total remaining units: " + removePolymer(inputData));
		System.out.println("Shortest polymer removed unit: " + shortestPolymer(inputData));
	}
}