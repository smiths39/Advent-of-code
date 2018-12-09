import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Rectangle {
    int id;
    int left;
    int top;
    int width;
    int height;

    public Rectangle(String info) {
        String[] split = info.split("\\s");
        this.id = Integer.parseInt(split[0].substring(1));
        String[] split2 = split[2].split(",");
        this.left = Integer.parseInt(split2[0]);
        this.top = Integer.parseInt(split2[1].substring(0,split2[1].length()-1));

        split2 = split[3].split("x");
        this.width = Integer.parseInt(split2[0]);
        this.height = Integer.parseInt(split2[1]);
    }
}

public class Day3b {
    public static void main(String[] args) {
        int[][] fabric = new int[1000][1000];

        try {
            Scanner reader = new Scanner(new File("fabric_input.txt"));

            while (reader.hasNext()) {
                String line = reader.nextLine();
                Rectangle rec = new Rectangle(line);

                for (int i = rec.top; i < rec.top + rec.height; i++) {
                    for (int j = rec.left; j < rec.left + rec.width; j++) {
                        fabric[i][j]++;
                    }
                }
            }
            reader = null;
            reader = new Scanner(new File("fabric_input.txt"));

            while (reader.hasNext()) {
                boolean noOverlap = true;

                String line = reader.nextLine();
                Rectangle rec = new Rectangle(line);

                x: for (int i = rec.top; i < rec.top + rec.height; i++) {
                    for (int j = rec.left; j < rec.left + rec.width; j++) {
                        if (fabric[i][j] > 1) {
                            noOverlap = false;
                            break x;
                        }
                    }
                }

                if (noOverlap) {
                    System.out.println(rec.id);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}