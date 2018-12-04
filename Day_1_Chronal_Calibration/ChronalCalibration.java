import java.io.*;
import java.util.*;

public class ChronalCalibration {

	private static ArrayList<String> readFrequencyInput() {
		ArrayList<String> inputData = new ArrayList<>();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader("frequency_readings.txt"));
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

	private static Integer getResultingFrequency(ArrayList<String> inputData) {
		int totalCount = 0;
		int num = 0;

		for (String reading : inputData) {
			num = Integer.parseInt(reading.substring(1, reading.length()));

			if (reading.charAt(0) == '+') {
				totalCount += num;
			} else if (reading.charAt(0) == '-') {
				totalCount -= num;
			}
		}

		return totalCount;
	}

	private static Integer findRepeatedFrequency(ArrayList<String> inputData) {
		int repeatedNum = 9;
		int totalCount = 0;
		int num = 0;
		boolean foundRepeated = false;

		ArrayList<Integer> existingFrequencies = new ArrayList<>();

		while (!foundRepeated) {
			for (String reading : inputData) {
				num = Integer.parseInt(reading.substring(1, reading.length()));

				if (reading.charAt(0) == '+') {
					totalCount += num;
				} else if (reading.charAt(0) == '-') {
					totalCount -= num;
				}

				if (existingFrequencies.contains(totalCount)) {
					repeatedNum = totalCount;
					foundRepeated = true;
					break;
				} else {
					existingFrequencies.add(totalCount);
				}
			}
		}
		return repeatedNum;
	}

	public static void main(String [] args) {
		ArrayList<String> frequencyData = readFrequencyInput();

		int totalFrequency = getResultingFrequency(frequencyData);
		System.out.println("Total Frequency: " + totalFrequency);

		int repeatedFrequency = findRepeatedFrequency(frequencyData);	
		System.out.println("Repeated Frequency: " + repeatedFrequency);
	}
}