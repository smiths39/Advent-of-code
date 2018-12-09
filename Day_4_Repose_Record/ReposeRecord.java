import java.io.*;
import java.util.*;
import java.text.*;

class Rota { 
	String guardDesc;
	Calendar date = Calendar.getInstance();

	public Rota(String input) {
  		SimpleDateFormat dateFormat = new SimpleDateFormat("y-M-d k:m");	

  		try {
			this.date.setTime(dateFormat.parse(input.substring(1, 18)));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// action described between 19-24
		switch (input.substring(19,24)) {
			case "falls":
				this.guardDesc = "falls";
				break;

			case "wakes":
				this.guardDesc = "wakes";
				break;

			// guard id
			default:
				this.guardDesc = input.substring(26).split("\\s")[0];
				break;
		}
	}   
}

class Guard {
	String id;
	int sleepTime;
	int [] minute = new int[59];

	public Guard(String id) {
		this.id = id;
	}

	public void fallsAsleep(int min) {
		this.sleepTime = min;
	}

	public void wakesUp(int min) {
		for (int i = sleepTime; i < min; i++) {
			minute[i]++;
		}
	}

	public int sleepTime() {
		int totalTime = 0;

		for (int i = 0; i < minute.length; i++) {
			totalTime += minute[i];
		}
		return totalTime;
	}

	public int mostMinuteSlept() {
		int mostMinute = 0;
		int maxMinuteSlept = 0;

		for (int i = 0; i < minute.length; i++) {
			if (maxMinuteSlept < this.minute[i]) {
				mostMinute = i;
				maxMinuteSlept = this.minute[i];
			}
		}

		return mostMinute;
	}
}

public class ReposeRecord {

	private static ArrayList<Rota> readShiftData() {
		ArrayList<Rota> guardRota = new ArrayList<>();

		try {
            Scanner reader = new Scanner(new File("shift_input.txt"));
            
            while (reader.hasNext()) {
                String line = reader.nextLine();
                guardRota.add(new Rota(line));
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
        return guardRota;
	}

	private static Map<String, Guard> getGuardTimings(ArrayList<Rota> guardRota) {
		Map<String, Guard> allGuards = new HashMap<>(); 
		Guard guard = null;

		for (Rota rota : guardRota) {
			if (!rota.guardDesc.equals("falls") && !rota.guardDesc.equals("wakes")) {
				if (allGuards.containsKey(rota.guardDesc)) {
					guard = allGuards.get(rota.guardDesc);
				} else {
					guard = new Guard(rota.guardDesc);
					allGuards.put(rota.guardDesc, guard);
				}
			}

			if (rota.guardDesc.equals("falls")) {
				guard.fallsAsleep(rota.date.get(Calendar.MINUTE));
			}

			if (rota.guardDesc.equals("wakes")) {
				guard.wakesUp(rota.date.get(Calendar.MINUTE));
			}
		}

		return allGuards;
	}

	private static void guardMostAsleep(Map<String, Guard> allGuards) {
		Guard targetGuard = null;
		
		for (Guard guard : allGuards.values()) {
			if (targetGuard == null || targetGuard.sleepTime() < guard.sleepTime()) {
				targetGuard = guard;
			}
		}	

		System.out.println("\nTarget guard id: " + targetGuard.id);
		System.out.println("Most asleep time: " + targetGuard.mostMinuteSlept());
		System.out.println("Answer 1: " + (Integer.parseInt(targetGuard.id) * targetGuard.mostMinuteSlept()));
	}

	private static void mostFrequentlyAsleepSameMinute(Map<String, Guard> allGuards) {
		Guard targetGuard = null;

		for (Guard guard : allGuards.values()) {
			if (targetGuard == null || targetGuard.minute[targetGuard.mostMinuteSlept()] < guard.minute[guard.mostMinuteSlept()]) {
				targetGuard = guard;
			}
		}

		System.out.println("\nTarget guard id: " + targetGuard.id);
		System.out.println("Most asleep time: " + targetGuard.mostMinuteSlept());
		System.out.println("Answer 2: " + (Integer.parseInt(targetGuard.id) * targetGuard.mostMinuteSlept()));
	}

	public static void main(String [] args) {
		ArrayList<Rota> rota = readShiftData();
		rota.sort(Comparator.comparing(x -> x.date));

		Map<String, Guard> allGuards = getGuardTimings(rota);
		guardMostAsleep(allGuards);
		mostFrequentlyAsleepSameMinute(allGuards);
	}
}