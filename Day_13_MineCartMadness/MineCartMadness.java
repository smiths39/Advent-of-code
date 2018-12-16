import java.io.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

class Cart {
	private Point location;
	private Direction direction;
	private Turn nextTurn;

	private static Direction cartDirection(char c) {
		switch (c) {
			case '^': 	return Direction.NORTH;
			case 'v': 	return Direction.SOUTH;
			case '>': 	return Direction.EAST;
			case '<': 	return Direction.WEST;
			default: 	throw new RuntimeException("Not a cart symbol: " + c);
		}
	}

	public static boolean isCartSymbol(char c) {
		return c == '^' || c == 'v' || c == '>' || c == '<';
	} 

	public static Cart from(Character character, Point location) {
		return new Cart(location, cartDirection(character));
	}

	public Cart(Point location, Direction direction) {
		this.location = location;
		this.direction = direction;
		this.nextTurn = Turn.LEFT;
	}

	public Point getLocation() {
		return location;
	}

	public Direction getDirection() {
		return direction;
	}

	public void advanceCart(List<List<Character>> trackMap) {
		direction.advanceDirection(location);

		char c = trackMap.get(location.y).get(location.x);

		switch (c) {
			case '\\':
			case '/':
				direction = direction.corner(c);
				break;
			case '+':
				direction = direction.turnDirection(nextTurn);
				nextTurn = nextTurn.getNextTurn();
				break;
			default:
				break;
		}
	}
}

enum Direction {
	NORTH(0, -1),
	SOUTH(0, 1),
	EAST(1, 0),
	WEST(-1, 0);

	private final int x, y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void advanceDirection(Point point) {
		point.translate(x, y);
	}

	public Direction corner(char c) {
		switch (this) {
			case NORTH:	return c == '/' ? EAST : WEST;
			case SOUTH:	return c == '/' ? WEST : EAST;
			case EAST:	return c == '/' ? NORTH : SOUTH;
			case WEST:	return c == '/' ? SOUTH : NORTH;
			default:	throw new RuntimeException();
		}
	}

	public Direction turnDirection(Turn turn) {
		switch (this) {
			case NORTH:	return turn == Turn.STRAIGHT ? NORTH : (turn == Turn.RIGHT ? EAST : WEST);
			case SOUTH:	return turn == Turn.STRAIGHT ? SOUTH : (turn == Turn.RIGHT ? WEST : EAST);
			case EAST:	return turn == Turn.STRAIGHT ? EAST  : (turn == Turn.RIGHT ? SOUTH : NORTH);
			case WEST:	return turn == Turn.STRAIGHT ? WEST  : (turn == Turn.RIGHT ? NORTH : SOUTH);
			default:	throw new RuntimeException();
		}
	}
}

enum Turn {
	LEFT,
	STRAIGHT,
	RIGHT;

	public Turn getNextTurn() {
		if (this == LEFT) {
			return STRAIGHT;
		} else if (this == STRAIGHT) {
			return RIGHT;
		} else {
			return LEFT;
		}
	}
}

public class MineCartMadness {
	private static List<List<Character>> readTrackInput() {
		List<List<Character>> trackMap = new ArrayList<>();

		try {
			Scanner scanner = new Scanner(new File("track_input.txt"));

			while (scanner.hasNextLine()) {
				trackMap.add(scanner.nextLine().chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return trackMap;
	}

	private static List<Cart> findCarts(List<List<Character>> trackMap) {
		List<Cart> carts = new ArrayList<>();

		for (int y = 0; y < trackMap.size(); y++) {
			List<Character> row = trackMap.get(y);

			for (int x = 0; x < row.size(); x++) {
				if (Cart.isCartSymbol(row.get(x))) {
					Cart newCart = Cart.from(row.get(x), new Point(x, y));
					carts.add(newCart);

					char startPoint;

					if (newCart.getDirection() == Direction.NORTH || newCart.getDirection() == Direction.SOUTH) {
						startPoint = '|';
					} else {
						startPoint = '-';
					}

					row.set(x, startPoint);
				}
			}
		}

		return carts;
	}

	private static void findCollisionLocation(List<List<Character>> trackMap, List<Cart> carts) {
		boolean notLastCart = false;

		while (!notLastCart) {
			for (int i = 0; i < carts.size(); i++) {
				Cart cart = carts.get(i);
				cart.advanceCart(trackMap);

				Map<Point, List<Cart>> cartsMap = carts.stream().collect(Collectors.groupingBy(Cart::getLocation));

				for (Map.Entry<Point, List<Cart>> entry : cartsMap.entrySet()) {
					if (entry.getValue().size() > 1) {
						System.out.println("Crash occurred at (" + entry.getKey().x + "," + entry.getKey().y + ")");
						carts.removeAll(entry.getValue());
					}
				}
			}

			if (carts.size() == 1) {
				System.out.println("Last car at (" + carts.get(0).getLocation().x + "," + carts.get(0).getLocation().y + ")");
				notLastCart = true;
			} else {
				carts.sort((cart1, cart2) -> {
					Point point1 = cart1.getLocation();
					Point point2 = cart2.getLocation();

					if (point1.y == point2.y) {
						return point1.x - point2.x;
					} else {
						return point1.y - point2.y;
					}
				});
			}
		}
	}

	public static void main(String [] args) {
		List<List<Character>> trackMap = readTrackInput();
		List<Cart> carts = findCarts(trackMap);

		findCollisionLocation(trackMap, carts);
	}
}