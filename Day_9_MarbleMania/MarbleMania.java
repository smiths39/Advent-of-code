import java.io.*;
import java.util.*;

 class MarbleList {

  private class Node {
    Node mNext;
    Node mPrev;
    Integer mValue;
  }

  private Node mCurrentNode;

  public MarbleList() {
  }

  public int remove() {
    Node removed = mCurrentNode;
    removed.mPrev.mNext = removed.mNext;
    removed.mNext.mPrev = removed.mPrev;
    mCurrentNode = removed.mNext;
    return removed.mValue;
  }

  public void add(int marble) {
    Node newNode = new Node();
    newNode.mValue = marble;

    if (mCurrentNode == null) {
      mCurrentNode = newNode;
      mCurrentNode.mNext = newNode;
      mCurrentNode.mPrev = newNode;
    } else {
      newNode.mNext = mCurrentNode;
      newNode.mPrev = mCurrentNode.mPrev;
      mCurrentNode.mPrev = newNode;
      newNode.mPrev.mNext = newNode;
      mCurrentNode = newNode;
    }
  }

  public void move(int delta) {
    while (delta > 0) {
      mCurrentNode = mCurrentNode.mNext;
      delta--;
    }

    while (delta < 0) {
      mCurrentNode = mCurrentNode.mPrev;
      delta++;
    }
  }
}

public class MarbleMania {
	private static int playerCount, resultingPoints;

	private static void readGameEntries() {
		try {
            Scanner reader = new Scanner(new File("game_entries.txt"));
            
            String line = "";
            while (reader.hasNext()) {
                line = reader.nextLine();
            }

            playerCount = Integer.parseInt(line.substring(0, line.indexOf('p')-1));
            resultingPoints = Integer.parseInt(line.substring(line.indexOf("worth ") + 6, line.indexOf("points")-1));
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private static long winningScore() {	
	    long[] scores = new long[playerCount];

	    int currentPlayer = 0;

	    MarbleList marbles = new MarbleList();
	    marbles.add(0);

	    for (int marble = 1; marble <= resultingPoints * 100; marble++) {
	      if (marble % 23 == 0) {
	        marbles.move(-7);
	        scores[currentPlayer] += marble + marbles.remove();
	      } else {
	        marbles.move(2);
	        marbles.add(marble);
	      }
	      currentPlayer = (currentPlayer + 1) % playerCount;
	    }

	    return Arrays.stream(scores).max().getAsLong();
	}

	public static void main(String [] args) {
		readGameEntries();
		System.out.println("Winning score: " + winningScore());
	}
}


// var scores = new long[players];
// var circle = new LinkedList<long>();
// var current = circle.AddFirst(0);

// for (int i = 1; i < last; i++)
// {
//     if (i % 23 == 0)
//     {
//         scores[i % players] += i;
//         for (int j = 0; j < 7; j++)
//         {
//             current = current.Previous ?? circle.Last;
//         }
//         scores[i % players] += current.Value;
//         var remove = current;
//         current = remove.Next;
//         circle.Remove(remove);
//     }
//     else
//     {
//         current = circle.AddAfter(current.Next ?? circle.First, i);
//     }
// }

// var answer = scores.Max();