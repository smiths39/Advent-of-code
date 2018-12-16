import java.io.*;
import java.util.*;
import java.nio.file.*;

public class SubterraneanSustainability {
	private static HashMap<String, Character> plantRules = new HashMap<>();
	private static char[] plantInput;
	private static String initial;
	private static long part1 = 0, part2 = 0;

	private final static int GENERATION_YEARS = 20;

	private static void readPlantData() {
		try {
	        List<String> lines = Files.readAllLines(Paths.get("plant_input.txt"));

	        for (String line : lines) {
	            if (line.isEmpty()) {
	                continue;
	            }

	           	String [] input = line.split(" ");

	           	if (input[0].toUpperCase().equals("INITIAL")) {
	           		initial = input[2];
	            } else {
	            	plantRules.put(input[0], input[2].charAt(0));
	            }
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	private static long countPlants(long num) {
		long count = 0;
		
		for (int i = 0; i < plantInput.length; i++) {
			if (plantInput[i] == '#') {
				int offset = i - 250;
				long addFiftyBillion = offset + (50000000000L - (num-1));
				
				if (num == 20) {
					count += offset;
				} else {
					count += addFiftyBillion;
				}
			}			
		}
		return count;
	}

	private static void findGenerationPlants() {
		String negative = "", positive = "";

		for (int i = 0; i < 250; i++) {
			negative += ".";
			positive += ".";
		}

		String merged = negative + initial + positive;
		plantInput = merged.toCharArray();

		long k;

		for (k = 1; k <= 150; k++) {
			char [] tempArr = plantInput.clone();

			for (int i = 2; i < tempArr.length - 2; i++) {
				String str = new String(tempArr, i-2, 5);
				Character rule = plantRules.get(str);

				if (rule != null) {
					plantInput[i] = rule;
				} else {
					plantInput[i] = '.';
				}
			}

			if (k == 20) {
				part1 = countPlants(k);
			}
		}

		part2 = countPlants(k);
	}

	public static void main(String [] args) {
		readPlantData();
		findGenerationPlants();

		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}
}

// Part 1: 3405
// Part 2: 3350000000000