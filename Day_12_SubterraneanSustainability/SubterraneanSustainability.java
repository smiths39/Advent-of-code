import java.io.*;
import java.util.*;

public class SubterraneanSustainability {
	private static ArrayList<String> plantRules = new ArrayList<>();
	private static String initialState = "";

	private final static int GENERATION_YEARS = 250;

	private static void readPlantData() {
		try {
            Scanner reader = new Scanner(new File("plant_input.txt"));
            
            while (reader.hasNext()) {
                String line = reader.nextLine();
                
                if (line.toUpperCase().contains("INITIAL")) {
                	initialState = line.substring(line.indexOf(':') + 1, line.length());
                } else {
                	plantRules.add(line);
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private static int findGenerationPlants() {
		String currentState = initialState;
		int totalPlants = 0;

		for (int gen = 0; gen <= GENERATION_YEARS; gen++) {
			for (int i = 0; i < currentState.length(); i++) {
				if (currentState.charAt(i) == '#') {
					totalPlants++;
				}
			}

			for (int ruleIndex = 0; ruleIndex < plantRules.size(); ruleIndex++) {
				for (int lineIndex = 5; lineIndex <= currentState.length(); lineIndex++) {
					String rulePrefix = currentState.substring(lineIndex-5, lineIndex);

					if (plantRules.contains(rulePrefix + " => #")) {
						currentState = currentState.substring(0, 2) + "#" + currentState.substring(3, 5);
					} else if (plantRules.contains(rulePrefix + " => .")) {
						currentState = currentState.substring(0, 2) + "." + currentState.substring(3, 5);
					}
				}
			}
		}

		return totalPlants;
	}

	public static void main(String [] args) {
		readPlantData();
		System.out.println("Total Plants: " + findGenerationPlants());
	}
}

// Part 1: 3405
// Part 2: 3350000000000