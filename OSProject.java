package osproject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Brian Hester
 */
public class OSProject {

    // Generates a well-defined reference string
    // Based on the 90-10 law
    public static ArrayList<Integer> generateWell(int num) {
        Random probRn = new Random();
        Random pageRn = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        int prob, pageNum;

        for (int i = 0; i < num; i++) {
            prob = probRn.nextInt(100);
            // If the random probability generated falls between 0 and 90, 
            // generate a number between 0 and 10. Otherwise, generate a 
            // number between 10 and 100.
            if (0 <= prob && prob < 90) {
                pageNum = pageRn.nextInt(10);
                list.add(pageNum);
            } else {
                pageNum = pageRn.nextInt(90) + 10;
                list.add(pageNum);
            }
        }
        return list;
    }

    // Generates a typically-defined reference string
    // Based on the 80-20 law, or Patero principle
    public static ArrayList<Integer> generateTypical(int num) {
        Random probRn = new Random();
        Random pageRn = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        int prob, pageNum;

        for (int i = 0; i < num; i++) {
            prob = probRn.nextInt(100);
            // If the random probability generated falls between 0 and 80, 
            // generate a number between 0 and 20. Otherwise, generate a 
            // number between 20 and 100
            if (0 <= prob && prob < 80) {
                pageNum = pageRn.nextInt(20);
                list.add(pageNum);
            } else {
                pageNum = pageRn.nextInt(80) + 20;
                list.add(pageNum);
            }
        }
        return list;
    }

    // Generates a typically-defined reference string based on the percentage given
    public static ArrayList<Integer> generateTypicalModified(int num, int percent) {
        Random probRn = new Random();
        Random pageRn = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        int prob, pageNum;

        for (int i = 0; i < num; i++) {
            prob = probRn.nextInt(100);
            // If the random probability generated falls between 0 and percent given, 
            // generate a number between 0 and (100 - percent). Otherwise, generate a 
            // number between (100 - percent) and 100
            if (0 <= prob && prob < percent) {
                pageNum = pageRn.nextInt(100 - percent);
                list.add(pageNum);
            } else {
                pageNum = pageRn.nextInt(percent) + (100 - percent);
                list.add(pageNum);
            }
        }
        return list;
    }

    // Generated a poorly-defined reference string
    // Creates random integers between 0 and 100
    public static ArrayList<Integer> generatePoor(int num) {
        Random pageRn = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        int pageNum;

        for (int i = 0; i < num; i++) {
            pageNum = pageRn.nextInt(100);
            list.add(pageNum);
        }
        return list;
    }

    // FIFO page replacement algorithm
    public static int FIFO(int resSetSize, int num, ArrayList<Integer> refStr) {
        int pageFaults = 0;
        Queue<Integer> resSet = new LinkedList<Integer>();

        for (int i = 0; i < num; i++) {
            int page = refStr.get(i);

            // If the page isn't in the resident set, add page and 
            // increment page fault counter. 
            if (!resSet.contains(page)) {
                // If the resident set is at max size, remove page at top of queue
                if (resSet.size() == resSetSize) {
                    resSet.remove();
                }
                resSet.add(page);
                pageFaults++;
            }
        }
        return pageFaults;
    }

    // LRU page replacement algorithm
    public static int LRU(int resSetSize, int num, ArrayList<Integer> refStr) {
        int pageFaults = 0;
        Queue<Integer> resSet = new LinkedList<Integer>();

        for (int i = 0; i < num; i++) {
            int page = refStr.get(i);

            // If the page isn't in the resident set, add page and 
            // increment page fault counter. 
            if (!resSet.contains(page)) {
                // If the resident set is at max size, remove page at top of queue
                if (resSet.size() == resSetSize) {
                    resSet.remove();
                }
                resSet.add(page);
                pageFaults++;
            } else {
                // If a page is seen that is already in queue, remove it and add it again.
                // This ensures that the least recently used is at top of queue.
                resSet.remove(page);
                resSet.add(page);
            }
        }
        return pageFaults;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int num = 10000;
        int resSetSize;
        ArrayList<Integer> well = generateWell(num);
        ArrayList<Integer> typical = generateTypical(num);
        ArrayList<Integer> poor = generatePoor(num);

        for (int i = 1; i <= 10; i++) {
            resSetSize = i * 10;
            System.out.println("\nResident Set Size: " + resSetSize);

            int pageFaults1 = LRU(resSetSize, num, well);
            int pageFaults2 = LRU(resSetSize, num, typical);
            int pageFaults3 = LRU(resSetSize, num, poor);
            System.out.println("Well LRU: " + pageFaults1);
            System.out.println("Typical LRU: " + pageFaults2);
            System.out.println("Poor LRU: " + pageFaults3);

            int pageFaults4 = FIFO(resSetSize, num, well);
            int pageFaults5 = FIFO(resSetSize, num, typical);
            int pageFaults6 = FIFO(resSetSize, num, poor);
            System.out.println("Well FIFO: " + pageFaults4);
            System.out.println("Typical FIFO: " + pageFaults5);
            System.out.println("Poor FIFO: " + pageFaults6);
        }
    }
}
