import java.io.*;
import java.util.*;
import java.nio.file.*;

class Workers {
	int id, time;

	public Workers(int id, int time) {
		this.id = id;
		this.time = time;
	}
}

public class TheSumOfItsParts {
	private final static int LETTERS_IN_ALPHABET = 26;
	private final static int TOTAL_WORKERS = 5;

	private static List<List<Integer>> graph = new ArrayList<List<Integer>>(LETTERS_IN_ALPHABET);
    private static boolean [] visited = new boolean[LETTERS_IN_ALPHABET];
    private static int [] counts = new int[LETTERS_IN_ALPHABET];
    private static PriorityQueue<Integer> queue = new PriorityQueue<>();

    private static Workers [] workers = new Workers[TOTAL_WORKERS];

    private static void printInstructionOrder() {
        for (int i = 0; i < LETTERS_IN_ALPHABET; i++) {
            if (visited[i] && counts[i] == 0) {
                queue.add(i);
            }
            visited[i] = false;
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print((char) (current + 65));

          	addQueueElements(current);
        }
        System.out.println();
    }

    private static void addQueueElements(int currentWorkerId) {
	  for (Integer i : graph.get(currentWorkerId)) {
            counts[i]--;

            if (counts[i] == 0) {
                queue.add(i);
            }
        }
    }

    private static boolean getAvailableWorkers() {
    	for (int i = 0; i < TOTAL_WORKERS; i++) {
    		if (workers[i].id != -1) {
    			return true;
    		}
    	}
    	return false;
    }

    private static void addWorkerToSchedule(int currentWorkerId) {
    	if (currentWorkerId == -1) {
    		return;
    	}

    	addQueueElements(currentWorkerId);
    }

	private static void checkNonWorking(int currentWorkerId) {
		if (currentWorkerId == TOTAL_WORKERS-1 && !queue.isEmpty()) {
			fillNonWorkers();
		}
	}

	private static void fillNonWorkers() {
		for (int i = 0; i < TOTAL_WORKERS; i++) {
			if (workers[i].id == -1) {
				Integer pulledWorker = queue.poll();
			    if (pulledWorker == null) {
			        workers[i].id = -1;
			        continue;
				}
			    workers[i].id = pulledWorker;
				workers[i].time = pulledWorker + 60;
			}
		}
	}
    
    private static int calculateTimeToComplete() {
    	int totalTime = -1;

        for (int i = 0; i < LETTERS_IN_ALPHABET; i++) {
            if (visited[i] && counts[i] == 0) {
                queue.add(i);
            }
            visited[i] = false;
        }

        for (int i = 0; i < TOTAL_WORKERS; i++) {
        	workers[i] = new Workers(-1, 0);
        }

        while (!queue.isEmpty() || getAvailableWorkers()) {
        	totalTime++;

        	for (int i = 0; i < TOTAL_WORKERS; i++) {
        		if (workers[i].time == 0) {
	        		addWorkerToSchedule(workers[i].id);
					Integer pulledWorker = queue.poll();
				    if (pulledWorker == null) {
				        workers[i].id = -1;
				        continue;
					}
				    workers[i].id = pulledWorker;
					workers[i].time = pulledWorker + 60;

                    checkNonWorking(i);
					continue;
        		}

                workers[i].time--;
				checkNonWorking(i);
        	}
        }

        return totalTime;
    }

    private static void populateGraph(List<String> lines) {
        for (int i = 0; i < LETTERS_IN_ALPHABET; i++) {
	        graph.add(new ArrayList<>());
        }

        for (String line : lines) {
            String[] div = line.split(" ");
            int first = div[1].charAt(0) - 65;
			int second = div[7].charAt(0) - 65;

            visited[first] = true;
            graph.get(first).add(second);

            counts[second]++;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("instructions_input.txt"));
        populateGraph(lines);

        // System.out.print("Instruction order: ");
        // printInstructionOrder();

        System.out.println("Total Time: " + calculateTimeToComplete());
	}
}
