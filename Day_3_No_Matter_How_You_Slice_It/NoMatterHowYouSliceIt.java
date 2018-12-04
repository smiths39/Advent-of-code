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

		System.out.println("claim id: " + claimId.get(0));
		System.out.println("left id: " + leftInch.get(0));
		System.out.println("top id: " + topInch.get(0));
		System.out.println("width: " + width.get(0));
		System.out.println("height: " + height.get(0));
	}

	private static String substringBetweenCharacters(String str, String startChar, String endChar) {
		if (endChar.length() == 0) {
			return str.substring(str.indexOf(startChar) + 1, str.length());
		} else {
			return str.substring(str.indexOf(startChar) + 1, str.indexOf(endChar)).trim();
		}
	}

	public static void main(String [] args) {
		ArrayList<String> fabricData = readFabricClaims();

		String str = "#3 @ 5,5: 2x27";
			str = str.substring(str.indexOf("x") + 1, str.length());
			System.out.println(str);

		splitFabricData(fabricData);

	}
}