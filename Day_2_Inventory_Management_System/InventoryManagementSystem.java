import java.io.*;
import java.util.*;
import com.google.common.collect.*;

public class InventoryManagementSystem {
	private static ArrayList<String> readBoxIds() {
		ArrayList<String> inputData = new ArrayList<>();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader("box_ids.txt"));
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

	private static SetMultimap<Character, String> findChecksum(ArrayList<String> inputData, int numEntries) {
		SetMultimap<Character, String> checksums = HashMultimap.create();

		int totalChecksum = 0;
		int characterCount = 0;

		for (String boxId : inputData) {
			for (char letter = 'a'; letter <= 'z'; letter++) {
				for (int i = 0; i < boxId.length(); i++) {
					if (boxId.charAt(i) == letter) {
						characterCount += 1;
					}
				}

				if (characterCount == numEntries && !checksums.containsEntry(letter, boxId)) {
					checksums.put(letter, boxId);
	        		System.out.println(letter + ": " + boxId + " - count: " + characterCount);
				}

				characterCount = 0;
			}
		}
		return checksums;
	}

	private static int calculateTotalChecksum(SetMultimap<Character, String> checksums) {
		Set<Character> keys = checksums.keySet();
	    
	    int count = 0;

	    for (char letter : keys) {
	        Collection<String> values = checksums.get(letter);

	        for (int i = 0; i < values.size(); i++) {
	        	//System.out.println(letter + ": " + i);
	        	count++;
	        }
	    }

	    return count;
	}

	public static void main(String [] args) {
		ArrayList<String> boxIds = readBoxIds();
		SetMultimap<Character, String> checksumsTwo = findChecksum(boxIds, 2);
		SetMultimap<Character, String> checksumsThree = findChecksum(boxIds, 3);

		int totalChecksumTwo = calculateTotalChecksum(checksumsTwo);
		int totalChecksumThree = calculateTotalChecksum(checksumsThree);

		System.out.println("Total Checksum: " + totalChecksumTwo + totalChecksumThree);

	}
}