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

	private static Integer findChecksum(ArrayList<String> inputData) {
		SetMultimap<Character, Integer> checksums = HashMultimap.create();

		int totalChecksum = 0;
		int characterCount = 0;

		for (String boxId : inputData) {
			for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
				for (int i = 0; i < boxId.length(); i++) {
					if (boxId.charAt(i) == alphabet) {
						characterCount += 1;
					}
				}

				if (characterCount > 0) {
					checksums.put(alphabet, characterCount);
				}

				characterCount = 0;
			}
		}

		
System.out.println(checksums); 

		return totalChecksum;
	}

	public static void main(String [] args) {
		ArrayList<String> boxIds = readBoxIds();
		int totalChecksum = findChecksum(boxIds);

		System.out.println("Total Checksum: " + totalChecksum);
	}
}