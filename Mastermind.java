import java.util.ArrayList;
import java.util.Scanner;

class Mastermind {
    private ArrayList<State> possibilities;
    private int positionCount, colorCount;

    public Mastermind(int positionCount, int colorCount) {
        possibilities = new ArrayList<State>();
        this.positionCount = positionCount;
        this.colorCount = colorCount;

        initializePossibilities(positionCount-1, colorCount, new int[positionCount]);
    }

    private void initializePossibilities(int positionIndex, int colorCount, int[] positions) {
        if (positionIndex < 0) {
            possibilities.add(new State(positions));
            return;
        }
        for (int color = 0; color < colorCount; color++) {
            positions[positionIndex] = color;
            initializePossibilities(positionIndex-1, colorCount, positions);
        }
    }

    public int getPositionCount() {
        return positionCount;
    }

    public int getColorCount() {
        return colorCount;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        int positionCount = getPositiveInteger(scanner, "Enter position count: ");
        int colorCount = getPositiveInteger(scanner, "Enter color count: ");

        Mastermind game = new Mastermind(positionCount, colorCount);
        
        scanner.close();
    }

    public static int getPositiveInteger(Scanner scanner, String message) {
        System.out.print(message);
        int retVal = -1;
        while (retVal <= 0) {
            try {
                retVal = Integer.parseInt(scanner.nextLine());
            } catch(Exception e) {}
            if (retVal <= 0) {
                System.out.println("Please enter postive integer!");
                System.out.print(message);
            }
        }
        return retVal;
    }

    class State {
        private int[] positions;

        public State(int positionCount) {
            positions = new int[positionCount];
        }

        public State(int[] values) {
            this(values.length);
            for (int i = 0; i < positions.length; i++) positions[i] = values[i];
        }

        public int getPositionValue(int index) {
            return positions[index];
        }
    }
}