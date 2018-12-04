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
				characterCount = 0;

				for (int i = 0; i < boxId.length(); i++) {
					if (boxId.charAt(i) == letter) {
						characterCount += 1;
					}
				}

				if (characterCount == numEntries && !checksums.containsEntry(letter, boxId)) {
					checksums.put(letter, boxId);
	        		break;
				}
			}
		}
		return checksums;
	}

	private static String findMatchingBoxIds(ArrayList<String> inputData) {
		String boxId = "";
		String nextBoxId = "";
		int mismatchCount = 0;
		int mismatchIndex = 0;

		for (int i = 0; i < inputData.size(); i++) {
			boxId = inputData.get(i);

			for (int j = i+1; j < inputData.size(); j++) {
				nextBoxId = inputData.get(j);

				for (int k = 0; k < boxId.length(); k++) {
					if (boxId.charAt(k) != nextBoxId.charAt(k)) {
						mismatchIndex = k;
						mismatchCount++;
					}
				}

				if (mismatchCount == 1) {
						System.out.println("index: " + mismatchIndex);
						System.out.println("box name:        " + boxId);
						System.out.println("next box name:   " + nextBoxId);

					return removeByIndex(boxId, mismatchIndex);
				}

				mismatchCount = 0;
				mismatchIndex = 0;
			}
		}

		return null;
	}

	 private static String removeByIndex(String str, int index) {
        return str.replaceFirst(String.valueOf(str.charAt(index)), "");
    }

	public static void main(String [] args) {
		ArrayList<String> boxIds = readBoxIds();
		SetMultimap<Character, String> checksumsTwo = findChecksum(boxIds, 2);
		SetMultimap<Character, String> checksumsThree = findChecksum(boxIds, 3);

		int totalChecksumTwo = checksumsTwo.size();
		int totalChecksumThree = checksumsThree.size();

		System.out.println("Total Checksum: " + (totalChecksumTwo * totalChecksumThree));
		System.out.println("Matching Box Id: " + findMatchingBoxIds(boxIds));
	}
}