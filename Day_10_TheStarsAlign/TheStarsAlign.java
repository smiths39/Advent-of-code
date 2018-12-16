import java.io.*;
import java.util.*;

class Point {
	int xCo, yCo;

	public Point(int xCo, int yCo) {
		this.xCo = xCo;
		this.yCo = yCo;
	}

	@Override
	public boolean equals(Object obj) {
		Point point = (Point) obj;

		if (this.xCo == point.xCo && this.yCo == point.yCo) {
			return true;
		}
		return false;
	}

	@Override
    public int hashCode() {
        return Objects.hash(xCo, yCo);
	}
}

class Velocity {
	int xCo, yCo;

	public Velocity(int xCo, int yCo) {
		this.xCo = xCo;
		this.yCo = yCo;
	}
}

public class TheStarsAlign {
	private static ArrayList<Point> pointsList = new ArrayList<>();
	private static ArrayList<Velocity> velocityList = new ArrayList<>();
 static int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

	private static void readCoordinatesData() {
		int pointX, pointY, velocityX, velocityY;

		try {
            Scanner reader = new Scanner(new File("coordinates_input.txt"));
            
            String line = "";
            while (reader.hasNext()) {
                line = reader.nextLine();

				String[] numbers = line.split("[^-?\\d+| $]");

                pointX = Integer.parseInt(numbers[10].trim());
                pointY = Integer.parseInt(numbers[11].trim());

                pointsList.add(new Point(pointX, pointY));

                velocityX = Integer.parseInt(numbers[22].trim());
                velocityY = Integer.parseInt(numbers[23].trim());

                velocityList.add(new Velocity(velocityX, velocityY));
           }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private static boolean validPoints() {
		HashMap<Integer, Integer> countX = new HashMap<>();
		HashMap<Integer, Integer> countY = new HashMap<>();

		int xCo, yCo;

		for (Point point : pointsList) {
			Integer x = countX.get(point.xCo);
			Integer y = countY.get(point.yCo);

			if (x == null) {
				xCo = 0;
			} else {
				xCo = x;
			}

			if (y == null) {
				yCo = 0;
			} else {
				yCo = y;
			}

			countX.put(point.xCo, xCo + 1);
			countY.put(point.yCo, yCo + 1);
		}

		int maxX = Collections.max(countX.entrySet(), Comparator.comparing(Map.Entry<Integer, Integer>::getValue)).getValue();
		int maxY = Collections.max(countY.entrySet(), Comparator.comparing(Map.Entry<Integer, Integer>::getValue)).getValue();

		return maxX >= 50 || maxY >= 50;
	}

	private static void findMessage() {
		HashSet<Point> pointSet = new HashSet<>();
		int seconds = 0;

		while (!validPoints()) {
			for (int i = 0; i < pointsList.size(); i++) {
				Velocity velocity = velocityList.get(i);
				Point point = pointsList.get(i);

				point.xCo += velocity.xCo;
				point.yCo += velocity.yCo;

				pointsList.set(i, point);
			}
			seconds++;
		}

		for (Point point : pointsList) {
			minX = Math.min(point.xCo, minX);
			minY = Math.min(point.yCo, minY);
			maxX = Math.max(point.xCo, maxX);
			maxY = Math.max(point.yCo, maxY);

			pointSet.add(point);
		}

		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				if (pointSet.contains(new Point(i, j))) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
		System.out.println("Seconds: " + seconds);
	}

	public static void main(String [] args) {
		readCoordinatesData();
		findMessage();
	}
}