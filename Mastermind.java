import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class Mastermind {
    private ArrayList<State> possibilities;
    private int positionCount, colorCount;

    public enum GameState {
        OK, ERROR_INCORRECT_STATE, ERROR_INCORRECT_POINTS, FOUND_SOLUTION, NO_SOLUTION
    };

    public Mastermind(int positionCount, int colorCount) {
        possibilities = new ArrayList<State>();
        this.positionCount = positionCount;
        this.colorCount = colorCount;

        initializePossibilities(positionCount-1, colorCount, new int[positionCount]);
    }

    private void initializePossibilities(int positionIndex, int colorCount, int[] positions) {
        if (positionIndex < 0) {
            possibilities.add(new State(positions, colorCount));
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

    public State getGuess() {
        int randomIndex = (int) (Math.random()*possibilities.size());
        return possibilities.get(randomIndex);
    }

    public GameState rateGuess(State guess, int positionPoints, int colorPoints) {
        if (possibilities.indexOf(guess) == -1) {
            System.err.println("Specified guess is not possible! (you need to send the object you recieve from getGuess() method)");
            return GameState.ERROR_INCORRECT_STATE;
        }
        if (positionPoints < 0 || colorPoints < 0 || positionPoints + colorPoints > positionCount) {
            System.err.println("Points incorrect! (too small or too high)");
            return GameState.ERROR_INCORRECT_POINTS;
        }

        Iterator possibilitiesIterator = possibilities.iterator();
        while (possibilitiesIterator.hasNext()) {
            State possibility = (State) possibilitiesIterator.next();

            int possibilityColorPoints = 0, possibilityPositionPoints = 0;
            for (int i = 0; i < positionCount; i++) if (possibility.getPositionValue(i) == guess.getPositionValue(i)) possibilityPositionPoints++;
            for (int i = 0; i < colorCount; i++) possibilityColorPoints += min(possibility.getColorCount(i), guess.getColorCount(i));
            possibilityColorPoints -= possibilityPositionPoints;

            if (colorPoints != possibilityColorPoints || positionPoints != possibilityPositionPoints) possibilitiesIterator.remove();
        }

        if (possibilities.size() == 0) return GameState.NO_SOLUTION;
        else if (possibilities.size() == 1) return GameState.FOUND_SOLUTION;

        return GameState.OK;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        int positionCount = getPositiveInteger(scanner, "Enter position count: ");
        int colorCount = getPositiveInteger(scanner, "Enter color count: ");

        Mastermind game = new Mastermind(positionCount, colorCount);
        while (true) {
            State guess = game.getGuess();

            System.out.print("Guess: ");
            for (int i = 0; i < game.positionCount; i++) System.out.print(guess.getPositionValue(i));
            System.out.println("\nRate the guess:");

            GameState gameState;
            do {
                int positionPoints = getNonNegativeInteger(scanner, "\tEnter correct position count:");
                int colorPoints = getNonNegativeInteger(scanner, "\tEnter correct color count:");
    
                gameState = game.rateGuess(guess, positionPoints, colorPoints);
            } while (gameState == GameState.ERROR_INCORRECT_POINTS);
            
            if (gameState == GameState.NO_SOLUTION) {
                System.out.println("There is no solution.");
                break;
            } else if (gameState == GameState.FOUND_SOLUTION) {
                System.out.println("Found solution!");
                State solution = game.getGuess();
                System.out.print("\tSolution is ");
                for (int i = 0; i < game.positionCount; i++) System.out.print(solution.getPositionValue(i));
                System.out.println(".");
                break;
            }
        }
        
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

    public static int getNonNegativeInteger(Scanner scanner, String message) {
        System.out.print(message);
        int retVal = -1;
        while (retVal < 0) {
            try {
                retVal = Integer.parseInt(scanner.nextLine());
            } catch(Exception e) {}
            if (retVal < 0) {
                System.out.println("Please enter postive integer!");
                System.out.print(message);
            }
        }
        return retVal;
    }

    public static int min(int a, int b) {
        return (a<b)?a:b;
    }

    class State {
        private int[] positions;
        private int[] colors;

        public State(int[] values, int colorCount) {
            positions = new int[values.length];
            colors = new int[colorCount];
            for (int i = 0; i < positions.length; i++) positions[i] = values[i];
            for (int i = 0; i < positions.length; i++) colors[positions[i]]++;
        }

        public int getPositionValue(int index) {
            return positions[index];
        }

        public int getColorCount(int color) {
            return colors[color];
        }
    }
}