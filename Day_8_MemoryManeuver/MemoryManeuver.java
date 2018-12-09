import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.nio.file.*;

public class MemoryManeuver {
	private static ArrayList<Integer> allNodes = new ArrayList<>();
	private static int totalMetadata = 0;
	private static int index = 0;

	private static int[] readInput() {
		Path myPath = Paths.get("node_input.txt");
		String[] strArray = new String[1];
		
		try {
			strArray = Files.lines(myPath)
			    .map(s -> s.split(","))
			    .findFirst()
			    .get();
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	}

		return Stream.of(strArray[0].split(" ")).mapToInt(Integer::parseInt).toArray(); 
	}

	private static int depthFirstSearch() {
		int total = 0;
		int currentMetadata;

		int childNodes = allNodes.get(index);
		int metadataEntries = allNodes.get(index + 1);

		index += 2;

		int [] tempNodes = new int[childNodes];

		for (int i = 0; i < childNodes; i++) {
			tempNodes[i] = depthFirstSearch();
		}

		for (int i = 0; i < metadataEntries; i++, index++) {
			currentMetadata = allNodes.get(index);
			totalMetadata += currentMetadata;

			if (childNodes == 0) {
				total += currentMetadata;
			} else if (currentMetadata <= childNodes) {
				total += tempNodes[currentMetadata - 1];
			}
		}

		return total;
	}

	public static void main(String [] args) {		
		int [] nodes = readInput();

		for (int n : nodes) {
			allNodes.add(n);
		}

		int valueRoot = depthFirstSearch();

		System.out.println("Total metadata: " + totalMetadata);
		System.out.println("Root node: " + valueRoot);
	}
}